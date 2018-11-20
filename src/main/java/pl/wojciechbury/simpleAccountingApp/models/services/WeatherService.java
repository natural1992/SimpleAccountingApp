package pl.wojciechbury.simpleAccountingApp.models.services;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.wojciechbury.simpleAccountingApp.models.dtos.WeatherDto;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class WeatherService {

    @Value("${api.key}")
    String API_KEY;

    final private Gson gson;

    @Autowired
    public WeatherService(Gson gson){
        this.gson = gson;
    }

    public WeatherDto loadWeatherFor(final String cityName){

        WeatherDto weatherDto = convertJsonToCurrentWeather(readWebsite("https://api.openweathermap.org/data/2.5/weather?q="
                + cityName
                + "&appid="
                + API_KEY));

        return weatherDto;
    }

    private WeatherDto convertJsonToCurrentWeather(String json){
        return gson.fromJson(json, WeatherDto.class);
    }

    private String readWebsite(String url){
        StringBuilder content = new StringBuilder();
        HttpURLConnection http;

        try{
            http = (HttpURLConnection) new URL(url).openConnection();
            http.setRequestMethod("GET");                                           //It is "GET" by default
            InputStream inputStream = http.getInputStream();

            int data;
            while((data = inputStream.read()) != -1){
                content.append((char) data);
            }

            inputStream.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        return content.toString();
    }
}
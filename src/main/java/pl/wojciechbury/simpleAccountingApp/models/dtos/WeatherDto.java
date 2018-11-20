package pl.wojciechbury.simpleAccountingApp.models.dtos;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class WeatherDto {

    @SerializedName("main")
    private TempDto tempDto;

    @SerializedName("clouds")
    private CloudsDto cloudsDto;

    @SerializedName("id")
    private int id;

    public TempDto getTempDto(){
        return tempDto;
    }

    public CloudsDto getCloudsDto(){
        return cloudsDto;
    }

    public int getId() {
        return id;
    }

    public static class TempDto {
        @SerializedName("temp")
        private double temperature;

        public double getTemperature() {
            return temperature;
        }

        public void setTemperature(double temperature) {
            this.temperature = temperature;
        }
    }

    public static class CloudsDto{
        @SerializedName("all")
        private double clouds;

        public double getClouds(){
            return clouds;
        }

        public void setClouds(Double clouds){
            this.clouds = clouds;
        }
    }
}
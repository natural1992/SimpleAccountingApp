package pl.wojciechbury.simpleAccountingApp.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pl.wojciechbury.simpleAccountingApp.models.services.ApiService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Configuration
public class LoginInterceptor extends HandlerInterceptorAdapter {

    final UserSession userSession;
    final ApiService apiService;
    public static final String PASSWORD_HEADER_API = "api-key";

    @Autowired
    public LoginInterceptor(UserSession userSession, ApiService apiService){
        this.userSession = userSession;
        this.apiService = apiService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getRequestURI().contains("register") || request.getRequestURI().contains("login") || userSession.isLoggedIn()){
            return super.preHandle(request, response, handler);
        }
        if(request.getRequestURI().contains("api")){
            String apiKey = request.getHeader(PASSWORD_HEADER_API);

            if(!(apiKey == null) && apiService.checkKey(apiKey) &&
                    request.getRequestURI().contains(apiService.getLoginByApiKey(apiKey))){

                return super.preHandle(request, response, handler);
            }
            response.sendError(403, "Bad api key");

            return false;
        }

        response.sendRedirect("/user/login");
        return false;
    }
}
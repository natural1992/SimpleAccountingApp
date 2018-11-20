package pl.wojciechbury.simpleAccountingApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.wojciechbury.simpleAccountingApp.models.UserSession;
import pl.wojciechbury.simpleAccountingApp.models.dtos.WeatherDto;
import pl.wojciechbury.simpleAccountingApp.models.forms.UserForm;
import pl.wojciechbury.simpleAccountingApp.models.services.NoteService;
import pl.wojciechbury.simpleAccountingApp.models.services.UserService;
import pl.wojciechbury.simpleAccountingApp.models.services.WeatherService;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
public class UserController {

    final UserService userService;
    final UserSession userSession;
    final WeatherService weatherService;
    final NoteService noteService;

    @Autowired
    public UserController(UserSession userSession, UserService userService, WeatherService weatherService, NoteService noteService){
        this.userService = userService;
        this.userSession = userSession;
        this.weatherService = weatherService;
        this.noteService = noteService;
    }

    @GetMapping("/user")
    public String showUserScreen(Model model){
        if(!userSession.isLoggedIn()){
            return "redirect:/user/login";
        }
        WeatherDto weather = weatherService.loadWeatherFor(userSession.getUserEntity().getCity());

        model.addAttribute("login", userSession.getUserEntity().getLogin());
        model.addAttribute("notes", noteService.getListOfNotesForToday());
        model.addAttribute("weather", (int) weather.getTempDto().getTemperature());
        model.addAttribute("clouds", (int) weather.getCloudsDto().getClouds());
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("city", userSession.getUserEntity().getCity());

        return "userScreen";
    }

    @PostMapping("/user")
    public String getUserScreen(){
        return "userScreen";
    }

    @GetMapping("/user/register")
    public String showRegisterForm(Model model){
        model.addAttribute("userForm", new UserForm());

        return "registration";
    }

    @PostMapping("/user/register")
    public String getRegisterForm(Model model, @ModelAttribute @Valid UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("registrationInfo", "Login should consist only small, big letters and numbers");
            return "registration";
        }
        boolean isSuchLogin = userService.isSuchLogin(userForm.getLogin());

        if(!isSuchLogin){
            userService.addUser(userForm);
            userService.tryLogin(userForm);

            return "redirect:/user";
        }else{
            model.addAttribute("registrationInfo", "This login is already taken");
            return "registration";
        }
    }

    @GetMapping("/user/login")
    public String showUserLogin(Model model){
        model.addAttribute("user", new UserForm());
        return "login";
    }

    @PostMapping("/user/login")
    public String getUserLogin(Model model, @ModelAttribute UserForm userForm){
        boolean didItLog = userService.tryLogin(userForm);

        if(didItLog){
            return "redirect:/user";
        }

        model.addAttribute("loginInfo", "Login or password are incorrect");
        return "login";
    }

    @GetMapping("/user/configuration")
    public String showUserConfiguration(Model model){
        model.addAttribute("user", userSession.getUserEntity());

        return "userConfiguration";
    }

    @GetMapping("/user/configuration/{var}")
    public String changeUserConfiguration(@PathVariable("var") String option){
        if(!userSession.isLoggedIn()){
            return "redirect:/user/login";
        }
        if(option.equals("vatFalse")){
            userSession.getUserEntity().setVatPayer(false);

            return "redirect/user/configuration";
        }else if(option.equals("vatTrue")){
            userSession.getUserEntity().setVatPayer(true);

            return "redirect/user/configuration";
        }else if(option.equals("linearIncomeFalse")){
            userSession.getUserEntity().setLinearIncomeTaxPayer(false);

            return "redirect/user/configuration";
        }else if(option.equals("linearIncomeTrue")){
            userSession.getUserEntity().setVatPayer(true);

            return "redirect/user/configuration";
        }

        return "redirect:/user/configuration";
    }
}

package pl.wojciechbury.simpleAccountingApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import pl.wojciechbury.simpleAccountingApp.models.forms.NoteForm;
import pl.wojciechbury.simpleAccountingApp.models.services.ApiService;

@Controller
public class ApiController {

    final ApiService apiService;

    @Autowired
    public ApiController(ApiService apiService){
        this.apiService = apiService;
    }

    @GetMapping("/api/key")
    public String showApiKey(){
        return "apiKey";
    }

    @GetMapping("/api/key/get")
    public String getApiKey(Model model){
        model.addAttribute("key", apiService.getNewKey());

        return "apiKey";
    }

    @GetMapping("/api/get/notes/{login}")
    public ResponseEntity getNotesForUser(@PathVariable("login") String login){
        return apiService.getListOfNotes(login);
    }

    @PostMapping(value = "/api/add/notes/{login}", consumes = "application/json")
    public ResponseEntity addNoteForUser(@PathVariable("login") String login, @RequestBody NoteForm noteForm){
        return apiService.addNote(noteForm, login);
    }
}

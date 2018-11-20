package pl.wojciechbury.simpleAccountingApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.wojciechbury.simpleAccountingApp.models.forms.NoteForm;
import pl.wojciechbury.simpleAccountingApp.models.services.NoteService;

@Controller
public class NoteController {

    final NoteService noteService;

    @Autowired
    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }

    @GetMapping("/note/create")
    public String showNewNote(Model model){
        model.addAttribute("noteForm", new NoteForm());

        return "createNote";
    }

    @PostMapping("/note/create")
    public String getNewNote(Model model, @ModelAttribute NoteForm noteForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            model.addAttribute("noteInfo", "date or priority is incorrect");
        }
        noteService.addNote(noteForm);

        return "redirect:/";
    }
}

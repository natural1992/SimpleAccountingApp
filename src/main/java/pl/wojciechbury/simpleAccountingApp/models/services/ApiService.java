package pl.wojciechbury.simpleAccountingApp.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.wojciechbury.simpleAccountingApp.models.UserSession;
import pl.wojciechbury.simpleAccountingApp.models.entities.ApiKeyEntity;
import pl.wojciechbury.simpleAccountingApp.models.entities.NoteEntity;
import pl.wojciechbury.simpleAccountingApp.models.entities.UserEntity;
import pl.wojciechbury.simpleAccountingApp.models.forms.NoteForm;
import pl.wojciechbury.simpleAccountingApp.models.repositories.ApiKeyRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ApiService {

    final ApiKeyRepository apiKeyRepository;
    final UserSession userSession;
    final NoteService noteService;
    final TransferService transferService;
    final UserService userService;

    @Autowired
    public ApiService(ApiKeyRepository apiKeyRepository, UserSession userSession, NoteService noteService, TransferService transferService, UserService userService){
        this.apiKeyRepository = apiKeyRepository;
        this.userSession = userSession;
        this.noteService = noteService;
        this.transferService = transferService;
        this.userService = userService;
    }

    public String getNewKey() {
        String newKey = UUID.randomUUID().toString();
        apiKeyRepository.save(new ApiKeyEntity(newKey, userSession.getUserEntity()));
        return newKey;
    }

    public boolean checkKey(String key){
        return apiKeyRepository.existsByKey(key);
    }

    public ResponseEntity getListOfNotes(String login) {
        List<NoteEntity> notes = noteService.getListOfNotes(login);

        return ResponseEntity.ok(notes);
    }

    public ResponseEntity addNote(NoteForm noteForm, String login){
        userService.getUserByLogin(login);
        noteService.addNote(noteForm, userService.getUserByLogin(login));

        return ResponseEntity.ok().body("Note added");
    }

    public String getLoginByApiKey(String key){
        return apiKeyRepository.findLoginByApiKey(key);
    }
}

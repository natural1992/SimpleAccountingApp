package pl.wojciechbury.simpleAccountingApp.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojciechbury.simpleAccountingApp.models.UserSession;
import pl.wojciechbury.simpleAccountingApp.models.forms.NoteForm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "note")
@NoArgsConstructor
public class NoteEntity {

    @GeneratedValue
    @Id
    private int id;

    private int user_id, priority;
    private String title, text;
    private LocalDate noteDate;

    public NoteEntity(NoteForm noteForm, UserSession userSession, LocalDate noteDate) {
        this.user_id = userSession.getUserEntity().getId();
        this.priority = Integer.valueOf(noteForm.getPriority());
        this.title = noteForm.getTitle();
        this.text = noteForm.getNoteText();
        this.noteDate = noteDate;
    }
}

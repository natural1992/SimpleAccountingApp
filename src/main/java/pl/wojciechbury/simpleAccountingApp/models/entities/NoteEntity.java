package pl.wojciechbury.simpleAccountingApp.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojciechbury.simpleAccountingApp.models.UserSession;
import pl.wojciechbury.simpleAccountingApp.models.forms.NoteForm;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "note")
@NoArgsConstructor
public class NoteEntity {

    @GeneratedValue
    @Id
    private int id;
    @Column(name = "user_id")
    private int userId;
    private int priority;
    private String title, text;
    private LocalDate noteDate;

    public NoteEntity(NoteForm noteForm, UserEntity userEntity, LocalDate noteDate) {
        this.userId = userEntity.getId();
        this.priority = Integer.valueOf(noteForm.getPriority());
        this.title = noteForm.getTitle();
        this.text = noteForm.getNoteText();
        this.noteDate = noteDate;
    }
}

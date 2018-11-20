package pl.wojciechbury.simpleAccountingApp.models.forms;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class NoteForm {
    @Pattern(regexp = "[0-9]")
    private String priority;

    private String title, noteText;

    @Pattern(regexp = "[0-9]{4}-[0-1][0-9]-[0-3][0-9]")
    private String noteDate;
}

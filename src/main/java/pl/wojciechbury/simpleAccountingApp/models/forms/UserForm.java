package pl.wojciechbury.simpleAccountingApp.models.forms;

import lombok.Data;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UserForm {

    @Pattern(regexp = "[a-zA-Z0-9]{3,25}")
    private String login;

    @Size(min = 5, max = 60)
    private String password;

    private boolean isVatPayer, isLinearIncomeTaxPayer;
}

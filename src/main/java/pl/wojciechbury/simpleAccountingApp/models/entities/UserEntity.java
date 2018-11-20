package pl.wojciechbury.simpleAccountingApp.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojciechbury.simpleAccountingApp.models.forms.UserForm;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
public class UserEntity {

    @Id
    @GeneratedValue
    private int id;

    private String login;
    private String password;
    private String city;
    private boolean isVatPayer;
    private boolean isLinearIncomeTaxPayer;

    public UserEntity(UserForm userForm){
        this.login = userForm.getLogin();
        this.password = userForm.getPassword();
        this.isVatPayer = userForm.isVatPayer();
        this.isLinearIncomeTaxPayer = userForm.isLinearIncomeTaxPayer();
        this.city = userForm.getCity();
    }
}

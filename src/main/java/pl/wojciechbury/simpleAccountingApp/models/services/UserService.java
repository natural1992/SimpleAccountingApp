package pl.wojciechbury.simpleAccountingApp.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.wojciechbury.simpleAccountingApp.models.UserSession;
import pl.wojciechbury.simpleAccountingApp.models.entities.UserEntity;
import pl.wojciechbury.simpleAccountingApp.models.forms.UserForm;
import pl.wojciechbury.simpleAccountingApp.models.repositories.UserRepository;

@Service
public class UserService {

    final UserRepository userRepository;
    final PasswordHashingService passwordHashingService;
    final UserSession userSession;

    @Autowired
    public UserService(UserRepository userRepository, PasswordHashingService passwordHashingService, UserSession userSession){
        this.userRepository = userRepository;
        this.passwordHashingService = passwordHashingService;
        this.userSession = userSession;
    }

    public void addUser(UserForm userForm){
        userForm.setPassword(passwordHashingService.hash(userForm.getPassword()));
        UserEntity newUser = new UserEntity(userForm);
        userRepository.save(newUser);
    }

    public boolean isSuchLogin(String login){
        return userRepository.existsByLogin(login);
    }

    public boolean tryLogin(UserForm userForm){
        if(userRepository.existsByLogin(userForm.getLogin())){
            UserEntity currentUser = userRepository.findUserByLogin(userForm.getLogin()).get();

            if(passwordHashingService.matches(userForm.getPassword(), currentUser.getPassword())){
                userSession.setLoggedIn(true);
                userSession.setUserEntity(currentUser);

                return true;
            }
        }

        return false;
    }
}

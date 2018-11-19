package pl.wojciechbury.simpleAccountingApp.models;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import pl.wojciechbury.simpleAccountingApp.models.entities.UserEntity;

@Component
@Scope(scopeName = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class UserSession {
    private boolean isLoggedIn;
    private UserEntity userEntity;
}

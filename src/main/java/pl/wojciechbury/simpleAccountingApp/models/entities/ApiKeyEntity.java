package pl.wojciechbury.simpleAccountingApp.models.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.wojciechbury.simpleAccountingApp.models.UserSession;

import javax.persistence.*;

@Entity
@Table(name = "api_key")
@Data
@NoArgsConstructor
public class ApiKeyEntity {
    @GeneratedValue
    @Id
    private int id;
    @Column(name = "user_id")
    private int userId;
    private String apiKey;

    public ApiKeyEntity(String apiKey, UserEntity userEntity){
        this.apiKey = apiKey;
        userId = userEntity.getId();
    }
}

package pl.wojciechbury.simpleAccountingApp.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechbury.simpleAccountingApp.models.entities.ApiKeyEntity;

@Repository
public interface ApiKeyRepository extends CrudRepository<ApiKeyEntity, Integer> {
    @Query(nativeQuery = true, value = "SELECT IF( EXISTS (SELECT * FROM api_key WHERE api_key = ?1) , 'true', 'false')")
    boolean existsByKey(String key);

    @Query(nativeQuery = true, value = "SELECT login FROM user JOIN api_key ON user_id = user.id WHERE api_key = ?1")
    String findLoginByApiKey(String key);
}

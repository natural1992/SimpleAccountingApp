package pl.wojciechbury.simpleAccountingApp.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechbury.simpleAccountingApp.models.entities.TransferEntity;

@Repository
public interface TransactionRepository extends CrudRepository<TransferEntity, Integer> {
}

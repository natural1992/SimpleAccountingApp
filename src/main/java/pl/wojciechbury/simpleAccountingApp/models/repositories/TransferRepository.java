package pl.wojciechbury.simpleAccountingApp.models.repositories;

import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechbury.simpleAccountingApp.models.entities.TransferEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransferRepository extends CrudRepository<TransferEntity, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT * FROM transfer WHERE user_id = ?1 ORDER BY date DESC",
            countQuery = "SELECT COUNT(*) FROM transfer WHERE user_id = ?1 ORDER BY date DESC")
    Page<TransferEntity> findAllByUserId(int id, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * FROM transfer WHERE user_id = ?1 AND date BETWEEN ?2 AND ?3 ORDER BY date DESC",
            countQuery = "SELECT COUNT(*) FROM transfer WHERE user_id = ?1 AND date BETWEEN ?2 AND ?3 ORDER BY date DESC")
    Page<TransferEntity> findByUserIdBetweenDates(int id, LocalDate beginning, LocalDate ending, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM transfer WHERE user_id = ?1 AND date BETWEEN ?2 AND ?3")
    List<TransferEntity> findTransfersForUserForGivenTime(int id, LocalDate beginning, LocalDate ending);
}

package pl.wojciechbury.simpleAccountingApp.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.wojciechbury.simpleAccountingApp.models.entities.NoteEntity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<NoteEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT text, title, priority, note.id, user_id, note_date FROM note JOIN user ON note.user_id = user.id WHERE login = ?1 AND note_date = ?2")
    List<NoteEntity> noteListForToday(String login, LocalDate currentDate);

    @Query(nativeQuery = true, value = "SELECT * FROM note JOIN user ON note.user_id = user.id WHERE login = ?1")
    List<NoteEntity> getNotesByUserLogin(String login);
}

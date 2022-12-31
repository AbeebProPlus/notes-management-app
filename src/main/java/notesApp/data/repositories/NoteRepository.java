package notesApp.data.repositories;

import notesApp.data.models.Note;
import notesApp.data.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    Note findNoteById(String noteId);
}

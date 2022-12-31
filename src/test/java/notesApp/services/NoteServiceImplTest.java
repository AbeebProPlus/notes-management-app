package notesApp.services;

import notesApp.data.repositories.NoteRepository;
import notesApp.dtos.requests.CreateNoteRequest;
import notesApp.dtos.requests.DeleteNoteRequest;
import notesApp.dtos.requests.EditNoteRequest;
import notesApp.dtos.responses.CreateNoteResponse;
import notesApp.dtos.responses.DeleteNoteResponse;
import notesApp.dtos.responses.EditNoteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class NoteServiceImplTest {
    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteRepository noteRepository;
    private CreateNoteRequest createNoteRequest;
    private EditNoteRequest editNoteRequest;
    private DeleteNoteRequest deleteNoteRequest;

    @BeforeEach
    void setUp(){
        createNoteRequest = new CreateNoteRequest();
        editNoteRequest = new EditNoteRequest();
        deleteNoteRequest = new DeleteNoteRequest();

        createNoteRequest.setUserName("mac8ver26");
        createNoteRequest.setTitle("This is a title");
        createNoteRequest.setContent("This is a content");
        createNoteRequest.setEditStatus("");

        editNoteRequest.setNoteId("63a72b79ef475662cd7b9c6f");
        editNoteRequest.setTitle("Edited note title");
        editNoteRequest.setContent("Edited note content");
        editNoteRequest.setUserName(createNoteRequest.getUserName());

        deleteNoteRequest.setNoteId("63ac6b55980baa40273a20f7");
        deleteNoteRequest.setUserName("mac8ver26");
    }

    @Test
    @DisplayName("Test that a registered user can create a note")
    void testThatANoteCanBeCreated(){
        CreateNoteResponse response = noteService.createNote(createNoteRequest);
        assertEquals(201, response.getStatusCode());
    }
    @Test
    @DisplayName("Test that a user can edit a note")
    void testThatANoteCanBeEdited(){
        EditNoteResponse response = noteService.editNote(editNoteRequest);
        assertEquals(201, response.getStatusCode());
    }
//    @Test
//    @DisplayName("Test that a user can delete a note")
//    void testThatANoteCanBeDeleted(){
//        DeleteNoteResponse response = noteService.deleteNote(deleteNoteRequest);
//        assertEquals(201, response.getStatusCode());
//        assertNull(noteRepository.findNoteById("63a72b79ef475662cd7b9c6f"));
//    }
}

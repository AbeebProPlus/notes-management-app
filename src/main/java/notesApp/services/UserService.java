package notesApp.services;

import notesApp.data.models.Note;
import notesApp.dtos.requests.FindUserNoteByTitleRequest;
import notesApp.dtos.requests.UserLoginRequest;
import notesApp.dtos.requests.UserRegistrationRequest;
import notesApp.dtos.responses.UserLoginResponse;
import notesApp.dtos.responses.UserRegistrationResponse;

import java.util.List;

public interface UserService {
    UserRegistrationResponse register(UserRegistrationRequest registrationRequest);
    UserLoginResponse login(UserLoginRequest loginRequest);
    void addNote(String userName, Note note);
    List<Note> getAllNotes(String userName);
    void deleteNote(String userName, Note note);
    List<Note> getUserNotesByTitle(FindUserNoteByTitleRequest findUserNoteByTitleRequest);
}

package notesApp.services;

import notesApp.exceptions.NoteException;
import notesApp.data.models.Note;
import notesApp.data.repositories.NoteRepository;
import notesApp.dtos.requests.CreateNoteRequest;
import notesApp.dtos.requests.DeleteNoteRequest;
import notesApp.dtos.requests.EditNoteRequest;
import notesApp.dtos.responses.CreateNoteResponse;
import notesApp.dtos.responses.DeleteNoteResponse;
import notesApp.dtos.responses.EditNoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;


@Service
public class NoteServiceImpl implements NoteService{
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private UserService userService;

    @Override
    public CreateNoteResponse createNote(CreateNoteRequest createNoteRequest){
        Note note = buildNote(createNoteRequest);
        Note savedNote = noteRepository.save(note);
        userService.addNote(createNoteRequest.getUserName(), savedNote);
        CreateNoteResponse createNoteResponse = buildNoteResponse();
        return createNoteResponse;
    }

    @Override
    public DeleteNoteResponse deleteNote(DeleteNoteRequest deleteNoteRequest) {
        Note foundNote = noteRepository.findNoteById(deleteNoteRequest.getNoteId());
        userService.deleteNote(deleteNoteRequest.getUserName(), foundNote);
        noteRepository.delete(foundNote);
        DeleteNoteResponse deleteNoteResponse = buildDeleteNoteResponse();
        return deleteNoteResponse;
    }

    @Override
    public EditNoteResponse editNote(EditNoteRequest editNoteRequest) {
        Note foundNote = edit(editNoteRequest);
        Note editedNote = noteRepository.save(foundNote);
        List<Note> notes = userService.getAllNotes(editNoteRequest.getUserName());
        for (int i = 0; i < notes.size(); i++){
            if (Objects.equals(notes.get(i).getId(), foundNote.getId())){
                notes.set(i, editedNote);
            }
        }
        EditNoteResponse editNoteResponse = buildEditNoteResponse();
        return editNoteResponse;
    }

    private Note edit(EditNoteRequest editNoteRequest){
        Note foundNote = noteRepository.findNoteById(editNoteRequest.getNoteId());
        if (!Objects.equals(editNoteRequest.getTitle(), "")) foundNote.setTitle(editNoteRequest.getTitle());
        if (!Objects.equals(editNoteRequest.getContent(), "")) foundNote.setContent(editNoteRequest.getContent());
        foundNote.setEditStatus("Last edited");
        foundNote.setEditDate(String.valueOf(LocalDate.now()));
        foundNote.setEditTime(String.valueOf(LocalTime.now()));
        return foundNote;
    }
    private Note buildNote(CreateNoteRequest createNoteRequest){
        Note note = new Note();
        if (!Objects.equals(createNoteRequest.getTitle(), "")) {
            note.setTitle(createNoteRequest.getTitle());
        }else{
            throw new NoteException("Title cannot be empty");
        }
        if (!Objects.equals(createNoteRequest.getContent(), "")) {
            note.setContent(createNoteRequest.getContent());
        }else {
            throw new NoteException("Content cannot be empty");

        }
        return note;
    }
    private CreateNoteResponse buildNoteResponse(){
        CreateNoteResponse response = new CreateNoteResponse();
        response.setStatusCode(200);
        response.setMessage("Note created successfully");
        response.setCreationDate(String.valueOf(LocalDate.now()));
        response.setCreationTime(String.valueOf(LocalTime.now()));
        return response;
    }

    private DeleteNoteResponse buildDeleteNoteResponse(){
        DeleteNoteResponse deleteNoteResponse = new DeleteNoteResponse();
        deleteNoteResponse.setStatusCode(200);
        deleteNoteResponse.setMessage("Note deleted succesfully");
        return deleteNoteResponse;
    }
    private EditNoteResponse buildEditNoteResponse(){
        EditNoteResponse editNoteResponse = new EditNoteResponse();
        editNoteResponse.setStatusCode(200);
        editNoteResponse.setMessage("Note edited succcessfully");
        return editNoteResponse;
    }
}

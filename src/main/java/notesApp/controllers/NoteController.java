package notesApp.controllers;

import notesApp.dtos.requests.CreateNoteRequest;
import notesApp.dtos.requests.DeleteNoteRequest;
import notesApp.dtos.requests.EditNoteRequest;
import notesApp.dtos.responses.CreateNoteResponse;
import notesApp.dtos.responses.DeleteNoteResponse;
import notesApp.dtos.responses.EditNoteResponse;
import notesApp.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @PostMapping("/create_note")
    public CreateNoteResponse createNote(@RequestBody CreateNoteRequest createNoteRequest){
        return noteService.createNote(createNoteRequest);
    }
    @DeleteMapping("/delete_note")
    public DeleteNoteResponse deleteNote(@RequestBody DeleteNoteRequest deleteNoteRequest){
        return noteService.deleteNote(deleteNoteRequest);
    }
    @PatchMapping("/patch_note")
    public EditNoteResponse editNote(@RequestBody EditNoteRequest editNoteRequest){
        return noteService.editNote(editNoteRequest);
    }
}
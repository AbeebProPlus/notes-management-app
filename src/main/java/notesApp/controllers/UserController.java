package notesApp.controllers;

import notesApp.data.models.Note;
import notesApp.dtos.requests.FindUserNoteByTitleRequest;
import notesApp.dtos.requests.UserLoginRequest;
import notesApp.dtos.requests.UserRegistrationRequest;
import notesApp.dtos.responses.UserLoginResponse;
import notesApp.dtos.responses.UserRegistrationResponse;
import notesApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserRegistrationResponse register(@RequestBody UserRegistrationRequest registrationRequest){
        return userService.register(registrationRequest);
    }
    @PostMapping("/login")
    public UserLoginResponse login(@RequestBody UserLoginRequest loginRequest){
        return userService.login(loginRequest);
    }
    @GetMapping("/notes/{userName}")
    public List<Note> getAllNotes(@PathVariable String userName){
        return userService.getAllNotes(userName);
    }

    @PostMapping("/noteTitle")
    public List<Note> findNotesByTitle(@RequestBody FindUserNoteByTitleRequest findUserNoteByTitleRequest){
        return userService.getUserNotesByTitle(findUserNoteByTitleRequest);
    }
}

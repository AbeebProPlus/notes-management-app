package notesApp.services;

import notesApp.exceptions.*;
import notesApp.data.models.Note;
import notesApp.data.models.User;
import notesApp.data.repositories.UserRepository;
import notesApp.dtos.requests.FindUserNoteByTitleRequest;
import notesApp.dtos.requests.UserLoginRequest;
import notesApp.dtos.requests.UserRegistrationRequest;
import notesApp.dtos.responses.UserLoginResponse;
import notesApp.dtos.responses.UserRegistrationResponse;
import notesApp.validations.Validation;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserRegistrationResponse register(UserRegistrationRequest registrationRequest){
        User builtUser = buildUser(registrationRequest);
        userRepository.save(builtUser);
        UserRegistrationResponse registrationResponse = buildResponse();
        return registrationResponse;
    }

    @Override
    public UserLoginResponse login(UserLoginRequest loginRequest){
        UserLoginResponse userLoginResponse = new UserLoginResponse();
        User foundUser = userRepository.findByEmail(loginRequest.getEmail().toLowerCase());
        if (foundUser == null){
            userLoginResponse.setStatusCode(401);
            userLoginResponse.setMessage("Authentication failed");
            throw new UserLoginException("User not found");
        }
        authenticateUser(loginRequest, userLoginResponse, foundUser);
        return userLoginResponse;
    }
    @Override
    public void addNote(String userName, Note note) {
        User foundUser = userRepository.findByUserName(userName);
        foundUser.getNotes().add(note);
        userRepository.save(foundUser);
    }

    @Override
    public void deleteNote(String userName, Note note){
        User foundUser = userRepository.findByUserName(userName);
        foundUser.getNotes().remove(note);
        userRepository.save(foundUser);
    }

    @Override
    public List<Note> getUserNotesByTitle(FindUserNoteByTitleRequest findUserNoteByTitleRequest) {
        User foundUser = userRepository.findByUserName(findUserNoteByTitleRequest.getUserName());
        String title = findUserNoteByTitleRequest.getNoteTitle();
        List<Note> foundNotes = foundUser.getNotes();
        List<Note> newNotes = findNotes(title, foundNotes);
        return newNotes;
    }
    private List<Note> findNotes(String title, List<Note> foundNotes) {
        List<Note> newNotes = new ArrayList<>();
        for (Note note: foundNotes){
            if (note.getTitle().toLowerCase().contains(title.toLowerCase().subSequence(0, 1))){
                newNotes.add(note);
            }
        }
        return newNotes;
    }

    @Override
    public List<Note> getAllNotes(String userName) {
        User foundUser = userRepository.findByUserName(userName);
        return foundUser.getNotes();
    }


    private void authenticateUser(UserLoginRequest loginRequest, UserLoginResponse userLoginResponse, User foundUser) {
        if (confirmPassword(loginRequest.getPassword(), foundUser.getPassword())){
            userLoginResponse.setStatusCode(201);
            userLoginResponse.setUserName(foundUser.getUserName());
            userLoginResponse.setMessage("You're logged in");
        }else {
            userLoginResponse.setStatusCode(401);
            userLoginResponse.setMessage("Authentication failed");
            throw new UserLoginException("Incorrect password");
        }
    }

    private User buildUser(UserRegistrationRequest registrationRequest){
        User savedUser = userRepository.findByUserName(registrationRequest.getUserName());
        User savedUser2 = userRepository.findByEmail(registrationRequest.getEmail().toLowerCase());
        if (savedUser != null) throw new ExistingUserException("User already exists!");
        if (savedUser == savedUser2) throw new ExistingUserException("User already exists!");
        User user = new User();
        validateUserRegistrationDetails(registrationRequest);
        user.setUserName(registrationRequest.getUserName());
        user.setEmail(registrationRequest.getEmail().toLowerCase());
        user.setPassword(hashPassword(registrationRequest.getPassword()));
        return user;
    }

    private void validateUserRegistrationDetails(UserRegistrationRequest registrationRequest) {
        if (!Validation.validateUserName(registrationRequest.getUserName()))throw new InvalidUserNameException("Empty user name");
        if (!Validation.validateMail(registrationRequest.getEmail())) throw new InvalidMailException("Invalid mail");
        if (!Validation.validatePassword(registrationRequest.getPassword()))throw new InvalidPasswordException("Invalid password");
    }

    private UserRegistrationResponse buildResponse(){
        UserRegistrationResponse registrationResponse = new UserRegistrationResponse();
        registrationResponse.setStatusCode(201);
        registrationResponse.setMessage("Registration successful.\nProceed to login page");
        return registrationResponse;
    }
    private static String hashPassword(String userPassword){
        String password = userPassword;
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    private static boolean confirmPassword(String candidate, String password){
        return BCrypt.checkpw(candidate, password);
    }
}

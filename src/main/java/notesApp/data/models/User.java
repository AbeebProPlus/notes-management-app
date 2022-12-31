package notesApp.data.models;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Data
@Document
public class User {
    @Id
    private String id;
    private String userName;
    private String email;
    private String password;
    @DBRef
    List<Note> notes = new ArrayList<>();
}

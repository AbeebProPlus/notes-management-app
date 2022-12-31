package notesApp.data.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Document
public class Note {
    @Id
    private String id;
    private String title;
    private String content;
    private String editStatus = "";
    private LocalDate creationDate = LocalDate.now();
    private LocalTime creationTime = LocalTime.now();
    private String editTime;
    private String editDate;
}

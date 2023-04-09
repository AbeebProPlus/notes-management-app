package notesApp.dtos.responses;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateNoteResponse {
    private int statusCode;
    private String message;
    private String creationDate;
    private String creationTime;
    private Note note;
}

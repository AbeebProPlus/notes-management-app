package notesApp.dtos.requests;

import lombok.Data;

@Data
public class DeleteNoteRequest {
    private String noteId;
    private String userName;
}
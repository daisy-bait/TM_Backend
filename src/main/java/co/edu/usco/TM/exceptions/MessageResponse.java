package co.edu.usco.TM.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    public Integer statusCode;
    public String message;
    public String details;
    public LocalDateTime timestamp;
}

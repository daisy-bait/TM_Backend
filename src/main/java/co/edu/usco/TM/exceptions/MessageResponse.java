package co.edu.usco.TM.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {

    public Integer statusCode;
    public String message;
    public Map<String, String> details;
    public LocalDateTime timestamp;
}

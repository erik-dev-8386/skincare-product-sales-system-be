package application.havenskin.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response <T> {
    private int code;
    private String message;
    private T result;
}

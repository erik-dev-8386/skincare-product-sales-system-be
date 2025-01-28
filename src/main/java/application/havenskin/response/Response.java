package application.havenskin.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response <T> {
    private int code;
    private String message;
    private T result;
}

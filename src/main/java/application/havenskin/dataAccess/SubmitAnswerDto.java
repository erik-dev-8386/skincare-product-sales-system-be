package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class SubmitAnswerDto {
    private String questionId;
    private String answerId;
}

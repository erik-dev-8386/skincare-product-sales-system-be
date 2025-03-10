package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswersDto {
    private String answerId;
    private String answerContent;
    private Double mark;
    private String questionId;
    private String questionContent;
}

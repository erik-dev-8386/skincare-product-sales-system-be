package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsResponseDto {
    private String questionId;
    private String questionContent;
    private double maxMark;
    private List<AnswersDto> answers;
    private byte status;


}

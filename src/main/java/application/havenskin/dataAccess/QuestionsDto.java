package application.havenskin.dataAccess;

import application.havenskin.models.Answers;
import application.havenskin.models.Questions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsDto {
    private String questionId;
    private String questionContent;
    private double maxMark;
    private List<AnswersDto> answers;


}

package application.havenskin.dataAccess;

import application.havenskin.enums.AnswerEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswersDto {

    private String answerId;
//
    @Nationalized
    private String answerContent;

    private Double mark;

    @Nationalized
    private String questionContent;

    private Byte status = AnswerEnum.ACTIVE.getStatus();
}

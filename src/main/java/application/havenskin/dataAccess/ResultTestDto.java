package application.havenskin.dataAccess;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResultTestDto {
    private String resultTestId;
    private String skinTestId;
    private String userId;
    private String firstName;
    private String lastName;
    private Double totalMark;
    private String skinTypeId;
    private Date createdTime;
    private String skinName;
    private byte status;
    private List<UserAnswerDto> userAnswers;
}

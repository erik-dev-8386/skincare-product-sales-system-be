package application.havenskin.dataAccess;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ResultTestDto {
    private String resultTestId;
//    private String skinTestId;
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private Double totalMark;
    private String skinTypeId;
    private LocalDateTime createdTime;
    private String skinName;
    private byte status;
//    private List<UserAnswerDto> userAnswers;
}

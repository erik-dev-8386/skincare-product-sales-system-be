package application.havenskin.dataAccess;

import application.havenskin.models.SkinTests;
import application.havenskin.models.SkinTypes;
import application.havenskin.models.UserAnswers;
import application.havenskin.models.Users;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
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
    private Date createdTime;
    private String skinName;
    private byte status;
    //  private List<UserAnswerDto> userAnswers;





}

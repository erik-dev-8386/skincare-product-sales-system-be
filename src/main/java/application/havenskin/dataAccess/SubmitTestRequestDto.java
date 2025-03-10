package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubmitTestRequestDto {
    //    private String userId;
    private String email;
    private String skinTestId;
    private List<SubmitAnswerDto> answers;
}

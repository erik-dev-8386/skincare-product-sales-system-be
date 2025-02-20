package application.havenskin.dataAccess;

import application.havenskin.models.SkinTests;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkinTestsDto {
    private String skinTestId;
//    private String testName;
    private Double maxMark;
    private byte status;
    private LocalDateTime createdTime;
    private LocalDateTime deletedTime;
    private List<QuestionsDto> questions;

}

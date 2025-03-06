package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkinTestsDto {
    private String skinTestId;
//    private String testName;
    private Double maxMark;
    private byte status;
    private Date createdTime;
    private Date deletedTime;
    private List<QuestionsResponseDto> questions;

}

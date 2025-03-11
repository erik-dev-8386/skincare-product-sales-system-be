package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkinTestsDto {
    private String skinTestId;
    @Nationalized
    private String testName;
    private Double maxMark;
    private byte status;
    private Date createdTime = new Date();
    private Date deletedTime;
    private List<QuestionsResponseDto> questions;
}

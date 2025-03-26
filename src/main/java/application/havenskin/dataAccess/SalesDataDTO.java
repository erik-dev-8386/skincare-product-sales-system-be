package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SalesDataDTO {
    private String productName;
    private int month;
    private int year;
    private int totalSales; // tổng số lượng khi thay đổi
}

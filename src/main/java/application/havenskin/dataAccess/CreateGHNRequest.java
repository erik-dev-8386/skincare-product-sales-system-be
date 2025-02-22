package application.havenskin.dataAccess;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGHNRequest {
    private String to_name;
    private String from_name;
    private String from_phone;
    private String from_address;
    private String from_ward_name;
    private String from_district_name;
    private String from_province_name;
    private String to_phone;
    private String to_address;
    private String to_ward_code;
    private int to_district_id;
    private int weight;
    private int length;
    private int width;
    private int height;
    private int service_type_id;
    private int payment_type_id;
    private String required_note;
    private List<Item> items; // ✅ Thêm danh sách sản phẩm
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item {
        private String name;    // ✅ Tên sản phẩm
        private int quantity;   // ✅ Số lượng
        private int price;      // ✅ Giá sản phẩm
    }
}

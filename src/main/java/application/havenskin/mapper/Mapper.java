package application.havenskin.mapper;

import application.havenskin.dataAccess.*;
import application.havenskin.models.*;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    SkinTests toSkinTests(SkinTestsDto skinTestsDto);
    Brands toBrands(BrandDTO brandDTO);
    //@Mapping(target = "brandId", ignore = true)
    void updateBrands(@MappingTarget Brands brands, BrandDTO brandDTO);
    Categories toCategories(CategoryDTO categoryDTO);
    void updateCategories(@MappingTarget Categories categories, CategoryDTO categoryDTO);
    Discounts toDiscounts(DiscountDTO discountDTO);
    void updateDiscounts(@MappingTarget Discounts discounts, DiscountDTO discountDTO);
    Orders toOrders(OrderDTO orderDTO);
    void updateOrders(@MappingTarget Orders orders, OrderDTO orderDTO);
    OrderDetails toOrderDetails(OrderDetails orderDetails);
    void updateOrderDetails(@MappingTarget OrderDetails orderDetails, OrderDetailDTO orderDetailsDTO);
    @Mapping(target = "discountPrice",ignore = true)
    Products toProducts(ProductDTO productDTO);
    void updateProducts(@MappingTarget Products products, ProductDTO productDTO);
    Shipments toShipments(ShipmentDTO shipmentDTO);
    void updateShipments(@MappingTarget Shipments shipments, ShipmentDTO shipmentDTO);
    SkinTypes toSkinTypes(SkinTypeDTO skinTypeDTO);
    void updateSkinType(@MappingTarget SkinTypes skinTypes, SkinTypeDTO skinTypeDTO);
    Transactions toTransactions(TransactionDTO transactionDTO);
    void updateTransactions(@MappingTarget Transactions transactions, TransactionDTO transactionDTO);
    Blogs toBlogs(BlogDTO blogDTO);
    void updateBlogs(@MappingTarget Blogs blogs, BlogDTO blogDTO);
    Feedbacks toFeedbacks(FeedbackDTO feedbackDTO);
    void updateFeedbacks(@MappingTarget Feedbacks feedbacks, FeedbackDTO feedbackDTO);
    Users toUsers(UserDTO userDTO);
    CoinWallets toCoinWallets(CoinWalletDTO coinWalletDTO);
    void updateCoinWallets(@MappingTarget CoinWallets coinWallets, CoinWalletDTO coinWalletDTO);
}

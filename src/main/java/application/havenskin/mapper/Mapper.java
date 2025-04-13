package application.havenskin.mapper;

import application.havenskin.dataAccess.*;
import application.havenskin.models.*;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@org.mapstruct.Mapper(componentModel = "spring")
public interface Mapper {
    SkinTests toSkinTests(SkinTestsDto skinTestsDto);
    Brands toBrands(BrandDTO brandDTO);
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
    SkinCaresPlan toSkinCaresPlan(PlanSkinCareDTO planSkinCareDTO);
    void updateSkincaresPlan(@MappingTarget SkinCaresPlan skinCaresPlan, PlanSkinCareDTO planSkinCareDTO);
    MiniSkinCarePlan toMiniSkinCarePlan(MiniSkinCarePlanDTO miniSkinCarePlanDTO);
    void updateMiniSkinCarePlan(@MappingTarget MiniSkinCarePlan miniSkinCarePlan, MiniSkinCarePlanDTO miniSkinCarePlanDTO);
}

package application.havenskin.mapper;

import application.havenskin.dataAccess.BlogDTO;
import application.havenskin.dataAccess.BrandDTO;
import application.havenskin.dataAccess.CategoryDTO;
import application.havenskin.dataAccess.DiscountDTO;
import application.havenskin.dataAccess.FeedbackDTO;
import application.havenskin.dataAccess.OrderDTO;
import application.havenskin.dataAccess.OrderDetailDTO;
import application.havenskin.dataAccess.ProductDTO;
import application.havenskin.dataAccess.ShipmentDTO;
import application.havenskin.dataAccess.SkinTypeDTO;
import application.havenskin.dataAccess.TransactionDTO;
import application.havenskin.dataAccess.UserDTO;
import application.havenskin.models.BlogImages;
import application.havenskin.models.Blogs;
import application.havenskin.models.Brands;
import application.havenskin.models.Categories;
import application.havenskin.models.Discounts;
import application.havenskin.models.Feedbacks;
import application.havenskin.models.OrderDetails;
import application.havenskin.models.Orders;
import application.havenskin.models.ProductImages;
import application.havenskin.models.Products;
import application.havenskin.models.ResultTests;
import application.havenskin.models.Shipments;
import application.havenskin.models.SkinCaresPlan;
import application.havenskin.models.SkinTypeImages;
import application.havenskin.models.SkinTypes;
import application.havenskin.models.Transactions;
import application.havenskin.models.Users;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-02T18:43:11+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class MapperImpl implements Mapper {

    @Override
    public Brands toBrands(BrandDTO brandDTO) {
        if ( brandDTO == null ) {
            return null;
        }

        Brands brands = new Brands();

        brands.setBrandName( brandDTO.getBrandName() );
        brands.setDescription( brandDTO.getDescription() );
        brands.setCountry( brandDTO.getCountry() );
        List<Products> list = brandDTO.getProducts();
        if ( list != null ) {
            brands.setProducts( new ArrayList<Products>( list ) );
        }
        brands.setStatus( brandDTO.getStatus() );

        return brands;
    }

    @Override
    public void updateBrands(Brands brands, BrandDTO brandDTO) {
        if ( brandDTO == null ) {
            return;
        }

        brands.setBrandName( brandDTO.getBrandName() );
        brands.setDescription( brandDTO.getDescription() );
        brands.setCountry( brandDTO.getCountry() );
        if ( brands.getProducts() != null ) {
            List<Products> list = brandDTO.getProducts();
            if ( list != null ) {
                brands.getProducts().clear();
                brands.getProducts().addAll( list );
            }
            else {
                brands.setProducts( null );
            }
        }
        else {
            List<Products> list = brandDTO.getProducts();
            if ( list != null ) {
                brands.setProducts( new ArrayList<Products>( list ) );
            }
        }
        brands.setStatus( brandDTO.getStatus() );
    }

    @Override
    public Categories toCategories(CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return null;
        }

        Categories categories = new Categories();

        categories.setCategoryName( categoryDTO.getCategoryName() );
        categories.setDescription( categoryDTO.getDescription() );
        List<Products> list = categoryDTO.getProducts();
        if ( list != null ) {
            categories.setProducts( new ArrayList<Products>( list ) );
        }
        categories.setStatus( categoryDTO.getStatus() );

        return categories;
    }

    @Override
    public void updateCategories(Categories categories, CategoryDTO categoryDTO) {
        if ( categoryDTO == null ) {
            return;
        }

        categories.setCategoryName( categoryDTO.getCategoryName() );
        categories.setDescription( categoryDTO.getDescription() );
        if ( categories.getProducts() != null ) {
            List<Products> list = categoryDTO.getProducts();
            if ( list != null ) {
                categories.getProducts().clear();
                categories.getProducts().addAll( list );
            }
            else {
                categories.setProducts( null );
            }
        }
        else {
            List<Products> list = categoryDTO.getProducts();
            if ( list != null ) {
                categories.setProducts( new ArrayList<Products>( list ) );
            }
        }
        categories.setStatus( categoryDTO.getStatus() );
    }

    @Override
    public Discounts toDiscounts(DiscountDTO discountDTO) {
        if ( discountDTO == null ) {
            return null;
        }

        Discounts discounts = new Discounts();

        discounts.setDiscountName( discountDTO.getDiscountName() );
        discounts.setDiscountCode( discountDTO.getDiscountCode() );
        discounts.setDescription( discountDTO.getDescription() );
        discounts.setCreatedTime( discountDTO.getCreatedTime() );
        discounts.setDeletedTime( discountDTO.getDeletedTime() );
        discounts.setActualStartTime( discountDTO.getActualStartTime() );
        discounts.setActualEndTime( discountDTO.getActualEndTime() );
        discounts.setDiscountPercent( discountDTO.getDiscountPercent() );
        discounts.setStatus( discountDTO.getStatus() );
        List<Products> list = discountDTO.getProducts();
        if ( list != null ) {
            discounts.setProducts( new ArrayList<Products>( list ) );
        }

        return discounts;
    }

    @Override
    public void updateDiscounts(Discounts discounts, DiscountDTO discountDTO) {
        if ( discountDTO == null ) {
            return;
        }

        discounts.setDiscountName( discountDTO.getDiscountName() );
        discounts.setDiscountCode( discountDTO.getDiscountCode() );
        discounts.setDescription( discountDTO.getDescription() );
        discounts.setCreatedTime( discountDTO.getCreatedTime() );
        discounts.setDeletedTime( discountDTO.getDeletedTime() );
        discounts.setActualStartTime( discountDTO.getActualStartTime() );
        discounts.setActualEndTime( discountDTO.getActualEndTime() );
        discounts.setDiscountPercent( discountDTO.getDiscountPercent() );
        discounts.setStatus( discountDTO.getStatus() );
        if ( discounts.getProducts() != null ) {
            List<Products> list = discountDTO.getProducts();
            if ( list != null ) {
                discounts.getProducts().clear();
                discounts.getProducts().addAll( list );
            }
            else {
                discounts.setProducts( null );
            }
        }
        else {
            List<Products> list = discountDTO.getProducts();
            if ( list != null ) {
                discounts.setProducts( new ArrayList<Products>( list ) );
            }
        }
    }

    @Override
    public Orders toOrders(OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return null;
        }

        Orders orders = new Orders();

        orders.setOrderTime( orderDTO.getOrderTime() );
        orders.setUserId( orderDTO.getUserId() );
        orders.setTotalAmount( orderDTO.getTotalAmount() );
        orders.setStatus( orderDTO.getStatus() );
        orders.setCancelTime( orderDTO.getCancelTime() );
        orders.setAddress( orderDTO.getAddress() );
        orders.setShipmentFree( orderDTO.getShipmentFree() );
        orders.setUser( orderDTO.getUser() );
        List<OrderDetails> list = orderDTO.getOrderDetails();
        if ( list != null ) {
            orders.setOrderDetails( new ArrayList<OrderDetails>( list ) );
        }
        List<Shipments> list1 = orderDTO.getShipments();
        if ( list1 != null ) {
            orders.setShipments( new ArrayList<Shipments>( list1 ) );
        }
        orders.setTransactions( orderDTO.getTransactions() );

        return orders;
    }

    @Override
    public void updateOrders(Orders orders, OrderDTO orderDTO) {
        if ( orderDTO == null ) {
            return;
        }

        orders.setOrderTime( orderDTO.getOrderTime() );
        orders.setUserId( orderDTO.getUserId() );
        orders.setTotalAmount( orderDTO.getTotalAmount() );
        orders.setStatus( orderDTO.getStatus() );
        orders.setCancelTime( orderDTO.getCancelTime() );
        orders.setAddress( orderDTO.getAddress() );
        orders.setShipmentFree( orderDTO.getShipmentFree() );
        orders.setUser( orderDTO.getUser() );
        if ( orders.getOrderDetails() != null ) {
            List<OrderDetails> list = orderDTO.getOrderDetails();
            if ( list != null ) {
                orders.getOrderDetails().clear();
                orders.getOrderDetails().addAll( list );
            }
            else {
                orders.setOrderDetails( null );
            }
        }
        else {
            List<OrderDetails> list = orderDTO.getOrderDetails();
            if ( list != null ) {
                orders.setOrderDetails( new ArrayList<OrderDetails>( list ) );
            }
        }
        if ( orders.getShipments() != null ) {
            List<Shipments> list1 = orderDTO.getShipments();
            if ( list1 != null ) {
                orders.getShipments().clear();
                orders.getShipments().addAll( list1 );
            }
            else {
                orders.setShipments( null );
            }
        }
        else {
            List<Shipments> list1 = orderDTO.getShipments();
            if ( list1 != null ) {
                orders.setShipments( new ArrayList<Shipments>( list1 ) );
            }
        }
        orders.setTransactions( orderDTO.getTransactions() );
    }

    @Override
    public OrderDetails toOrderDetails(OrderDetails orderDetails) {
        if ( orderDetails == null ) {
            return null;
        }

        OrderDetails orderDetails1 = new OrderDetails();

        orderDetails1.setOrderDetailId( orderDetails.getOrderDetailId() );
        orderDetails1.setQuantity( orderDetails.getQuantity() );
        orderDetails1.setDiscountPrice( orderDetails.getDiscountPrice() );
        orderDetails1.setProductId( orderDetails.getProductId() );
        orderDetails1.setProducts( orderDetails.getProducts() );
        orderDetails1.setOrderId( orderDetails.getOrderId() );
        orderDetails1.setOrders( orderDetails.getOrders() );
        orderDetails1.setStatus( orderDetails.getStatus() );

        return orderDetails1;
    }

    @Override
    public void updateOrderDetails(OrderDetails orderDetails, OrderDetailDTO orderDetailsDTO) {
        if ( orderDetailsDTO == null ) {
            return;
        }

        orderDetails.setQuantity( orderDetailsDTO.getQuantity() );
        orderDetails.setDiscountPrice( orderDetailsDTO.getDiscountPrice() );
        orderDetails.setProductId( orderDetailsDTO.getProductId() );
        orderDetails.setProducts( orderDetailsDTO.getProducts() );
        orderDetails.setOrderId( orderDetailsDTO.getOrderId() );
        orderDetails.setOrders( orderDetailsDTO.getOrders() );
        orderDetails.setStatus( orderDetailsDTO.getStatus() );
    }

    @Override
    public Products toProducts(ProductDTO productDTO) {
        if ( productDTO == null ) {
            return null;
        }

        Products products = new Products();

        products.setDiscountPrice( productDTO.getDiscountPrice() );
        products.setProductName( productDTO.getProductName() );
        products.setUnitPrice( productDTO.getUnitPrice() );
        products.setDescription( productDTO.getDescription() );
        products.setIngredients( productDTO.getIngredients() );
        products.setQuantity( productDTO.getQuantity() );
        products.setCreatedTime( productDTO.getCreatedTime() );
        products.setDeletedTime( productDTO.getDeletedTime() );
        products.setMfg( productDTO.getMfg() );
        products.setExp( productDTO.getExp() );
        products.setNetWeight( productDTO.getNetWeight() );
        products.setStatus( productDTO.getStatus() );
        products.setDiscountId( productDTO.getDiscountId() );
        products.setCategoryId( productDTO.getCategoryId() );
        products.setBrandId( productDTO.getBrandId() );
        products.setSkinTypeId( productDTO.getSkinTypeId() );
        List<ProductImages> list = productDTO.getProductImages();
        if ( list != null ) {
            products.setProductImages( new ArrayList<ProductImages>( list ) );
        }

        return products;
    }

    @Override
    public void updateProducts(Products products, ProductDTO productDTO) {
        if ( productDTO == null ) {
            return;
        }

        products.setProductName( productDTO.getProductName() );
        products.setUnitPrice( productDTO.getUnitPrice() );
        products.setDiscountPrice( productDTO.getDiscountPrice() );
        products.setDescription( productDTO.getDescription() );
        products.setIngredients( productDTO.getIngredients() );
        products.setQuantity( productDTO.getQuantity() );
        products.setCreatedTime( productDTO.getCreatedTime() );
        products.setDeletedTime( productDTO.getDeletedTime() );
        products.setMfg( productDTO.getMfg() );
        products.setExp( productDTO.getExp() );
        products.setNetWeight( productDTO.getNetWeight() );
        products.setStatus( productDTO.getStatus() );
        products.setDiscountId( productDTO.getDiscountId() );
        products.setCategoryId( productDTO.getCategoryId() );
        products.setBrandId( productDTO.getBrandId() );
        products.setSkinTypeId( productDTO.getSkinTypeId() );
        if ( products.getProductImages() != null ) {
            List<ProductImages> list = productDTO.getProductImages();
            if ( list != null ) {
                products.getProductImages().clear();
                products.getProductImages().addAll( list );
            }
            else {
                products.setProductImages( null );
            }
        }
        else {
            List<ProductImages> list = productDTO.getProductImages();
            if ( list != null ) {
                products.setProductImages( new ArrayList<ProductImages>( list ) );
            }
        }
    }

    @Override
    public Shipments toShipments(ShipmentDTO shipmentDTO) {
        if ( shipmentDTO == null ) {
            return null;
        }

        Shipments shipments = new Shipments();

        shipments.setExpectedDeliveryTime( shipmentDTO.getExpectedDeliveryTime() );
        shipments.setFee( shipmentDTO.getFee() );
        shipments.setCoupon( shipmentDTO.getCoupon() );
        shipments.setInsurance( shipmentDTO.getInsurance() );
        shipments.setMainService( shipmentDTO.getMainService() );
        shipments.setR2s( shipmentDTO.getR2s() );
        shipments.setReturnFee( shipmentDTO.getReturnFee() );
        shipments.setStationDo( shipmentDTO.getStationDo() );
        shipments.setStationPu( shipmentDTO.getStationPu() );
        shipments.setOrderCode( shipmentDTO.getOrderCode() );
        shipments.setSortCode( shipmentDTO.getSortCode() );
        shipments.setTotalFee( shipmentDTO.getTotalFee() );
        shipments.setTransType( shipmentDTO.getTransType() );

        return shipments;
    }

    @Override
    public void updateShipments(Shipments shipments, ShipmentDTO shipmentDTO) {
        if ( shipmentDTO == null ) {
            return;
        }

        shipments.setExpectedDeliveryTime( shipmentDTO.getExpectedDeliveryTime() );
        shipments.setFee( shipmentDTO.getFee() );
        shipments.setCoupon( shipmentDTO.getCoupon() );
        shipments.setInsurance( shipmentDTO.getInsurance() );
        shipments.setMainService( shipmentDTO.getMainService() );
        shipments.setR2s( shipmentDTO.getR2s() );
        shipments.setReturnFee( shipmentDTO.getReturnFee() );
        shipments.setStationDo( shipmentDTO.getStationDo() );
        shipments.setStationPu( shipmentDTO.getStationPu() );
        shipments.setOrderCode( shipmentDTO.getOrderCode() );
        shipments.setSortCode( shipmentDTO.getSortCode() );
        shipments.setTotalFee( shipmentDTO.getTotalFee() );
        shipments.setTransType( shipmentDTO.getTransType() );
    }

    @Override
    public SkinTypes toSkinTypes(SkinTypeDTO skinTypeDTO) {
        if ( skinTypeDTO == null ) {
            return null;
        }

        SkinTypes skinTypes = new SkinTypes();

        skinTypes.setSkinName( skinTypeDTO.getSkinName() );
        skinTypes.setDescription( skinTypeDTO.getDescription() );
        skinTypes.setMinMark( skinTypeDTO.getMinMark() );
        skinTypes.setMaxMark( skinTypeDTO.getMaxMark() );
        List<SkinTypeImages> list = skinTypeDTO.getSkinTypeImages();
        if ( list != null ) {
            skinTypes.setSkinTypeImages( new ArrayList<SkinTypeImages>( list ) );
        }
        skinTypes.setStatus( skinTypeDTO.getStatus() );

        return skinTypes;
    }

    @Override
    public void updateSkinType(SkinTypes skinTypes, SkinTypeDTO skinTypeDTO) {
        if ( skinTypeDTO == null ) {
            return;
        }

        skinTypes.setSkinName( skinTypeDTO.getSkinName() );
        skinTypes.setDescription( skinTypeDTO.getDescription() );
        skinTypes.setMinMark( skinTypeDTO.getMinMark() );
        skinTypes.setMaxMark( skinTypeDTO.getMaxMark() );
        if ( skinTypes.getSkinTypeImages() != null ) {
            List<SkinTypeImages> list = skinTypeDTO.getSkinTypeImages();
            if ( list != null ) {
                skinTypes.getSkinTypeImages().clear();
                skinTypes.getSkinTypeImages().addAll( list );
            }
            else {
                skinTypes.setSkinTypeImages( null );
            }
        }
        else {
            List<SkinTypeImages> list = skinTypeDTO.getSkinTypeImages();
            if ( list != null ) {
                skinTypes.setSkinTypeImages( new ArrayList<SkinTypeImages>( list ) );
            }
        }
        skinTypes.setStatus( skinTypeDTO.getStatus() );
    }

    @Override
    public Transactions toTransactions(TransactionDTO transactionDTO) {
        if ( transactionDTO == null ) {
            return null;
        }

        Transactions transactions = new Transactions();

        transactions.setTransactionStatus( transactionDTO.getTransactionStatus() );
        transactions.setTransactionTime( transactionDTO.getTransactionTime() );
        transactions.setOrderId( transactionDTO.getOrderId() );
        transactions.setTransactionType( transactionDTO.getTransactionType() );
        transactions.setOrders( transactionDTO.getOrders() );

        return transactions;
    }

    @Override
    public void updateTransactions(Transactions transactions, TransactionDTO transactionDTO) {
        if ( transactionDTO == null ) {
            return;
        }

        transactions.setTransactionStatus( transactionDTO.getTransactionStatus() );
        transactions.setTransactionTime( transactionDTO.getTransactionTime() );
        transactions.setOrderId( transactionDTO.getOrderId() );
        transactions.setTransactionType( transactionDTO.getTransactionType() );
        transactions.setOrders( transactionDTO.getOrders() );
    }

    @Override
    public Blogs toBlogs(BlogDTO blogDTO) {
        if ( blogDTO == null ) {
            return null;
        }

        Blogs blogs = new Blogs();

        blogs.setBlogId( blogDTO.getBlogId() );
        blogs.setBlogTitle( blogDTO.getBlogTitle() );
        blogs.setBlogContent( blogDTO.getBlogContent() );
        blogs.setUserId( blogDTO.getUserId() );
        blogs.setPostedTime( blogDTO.getPostedTime() );
        blogs.setDeletedTime( blogDTO.getDeletedTime() );
        blogs.setUser( blogDTO.getUser() );
        List<BlogImages> list = blogDTO.getBlogImages();
        if ( list != null ) {
            blogs.setBlogImages( new ArrayList<BlogImages>( list ) );
        }
        blogs.setBlogCategory( blogDTO.getBlogCategory() );

        return blogs;
    }

    @Override
    public void updateBlogs(Blogs blogs, BlogDTO blogDTO) {
        if ( blogDTO == null ) {
            return;
        }

        blogs.setBlogId( blogDTO.getBlogId() );
        blogs.setBlogTitle( blogDTO.getBlogTitle() );
        blogs.setBlogContent( blogDTO.getBlogContent() );
        blogs.setUserId( blogDTO.getUserId() );
        blogs.setPostedTime( blogDTO.getPostedTime() );
        blogs.setDeletedTime( blogDTO.getDeletedTime() );
        blogs.setUser( blogDTO.getUser() );
        if ( blogs.getBlogImages() != null ) {
            List<BlogImages> list = blogDTO.getBlogImages();
            if ( list != null ) {
                blogs.getBlogImages().clear();
                blogs.getBlogImages().addAll( list );
            }
            else {
                blogs.setBlogImages( null );
            }
        }
        else {
            List<BlogImages> list = blogDTO.getBlogImages();
            if ( list != null ) {
                blogs.setBlogImages( new ArrayList<BlogImages>( list ) );
            }
        }
        blogs.setBlogCategory( blogDTO.getBlogCategory() );
    }

    @Override
    public Feedbacks toFeedbacks(FeedbackDTO feedbackDTO) {
        if ( feedbackDTO == null ) {
            return null;
        }

        Feedbacks feedbacks = new Feedbacks();

        feedbacks.setFeedbackContent( feedbackDTO.getFeedbackContent() );
        feedbacks.setFeedbackDate( feedbackDTO.getFeedbackDate() );
        feedbacks.setProductId( feedbackDTO.getProductId() );
        feedbacks.setProducts( feedbackDTO.getProducts() );
        feedbacks.setUserId( feedbackDTO.getUserId() );
        feedbacks.setUsers( feedbackDTO.getUsers() );
        feedbacks.setStatus( feedbackDTO.getStatus() );

        return feedbacks;
    }

    @Override
    public void updateFeedbacks(Feedbacks feedbacks, FeedbackDTO feedbackDTO) {
        if ( feedbackDTO == null ) {
            return;
        }

        feedbacks.setFeedbackContent( feedbackDTO.getFeedbackContent() );
        feedbacks.setFeedbackDate( feedbackDTO.getFeedbackDate() );
        feedbacks.setProductId( feedbackDTO.getProductId() );
        feedbacks.setProducts( feedbackDTO.getProducts() );
        feedbacks.setUserId( feedbackDTO.getUserId() );
        feedbacks.setUsers( feedbackDTO.getUsers() );
        feedbacks.setStatus( feedbackDTO.getStatus() );
    }

    @Override
    public Users toUsers(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        Users users = new Users();

        users.setFirstName( userDTO.getFirstName() );
        users.setLastName( userDTO.getLastName() );
        users.setGender( userDTO.getGender() );
        users.setAddress( userDTO.getAddress() );
        users.setBirthDate( userDTO.getBirthDate() );
        users.setEmail( userDTO.getEmail() );
        users.setPassword( userDTO.getPassword() );
        users.setPhoneNumber( userDTO.getPhoneNumber() );
        users.setRating( userDTO.getRating() );
        users.setImage( userDTO.getImage() );
        users.setRole( userDTO.getRole() );
        users.setStatus( userDTO.getStatus() );
        List<Orders> list = userDTO.getOrders();
        if ( list != null ) {
            users.setOrders( new ArrayList<Orders>( list ) );
        }
        List<ResultTests> list1 = userDTO.getResultTests();
        if ( list1 != null ) {
            users.setResultTests( new ArrayList<ResultTests>( list1 ) );
        }
        List<Blogs> list2 = userDTO.getBlogs();
        if ( list2 != null ) {
            users.setBlogs( new ArrayList<Blogs>( list2 ) );
        }
        List<Feedbacks> list3 = userDTO.getFeedbacks();
        if ( list3 != null ) {
            users.setFeedbacks( new ArrayList<Feedbacks>( list3 ) );
        }
        List<SkinCaresPlan> list4 = userDTO.getSkinCaresPlan();
        if ( list4 != null ) {
            users.setSkinCaresPlan( new ArrayList<SkinCaresPlan>( list4 ) );
        }

        return users;
    }
}

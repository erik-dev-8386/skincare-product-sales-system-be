package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Brands;
import application.havenskin.BusinessObject.Models.Categories;
import application.havenskin.BusinessObject.Models.Products;
import application.havenskin.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private CategoryService categoryService;

    public List<Products> getAllProducts() {
//        return productsRepository.findAll();
        return productsRepository.findByStatus((byte) 1);
    }

    public Products getProductById(String id) {
        return productsRepository.findById(id).get();
    }

    public Products getProductByName(String name) {
        return productsRepository.findByProductName(name);
    }

    public Products addProduct(Products product) {
        return productsRepository.save(product);
    }

    public Products updateProduct(String id, Products product) {
        Products existingProduct = productsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (product.getProductName() != null) {
            existingProduct.setProductName(product.getProductName());
        }
        if (product.getUnitPrice() != 0) {
            existingProduct.setUnitPrice(product.getUnitPrice());
        }
        if (product.getDiscountPrice() != 0) {
            existingProduct.setDiscountPrice(product.getDiscountPrice());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }
        if (product.getIngredients() != null) {
            existingProduct.setIngredients(product.getIngredients());
        }
        if (product.getQuantity() != 0) {
            existingProduct.setQuantity(product.getQuantity());
        }
        if (product.getMfg() != null) {
            existingProduct.setMfg(product.getMfg());
        }
        if (product.getExp() != null) {
            existingProduct.setExp(product.getExp());
        }
        if (product.getNetWeight() != 0) {
            existingProduct.setNetWeight(product.getNetWeight());
        }
        if (product.getStatus() != 0) {
            existingProduct.setStatus(product.getStatus());
        }
        if (product.getBrandId() != null) {
            existingProduct.setBrandId(product.getBrandId());
        }
        if (product.getCategoryId() != null) {
            existingProduct.setCategoryId(product.getCategoryId());
        }
        if (product.getDiscountId() != null) {
            existingProduct.setDiscountId(product.getDiscountId());
        }
        if (product.getSkinTypeId() != null) {
            existingProduct.setSkinTypeId(product.getSkinTypeId());
        }
        if (product.getDeletedTime() != null) {
            existingProduct.setDeletedTime(product.getDeletedTime());
        }

        return productsRepository.save(existingProduct);
    }

    public void softDeleteProduct(String id) {
        Products products = productsRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Product not found"));
        products.setStatus((byte) 0);
        productsRepository.save(products);
    }

    public List<Products> getProductsByCategoryName(String categoryName) {
        if (productsRepository.findProductsByCategories_CategoryName(categoryName) == null) {
            throw new RuntimeException("Product not found");
        }
        return productsRepository.findProductsByCategories_CategoryName(categoryName);
    }

    public List<Products> getProductsByBrandName(String brandName) {
        if (productsRepository.findByBrands_BrandName(brandName) == null) {
            throw new RuntimeException("Product not found with " + brandName);
        }
        return productsRepository.findByBrands_BrandName(brandName);
    }

    public List<Products> getProductsByBrandCountry(String country){
        if(productsRepository.findByCountry(country) == null){
            throw new RuntimeException("Product not found with " + country);
        }
        return productsRepository.findByCountry(country);
    }

    public List<Products> getProductsBySkinName(String skinName) {
        if (productsRepository.findBySkinName(skinName) == null) {
            throw new RuntimeException("Product not found with " + skinName);
        }
        return productsRepository.findBySkinName(skinName);
    }

    public List<Products> getProductsByDiscountName(String discountName) {
        if (productsRepository.findProductsByDiscounts_DiscountName(discountName) == null) {
            throw new RuntimeException("Product not found with " + discountName);
        }
        return productsRepository.findProductsByDiscounts_DiscountName(discountName);
    }
}

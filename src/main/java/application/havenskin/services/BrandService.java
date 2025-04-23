package application.havenskin.services;

import application.havenskin.dataAccess.BrandDTO;
import application.havenskin.enums.BrandEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Brands;
import application.havenskin.models.Categories;
import application.havenskin.repositories.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {
    @Autowired
    private BrandsRepository brandsRepository;
    @Autowired
    private Mapper mapper;
    public List<Brands> getAllBrands(){
        return brandsRepository.findAll();
        //return brandsRepository.findActiveBrandsSortedByName();
    }

    public Brands getBrandById(String id){
        Brands brand = brandsRepository.findById(id).get();
        return brand;
    }
    public Brands createBrand(BrandDTO brands){
        if (brandsRepository.existsByBrandName(brands.getBrandName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên thương hiệu đã tồn tại!");
        }
        Brands x = mapper.toBrands(brands);
        return brandsRepository.save(x);
    }
//public Brands createBrand(BrandDTO brands){
//    Brands x = mapper.toBrands(brands);
//    return brandsRepository.save(x);
//}

    public Brands updateBrand(String id, BrandDTO brandDTO){
        Brands brand = brandsRepository.findById(id).orElseThrow(()-> new RuntimeException("Brand not found"));

        mapper.updateBrands(brand, brandDTO);

        return brandsRepository.save(brand);
    }
    //        Brands x = brandsRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand not found"));
//        Brands a = mapper.toBrands(brand);
//        a.setBrandId(x.getBrandId());
//        return brandsRepository.save(a);
    public Brands deleteBrand(String id){
//        Brands brand = brandsRepository.findById(id).get();
//        if(!brandsRepository.existsById(id)){
//            throw new RuntimeException("Brand not found");
//        }
//        brandsRepository.deleteById(id);
        Optional<Brands> brand = brandsRepository.findById(id);
        if(brand.isPresent()){
            Brands x = brand.get();
            x.setStatus(BrandEnums.Inactive.getValue());
            return brandsRepository.save(x);
        }
        return null;
    }

    public void deleteAllBrands(){
        brandsRepository.deleteAll();
    }
    public String getBrandsByName(String name){
        if(brandsRepository.findByBrandName(name) == null){
            throw new RuntimeException("Brand not found");
        }
        return brandsRepository.findByBrandName(name).getBrandId();
    }

    public List<Brands> addBrands(List<Brands> x){
        return brandsRepository.saveAll(x);
    }

    public Brands getBrandByName(String name){
        if(brandsRepository.findByBrandName(name) == null){
            throw new RuntimeException("Brand not found");
        }
        return brandsRepository.findByBrandName(name);
    }

    public List<Brands> getBrandsByCountry(String country){
        if(brandsRepository.findByCountry(country) == null){
            throw new RuntimeException("Brand not found");
        }
        return brandsRepository.findByCountry(country);
    }

    // lấy các brand theo status là Active
    public List<Brands> getAllBrandByName(){

        //return  brandsRepository.findAllByBrandName();
        return  brandsRepository.findByStatusOrderByBrandName((byte) 1);
    }

    public List<Brands> searchBrand(String brandName)
    {
        return brandsRepository.findByBrandNameContaining(brandName);
    }

}

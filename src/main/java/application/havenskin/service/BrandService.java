package application.havenskin.service;

import application.havenskin.BusinessObject.Models.Brands;
//import application.havenskin.DTORequest.BrandDTO;
//import application.havenskin.mapper.Mapper;
import application.havenskin.repository.BrandsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandsRepository brandsRepository;
   // @Autowired
   // private Mapper mapper;
//    public List<Brands> getAllBrands(){
//        return brandsRepository.findAll();
//    }

    public List<Brands> getAllBrands(){
        return brandsRepository.findAll();
    }

    public Brands getBrandById(String id){
        Brands brand = brandsRepository.findById(id).get();
        return brand;
    }

    public Brands createBrand(Brands x){
       // Brands x = mapper.toBrands(brand);
        return brandsRepository.save(x);
    }

//    public Brands updateBrand(String id,Brands brand){
//        Brands brands = brandsRepository.findById(id).get();
//        if(brands == null){
//            throw new RuntimeException("Brand not found");
//        }
//        return brandsRepository.save(brands);
//    }

    public Brands updateBrand(String id, Brands brand){
        Brands existingBrand = brandsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Brand not found"));
        if(brand.getBrandName() != null){
            existingBrand.setBrandName(brand.getBrandName());
        }
        if(brand.getDescription() != null){
            existingBrand.setDescription(brand.getDescription());
        }
        if(brand.getCountry() != null){
            existingBrand.setCountry(brand.getCountry());
        }
        return brandsRepository.save(existingBrand);
    }

//    public void deleteBrand(String id){
//        Brands brand = brandsRepository.findById(id).get();
//        if(brand == null){
//            throw new RuntimeException("Brand not found");
//        }
//        brandsRepository.deleteById(id);
//    }

    public void deleteBrandsById(String id){
        brandsRepository.deleteById(id);
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

}

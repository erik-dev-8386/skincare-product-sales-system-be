package application.havenskin.services;

import application.havenskin.dataAccess.SkinTypeDTO;
import application.havenskin.enums.SkinTypeEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.SkinTypeImages;
import application.havenskin.models.SkinTypes;
import application.havenskin.repositories.SkinTypeImagesRepository;
import application.havenskin.repositories.SkinTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SkinTypeService {
    @Autowired
    private SkinTypesRepository skinTypeRepository;
    @Autowired
    private Mapper mapper;
    @Autowired
    private SkinTypeImagesRepository skinTypeImagesRepository;
    @Autowired
    private FirebaseService firebaseService;
    @Autowired
    private SkinTypeImagesRepository imagesRepository;

    public List<SkinTypes> getAllSkinTypes() {
        return skinTypeRepository.findAll();
    }

    public SkinTypes getSkinTypeById(String id) {
        return skinTypeRepository.findBySkinTypeId(id);
    }

    public SkinTypes getSkinTypeByName(String name) {
        SkinTypes skinType = skinTypeRepository.findBySkinName(name).orElseThrow(()-> new RuntimeException("Không có loại da này"));
        return skinType;
    }

    public SkinTypes createSkinType(SkinTypeDTO skinType, List<MultipartFile> images) throws IOException {
        if(skinTypeRepository.existsBySkinName(skinType.getSkinName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Loại da này đã tồn tại!");
        }
        SkinTypes x = mapper.toSkinTypes(skinType);
        SkinTypes saved = skinTypeRepository.save(x);
        if (images != null && !images.isEmpty()) {
            List<SkinTypeImages> skinTypeImagesList = new ArrayList<>();
            for (MultipartFile file : images) {
                String imageUrl = firebaseService.uploadImage(file);

                SkinTypeImages skinTypeImages = new SkinTypeImages();
                skinTypeImages.setImageURL(imageUrl);
                skinTypeImages.setSkinTypeId(saved.getSkinTypeId());
                skinTypeImages.setSkinType(saved);

                skinTypeImagesList.add(skinTypeImages);
                //    skinTypeImages.setSkinType(x);
                //  skinTypeImages.setSkinType(x);
//               skinTypeImages.setSkinType(x.getS);
//                  skinTypeImages.setSkinTypeId(x.getSkinTypeId());
//                  skinTypeImages.setSkinType(x.getSKI);

                //    *
                // skinTypeImagesRepository.save(skinTypeImages);
                //           x.getSkinTypeImages().add(skinTypeImages);
            }
            skinTypeImagesRepository.saveAll(skinTypeImagesList);
            saved.setSkinTypeImages(skinTypeImagesList);
        }
        return saved;
        //*  x.setSkinTypeImages(skinTypeImagesRepository.findBySkinTypeId(x.getSkinTypeId()));
        //*  System.out.println(x);
        //*  return skinTypeRepository.save(x);
    }

    public SkinTypes updateSkinType(String id, SkinTypeDTO skinType, List<MultipartFile> images) throws IOException {
        SkinTypes existingSkinType = skinTypeRepository.findBySkinTypeId(id);
        if (existingSkinType == null) {
            throw new RuntimeException("SkinType not found");
        }
        mapper.updateSkinType(existingSkinType, skinType);

        if(images != null && !images.isEmpty()) {
            List<SkinTypeImages> skinTypeImagesList = new ArrayList<>();
            for (MultipartFile file : images) {
                String imageUrl = firebaseService.uploadImage(file);
                SkinTypeImages skinTypeImages = new SkinTypeImages();
                skinTypeImages.setImageURL(imageUrl);
                skinTypeImages.setSkinTypeId(existingSkinType.getSkinTypeId());
                skinTypeImages.setSkinType(existingSkinType);

                skinTypeImagesList.add(skinTypeImages);
            }
            skinTypeImagesRepository.saveAll(skinTypeImagesList);
            existingSkinType.setSkinTypeImages(skinTypeImagesList);
        }
        else{
            existingSkinType.setSkinTypeImages(existingSkinType.getSkinTypeImages());
        }
        return skinTypeRepository.save(existingSkinType);
    }

    public SkinTypes deleteSkinType(String id) {
        Optional<SkinTypes> x = skinTypeRepository.findById(id);
        if (x.isPresent()) {
            SkinTypes skinType = x.get();
            skinType.setStatus(SkinTypeEnums.INACTIVE.getSkin_type_status());
            return skinTypeRepository.save(skinType);
        }
        return null;
    }

    public void deleteAllSkinTypes() {
        skinTypeRepository.deleteAll();
    }

    public List<SkinTypes> addListOfSkinTypes(List<SkinTypes> x) {
        return skinTypeRepository.saveAll(x);
    }

    public List<SkinTypes> getAllSkinTypeNames() {
        return skinTypeRepository.findAllBySkinTypeByName();
    }

    public String getSkinTypeNameById(String name) {
//        return skinTypeRepository.findBySkinName(name).getSkinTypeId();
        Optional<SkinTypes> x  = skinTypeRepository.findBySkinName(name);
        if (x.isPresent()) {
            return x.get().getSkinTypeId();
        }
        return null;
    }


    public List<SkinTypes> searchSkinTypes(String name) {
        return skinTypeRepository.findBySkinNameContaining(name);
    }


}

package application.havenskin.services;

import application.havenskin.dataAccess.SkinTypeDTO;
import application.havenskin.enums.SkinTypeEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.SkinTypeImages;
import application.havenskin.models.SkinTypes;
import application.havenskin.repositories.SkinTypeImagesRepository;
import application.havenskin.repositories.SkinTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private CloundinaryServiceImpl cloundinaryService;
    @Autowired
    private SkinTypeImagesRepository imagesRepository;
    public List<SkinTypes> getAllSkinTypes() {
        return skinTypeRepository.findAll();
    }
    public SkinTypes getSkinTypeById(String id) {
        return skinTypeRepository.findBySkinTypeId(id);
    }
    public SkinTypes createSkinType(SkinTypeDTO skinType, List<MultipartFile> images) throws IOException {
        SkinTypes x = mapper.toSkinTypes(skinType);
        SkinTypes saved = skinTypeRepository.save(x);
        if(skinType.getImages() != null && !skinType.getImages().isEmpty()) {
            for (MultipartFile file : skinType.getImages() ) {
                String imageUrl = cloundinaryService.uploadImageFile(file);
                SkinTypeImages skinTypeImages = new SkinTypeImages();
                skinTypeImages.setImageURL(imageUrl);
                skinTypeImages.setSkinTypeId(x.getSkinTypeId());
                skinTypeImagesRepository.save(skinTypeImages);
            }
        }
        return saved;
    }
    public SkinTypes updateSkinType(String id, SkinTypeDTO skinType) {
        SkinTypes x = skinTypeRepository.findBySkinTypeId(id);
        if(x == null){
            throw new RuntimeException("SkinType not found");
        }
        mapper.updateSkinType(x, skinType);
        return skinTypeRepository.save(x);
    }
    public SkinTypes deleteSkinType(String id) {
        Optional<SkinTypes> x = skinTypeRepository.findById(id);
        if(x.isPresent()){
            SkinTypes skinType = x.get();
            skinType.setStatus(SkinTypeEnums.DELETED.getSkin_type_status());
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
    public List<String> getAllSkinTypeNames() {
        return skinTypeRepository.findAllBySkinTypeByName();
    }
    public String getSkinTypeNameByName(String name) {
        return skinTypeRepository.findBySkinName(name).getSkinTypeId();
    }

}

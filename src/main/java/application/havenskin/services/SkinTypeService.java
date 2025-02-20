package application.havenskin.services;

import application.havenskin.dataAccess.SkinTypeDTO;
import application.havenskin.enums.SkinTypeEnums;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.SkinTypes;
import application.havenskin.repositories.SkinTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkinTypeService {
    @Autowired
    private SkinTypesRepository skinTypeRepository;
    @Autowired
    private Mapper mapper;
    public List<SkinTypes> getAllSkinTypes() {
        return skinTypeRepository.findAll();
    }
    public SkinTypes getSkinTypeById(String id) {
        return skinTypeRepository.findBySkinTypeId(id);
    }
    public SkinTypes createSkinType(SkinTypeDTO skinType) {
        SkinTypes x = mapper.toSkinTypes(skinType);
        return skinTypeRepository.save(x);
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

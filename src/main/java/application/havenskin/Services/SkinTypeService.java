package application.havenskin.services;

import application.havenskin.dataAccess.SkinTypeDTO;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.SkinTypes;
import application.havenskin.repositories.SkinTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public SkinTypes createSkinType(SkinTypes skinType) {
        return skinTypeRepository.save(skinType);
    }
    public SkinTypes updateSkinType(String id, SkinTypeDTO skinType) {
      SkinTypes x = skinTypeRepository.findBySkinTypeId(id);
      if(x == null){
          throw new RuntimeException("SkinType not found");
      }
      mapper.updateSkinType(x, skinType);
      return skinTypeRepository.save(x);

    }
    public void deleteSkinType(String id) {
        skinTypeRepository.deleteById(id);
    }
    public void deleteAllSkinTypes() {
        skinTypeRepository.deleteAll();
    }
    public List<SkinTypes> addListOfSkinTypes(List<SkinTypes> x) {
        return skinTypeRepository.saveAll(x);
    }
}

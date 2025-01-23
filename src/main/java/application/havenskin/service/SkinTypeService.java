package application.havenskin.service;

import application.havenskin.BusinessObject.Models.SkinTypes;
import application.havenskin.repository.SkinTestsRepository;
import application.havenskin.repository.SkinTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkinTypeService {
    @Autowired
    private SkinTypesRepository skinTypeRepository;
    public List<SkinTypes> getAllSkinTypes() {
        return skinTypeRepository.findAll();
    }
    public SkinTypes getSkinTypeById(String id) {
        return skinTypeRepository.findBySkinTypeId(id);
    }
    public SkinTypes createSkinType(SkinTypes skinType) {
        return skinTypeRepository.save(skinType);
    }
    public SkinTypes updateSkinType(String id,SkinTypes skinType) {
        if(getSkinTypeById(id) == null) {
            throw new RuntimeException("SkinType with id " + id + " not found");
        }
        return skinTypeRepository.save(skinType);
    }
    public void deleteSkinType(String id) {
        skinTypeRepository.deleteById(id);
    }
    public void deleteAllSkinTypes() {
        skinTypeRepository.deleteAll();
    }
}

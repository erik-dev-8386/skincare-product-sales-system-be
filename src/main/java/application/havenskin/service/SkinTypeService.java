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

    public SkinTypes getSkinTypeByName(String skinName) {
        return skinTypeRepository.findBySkinName(skinName);
    }

    public SkinTypes createSkinType(SkinTypes skinType) {
        return skinTypeRepository.save(skinType);
    }

    public SkinTypes updateSkinType(String id, SkinTypes skinType) {
        SkinTypes existingSkinType = skinTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SkinType not found"));
        if (existingSkinType.getSkinName() != null) {
            existingSkinType.setSkinName(skinType.getSkinName());
        }
        if (existingSkinType.getDescription() != null) {
            existingSkinType.setDescription(skinType.getDescription());
        }
        if (existingSkinType.getMaxMark() != 0) {
            existingSkinType.setMaxMark(skinType.getMaxMark());
        }
        if (existingSkinType.getMinMark() != 0) {
            existingSkinType.setMinMark(skinType.getMinMark());
        }
        if (existingSkinType.getSkinTypeImages() != null) {
            existingSkinType.setSkinTypeImages(skinType.getSkinTypeImages());
        }
        if (existingSkinType.getPlanSkinCare() != null) {
            existingSkinType.setPlanSkinCare(skinType.getPlanSkinCare());
        }
        if (existingSkinType.getResultTests() != null) {
            existingSkinType.setResultTests(skinType.getResultTests());
        }

        return skinTypeRepository.save(existingSkinType);
    }

    public void deleteSkinType(String id) {
        skinTypeRepository.deleteById(id);
    }

    public void deleteAllSkinTypes() {
        skinTypeRepository.deleteAll();
    }
}

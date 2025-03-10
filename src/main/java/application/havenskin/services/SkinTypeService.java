package application.havenskin.services;

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

    public List<SkinTypes> getAllSkinTypes() {
        return skinTypeRepository.findAll();
    }

    public SkinTypes getSkinTypeById(String id) {
        return skinTypeRepository.findBySkinTypeId(id);
    }

    public Optional<SkinTypes> getSkinTypeByName(String skinName) {
        return skinTypeRepository.findBySkinName(skinName);
    }

    public SkinTypes createSkinType(SkinTypes skinType) {
        return skinTypeRepository.save(skinType);
    }

    public SkinTypes updateSkinType(String id, SkinTypes skinType) {
        SkinTypes existingSkinType = skinTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SkinType not found"));
        if (skinType.getSkinName() != null) {
            existingSkinType.setSkinName(skinType.getSkinName());
        }
        if (skinType.getDescription() != null) {
            existingSkinType.setDescription(skinType.getDescription());
        }
        if (skinType.getMaxMark() != 0) {
            existingSkinType.setMaxMark(skinType.getMaxMark());
        }
        if (skinType.getMinMark() != 0) {
            existingSkinType.setMinMark(skinType.getMinMark());
        }
        if (skinType.getSkinTypeImages() != null) {
            existingSkinType.setSkinTypeImages(skinType.getSkinTypeImages());
        }
        if (skinType.getPlanSkinCare() != null) {
            existingSkinType.setPlanSkinCare(skinType.getPlanSkinCare());
        }
        if (skinType.getResultTests() != null) {
            existingSkinType.setResultTests(skinType.getResultTests());
        }
        if (skinType.getStatus() != 0) {
            existingSkinType.setStatus(skinType.getStatus());
        }

        return skinTypeRepository.save(existingSkinType);
    }

    public void softDeleteSkinType(String id) {
        SkinTypes skinType = skinTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SkinType not found"));
        skinType.setStatus((byte)0);
        skinTypeRepository.save(skinType);
    }

    public void deleteAllSkinTypes() {
        skinTypeRepository.deleteAll();
    }
}

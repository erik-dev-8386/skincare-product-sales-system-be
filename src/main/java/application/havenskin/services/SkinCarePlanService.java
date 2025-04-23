package application.havenskin.services;

import application.havenskin.dataAccess.PlanSkinCareDTO;
import application.havenskin.enums.SkinCarePlanEnum;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.SkinCaresPlan;
import application.havenskin.models.SkinTypes;
import application.havenskin.repositories.PlanSkinCareRepository;
import application.havenskin.repositories.SkinTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SkinCarePlanService {

    @Autowired
    private PlanSkinCareRepository planSkinCareRepository;

    @Autowired
    private SkinTypesRepository skinTypesRepository;

    @Autowired
    private Mapper mapper;

    // hàm này gọi bên admin
    public List<SkinCaresPlan> getAllSkinCarePlans() {
        return planSkinCareRepository.findAll();
    }

    // hàm này gọi bên giao diện người dùng
    public List<SkinCaresPlan> getAllSkinCarePlansByStatus() {
        return planSkinCareRepository.findByStatus((byte) 1);
    }

    public SkinCaresPlan createPlanSkinCare(String skinCareName, PlanSkinCareDTO x) {
        if (x.getDescription() == null || x.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description không được để trống");
        }

        SkinTypes skinTypes = skinTypesRepository.findBySkinName(skinCareName).orElseThrow(() -> new RuntimeException("Skin Type Not Found"));

        boolean planExists = planSkinCareRepository.existsByDescriptionAndSkinType_SkinTypeId(
                x.getDescription(),
                skinTypes.getSkinTypeId()
        );
//        if (planExists) {
//            throw new RuntimeException("Loại da này đã có lộ trình với description tương tự");
//        }

        SkinCaresPlan skinCaresPlan = mapper.toSkinCaresPlan(x);
        skinCaresPlan.setSkinType(skinTypes);
        skinCaresPlan.setStatus(SkinCarePlanEnum.ACTIVE.getSkinCarePlan_status());
        SkinCaresPlan saved = planSkinCareRepository.save(skinCaresPlan);
        skinTypes.getPlanSkinCare().add(saved);
        skinTypesRepository.save(skinTypes);
//        skinTypes.setPlanSkinCare(skinCaresPlan);
        return skinCaresPlan;
    }

    public List<SkinCaresPlan> getPlanSkinCare(String skinName) {
        SkinTypes x = skinTypesRepository.findBySkinName(skinName).orElseThrow(() -> new RuntimeException("Skin Type Not Found"));

        List<SkinCaresPlan> skinCaresPlan = x.getPlanSkinCare();
        if (skinCaresPlan == null || skinCaresPlan.isEmpty()) {
            throw new RuntimeException("Không tìm thấy lộ trình chăm sóc da cho 1 loại da này");
        }
        return skinCaresPlan;
    }

    public SkinCaresPlan updatePlanSkinCares(String skinCareName, String description, PlanSkinCareDTO x) {
        SkinTypes skinTypes = skinTypesRepository.findBySkinName(skinCareName).orElseThrow(() -> new RuntimeException("Skin Type Not Found"));

        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescriptionAndSkinType_SkinTypeId(description, skinTypes.getSkinTypeId());
        if (skinCaresPlan == null) {
            throw new RuntimeException("Không tìm thấy lộ trình chăm sóc da cho loại da này");
        }

        mapper.updateSkincaresPlan(skinCaresPlan, x);
//        if(x.getSkinName() == null || x.getSkinName().trim().isEmpty()) {
//            skinCaresPlan.setSkinType(skinTypes);
//        }
////        skinCaresPlan.setSkinType(skinTypes);
        if (x.getSkinName() != null) {
            SkinTypes newSkinTypes = skinTypesRepository.findBySkinName(x.getSkinName())
                    .orElseThrow(() -> new RuntimeException("New Skin Type Not Found"));

            // Kiểm tra xem lộ trình với description này đã tồn tại cho loại da mới chưa
            boolean exists = planSkinCareRepository.existsByDescriptionAndSkinType_SkinTypeId(
                    skinCaresPlan.getDescription(),
                    newSkinTypes.getSkinTypeId()
            );
//            if(exists) {
//                throw new RuntimeException("Loại da mới đã có lộ trình với description tương tự");
//            }

            // Cập nhật loại da mới
            skinCaresPlan.setSkinType(newSkinTypes);
            skinTypesRepository.save(newSkinTypes);
        }

        planSkinCareRepository.save(skinCaresPlan);

        return skinCaresPlan;
    }

    public SkinCaresPlan deletePlanSkinCare(String skinName, String description) {
        SkinTypes skinTypes = skinTypesRepository.findBySkinName(skinName).orElseThrow(() -> new RuntimeException("Skin Type Not Found"));
//
//        SkinCaresPlan skinCaresPlan = skinTypes.getPlanSkinCare();
        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescriptionAndSkinType_SkinTypeId(description, skinTypes.getSkinTypeId());
        if (skinCaresPlan == null) {
            throw new RuntimeException("Không tìm thấy lộ trình chăm sóc da cho loại da này");
        }
        skinCaresPlan.setStatus(SkinCarePlanEnum.INACTIVE.getSkinCarePlan_status());
        planSkinCareRepository.save(skinCaresPlan);
        return skinCaresPlan;
    }

    public List<SkinCaresPlan> searchSkinCarePlan(String description) {
        return planSkinCareRepository.findByDescriptionContaining(description);
    }

    public List<SkinCaresPlan> getAllSkinCarePlanDescriptions() {
        return planSkinCareRepository.findByStatus((byte) 1);
    }

}

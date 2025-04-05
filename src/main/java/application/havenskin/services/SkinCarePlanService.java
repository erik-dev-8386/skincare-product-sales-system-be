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
        return planSkinCareRepository.findAllSkinCarePlanByStatus();
    }

    public PlanSkinCareDTO createPlanSkinCare(String skinCareName,PlanSkinCareDTO x) {
        SkinTypes skinTypes = skinTypesRepository.findBySkinName(skinCareName).orElseThrow(()-> new RuntimeException("Skin Type Not Found"));
        SkinCaresPlan skinCaresPlan = mapper.toSkinCaresPlan(x);
        skinCaresPlan.setSkinType(skinTypes);

        planSkinCareRepository.save(skinCaresPlan);

        skinTypes.setPlanSkinCare(skinCaresPlan);
        skinTypesRepository.save(skinTypes);
        return x;
    }

    public SkinCaresPlan getPlanSkinCare(String skinCareName) {
        SkinTypes x = skinTypesRepository.findBySkinName(skinCareName).orElseThrow(()-> new RuntimeException("Skin Type Not Found"));

        SkinCaresPlan skinCaresPlan = x.getPlanSkinCare();

        if(skinCaresPlan == null) {
            throw new RuntimeException("Không tìm thấy lộ trình chăm sóc da cho 1 loại da này");
        }
        return skinCaresPlan;
    }
    public SkinCaresPlan updatePlanSkinCares(String skinCareName,String description, PlanSkinCareDTO x) {
        SkinTypes skinTypes = skinTypesRepository.findBySkinName(skinCareName).orElseThrow(()-> new RuntimeException("Skin Type Not Found"));

        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescriptionAndSkinType_SkinTypeId(description, skinTypes.getSkinTypeId());
        if(skinCaresPlan == null) {
            throw new RuntimeException("Không tìm thấy lộ trình chăm sóc da cho loại da này");
        }

        mapper.updateSkincaresPlan(skinCaresPlan, x);

        planSkinCareRepository.save(skinCaresPlan);

        return skinCaresPlan;
    }
    public SkinCaresPlan deletePlanSkinCare(String skinName,String description) {
        SkinTypes skinTypes = skinTypesRepository.findBySkinName(skinName).orElseThrow(()-> new RuntimeException("Skin Type Not Found"));
//
//        SkinCaresPlan skinCaresPlan = skinTypes.getPlanSkinCare();
        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescriptionAndSkinType_SkinTypeId(description, skinTypes.getSkinTypeId());
        if(skinCaresPlan == null) {
            throw new RuntimeException("Không tìm thấy lộ trình chăm sóc da cho loại da này");
        }
        skinCaresPlan.setStatus(SkinCarePlanEnum.INACTIVE.getSkinCarePlan_status());
        planSkinCareRepository.save(skinCaresPlan);
        return skinCaresPlan;
    }

    public List<SkinCaresPlan> searchSkinCarePlan(String description) {
        return planSkinCareRepository.findAllSkinCarePlanByDescription(description);
    }

}

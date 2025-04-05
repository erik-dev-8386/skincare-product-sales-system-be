package application.havenskin.services;

import application.havenskin.dataAccess.MiniSkinCarePlanDTO;
import application.havenskin.enums.MiniSkinCarePlanEnum;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.MiniSkinCarePlan;
import application.havenskin.models.SkinCaresPlan;
import application.havenskin.repositories.MiniSkinCareRepository;
import application.havenskin.repositories.PlanSkinCareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MiniSkinCareService {

    @Autowired
    private MiniSkinCareRepository miniSkinCareRepository;

    @Autowired
    private PlanSkinCareRepository planSkinCareRepository;

    @Autowired
    private Mapper mapper;

    // hàm này gọi bên admin
    public List<MiniSkinCarePlan> getAllMiniSkinCarePlans() {
        return miniSkinCareRepository.findAll();
    }

    // hàm này gọi bên users thường
    public List<MiniSkinCarePlan> getAllMiniSkinCarePlansByStatus() {
        return miniSkinCareRepository.findAllMiniSkinCarePlanByStatus();
    }
    public MiniSkinCarePlanDTO addMiniSkinCarePlan(String description,MiniSkinCarePlanDTO miniSkinCarePlanDTO ) {
        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescription(description);
        if (skinCaresPlan == null) {
            throw new RuntimeException("Không có lộ trình chăm sóc da này!");
        }
        MiniSkinCarePlan miniSkinCarePlan = mapper.toMiniSkinCarePlan(miniSkinCarePlanDTO);

        miniSkinCarePlan.setSkinCarePlan(skinCaresPlan);

        return miniSkinCarePlanDTO;
    }

    public MiniSkinCarePlan getMiniSkinCarePlan(String actions) {
        return miniSkinCareRepository.findByAction(actions);
    }

    public MiniSkinCarePlan updateMiniSkinCarePlan(String actions, MiniSkinCarePlanDTO miniSkinCarePlanDTO) {
        MiniSkinCarePlan x = miniSkinCareRepository.findByAction(actions);
        if (x == null) {
            throw new RuntimeException("Không có lộ trình chăm sóc da này!");
        }
        mapper.updateMiniSkinCarePlan(x, miniSkinCarePlanDTO);
        miniSkinCareRepository.save(x);
        return x;
    }

    public MiniSkinCarePlan deleteMiniSkinCarePlan(String actions) {
        MiniSkinCarePlan x = miniSkinCareRepository.findByAction(actions);
        if (x == null) {
            throw new RuntimeException("Không có lộ trình chăm sóc da này!");
        }
        x.setStatus(MiniSkinCarePlanEnum.INACTIVE.getMini_skin_care_plan_status());
        miniSkinCareRepository.save(x);
        return x;
    }

    public List<MiniSkinCarePlan> getMiniPlansByDescription(String description) {
        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescription(description);
        if (skinCaresPlan == null) {
            throw new RuntimeException("Không tìm thấy lộ trình da với description: " + description);
        }
        return skinCaresPlan.getMiniSkinCarePlans();
    }
    public List<MiniSkinCarePlan> searchMiniSkinCarePlan(String actions) {
        return miniSkinCareRepository.findAllMiniSkinCarePlanByAction(actions);
    }



}

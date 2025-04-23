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
import java.util.stream.Collectors;

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
        return miniSkinCareRepository.findByStatus((byte) 1);
    }
    public MiniSkinCarePlan addMiniSkinCarePlan(String description,MiniSkinCarePlanDTO miniSkinCarePlanDTO ) {
        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescriptionAndStatus(description, (byte) 1);
        if (skinCaresPlan == null) {
            throw new RuntimeException("Không có lộ trình chăm sóc da này!");
        }
        List<MiniSkinCarePlan> existingSteps = miniSkinCareRepository.findByStepNumberAndSkinCarePlanIdAndStatus(
                        miniSkinCarePlanDTO.getStepNumber(),
                        skinCaresPlan.getSkinCarePlanId(),
                        MiniSkinCarePlanEnum.ACTIVE.getMini_skin_care_plan_status()
                );

        if (!existingSteps.isEmpty()) {
            throw new RuntimeException("Đã tồn tại bước " + miniSkinCarePlanDTO.getStepNumber() +
                    " với trạng thái hoạt động trong lộ trình này!");
        }
        MiniSkinCarePlan miniSkinCarePlan = new MiniSkinCarePlan();
        miniSkinCarePlan = mapper.toMiniSkinCarePlan(miniSkinCarePlanDTO);
//        miniSkinCarePlan.setStepNumber(miniSkinCarePlanDTO.getStepNumber());
//        miniSkinCarePlan.setAction(miniSkinCarePlanDTO.getAction());

        miniSkinCarePlan.setSkinCarePlan(skinCaresPlan);
        skinCaresPlan.getMiniSkinCarePlans().add(miniSkinCarePlan);
        planSkinCareRepository.save(skinCaresPlan);
        return miniSkinCarePlan;
    }

//    public MiniSkinCarePlan getMiniSkinCarePlan(String actions) {
//        return miniSkinCareRepository.findByAction(actions);
//    }

    public MiniSkinCarePlan updateMiniSkinCarePlan(String actions, String description,MiniSkinCarePlanDTO miniSkinCarePlanDTO) {
        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescriptionAndStatus(description, (byte) 1);
        MiniSkinCarePlan x = miniSkinCareRepository.findByActionAndSkinCarePlanId(actions, skinCaresPlan.getSkinCarePlanId());
        if (x == null) {
            throw new RuntimeException("Không có lộ trình chăm sóc da này!");
        }
        if(miniSkinCarePlanDTO.getStatus() == MiniSkinCarePlanEnum.ACTIVE.getMini_skin_care_plan_status()){
            List<MiniSkinCarePlan> active = miniSkinCareRepository.findByStepNumberAndSkinCarePlanIdAndStatus(miniSkinCarePlanDTO.getStepNumber(), skinCaresPlan.getSkinCarePlanId(), MiniSkinCarePlanEnum.ACTIVE.getMini_skin_care_plan_status());
            if(!active.isEmpty() && !active.get(0).getMiniSkinCarePlanId().equals(x.getMiniSkinCarePlanId())) {
                throw new RuntimeException("Đã tồn tại buớc " + miniSkinCarePlanDTO.getStepNumber());
            }

        }
        mapper.updateMiniSkinCarePlan(x, miniSkinCarePlanDTO);
        x.setSkinCarePlan(skinCaresPlan);
        miniSkinCareRepository.save(x);
        return x;
    }

    public MiniSkinCarePlan deleteMiniSkinCarePlan(String actions, String description) {
        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescriptionAndStatus(description, (byte) 1);
        MiniSkinCarePlan x = miniSkinCareRepository.findByActionAndSkinCarePlanId(actions, skinCaresPlan.getSkinCarePlanId());
        if (x == null) {
            throw new RuntimeException("Không có lộ trình chăm sóc da này!");
        }
        x.setStatus(MiniSkinCarePlanEnum.INACTIVE.getMini_skin_care_plan_status());
        miniSkinCareRepository.save(x);
        return x;
    }

    public List<MiniSkinCarePlan> getMiniPlansByDescription(String description) {
        SkinCaresPlan skinCaresPlan = planSkinCareRepository.findByDescriptionAndStatus(description, (byte) 1);
        if (skinCaresPlan == null) {
            throw new RuntimeException("Không tìm thấy lộ trình da với description: " + description);
        }
        return skinCaresPlan.getMiniSkinCarePlans().stream()
                .filter(plan -> plan.getStatus() == MiniSkinCarePlanEnum.ACTIVE.getMini_skin_care_plan_status())
                .collect(Collectors.toList());
    }
    public List<MiniSkinCarePlan> searchMiniSkinCarePlan(String actions) {
        return miniSkinCareRepository.findByActionContaining(actions);
    }



}

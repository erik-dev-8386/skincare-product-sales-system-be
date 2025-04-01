package application.havenskin.services;

import application.havenskin.dataAccess.ResultTestDto;
import application.havenskin.dataAccess.SubmitAnswerDto;
import application.havenskin.dataAccess.SubmitTestRequestDto;
import application.havenskin.dataAccess.UserAnswerDto;
import application.havenskin.models.*;
import application.havenskin.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResultTestService {
    @Autowired
    private ResultTestsRepository resultTestsRepository;
    @Autowired
    private UserAnswersRepository userAnswersRepository;
    @Autowired
    private AnswerRepository answersRepository;
    @Autowired
    private SkinTestsRepository skinTestsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SkinTypesRepository skinTypesRepository;
    @Autowired
    private QuestionsRepository questionsRepository;
    @Autowired
    private AnswerRepository answerRepository;
    public ResultTests processTestResult(SubmitTestRequestDto request) {
        //tạo result_test
        Users users = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        String userId = users.getUserId();
        ResultTests resultTest = new ResultTests();
        resultTest.setUserId(userId);
        resultTest.setCreatedTime(new Date());
        resultTest.setSkinTestId("1");
        resultTest = resultTestsRepository.save(resultTest);

        double totalMark = 0;

        // Lưu từng userAnswer
        for (SubmitAnswerDto ans : request.getAnswers()) {
            Answers answerObj = answersRepository.findById(ans.getAnswerId())
                    .orElseThrow(() -> new RuntimeException("Answer not found"));
            //tạo users_answers
            UserAnswers userAnswer = new UserAnswers();
            userAnswer.setResultTest(resultTest);
            userAnswer.setQuestionId(ans.getQuestionId());
            userAnswer.setAnswerId(ans.getAnswerId());
            userAnswer.setMark(answerObj.getMark());

            totalMark += answerObj.getMark();
            userAnswersRepository.save(userAnswer);
        }


        // 4) Tính loại da
        resultTest.setTotalMark(totalMark);
        // xac dinh theo loai da
       // resultTest.setSkinTestId("1");
        String skinName = determineSkinType(totalMark);
        SkinTypes skinTypes = skinTypesRepository.findBySkinName(skinName).orElseThrow(()->new RuntimeException("Skin type not found"));
        resultTest.setSkinType(skinTypes);
        resultTest.setSkinTypeId(skinTypes.getSkinTypeId());
        return resultTestsRepository.save(resultTest);
    }


    private String determineSkinType(double totalMark) {
        List<SkinTypes> allSkinTypes = skinTypesRepository.findAll();

        for (SkinTypes x : allSkinTypes) {
            if(totalMark >= x.getMinMark() && totalMark <= x.getMaxMark()) {
                return x.getSkinName();
//                x.getSkinTypeId();
            }
        }
        throw new RuntimeException("No matching SkinType for totalMark " + totalMark);
    }
    public ResultTestDto getResultTestsWithDetails(String resultTestId) {
        //Lấy resultTest
        ResultTests rt = resultTestsRepository.findById(resultTestId)
                .orElseThrow(() -> new RuntimeException("ResultTest not found"));

        // Tạo DTO
        ResultTestDto dto = new ResultTestDto();
        dto.setResultTestId(rt.getResultTestId());
        dto.setUserId(rt.getUserId());
//        dto.setSkinTestId(rt.getSkinTestId());
        dto.setEmail(rt.getUser().getEmail());
        dto.setTotalMark(rt.getTotalMark());
        dto.setSkinTypeId(rt.getSkinTypeId());
        dto.setCreatedTime(rt.getCreatedTime());

        // 1) Lấy tên user
        if (rt.getUserId() != null) {
            userRepository.findById(rt.getUserId()).ifPresent(user -> {
                dto.setFirstName(user.getFirstName());
                dto.setLastName(user.getLastName());
            });
        }

        // 2) Lấy tên loại da
        if (rt.getSkinTypeId() != null) {
            // Tìm trong bảng skin_types
            skinTypesRepository.findById(rt.getSkinTypeId()).ifPresent(st -> {
                dto.setSkinName(st.getSkinName());
            });
        }

//        // 3) Lấy danh sách user_answers
//        List<UserAnswers> uaList = userAnswersRepository.findByResultTest_ResultTestId(rt.getResultTestId());
//        List<UserAnswerDto> uaDtoList = uaList.stream().map(ua -> {
//            UserAnswerDto uad = new UserAnswerDto();
//            uad.setUserAnswerId(ua.getUserAnswersId());
//            uad.setQuestionId(ua.getQuestionId());
//
//            uad.setAnswerId(ua.getAnswerId());
//            uad.setMark(ua.getMark());
//
//            // Lấy nội dung câu hỏi
//            if (ua.getQuestion() != null) {
//                uad.setQuestionContent(ua.getQuestion().getQuestionContent());
//            } else {
//                questionsRepository.findById(ua.getQuestionId()).ifPresent(q -> uad.setQuestionContent(q.getQuestionContent()));
//            }
//
//            // Lấy nội dung câu trả lời
//            if (ua.getAnswer() != null) {
//                uad.setAnswerContent(ua.getAnswer().getAnswerContent());
//            } else {
//                answersRepository.findById(ua.getAnswerId()).ifPresent(a -> uad.setAnswerContent(a.getAnswerContent()));
//            }
//
//
//
//            return uad;
//        }).collect(Collectors.toList());
//
//        dto.setUserAnswers(uaDtoList);
//
        return dto;
    }

    public List<ResultTestDto> getAllResultTests() {
        List<ResultTests> resultTestsList = resultTestsRepository.findAll();
        return resultTestsList.stream().map(rt -> {
            ResultTestDto dto = new ResultTestDto();
            dto.setResultTestId(rt.getResultTestId());
            dto.setUserId(rt.getUserId());
//            dto.setSkinTestId(rt.getSkinTestId());
            dto.setTotalMark(rt.getTotalMark());
            dto.setSkinTypeId(rt.getSkinTypeId());
            dto.setCreatedTime(rt.getCreatedTime());

            // Lấy tên user
            if (rt.getUserId() != null) {
                userRepository.findById(rt.getUserId()).ifPresent(user -> {
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                });
            }

            // Lấy tên loại da
            if (rt.getSkinTypeId() != null) {
                skinTypesRepository.findById(rt.getSkinTypeId()).ifPresent(st -> {
                    dto.setSkinName(st.getSkinName());
                });
            }

            return dto;
        }).collect(Collectors.toList());
    }

    public String deleteResultTestById(String resultTestId) {
        ResultTests rt = resultTestsRepository.findById(resultTestId)
                .orElseThrow(() -> new RuntimeException("ResultTest not found"));
        rt.setStatus((byte) 0);
        resultTestsRepository.save(rt);
        return "Deleted ResultTest";
    }

    public ResultTestDto updateStatus(String resultTestId, ResultTestDto rt) {

        ResultTests exitingResultTest = resultTestsRepository.findById(resultTestId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy ResultTest với ID: " + resultTestId));

        if (rt.getStatus() != 0) {
            exitingResultTest.setStatus(rt.getStatus());
        }


        resultTestsRepository.save(exitingResultTest);

        // Chuyển đổi sang DTO để trả về
        ResultTestDto dto = new ResultTestDto();
  //      dto.setResultTestId(exitingResultTest.getResultTestId());
        dto.setUserId(exitingResultTest.getUserId());
//        dto.setSkinTestId(exitingResultTest.getSkinTestId());
        dto.setTotalMark(exitingResultTest.getTotalMark());
        dto.setSkinTypeId(exitingResultTest.getSkinTypeId());
        dto.setCreatedTime(exitingResultTest.getCreatedTime());
        dto.setStatus(exitingResultTest.getStatus()); // Cập nhật trạng thái mới

        return dto;
    }

    public List<ResultTestDto> getResultTestByEmail(String email) {
        // Tìm user theo email
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Tìm tất cả bài test của user
        List<ResultTests> resultTests = resultTestsRepository.findByUserId(user.getUserId());
        if (resultTests.isEmpty()) {
            throw new RuntimeException("No ResultTest found for user: " + email);
        }

        //Chuyển danh sách kết quả thành DTO
//        List<ResultTestDto> resultDtos = resultTests.stream().map(rt -> {
//            String skinName = skinTypesRepository.findBySkinName(rt.getSkinType().getSkinName())
//                    .map(SkinTypes::getSkinName)
//                    .orElse("Unknown");
//
//            ResultTestDto dto = new ResultTestDto();
//            dto.setResultTestId(rt.getResultTestId());
//            dto.setEmail(rt.getUser().getEmail());
//            dto.setFirstName(rt.getUser().getFirstName());
//            dto.setLastName(rt.getUser().getLastName());
//            dto.setSkinTypeId(rt.getSkinType().getSkinTypeId());
//            dto.setUserId(rt.getUserId());
//            dto.setTotalMark(rt.getTotalMark());
//            dto.setSkinName(skinName);
//            dto.setCreatedTime(rt.getCreatedTime());
//
//            return dto;
//        }).collect(Collectors.toList());
//        List<ResultTestDto> resultDtos = resultTests.stream().map(rt -> {
//            String skinName = skinTypesRepository.findBySkinName(rt.getSkinType().getSkinName())
//                    .map(SkinTypes::getSkinName)
//                    .orElse("Unknown");
//        List<ResultTestDto> resultDtos = resultTests.stream().map(rt -> {
//            String skinName = skinTypesRepository.findBySkinName(rt.getSkinType().getSkinName())
//                    .map(SkinTypes::getSkinName)
//                    .orElse("Unknown");
        List<ResultTestDto> resultDtos = resultTests.stream().map(rt -> {
            String skinName = skinTypesRepository.findBySkinName(rt.getSkinType().getSkinName())
                    .map(SkinTypes::getSkinName)
                    .orElse("Unknown");

            ResultTestDto dto = new ResultTestDto();
            dto.setResultTestId(rt.getResultTestId());
            dto.setEmail(rt.getUser().getEmail());
            dto.setFirstName(rt.getUser().getFirstName());
            dto.setLastName(rt.getUser().getLastName());
            dto.setSkinTypeId(rt.getSkinType().getSkinTypeId());
            dto.setUserId(rt.getUserId());
            dto.setTotalMark(rt.getTotalMark());
            dto.setSkinName(skinName);
            dto.setCreatedTime(rt.getCreatedTime());

            return dto;
        }).collect(Collectors.toList());

        return resultDtos;
    }

}

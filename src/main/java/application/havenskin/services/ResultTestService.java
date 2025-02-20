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
        // 1) Kiểm tra skinTestId
        skinTestsRepository.findById(request.getSkinTestId())
                .orElseThrow(() -> new RuntimeException("SkinTest not found"));

        // 2) Tạo result_test
        ResultTests resultTest = new ResultTests();
        resultTest.setUserId(request.getUserId());
        resultTest.setSkinTestId(request.getSkinTestId());
        resultTest.setCreatedTime(LocalDateTime.now());

        resultTest = resultTestsRepository.save(resultTest);

        double totalMark = 0;

        // 3) Lưu từng userAnswer
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
        resultTest.setSkinTypeId(determineSkinType(totalMark));
        return resultTestsRepository.save(resultTest);
    }

    private String determineSkinType(double totalMark) {
        if (totalMark <= 15) return "04618ed1-dd2b-4b4c-be04-66f441b80315";  // Da khô
        else if (totalMark <= 25) return "a134dad2-aef2-4841-b92f-58609c257543"; // Da thường
        else if (totalMark <= 35) return "9e08293c-e8a3-4c04-82e2-1a692a9ad434"; // Da hỗn hợp
        else return "41e9c6bb-5f12-4e93-bffd-cede74a01175"; // Da dầu
    }

    public ResultTestDto getResultTestsWithAnswers(String resultTestId) {
        //Lấy resultTest
        ResultTests rt = resultTestsRepository.findById(resultTestId)
                .orElseThrow(() -> new RuntimeException("ResultTest not found"));

        // Tạo DTO
        ResultTestDto dto = new ResultTestDto();
        dto.setResultTestId(rt.getResultTestId());
        dto.setUserId(rt.getUserId());
        dto.setSkinTestId(rt.getSkinTestId());
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

        // 3) Lấy danh sách user_answers
        List<UserAnswers> uaList = userAnswersRepository.findByResultTest_ResultTestId(rt.getResultTestId());
        List<UserAnswerDto> uaDtoList = uaList.stream().map(ua -> {
            UserAnswerDto uad = new UserAnswerDto();
            uad.setUserAnswerId(ua.getUserAnswersId());
            uad.setQuestionId(ua.getQuestionId());

            uad.setAnswerId(ua.getAnswerId());
            uad.setMark(ua.getMark());

            // Lấy nội dung câu hỏi
            if (ua.getQuestion() != null) {
                uad.setQuestionContent(ua.getQuestion().getQuestionContent());
            } else {
                questionsRepository.findById(ua.getQuestionId()).ifPresent(q -> uad.setQuestionContent(q.getQuestionContent()));
            }

            // Lấy nội dung câu trả lời
            if (ua.getAnswer() != null) {
                uad.setAnswerContent(ua.getAnswer().getAnswerContent());
            } else {
                answersRepository.findById(ua.getAnswerId()).ifPresent(a -> uad.setAnswerContent(a.getAnswerContent()));
            }



            return uad;
        }).collect(Collectors.toList());

        dto.setUserAnswers(uaDtoList);

        return dto;
    }

    public List<ResultTestDto> getAllResultTests() {
        // Lấy danh sách tất cả result_tests từ database
        List<ResultTests> resultTestsList = resultTestsRepository.findAll();

        // Chuyển đổi sang DTO
        return resultTestsList.stream().map(rt -> {
            ResultTestDto dto = new ResultTestDto();
            dto.setResultTestId(rt.getResultTestId());
            dto.setUserId(rt.getUserId());
            dto.setSkinTestId(rt.getSkinTestId());
            dto.setTotalMark(rt.getTotalMark());
            dto.setSkinTypeId(rt.getSkinTypeId());
            dto.setCreatedTime(rt.getCreatedTime());

            // 1) Lấy tên user
            userRepository.findById(rt.getUserId()).ifPresent(user -> {
                dto.setFirstName(user.getFirstName());
                dto.setLastName(user.getLastName());
            });

            // 2) Lấy tên loại da
            skinTypesRepository.findById(rt.getSkinTypeId()).ifPresent(st -> {
                dto.setSkinName(st.getSkinName());
            });

            // 3) Lấy danh sách user_answers
            List<UserAnswers> uaList = userAnswersRepository.findByResultTest_ResultTestId(rt.getResultTestId());
            List<UserAnswerDto> uaDtoList = uaList.stream().map(ua -> {
                UserAnswerDto uad = new UserAnswerDto();
                uad.setUserAnswerId(ua.getUserAnswersId());
                uad.setQuestionId(ua.getQuestionId());
                uad.setAnswerId(ua.getAnswerId());
                uad.setMark(ua.getMark());

                // Kiểm tra và lấy nội dung câu hỏi
                if (ua.getQuestion() != null) {
                    uad.setQuestionContent(ua.getQuestion().getQuestionContent());
                } else {
                    questionsRepository.findById(ua.getQuestionId()).ifPresent(q -> uad.setQuestionContent(q.getQuestionContent()));
                }

                // Kiểm tra và lấy nội dung câu trả lời
                if (ua.getAnswer() != null) {
                    uad.setAnswerContent(ua.getAnswer().getAnswerContent());
                } else {
                    answersRepository.findById(ua.getAnswerId()).ifPresent(a -> uad.setAnswerContent(a.getAnswerContent()));
                }

                return uad;
            }).collect(Collectors.toList());

            dto.setUserAnswers(uaDtoList);

            return dto;
        }).collect(Collectors.toList());
    }

    public String deleteResultTestById(String resultTestId) {
        ResultTests rt = resultTestsRepository.findById(resultTestId)
                .orElseThrow(() -> new RuntimeException("ResultTest not found"));
        resultTestsRepository.delete(rt);
        return "Deleted ResultTest";
    }

}

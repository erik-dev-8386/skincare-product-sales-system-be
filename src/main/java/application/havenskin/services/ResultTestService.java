package application.havenskin.services;

import application.havenskin.dataAccess.ResultTestDto;
import application.havenskin.dataAccess.SubmitAnswerDto;
import application.havenskin.dataAccess.SubmitTestRequestDto;
import application.havenskin.dataAccess.UserAnswerDto;
import application.havenskin.models.Answers;
import application.havenskin.models.ResultTests;
import application.havenskin.models.UserAnswers;
import application.havenskin.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
        resultTest.setCreatedTime(new Date());

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
        if (totalMark <= 15) return "Da khô";  // Da khô
        else if (totalMark <= 25) return "Da thường"; // Da thường
        else if (totalMark <= 35) return "Da hỗn hợp"; // Da hỗn hợp
        else return "Da dầu"; // Da dầu
    }
    // Lay ket qua cua khach  hang
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
        //dto.setCreatedTime(rt.getCreatedTime());

        // id:
        // userid -> firstName, lastName
        // SkintestID??
        // MARK
        // LOẠI DA -> TÊN

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
            //dto.setCreatedTime(rt.getCreatedTime());

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

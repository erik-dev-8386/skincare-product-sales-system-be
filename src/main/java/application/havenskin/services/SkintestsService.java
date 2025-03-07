package application.havenskin.services;

import application.havenskin.dataAccess.AnswersDto;
import application.havenskin.dataAccess.QuestionsResponseDto;
import application.havenskin.dataAccess.SkinTestsDto;
import application.havenskin.models.Answers;
import application.havenskin.models.Questions;
import application.havenskin.models.SkinTests;
import application.havenskin.repositories.AnswerRepository;
import application.havenskin.repositories.QuestionsRepository;
import application.havenskin.repositories.SkinTestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkintestsService {
    @Autowired
    private SkinTestsRepository skinTestRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private AnswerRepository answerRepository;

    // 1. Lấy tất cả bài test
//    public List<SkinTests> getAllSkinTests() {
//        return skinTestRepository.findAll();
//    }

    // 2. Lấy bài test theo ID
    public SkinTests getSkinTestById(String id) {
        return skinTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skin Test not found"));
    }



    // 4. Cập nhật bài test
    public SkinTests updateSkinTest(String id, SkinTests newSkinTest) {
        // Lấy bản ghi hiện có, nếu không tìm thấy thì ném ngoại lệ
        SkinTests existingTest = getSkinTestById(id);

        // Chỉ cập nhật nếu giá trị mới không phải null
        if (newSkinTest.getCreatedTime() != null) {
            existingTest.setCreatedTime(newSkinTest.getCreatedTime());
        }
        if (newSkinTest.getMaxMark() != null) {
            existingTest.setMaxMark(newSkinTest.getMaxMark());
        }
        if (newSkinTest.getStatus() != 0) {
            existingTest.setStatus(newSkinTest.getStatus());
        }

        // Lưu bản ghi đã cập nhật
        return skinTestRepository.save(existingTest);
    }

    // 5. Xóa mềm bài test (Chuyển status thành 0)
    public String deleteSkinTest(String id) {
        SkinTests existingTest = getSkinTestById(id);
        existingTest.setStatus((byte) 0);
        skinTestRepository.save(existingTest);
        return "Skin Test has been deleted";
    }

    public SkinTestsDto getSkinTestWithQuestionsAndAnswers(String skinTestId) {
        SkinTests st = skinTestRepository.findById(skinTestId)
                .orElseThrow(() -> new RuntimeException("Skin Test not found"));

        List<Questions> questions = questionsRepository.findBySkinTestId(skinTestId);

        List<QuestionsResponseDto> questionDTOs = questions.stream().map(q -> {
            QuestionsResponseDto qDto = new QuestionsResponseDto();
            qDto.setQuestionId(q.getQuestionId());
            qDto.setQuestionContent(q.getQuestionContent());
            qDto.setMaxMark(q.getMaxMark());

            // Lấy answers theo questionId
            List<Answers> ansList = answerRepository.findByQuestionId(q.getQuestionId());

            List<AnswersDto> answerDTOs = ansList.stream().map(a -> {
                AnswersDto aDto = new AnswersDto();
                aDto.setAnswerId(a.getAnswerId());
                aDto.setAnswerContent(a.getAnswerContent());
                aDto.setMark(a.getMark());
                return aDto;
            }).collect(Collectors.toList());

            qDto.setAnswers(answerDTOs);
            return qDto;
        }).collect(Collectors.toList());

        SkinTestsDto dto = new SkinTestsDto();
        dto.setSkinTestId(st.getSkinTestId());
        dto.setMaxMark(st.getMaxMark());
        dto.setStatus(st.getStatus());
        dto.setQuestions(questionDTOs);

        return dto;
    }
}

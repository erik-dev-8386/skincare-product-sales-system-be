package application.havenskin.services;

import application.havenskin.dataAccess.AnswersDto;
import application.havenskin.dataAccess.QuestionsResponseDto;
import application.havenskin.dataAccess.SkinTestsDto;
import application.havenskin.enums.QuestionEnum;
import application.havenskin.mapper.Mapper;
import application.havenskin.models.Answers;
import application.havenskin.models.Questions;
import application.havenskin.models.SkinTests;
import application.havenskin.repositories.AnswerRepository;
import application.havenskin.repositories.QuestionsRepository;
import application.havenskin.repositories.SkinTestsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SkintestsService {
    @Autowired
    private SkinTestsRepository skinTestRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private Mapper mapper;

    // 1. Lấy tất cả bài test
//    public List<SkinTests> getAllSkinTests() {
//        return skinTestRepository.findAll();
//    }

    // 2. Lấy bài test theo ID
    public SkinTests getSkinTestById(String id) {
        return skinTestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skin Test not found"));
    }
    public SkinTests createSkinTest(SkinTestsDto skinTest) {
        SkinTests x = mapper.toSkinTests(skinTest);
        return skinTestRepository.save(x);
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

        List<Questions> questions = questionsRepository.findBySkinTestIdAndStatus(skinTestId, (byte) 1);
        List<Questions> randomQuestion;
        if(questions.size() <= 10) {
            randomQuestion = getRandomQuestions(questions, questions.size());
        }
        else{
            randomQuestion = getRandomQuestions(questions, 10);
        }
        List<QuestionsResponseDto> questionDTOs = randomQuestion.stream().map(q -> {
            QuestionsResponseDto qDto = new QuestionsResponseDto();
            qDto.setQuestionId(q.getQuestionId());
            qDto.setQuestionContent(q.getQuestionContent());
            qDto.setMaxMark(q.getMaxMark());

            // Lấy answers theo questionId
            List<Answers> ansList = answerRepository.findByQuestionIdAndStatus(q.getQuestionId(), QuestionEnum.ACTIVE.getStatus());

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
    private List<Questions> getRandomQuestions(List<Questions> questions, int count) {
        List<Questions> x = new ArrayList<>(questions); // tạo bản sao cho question chứ ko thao tác trưc tiếp trên db
        Collections.shuffle(x); // hàm này là xử lý random
        // Nếu số câu hỏi ít hơn count, trả về tất cả
        return x.subList(0, Math.min(count, x.size()));
    }
}

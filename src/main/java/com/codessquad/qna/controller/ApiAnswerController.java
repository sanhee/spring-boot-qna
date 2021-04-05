package com.codessquad.qna.controller;

import com.codessquad.qna.domain.Answer;
import com.codessquad.qna.domain.Question;
import com.codessquad.qna.service.AnswerService;
import com.codessquad.qna.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by 68936@naver.com on 2021-03-16 오전 2:28
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
@RestController
@RequestMapping("/api/questions/{question.id}/answers")
public class ApiAnswerController {

    private final AnswerService answerService;
    private final QuestionService questionService;

    @Autowired
    public ApiAnswerController(AnswerService answerService, QuestionService questionService) {
        this.answerService = answerService;
        this.questionService = questionService;
    }

    @PostMapping("") // 댓글작성하고 버튼 눌렀을 때
    public Answer createAnswer(Answer answer, @PathVariable("question.id") Long questionId, HttpSession session) {
        Question question = questionService.findById(questionId);
        return answerService.save(session, question, answer);
    }

    @DeleteMapping("{answerId}")
    public Answer deleteAnswer(HttpSession session, @PathVariable Long answerId) {
        Answer answer = answerService.findById(answerId);
        return answerService.delete(session, answer);
    }

    @GetMapping("{answerId}/update")
    public String modifyAnswerButton(@PathVariable("question.id") Long targetId, @PathVariable Long answerId, Model model) {
        Question questionData = questionService.findById(targetId);
        Answer selectedAnswer = answerService.getSelectedAnswers(questionData, answerId);

        model.addAttribute("answer", selectedAnswer); // 수정할 댓글만 가져온다.
        model.addAttribute("question", questionData);
        return "qna/updateShow";
    }

    @PutMapping("{answerId}")
    public String updateAnswer(HttpSession session, @PathVariable("answerId") Long id, String replyContents) {
        answerService.update(session, id, replyContents);
        return "redirect:/questions/{question.id}";
    }

}

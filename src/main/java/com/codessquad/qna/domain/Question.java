package com.codessquad.qna.domain;

import com.codessquad.qna.utils.ValidUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    @JsonProperty
    private User writer;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    @OrderBy("answerId DESC")
    @JsonIgnore
    private List<Answer> answers;

    @Column(nullable = false, length = 20)
    @JsonProperty
    private String title;

    @Column(nullable = false, length = 500)
    @JsonProperty
    private String contents;

    @Column(nullable = false, length = 20)
    private final LocalDateTime time = LocalDateTime.now();

    @Column(nullable = false)
    @JsonIgnore
    private boolean questionDeleted = false;

    @Column(nullable = false)
    @JsonProperty
    private Integer countOfAnswer = 0;

    public User getWriter() {
        return writer;
    }

    public String getTitle() {
        return title;
    }

    public Integer getCountOfAnswer() {
        return countOfAnswer;
    }

    public String getContents() {
        return contents;
    }

    public Long getId() {
        return id;
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public List<Answer> getAnswers() {
        List<Answer> enableAnswers = new ArrayList<>();

        for (Answer answer : answers) {
            if (!answer.isAnswerDeleted()) {
                enableAnswers.add(answer);
            }
        }

        return enableAnswers;
    }

    public boolean isQuestionDeleted() {
        return questionDeleted;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public void setTitle(String title) {
        ValidUtils.checkIllegalArgumentOf(title);
        this.title = title;
    }

    public void setContents(String contents) {
        ValidUtils.checkIllegalArgumentOf(contents);
        this.contents = contents;
    }

    public void setId(Long id) {
        ValidUtils.checkIllegalArgumentOf(id);
        this.id = id;
    }

    public void setDeleted(boolean deleted) {
        this.questionDeleted = deleted;
    }

    public void setAnswers(List<Answer> answers) { // 순환참조 문제점?? 우디 PR 내용중에 있었는데 나중에 확인.
        answers = Optional.ofNullable(answers).orElseThrow(IllegalArgumentException::new);
        this.answers = answers;
    }

    public void addCountOfAnswer() {
        this.countOfAnswer = this.countOfAnswer + 1;
    }

    public void minusCountOfAnswer() {
        this.countOfAnswer -= this.countOfAnswer > 0 ? 1 : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return questionDeleted == question.questionDeleted && Objects.equals(id, question.id) && Objects.equals(writer, question.writer) && Objects.equals(answers, question.answers) && Objects.equals(title, question.title) && Objects.equals(contents, question.contents) && Objects.equals(time, question.time) && Objects.equals(countOfAnswer, question.countOfAnswer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, writer, answers, title, contents, time, questionDeleted, countOfAnswer);
    }
}

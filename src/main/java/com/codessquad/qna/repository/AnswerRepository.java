package com.codessquad.qna.repository;

import com.codessquad.qna.domain.Answer;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by 68936@naver.com on 2021-03-16 오전 2:24
 * Blog : https://velog.io/@san
 * Github : https://github.com/sanhee
 */
public interface AnswerRepository extends CrudRepository<Answer,Long> {
}

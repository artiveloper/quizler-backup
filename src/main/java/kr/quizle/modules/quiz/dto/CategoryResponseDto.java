package kr.quizle.modules.quiz.dto;

import kr.quizle.modules.quiz.domain.Category;
import lombok.Data;

@Data
public class CategoryResponseDto {

    private Long id;

    private String name;

    public CategoryResponseDto(Category category) {
        this.id = category.getId();
        this.name = category.getName();
    }

}

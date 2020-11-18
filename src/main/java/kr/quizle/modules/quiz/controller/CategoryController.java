package kr.quizle.modules.quiz.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.quizle.modules.quiz.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    /*
        카테고리 목록 가져오기
     */
    @GetMapping
    public ResponseEntity<CategoryResponseDto> getCategories() {
        return null;
    }

    /*
        카테고리 추가
     */
    @PostMapping
    public ResponseEntity createCategory() {
        return null;
    }

    /*
        카테고리 변경
     */
    @PutMapping
    public ResponseEntity updateCategory() {
        return null;
    }

    /*
        카테고리 삭제 (disable)
     */
    @DeleteMapping
    public ResponseEntity deleteCategory() {
        return null;
    }

}

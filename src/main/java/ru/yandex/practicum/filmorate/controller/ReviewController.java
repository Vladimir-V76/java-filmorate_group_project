package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.NewReviewRequest;
import ru.yandex.practicum.filmorate.dto.UpdateReviewRequest;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.model.film.Review;
import ru.yandex.practicum.filmorate.service.ReviewService;

@Slf4j
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
@Validated
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public Review addNewReview(@Valid @RequestBody NewReviewRequest review) {
        return reviewService.postReview(review);
    }

    @PutMapping
    public Review updateReview(@Valid @RequestBody UpdateReviewRequest review) {
        return reviewService.updateReview(review);
    }

    @DeleteMapping("/")
    public Review noneIdInDeleteRequest() {
        log.trace("Передан Delete запрос на удаление отзыва по id без id");
        throw new ConditionsNotMetException("Id должен быть указан.");
    }

    @DeleteMapping("/{id}")
    public void deleteReview(
            @Min(value = 1, message = "Id должно быть числом положительным")
            @PathVariable Long id) {

        reviewService.deleteReview(id);
    }

    @GetMapping("/")
    public void noneIdInGetRequest() {
        log.trace("Передан Get запрос на получение отзыва по id без id");
        throw new ConditionsNotMetException("Id должен быть указан.");
    }

    @GetMapping("/{id}")
    public Review getReviewById(
            @Min(value = 1, message = "Id должно быть числом положительным")
            @PathVariable Long id) {

        return reviewService.getReviewById(id);
    }
}

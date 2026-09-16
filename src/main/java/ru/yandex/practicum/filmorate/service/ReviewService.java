package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.NewReviewRequest;
import ru.yandex.practicum.filmorate.dto.UpdateReviewRequest;
import ru.yandex.practicum.filmorate.exception.ConditionsNotMetException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.ReviewMapper;
import ru.yandex.practicum.filmorate.model.film.Review;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.ReviewStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Optional;

@Service
public class ReviewService {

    private final ReviewStorage reviewStorage;
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    public ReviewService(
            @Qualifier("reviewDbStorage") ReviewStorage reviewStorage,
            @Qualifier("filmDbStorage") FilmStorage filmStorage,
            @Qualifier("userDbStorage") UserStorage userStorage

    ) {
        this.reviewStorage = reviewStorage;
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Review postReview(NewReviewRequest review) {
        findFilmById(review.getFilmId());
        findUserById(review.getUserId());
        return reviewStorage.postReview(ReviewMapper.mapToReview(review));
    }

    public Review updateReview(UpdateReviewRequest updateReview) {
        Review review = findReviewById(updateReview.getReviewId());
        if (updateReview.hasFilmId()) { findFilmById(updateReview.getFilmId()); }
        if (updateReview.hasUserId()) { findUserById(updateReview.getUserId()); }
        ReviewMapper.updateReviewFields(review, updateReview);

        //Пара user_id и film_id уникальная для таблицы reviews. Проверяем наличие такой пары в другом отзыве
        Optional<Review> otherReview = reviewStorage.findReviewByUserIdAndFilmId(review.getUserId(), review.getFilmId());
        if (otherReview.isPresent()) {
            if (!(otherReview.get().getReviewId().equals(review.getReviewId()))) {
                String message = String.format(
                        "Пользовать с id=%s уже оставлял отзыв к фильму с id=%s под id=%s",
                        review.getUserId(), review.getFilmId(), otherReview.get().getReviewId()
                );
                throw new ConditionsNotMetException(message);
            }
        }
        return reviewStorage.updateReview(review);
    }

    public void deleteReview(Long id) {
        reviewStorage.deleteReviewById(id);
    }

    public Review getReviewById(Long id) {
        return findReviewById(id);
    }

    private void findFilmById(Long id) {
        filmStorage.findFilmById(id)
                .orElseThrow(() -> new NotFoundException("Фильм не найден, id=" + id));
    }

    private void findUserById(Long id) {
        userStorage.findUserById(id)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден, id=" + id));
    }

    private Review findReviewById(Long id) {
        return reviewStorage.findReviewById(id)
                .orElseThrow(() -> new NotFoundException("Отзыв не найден, id=" + id));
    }
}

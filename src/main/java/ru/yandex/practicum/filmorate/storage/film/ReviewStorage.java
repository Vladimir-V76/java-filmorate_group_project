package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Review;

import java.util.Collection;
import java.util.Optional;

public interface ReviewStorage {

    Review postReview(Review review);

    Review updateReview(Review review);

    void deleteReviewById(Long id);

    Optional<Review> getReviewById(Long id);

    Collection<Review> getReviewsByFilmId(Long id, int count);

    Review putLikeReviewById(Long id, Long userId);

    Review putDislikeReviewById(Long id, Long userId);

    Optional<Review> findReviewById(Long Id);

    Optional<Review> findReviewByUserIdAndFilmId(Long userId, Long filmId);
}

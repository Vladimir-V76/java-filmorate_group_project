package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dal.repositories.film.ReviewRepository;
import ru.yandex.practicum.filmorate.model.film.Review;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component("reviewDbStorage")
@RequiredArgsConstructor
public class ReviewDbStorage implements ReviewStorage{

    private final ReviewRepository reviewRepository;

    @Override
    public Review postReview(Review review) {
        return reviewRepository.createReview(review);
    }

    @Override
    public Review updateReview(Review review) {
        return reviewRepository.updateReview(review);
    }

    @Override
    public void deleteReviewById(Long id) {
        reviewRepository.deleteByReviewId(id);
    }

    @Override
    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Collection<Review> getReviewsByFilmId(Long id, int count) {
        return List.of();
    }

    @Override
    public Review putLikeReviewById(Long id, Long userId) {
        return null;
    }

    @Override
    public Review putDislikeReviewById(Long id, Long userId) {
        return null;
    }

    @Override
    public Optional<Review> findReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Optional<Review> findReviewByUserIdAndFilmId(Long userId, Long filmId) {
        return reviewRepository.findByUserIdAndFilmId(userId, filmId);
    }
}

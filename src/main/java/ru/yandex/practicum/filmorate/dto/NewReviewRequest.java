package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NewReviewRequest {
    private String content;

    @NotNull(message = "Тип отзыва должен быть указан")
    private Boolean isPositive;

    @NotNull(message = "Id пользователя должен быть указан")
    @Min(value = 1, message = "Id пользователя должно быть числом положительным")
    private Long userId;

    @NotNull(message = "Id фильма должен быть указан")
    @Min(value = 1, message = "Id фильма должно быть числом положительным")
    private Long filmId;
}

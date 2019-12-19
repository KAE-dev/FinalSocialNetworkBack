package ru.rosbank.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.entity.UserEntity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveRequestDto {
  @Min(value = 0, message = "error.validation.value")
  private int id;
  @NotNull
  private UserEntity author;
  @NotNull
  @Size(min = 10, message = "error.validation.content_min_size")
  private String content;
  private String media;
}

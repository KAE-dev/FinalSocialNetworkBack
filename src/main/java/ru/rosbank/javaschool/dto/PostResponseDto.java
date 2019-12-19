package ru.rosbank.javaschool.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
  private int id;
  private int authorId;
  private String authorName;
  private String content;
  private String media;
  private int likes;

}

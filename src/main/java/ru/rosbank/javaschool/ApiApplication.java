package ru.rosbank.javaschool;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.rosbank.javaschool.dto.UserSaveRequestDto;
import ru.rosbank.javaschool.entity.PostEntity;
import ru.rosbank.javaschool.entity.UserEntity;
import ru.rosbank.javaschool.repository.PostRepository;
import ru.rosbank.javaschool.service.UserService;

import java.util.List;

@SpringBootApplication
public class ApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(ApiApplication.class, args);
  }


  @Bean
  public CommandLineRunner runner(PostRepository repository, UserService service) {
    return args -> {
      UserSaveRequestDto petyaSave = new UserSaveRequestDto(0, "Петя", "petya", "123456", "89991234321");
      UserEntity petya = service.save(petyaSave);
      UserSaveRequestDto rosbankSave = new UserSaveRequestDto(0, "Росбанк", "rosbank", "rosbank", "89999876543");
      UserEntity rosbank = service.save(rosbankSave);
      repository.saveAll(List.of(
              new PostEntity(0, petya, "Новые звездные воины так-себе", null, false, 22),
              new PostEntity(0, rosbank,  "Хочу кофе и спать", null, false, 3),
              new PostEntity(0, rosbank, "Хочу кофе", null, false, 653),
              new PostEntity(0, rosbank,  "В офисе Росбанка", null, false, 542),
              new PostEntity(0, rosbank,  "Сегодня день сдачи", null, false, 122),
              new PostEntity(0, petya, "Как дела?", null, false, 12),
              new PostEntity(0, petya, "Всех приветствую", null, false, 2)
      ));
    };
  }
}

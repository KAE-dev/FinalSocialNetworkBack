package ru.rosbank.javaschool;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.context.WebApplicationContext;
import ru.rosbank.javaschool.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.dto.UserSaveRequestDto;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApiApplicationTests {

  @Autowired
  private WebApplicationContext context;

  @BeforeEach
  void setUp() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }


  @Test
  void shouldBeAbleSeePostWithAuth() {
    given()
            .auth().with(httpBasic("rosbank", "rosbank"))
            .when()
            .get("/api/posts?lastPost=0&i=6")
            .then()
            .statusCode(200)
            .body("$", not(emptyArray()))
            .body("$", hasSize(6));
  }


  @Test
  void shouldBeAbleCreatePostWithAuth() {
    given()
            .auth().with(httpBasic("rosbank", "rosbank"))
            .contentType(ContentType.JSON)
            .body(new PostSaveRequestDto(0, null, "content", null))
            .when()
            .post("/api/posts")
            .then()
            .statusCode(200)
            .body("id", not(equalTo(0)));
  }

  @Test
  void shouldBeAbleCreateUser() {
    given()
            .contentType(ContentType.JSON)
            .body(new UserSaveRequestDto(0, "User", "username", "secret", null))
            .when()
            .post("/api/users")
            .then()
            .statusCode(200)
            .body("id", not(equalTo(0)));
  }

}

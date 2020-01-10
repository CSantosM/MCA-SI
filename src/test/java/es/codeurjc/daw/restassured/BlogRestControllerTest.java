package es.codeurjc.daw.restassured;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.restassured.RestAssured;
import com.fasterxml.jackson.core.JsonProcessingException;

import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BlogRestControllerTest {

    private static final String POST_TITLE = "Michel va al parque";
    private static final String POST_MESSAGE = "a jugar con los cacharros";
    private static final String COMMENT_AUTHOR = "Carlos";
    private static final String COMMENT_MESSAGE = "contenido comentario";
    private static final int STATUS_CREATED = 201;
    private static final int STATUS_NO_CONTENT = 204;

    private static BlogRest blog = new BlogRest();

    @LocalServerPort
    int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void createPostTest() throws JsonProcessingException {

        blog.createPost(POST_TITLE, POST_MESSAGE)
            .then()
                .statusCode(STATUS_CREATED)
                .body("title", equalTo(POST_TITLE));
    }

    @Test
    public void createCommentTest() throws JsonProcessingException {

        blog.createPost(POST_TITLE, POST_MESSAGE);

        blog.createComment(COMMENT_AUTHOR, COMMENT_MESSAGE)
            .then()
                .statusCode(STATUS_CREATED)
                .body("author", equalTo(COMMENT_AUTHOR))
                .and()
                .body("message", equalTo(COMMENT_MESSAGE));
    }

    @Test
    public void deleteCommentTest() throws JsonProcessingException {

        blog.createPost(POST_TITLE, POST_MESSAGE);
        blog.createComment(COMMENT_AUTHOR, COMMENT_MESSAGE);
        blog.deleteComment()
            .then()
                .statusCode(STATUS_NO_CONTENT);
    }
}
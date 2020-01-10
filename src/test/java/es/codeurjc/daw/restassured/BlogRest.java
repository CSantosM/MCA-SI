package es.codeurjc.daw.restassured;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.codeurjc.daw.Comment;
import es.codeurjc.daw.Post;
import io.restassured.response.Response;
import static io.restassured.path.json.JsonPath.*;
import static io.restassured.RestAssured.*;

public class BlogRest {

    private static final String URL = "http://localhost:8080/api/";

    private ObjectMapper mapper = new ObjectMapper();

    private Integer postId;
    private Integer commentId;


    public BlogRest() {
    }


    Response createPost(String title, String content) throws JsonProcessingException {
        Post post = new Post(title, content);
        Response response = given()
                .contentType("application/json")
                .body(mapper.writeValueAsString(post))
            .when()
                .post(URL + "post");

        postId = from(response.getBody().asString()).get("id");

        return response;
    }

    Response createComment(String author, String message) throws JsonProcessingException {
        Comment comment = new Comment(author, message);
        Response response = given()
                .contentType("application/json")
                .body(mapper.writeValueAsString(comment))
            .when()
                .post(URL + "post/" + postId + "/comment");

        commentId = from(response.getBody().asString()).get("id");

        return response;
    }

    Response deleteComment() throws JsonProcessingException {
        return given()
                .contentType("application/json")
            .when()
                .delete(URL + "post/" + postId + "/comment/" + commentId);
    }

}
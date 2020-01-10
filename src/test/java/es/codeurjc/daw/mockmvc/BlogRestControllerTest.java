package es.codeurjc.daw.mockmvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import es.codeurjc.daw.Comment;
import es.codeurjc.daw.Post;
import es.codeurjc.daw.PostService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.hamcrest.CoreMatchers.equalTo;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class BlogRestControllerTest {

    static final String POST_PATH = "/api/post/";
    static final String COMMENT_PATH = "/comment/";

    private static final String POST_TITLE = "Michel colecciona botellas";
    private static final String POST_MESSAGE = "El plastico acabara con el planeta";
    private static final String COMMENT_AUTHOR = "Carlos";
    private static final String COMMENT_MESSAGE = "eso se llama síndrome de diógenes";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private PostService postService;

    private ObjectMapper mapper = new ObjectMapper();

    private Long postId;
    private Long commentId;

    @Test
    public void checkPostCreatedTest() throws Exception {
        this.createPost(POST_TITLE, POST_MESSAGE)
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title", equalTo(POST_TITLE)));
    }

    @Test
    public void createCommentTest() throws Exception {
        this.createPost(POST_TITLE, POST_MESSAGE);
        this.createComment(COMMENT_AUTHOR, COMMENT_MESSAGE)
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.author", equalTo(COMMENT_AUTHOR)))
        .andExpect(jsonPath("$.message", equalTo(COMMENT_MESSAGE)));
    }

    @Test
    public void deleteCommentTest() throws Exception {
        this.createPost(POST_TITLE, POST_MESSAGE);
        this.createComment(COMMENT_AUTHOR, COMMENT_MESSAGE);
        this.deleteComment()
            .andExpect(status().isNoContent());
    }

    private ResultActions createPost(String title, String message) throws Exception {
        Post post = new Post(title, message);
        String json = mapper.writeValueAsString(post);

        ResultActions result = mvc.perform(post(POST_PATH)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json));
        this.postId = postService.getLastPostId();
        return result;
    }

    private ResultActions createComment(String author, String message) throws Exception {
        Comment comment = new Comment(author, message);
        String json = mapper.writeValueAsString(comment);

        ResultActions result = mvc.perform(
                post(POST_PATH + this.postId + COMMENT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        this.commentId = postService.getLastCommentId();
        return result;

    }

    private ResultActions deleteComment() throws Exception {
        return mvc.perform(delete(POST_PATH + this.postId + COMMENT_PATH + this.commentId )
                .contentType(MediaType.APPLICATION_JSON));
    }

}
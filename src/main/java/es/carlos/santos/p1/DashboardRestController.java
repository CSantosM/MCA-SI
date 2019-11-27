package es.carlos.santos.p1;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DashboardRestController {

    interface FullInfo extends Post.Full, Comment.Full{}

    @Autowired
    private PostService postService;

    @JsonView(FullInfo.class)
    @PostMapping("/posts")
    public ResponseEntity<Post> newPost(@RequestBody Post post) {

        if (post != null) {
            this.postService.add(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @JsonView(Post.Shallow.class)
    @GetMapping("/posts")
    public ResponseEntity<Map<Long, Post>> getAllPosts() {

        return new ResponseEntity<>(this.postService.getPosts(), HttpStatus.OK);
    }

    @JsonView(FullInfo.class)
    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {

        Post post = this.postService.getPost(id);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @RequestBody Comment comment) {

        Post post = this.postService.getPost(postId);
        if (post != null) {
            comment.setId(post.getCommentsNumber());
            post.addComment(comment);
            return new ResponseEntity<>(comment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long postId, @PathVariable int commentId) {

        Post post = this.postService.getPost(postId);
        if (post != null) {
            Comment comment = post.getComment(commentId);
            if(comment != null){
                post.deleteComment(comment.getId());
                return new ResponseEntity<>(comment, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
package es.carlos.santos.p2;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

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

    interface FullInfo extends Post.Full, Comment.Full, Author.Shallow {
    }

    interface ShallowPost extends Post.Shallow {
    }

    interface AuthorCommentsInfo extends Post.ID, Post.Comments, Comment.Shallow {
    }

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @PostConstruct
    public void init() {
        List<Comment> comments = new ArrayList<Comment>();
        Author author = new Author("Juanito", 20);
        Author author2 = new Author("Jupiter", 800);
        comments.add(new Comment("Comentario de prueba", author));
        comments.add(new Comment("Comentario 2", author2));
        this.authorRepository.saveAndFlush(author);
        this.authorRepository.saveAndFlush(author2);
        postRepository.save(new Post("Pepe", "Contenido de mi post", comments));

        List<Comment> comments2 = new ArrayList<Comment>();
        comments2.add(new Comment("wwwwwwwww", author2));

        postRepository.save(new Post("XXX", "55555551515", comments2));

    }

    @JsonView(FullInfo.class)
    @PostMapping("/posts")
    public ResponseEntity<Post> newPost(@RequestBody Post post) {

        if (post != null) {
            this.postRepository.saveAndFlush(post);
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @JsonView(ShallowPost.class)
    @GetMapping("/posts")
    public ResponseEntity<List<Post>> getAllPosts() {

        return new ResponseEntity<>(this.postRepository.findAll(), HttpStatus.OK);
    }

    @JsonView(FullInfo.class)
    @GetMapping("/post/{id}")
    public ResponseEntity<Post> getPost(@PathVariable Long id) {

        Post post = this.postRepository.getById(id);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post/{postId}/comment/{authorId}")
    public ResponseEntity<Comment> createComment(@PathVariable Long postId, @PathVariable Long authorId,
            @RequestBody Comment comment) {

        if (authorId != null) {
            Post post = this.postRepository.getById(postId);
            Author author = this.authorRepository.getById(authorId);

            if (post != null && author != null) {
                comment.setAuthor(author);
                post.addComment(comment);
                this.postRepository.saveAndFlush(post);
                return new ResponseEntity<>(comment, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<Comment> deleteComment(@PathVariable Long postId, @PathVariable int commentId) {

        Post post = this.postRepository.getById(postId);
        if (post != null) {
            Comment comment = this.commentRepository.getById(commentId);
            if (comment != null) {
                post.deleteComment(comment.getId());
                this.postRepository.save(post);
                return new ResponseEntity<>(comment, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        if (author != null) {
            this.authorRepository.saveAndFlush(author);
            return new ResponseEntity<>(author, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @JsonView(AuthorCommentsInfo.class)
    @GetMapping("/comments/author/{id}")
    public ResponseEntity<List<Post>> getAuthorComments(@PathVariable Long id) {
        List<Post> postsWithAuthorComments = this.postRepository.findByCommentsAuthorId(id);
        List<Comment> commentsOfAuthor = this.commentRepository.findByAuthorId(id);

        // Intersection
        for (Post post : postsWithAuthorComments) {
            post.getComments().retainAll(commentsOfAuthor);
        }

        return new ResponseEntity<>(postsWithAuthorComments, HttpStatus.OK);

    }
}
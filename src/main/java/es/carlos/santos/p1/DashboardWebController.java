package es.carlos.santos.p1;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class DashboardWebController {

    @Autowired
    private User user;

    @Autowired
    private PostService postService;

    @GetMapping("/")
    public String dashboard(Model model, HttpSession session) {
        model.addAttribute("isUserNew", session.isNew());
        model.addAttribute("postList", this.postService.getPosts().entrySet());

        return "dashboard";
    }

    @GetMapping("/post/form")
    public String postForm(Model model) {

        model.addAttribute("postsNum", this.user.getPostsNum());
        return "new_post_form";
    }

    @PostMapping("/post/new")
    public String newPost(Model model, Post post){

        this.postService.add(post);
        this.user.addPostNum();

        return "success";
    }

    @GetMapping("/post/{id}")
    public String getPostDetail(Model model, @PathVariable long id) {
        Post post = this.postService.getPost(id);
        model.addAttribute("post", post);
        model.addAttribute("name", this.user.getName());
        model.addAttribute("showCommentTitle", post.getCommentsNumber() > 0);

        return "post_detail";
    }

    @PostMapping("/post/{postId}/newcomment")
    public String newCommentPost(Model model, @PathVariable long postId, Comment comment){
        this.user.setName(comment.getName());
        Post post = this.postService.getPost(postId);
        if(post != null){
            comment.setId(post.getCommentsNumber());
            post.addComment(comment);
        }

        return "success";
    }

    @PostMapping("/post/{postId}/comment/{commentId}/delete")
    public String deleteComment(Model model, @PathVariable long postId, @PathVariable long commentId){
        Post post = this.postService.getPost(postId);

        if(post != null){
            post.deleteComment(commentId);
        }

        return "success";
    }

}
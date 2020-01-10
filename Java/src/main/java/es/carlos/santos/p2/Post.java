package es.carlos.santos.p2;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonView;


@Entity
public class Post {

    interface ID {}
    interface Title{}
    interface Content{}
    interface Comments{}

    interface Shallow extends ID, Title{}
    interface Full extends ID, Title, Content, Comments{}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(ID.class)
    private long id;

    @JsonView(Title.class)
    private String title;

    @JsonView(Content.class)
    private String content;

    @OneToMany(cascade = CascadeType.ALL)
    @JsonView(Comments.class)
    private List<Comment> comments;

    Post() {
    }

    Post(String title, String content, List<Comment> commentList) {
        this.content = content;
        this.title = title;
        this.comments = commentList;
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
    }

    public void deleteComment(long id){
        this.getComments().removeIf(comment -> comment.getId().equals(id));
    }

    @Override
    public String toString() {
        return "Post [content=" + content + ", comments=" + comments + ", id=" + id + ", title=" + title + "]";
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
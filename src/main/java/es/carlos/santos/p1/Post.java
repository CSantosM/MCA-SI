package es.carlos.santos.p1;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;


public class Post {


    interface Shallow{}
    interface Deep{}
    interface Full extends Shallow, Deep{}

    @JsonView(Shallow.class)
    private long id = -1;

    @JsonView(Shallow.class)
    private String title;

    @JsonView(Deep.class)
    private String body;

    @JsonView(Deep.class)
    private List<Comment> commentList = new ArrayList<Comment>();

    Post() {
    }

    Post(String title, String body) {
        this.body = body;
        this.title = title;
    }

    public void addComment(Comment comment) {
        this.commentList.add(comment);
    }

    public Comment getComment(int i) {
        if(this.commentList != null && i < this.commentList.size()){
            return this.commentList.get(i);
        }
        return null;
    }

    public long getCommentsNumber(){
        return this.commentList.size();
    }

    public boolean deleteComment(long i){
        return this.commentList.removeIf(comment -> (comment.getId() == i));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    @Override
    public String toString() {
        return "Titulo: " + this.title + "\n Contenido: " + this.body;

    }

}
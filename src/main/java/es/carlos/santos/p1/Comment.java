package es.carlos.santos.p1;

import com.fasterxml.jackson.annotation.JsonView;

public class Comment {

    interface Full {}

    @JsonView(Full.class)
    private Long id;

    @JsonView(Full.class)
    private String name;

    @JsonView(Full.class)
    private String comment;

    public Comment() {
    }

    public Comment(String name, String comment) {
        this.id = (long) 0;
        this.name = name;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

    @Override
    public String toString() {
        return this.name + ": " + this.comment ;
    }


}
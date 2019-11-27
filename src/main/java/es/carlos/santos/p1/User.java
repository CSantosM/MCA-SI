package es.carlos.santos.p1;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class User {

    private String name = "";
	private int postsNum = 0;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addPostNum() {
		this.postsNum++;
	}

	public int getPostsNum() {
		return postsNum;
	}

	public void setPostsNum(int postsNum) {
		this.postsNum = postsNum;
	}


}
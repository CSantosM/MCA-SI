package es.codeurjc.daw.selenium;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PostResultPage extends Page {

    public PostResultPage(Page page) {
        super(page);
    }

    public BlogPage goBackToBlog() {
        this.findByLinkText("Atrás").click();
        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("h1")));

        return new BlogPage(this);
    }

    public PostResultPage createAComment(String author, String commentMessage) {
        this.findById("form-author").sendKeys(author);
        this.findById("form-comment-message").sendKeys(commentMessage);
        this.findById("form-comment-submit").click();
        wait.until(ExpectedConditions.numberOfElementsToBe(By.tagName("ul"), 1));

        return this;
    }

    public PostResultPage checkNewCommentCreated(String author, String commentMessage) {

        WebElement comment = this.findById("comment-info");
        assertNotNull(comment);
        assertEquals(comment.getText(), author + ": " + commentMessage);

        return this;
	}

	public PostResultPage deleteComment() {
        this.findById("delete-comment").click();
		return this;
	}

	public PostResultPage checkCommentDeleted() {
        assertEquals(findByTagName("ul").findElements(By.tagName("li")).size(), 0);
        return this;
	}

}
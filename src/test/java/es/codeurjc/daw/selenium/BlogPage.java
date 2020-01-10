package es.codeurjc.daw.selenium;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BlogPage extends Page {

    public BlogPage(WebDriver driver, int port) {
        super(driver, port);
    }

    public BlogPage(Page page) {
        super(page);
    }

    public BlogPage get() {
        this.get("/");
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Nuevo post")));
        return this;
    }

    public PostFormPage goToNewPost() {
        this.findByLinkText("Nuevo post").click();
        wait.until(ExpectedConditions.elementToBeClickable(By.tagName("h1")));
        return new PostFormPage(this);
    }


    public BlogPage checkNewPostCreated(String title) {

        assertNotNull(this.findByLinkText(title));
        return this;
    }

}
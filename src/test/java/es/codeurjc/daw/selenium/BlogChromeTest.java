package es.codeurjc.daw.selenium;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class
BlogChromeTest {

    protected WebDriver driver;

    static final String TITLE = "Carlos";
    static final String MESSAGE = "Vendo moto";

    static final String AUTHOR = "Pepe";
    static final String COMMENT_MESSAGE = "Te lo compro";

    int port = 8080;
    BlogPage blog;

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setupTest() {
        driver = new ChromeDriver();
        blog = new BlogPage(driver, port);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void createPostTest() {
        BlogPage blog = new BlogPage(driver, port);

        blog.get()
            .goToNewPost()
            .submitPost(TITLE, MESSAGE)
            .goBackToBlog()
            .checkNewPostCreated(TITLE);
    }

    @Test
    public void createCommentTest() {

        blog.get()
            .goToNewPost()
            .submitPost(TITLE, MESSAGE)
            .createAComment(AUTHOR, COMMENT_MESSAGE)
            .checkNewCommentCreated(AUTHOR, COMMENT_MESSAGE);
    }

    @Test
    public void deleteCommentTest() {

        blog.get()
            .goToNewPost()
            .submitPost(TITLE, MESSAGE)
            .createAComment(AUTHOR, COMMENT_MESSAGE)
            .checkNewCommentCreated(AUTHOR, COMMENT_MESSAGE)
            .deleteComment()
            .checkCommentDeleted();
    }

}
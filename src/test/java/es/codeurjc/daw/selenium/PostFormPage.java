package es.codeurjc.daw.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PostFormPage extends Page {

    public PostFormPage(Page page) {
        super(page);
    }

    public PostResultPage submitPost(String title, String message) {

        this.findById("form-title").sendKeys(title);
        this.findById("form-message").sendKeys(message);
        this.findById("form-submit").click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Atrás")));

        return new PostResultPage(this);
    }


}
package es.codeurjc.daw.selenium;

import static java.lang.String.format;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Page {

	protected WebDriver driver;
	protected WebDriverWait wait;
	protected int port;

    public Page(WebDriver driver, int port) {
        this.driver = driver;
        this.port = port;
        this.wait = new WebDriverWait(driver, 5);
    }

    public Page(Page page) {
        this.driver = page.driver;
        this.port = page.port;
        this.wait = new WebDriverWait(driver, 5);
    }

    protected void get(String path) {
        driver.get("localhost:"+this.port+path);
    }

    protected WebElement findByTagName(String tag){
        return driver.findElement(By.tagName(tag));
    }

    protected WebElement findById(String id){
        return driver.findElement(By.id(id));
    }

    protected WebElement findByLinkText(String text){
        return driver.findElement(By.linkText(text));
    }





}
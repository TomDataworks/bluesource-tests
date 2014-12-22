/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import com.github.arven.bluesourcetests.WebHelper;
import com.github.arven.bluesourcetests.WebHelperFactory;
import com.google.common.base.Function;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author brian.becker
 */
public class BaseWebPage
{
        public WebDriver driver;
        public WebHelper helper;

        public BaseWebPage(WebDriver driver)
        {
            //System.out.println("Starting init elements on base web page...");
            this.driver = driver;
            PageFactory.initElements(driver, this);
            this.helper = WebHelperFactory.getFromDriver(this.driver);
            //PageFactory.initElements(this.driver, this);
        }
        
        public boolean SyncElement(By by) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.ignoring(NoSuchElementException.class);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
            return element.isEnabled();
        }

        public boolean SyncElement(WebElement el) {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.ignoring(NoSuchElementException.class);
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(el));
            return element.isEnabled();
        }
}

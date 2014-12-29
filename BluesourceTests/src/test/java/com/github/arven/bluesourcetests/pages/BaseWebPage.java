/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import com.github.arven.bluesourcetests.WebHelper;
import com.github.arven.bluesourcetests.WebHelperFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
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
        
        public void waitAndAcceptAlert() {
            boolean waiting = true;
            while(waiting) {
                try {
                    driver.switchTo().alert().accept();
                    waiting = false;
                } catch (NoAlertPresentException ex) {}
            }
        }
}

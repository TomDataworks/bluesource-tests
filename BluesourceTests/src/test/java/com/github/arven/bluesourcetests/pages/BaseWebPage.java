/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author brian.becker
 */
public class BaseWebPage
{
        public WebDriver driver;

        public BaseWebPage(WebDriver driver)
        {
            //System.out.println("Starting init elements on base web page...");
            this.driver = driver;
            PageFactory.initElements(driver, this);
            //PageFactory.initElements(this.driver, this);
        }

        public boolean SyncElement(By by) {
                WebDriverWait wait = new WebDriverWait(driver, 10);
                wait.ignoring(NoSuchElementException.class);
                //wait.IgnoreExceptionTypes (NoSuchElementException);
                WebElement element = wait.until(ExpectedConditions.elementToBeClickable(by));
                return element.isEnabled();
        }
}

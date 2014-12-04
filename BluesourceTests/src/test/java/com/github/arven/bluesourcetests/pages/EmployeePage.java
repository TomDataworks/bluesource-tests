/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 *
 * @author brian.becker
 */
public class EmployeePage {
    private final WebDriver driver;
    
    @FindBy(linkText = "back")
    private WebElement back;
    
    public EmployeePage(WebDriver driver) {
        this.driver = driver;
        driver.get("http://bluesourcestaging.herokuapp.com/login");
    }
    
    public AdminPage back() {
        this.back.click();
        return PageFactory.initElements(this.driver, AdminPage.class); 
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import com.github.arven.by.CustomPageFactory;
import com.github.arven.by.OtherFindBy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author brian.becker
 */
public class SignInPage {
    private final WebDriver driver;
    
    @FindBy(id = "employee_username")
    private WebElement username;
    
    @FindBy(id = "employee_password")
    private WebElement password;
    
    @OtherFindBy(with = com.github.arven.by.NullLocator.class, using = "anything")
    private WebElement string;
    
    public SignInPage(WebDriver driver) {
        this.driver = driver;
        driver.get("http://bluesourcestaging.herokuapp.com/login");
    }
    
    public AdminPage login(String login_username, String login_password) {
        //this.string.sendKeys("something");
        
        this.username.sendKeys(login_username);
        this.password.sendKeys(login_password);
        this.password.submit();
                
        return CustomPageFactory.initElements(this.driver, AdminPage.class); 
    }
    
}

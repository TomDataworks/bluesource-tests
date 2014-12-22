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
public class SignInPage extends BaseWebPage {   
    @FindBy(id = "employee_username")
    private WebElement username;
    
    @FindBy(id = "employee_password")
    private WebElement password;
    
    public SignInPage ( WebDriver driver ) {
        super(driver);
    }
    
    public AdminPage login(String login_username, String login_password) {       
        this.username.sendKeys(login_username);
        this.password.sendKeys(login_password);
        this.password.submit();
                
        return PageFactory.initElements(this.driver, AdminPage.class); 
    }
    
}

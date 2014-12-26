/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class LoginPage extends BaseWebPage
{
    @FindBy(how = How.ID, using="employee_username")
    private WebElement username; // How.NAME = userName

    @FindBy(how = How.ID, using="employee_password")
    private WebElement password; // How.NAME = password

    public LoginPage ( WebDriver driver ) {
        super(driver);
    }

    public EmployeesPage doLogin(String username, String password)
    {
            this.username.sendKeys(username);
            this.password.sendKeys(password);
            this.username.submit ();

            return new EmployeesPage(driver);
    }

    public boolean hasLoginLink() {
            return SyncElement (By.linkText("Can't log in?"));
    }
}
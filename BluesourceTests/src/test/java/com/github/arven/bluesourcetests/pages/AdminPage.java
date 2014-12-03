/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import java.util.Iterator;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 *
 * @author brian.becker
 */
public class AdminPage {
    private final WebDriver driver;
    
    @FindBy(xpath = "//a[@href=\"/logout\"]")
    private WebElement logout;
    
    @FindBy(xpath = "//div[@id=\"search-bar\"]/input")
    private WebElement search;
    
    @FindBy(xpath = "//button[contains(., \"Add\")]")
    private WebElement add;

    private WebElement employee_username;
    private WebElement employee_first_name;
    private WebElement employee_last_name;
    private WebElement employee_title_id;
    private WebElement employee_role;
    private WebElement employee_manager_id;
    private WebElement employee_status;
    private WebElement employee_bridge_time;
    private WebElement employee_location;
    private WebElement employee_start_date;
    private WebElement employee_cell_phone;
    private WebElement employee_office_phone;
    private WebElement employee_email;
    private WebElement employee_im_name;
    private WebElement employee_im_client;
    private WebElement employee_department_id;

    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }
    
    public SignInPage logout() {      
        this.logout.click();
        return PageFactory.initElements(this.driver, SignInPage.class); 
    }
    
    public AdminPage addEmployee(String username, String firstname, String lastname) {
        this.add.click();

        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("employee_username")));
        this.employee_username.sendKeys(username);
        this.employee_first_name.sendKeys(firstname);
        this.employee_last_name.sendKeys(lastname);
        this.employee_username.submit();
        
        return PageFactory.initElements(this.driver, AdminPage.class); 
    }
    
    public boolean search(String value) {
        this.search.sendKeys(value);
        
        WebDriverWait wait = new WebDriverWait(driver, 60);        
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#resource-content .ng-binding")));
        List<WebElement> all = driver.findElements(By.cssSelector("#resource-content a.ng-binding"));
        
        for(Iterator<WebElement> it = all.iterator(); it.hasNext(); ) {
            WebElement first = it.next();
            WebElement last = it.next();
            if(value.equalsIgnoreCase(first.getText() + " " + last.getText())) {
                return true;
            }
        }
        
        return false;
    }
        
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import com.github.arven.bluesourcetests.pages.AdminPage;
import com.github.arven.bluesourcetests.pages.SignInPage;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author brian.becker
 */
public class TestAddEmployee {
    private static WebDriver driver;    
    
    public TestAddEmployee() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @BeforeClass
    public static void setUpClass() throws Exception {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        driver.close();
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
    
    @Test
    public void canLogin() {
        SignInPage page = PageFactory.initElements(driver, SignInPage.class);
        AdminPage admin = page.login("company.admin", "anything");
        admin.logout();
    }
    
    @Test
    public void canAddEmployee() {
        SignInPage page = PageFactory.initElements(driver, SignInPage.class);
        AdminPage admin = page.login("company.admin", "anything");
        Random r = new Random();
        String username = UUID.randomUUID().toString();
        String firstname = UUID.randomUUID().toString();
        String lastname = UUID.randomUUID().toString();
        admin = admin.addEmployee(username, firstname, lastname);
        Assert.assertTrue(admin.search(firstname + " " + lastname));
        admin.logout();
    }
}

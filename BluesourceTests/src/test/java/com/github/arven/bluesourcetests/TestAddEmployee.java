/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import com.github.arven.angular.AngularExpectedConditions;
import com.github.arven.angular.ByAngular;
import com.github.arven.bluesourcetests.pages.AdminPage;
import com.github.arven.bluesourcetests.pages.EmployeePage;
import com.github.arven.bluesourcetests.pages.SignInPage;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 *
 * @author brian.becker
 */
public class TestAddEmployee {
    private static WebDriver driver;  
    private static ByAngular ng;
    
    public TestAddEmployee() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

    @Parameters("browser")
    @BeforeClass
    public static void setUpClass(String browser) throws Exception {
        driver = (WebDriver) Class.forName(browser).newInstance();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        ng = new ByAngular((JavascriptExecutor) driver);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        driver.quit();
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }
    
    @Test
    public void canLogin() {
        //SignInPage page = PageFactory.initElements(driver, SignInPage.class);
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
        EmployeePage empl = admin.search(firstname, lastname);
        Assert.assertNotNull(empl);
        admin.logout();
    }
    
    @Test
    public void locateAngularResources() {
        SignInPage page = PageFactory.initElements(driver, SignInPage.class);
        AdminPage admin = page.login("company.admin", "anything");


        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.linkText("Email team")));

        List<WebElement> wes = driver.findElements(ng.repeater("employee in pagedResources[currentPage]"));
        System.err.println("Number of resources:" + wes.size());
        Assert.assertEquals(wes.size(), 20);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import com.github.arven.bluesourcetests.pages.LoginPage;
import com.github.arven.bluesourcetests.pages.NavigationBar;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.ITest;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 *
 * @author brian.becker
 */
public class BaseWebTest implements ITest {
    protected static WebDriver driver;
    private String mTestCaseName = "";
    private static String browserName = "";
    
    @BeforeMethod(alwaysRun = true)
    public void setUpMethod(Method m, Object[] data) throws Exception {
        StringBuilder b = new StringBuilder();
        b.append(browserName);
        b.append(": ");
        b.append(m.getName()).append("(");
        for(int i = 0; i < data.length; i++) {
            b.append(data[i].toString());
            if(i < data.length - 1)
                b.append(", ");
        }
        b.append(")");
        this.mTestCaseName = b.toString();
    }
    
    @Parameters("browser")
    @BeforeClass
    public static void setUpClass(@Optional String browser) throws Exception {
        if(browser == null)
            browser = "org.openqa.selenium.firefox.FirefoxDriver";

        driver = (WebDriver) Class.forName(browser).newInstance();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
        driver.navigate().to("http://bluesourcestaging.herokuapp.com/login");
        //ng = new ByAngular((JavascriptExecutor) driver);
        
        //browserName = browser.split("\\.")[3];
        browserName = driver.getClass().getSimpleName();
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        driver.quit();
    }



    @AfterMethod
    public void tearDownMethod(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE)
        {
            NavigationBar nav = new NavigationBar (driver);
            if (nav.hasLogoutLink ()) {
                LoginPage page = nav.doLogout ();
            }
        }
    }
    
    @Override
    public String getTestName() {
        return this.mTestCaseName;
    }
}

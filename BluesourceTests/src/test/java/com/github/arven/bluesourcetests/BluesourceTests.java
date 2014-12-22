/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import com.github.arven.angular.AngularExpectedConditions;
import com.github.arven.angular.ByAngular;
import com.github.arven.bluesourcetests.pages.AddDepartmentPage;
import com.github.arven.bluesourcetests.pages.AddEmployeePage;
import com.github.arven.bluesourcetests.pages.AddTitlePage;
import com.github.arven.bluesourcetests.pages.AdminPage;
import com.github.arven.bluesourcetests.pages.DepartmentsPage;
import com.github.arven.bluesourcetests.pages.EmployeeDataPage;
import com.github.arven.bluesourcetests.pages.EmployeePage;
import com.github.arven.bluesourcetests.pages.EmployeesPage;
import com.github.arven.bluesourcetests.pages.LoginPage;
import com.github.arven.bluesourcetests.pages.ManageTimeOffPage;
import com.github.arven.bluesourcetests.pages.NavigationBar;
import com.github.arven.bluesourcetests.pages.SignInPage;
import com.github.arven.bluesourcetests.pages.TimeOff;
import com.github.arven.bluesourcetests.pages.TitlesPage;
import com.github.arven.bluesourcetests.pages.ViewTimeOffPage;
import com.sun.jna.platform.win32.Guid;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
import org.testng.ITestResult;
import org.testng.annotations.Optional;

/**
 *
 * @author brian.becker
 */
public class BluesourceTests {
    private static WebDriver driver;
    
    public BluesourceTests() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

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
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        driver.quit();
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE)
        {
            NavigationBar nav = new NavigationBar (driver);
            if (nav.HasLogoutLink ()) {
                LoginPage page = nav.DoLogout ();
            }
        }
    }

    //NEWTESTS

    @Test
    public void TestLoginLogout()
    {
            LoginPage page = new LoginPage (driver);
            page.DoLogin ("company.admin", "anything");
            NavigationBar nav = new NavigationBar (driver);
            Assert.assertTrue (nav.HasLogoutLink ());
            page = nav.DoLogout();
            Assert.assertTrue (page.HasLoginLink ());
    }
    
    @Test
    public void TestAddEmployee()
    {
            LoginPage page = new LoginPage (driver);
            EmployeesPage empl = page.DoLogin ("company.admin", "anything");
            NavigationBar nav = new NavigationBar (driver);
            Assert.assertTrue (nav.HasLogoutLink ());
            AddEmployeePage newempl = empl.GotoAddEmployee ();
            String user = UUID.randomUUID().toString ();
            String first = UUID.randomUUID().toString ();
            String last = UUID.randomUUID().toString ();
            newempl.AddEmployee (user, first, last);
            Assert.assertTrue (nav.HasAddedEmployeeText ());
            page = nav.DoLogout ();
            Assert.assertTrue (page.HasLoginLink ());
    }

    @Test
    public void TestAddDepartment()
    {
            LoginPage page = new LoginPage (driver);
            page.DoLogin ("company.admin", "anything");
            NavigationBar nav = new NavigationBar (driver);
            DepartmentsPage depts = nav.GotoDepartments ();
            AddDepartmentPage addDept = depts.GotoAddDepartment ();
            depts = addDept.AddDepartment ("The Investigators");
            Assert.assertTrue (nav.HasAddedDepartmentText ());
            Assert.assertNotNull (depts.FindDepartmentByName ("The Investigators"));
            depts.TrashDepartment ("The Investigators");
            // Assert.IsNull (depts.FindDepartmentByName ("The Investigators"));
            page = nav.DoLogout ();
            Assert.assertTrue (page.HasLoginLink ());
    }

    @Test
    public void TestAddTitle()
    {
            LoginPage page = new LoginPage (driver);
            page.DoLogin ("company.admin", "anything");
            NavigationBar nav = new NavigationBar (driver);
            TitlesPage titles = nav.GotoTitles ();
            AddTitlePage addTitle = titles.GotoAddTitle();
            titles = addTitle.AddTitle ("Agent");
            Assert.assertTrue (nav.HasAddedTitleText ());
            Assert.assertNotNull (titles.FindTitleByName ("Agent"));
            titles.TrashTitle ("Agent");
            // Assert.IsNull (titles.FindTitleByName ("Agent"));
            page = nav.DoLogout ();
            Assert.assertTrue (page.HasLoginLink ());
    }
    
    @Test(dataProvider="TestTimeOffData",dataProviderClass=BluesourceTestData.class)
    public void TestTimeOff(String name, String s_start, String s_end, String type, String reason, boolean halfday, float days, boolean succeeds) throws Exception
    {   
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date start = sdf.parse(s_start);
        Date end = sdf.parse(s_end);
        
        LoginPage page = new LoginPage (driver);
        EmployeesPage empl = page.DoLogin ("company.admin", "anything");
        NavigationBar nav = new NavigationBar (driver);
        empl.EnterInSearch (name);
        EmployeeDataPage data = empl.SelectFirstMatchingEmployee ();
        ManageTimeOffPage timeOff = data.GotoManageTimeOff ();
        timeOff = timeOff.SetVacationInfo (start, end, type, reason, halfday);
        if (succeeds) {
                WebElement time = timeOff.GetVacationInfo (start);
                Assert.assertNotNull (time);
                String vacationDays = time.findElement(By.cssSelector(".business-days")).getText();
                timeOff.TrashVacationInfo (start);
                Assert.assertTrue(Float.parseFloat(vacationDays) == days);
                // Assert.IsNull (timeOff.GetVacationInfo (start));
        }
        page = nav.DoLogout ();
        Assert.assertTrue (page.HasLoginLink ());
    }

    @Test(dataProvider="TestTotalTimeOffData",dataProviderClass=BluesourceTestData.class)
    public void TestTotalVacationDays(String name)
    {
            TimeOff.TimeOffLimits manageLimits, viewLimits, employeeDataLimits;
            LoginPage page = new LoginPage (driver);
            EmployeesPage empl = page.DoLogin ("company.admin", "anything");
            NavigationBar nav = new NavigationBar (driver);
            empl.EnterInSearch (name);
            EmployeeDataPage data = empl.SelectFirstMatchingEmployee ();
            ManageTimeOffPage manageTimeOff = data.GotoManageTimeOff ();
            manageLimits = manageTimeOff.GetTimeOffLimits ();
            data = manageTimeOff.GoBack ();
            ViewTimeOffPage viewTimeOff = data.GotoViewTimeOff ();
            viewLimits = viewTimeOff.GetTimeOffLimits ();
            Assert.assertTrue (manageLimits.equals(viewLimits));
            data = viewTimeOff.GoBack ();
            employeeDataLimits = data.GetTimeOffLimits ();
            Assert.assertTrue (manageLimits.equals (employeeDataLimits));
            page = nav.DoLogout ();
            Assert.assertTrue (page.HasLoginLink ());
    }

    @Test(dataProvider="TestUsedTimeOffData",dataProviderClass=BluesourceTestData.class)
    public void TestUsedVacationDays(String name)
    {
            TimeOff.TimeOffUsed manageUsed, viewUsed, employeeDataUsed;
            LoginPage page = new LoginPage (driver);
            EmployeesPage empl = page.DoLogin ("company.admin", "anything");
            NavigationBar nav = new NavigationBar (driver);
            empl.EnterInSearch (name);
            EmployeeDataPage data = empl.SelectFirstMatchingEmployee ();
            ManageTimeOffPage manageTimeOff = data.GotoManageTimeOff ();
            manageUsed = manageTimeOff.GetTimeOffUsed ();
            data = manageTimeOff.GoBack ();
            ViewTimeOffPage viewTimeOff = data.GotoViewTimeOff ();
            viewUsed = viewTimeOff.GetTimeOffUsed ();
            Assert.assertTrue (manageUsed.equals(viewUsed));
            data = viewTimeOff.GoBack ();
            employeeDataUsed = data.GetTimeOffUsed ();
            Assert.assertTrue (manageUsed.equals (employeeDataUsed));
            page = nav.DoLogout ();
            Assert.assertTrue (page.HasLoginLink ());
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import com.github.arven.bluesourcetests.pages.AddDepartmentPage;
import com.github.arven.bluesourcetests.pages.AddEmployeePage;
import com.github.arven.bluesourcetests.pages.AddTitlePage;
import com.github.arven.bluesourcetests.pages.DepartmentsPage;
import com.github.arven.bluesourcetests.pages.EmployeeDataPage;
import com.github.arven.bluesourcetests.pages.EmployeesPage;
import com.github.arven.bluesourcetests.pages.LoginPage;
import com.github.arven.bluesourcetests.pages.ManageTimeOffPage;
import com.github.arven.bluesourcetests.pages.NavigationBar;
import com.github.arven.bluesourcetests.pages.TimeOff;
import com.github.arven.bluesourcetests.pages.TitlesPage;
import com.github.arven.bluesourcetests.pages.ViewTimeOffPage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
            if (nav.hasLogoutLink ()) {
                LoginPage page = nav.doLogout ();
            }
        }
    }

    //NEWTESTS

    @Test
    public void testLoginLogout()
    {
        LoginPage page = new LoginPage (driver);
        page.doLogin ("company.admin", "anything");
        NavigationBar nav = new NavigationBar (driver);
        Assert.assertTrue (nav.hasLogoutLink ());
        page = nav.doLogout();
        Assert.assertTrue (page.hasLoginLink ());
    }
    
    @Test
    public void testAddEmployee()
    {
        LoginPage page = new LoginPage (driver);
        EmployeesPage empl = page.doLogin ("company.admin", "anything");
        NavigationBar nav = new NavigationBar (driver);
        Assert.assertTrue (nav.hasLogoutLink ());
        AddEmployeePage newempl = empl.gotoAddEmployee ();
        String user = UUID.randomUUID().toString ();
        String first = UUID.randomUUID().toString ();
        String last = UUID.randomUUID().toString ();
        newempl.AddEmployee (user, first, last);
        Assert.assertTrue (nav.hasAddedEmployeeText ());
        page = nav.doLogout ();
        Assert.assertTrue (page.hasLoginLink ());
    }

    @Test
    public void testAddDepartment()
    {
        LoginPage page = new LoginPage (driver);
        page.doLogin ("company.admin", "anything");
        NavigationBar nav = new NavigationBar (driver);
        DepartmentsPage depts = nav.gotoDepartments ();
        AddDepartmentPage addDept = depts.gotoAddDepartment ();
        depts = addDept.AddDepartment ("The Investigators");
        Assert.assertTrue (nav.hasAddedDepartmentText ());
        Assert.assertNotNull (depts.findDepartmentByName ("The Investigators"));
        depts.TrashDepartment ("The Investigators");
        // Assert.IsNull (depts.findDepartmentByName ("The Investigators"));
        page = nav.doLogout ();
        Assert.assertTrue (page.hasLoginLink ());
    }

    @Test
    public void testAddTitle()
    {
        LoginPage page = new LoginPage (driver);
        page.doLogin ("company.admin", "anything");
        NavigationBar nav = new NavigationBar (driver);
        TitlesPage titles = nav.gotoTitles ();
        AddTitlePage addTitle = titles.gotoAddTitle();
        titles = addTitle.addTitle ("Agent");
        Assert.assertTrue (nav.hasAddedTitleText ());
        Assert.assertNotNull (titles.findTitleByName ("Agent"));
        titles.trashTitle ("Agent");
        // Assert.IsNull (titles.findTitleByName ("Agent"));
        page = nav.doLogout ();
        Assert.assertTrue (page.hasLoginLink ());
    }
    
    @Test(dataProvider="TestTimeOffData",dataProviderClass=BluesourceTestData.class)
    public void testTimeOff(String name, String s_start, String s_end, String type, String reason, boolean halfday, float days, boolean succeeds) throws Exception
    {   
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date start = sdf.parse(s_start);
        Date end = sdf.parse(s_end);
        
        LoginPage page = new LoginPage (driver);
        EmployeesPage empl = page.doLogin ("company.admin", "anything");
        NavigationBar nav = new NavigationBar (driver);
        empl.enterInSearch (name);
        EmployeeDataPage data = empl.selectFirstMatchingEmployee ();
        ManageTimeOffPage timeOff = data.gotoManageTimeOff ();
        timeOff = timeOff.setVacationInfo (start, end, type, reason, halfday);
        if (succeeds) {
            WebElement time = timeOff.getVacationInfo (start);
            Assert.assertNotNull (time);
            String vacationDays = time.findElement(By.cssSelector(".business-days")).getText();
            timeOff.trashVacationInfo (start);
            Assert.assertTrue(Float.parseFloat(vacationDays) == days);
            // Assert.IsNull (timeOff.getVacationInfo (start));
        }
        page = nav.doLogout ();
        Assert.assertTrue (page.hasLoginLink ());
    }

    @Test(dataProvider="TestTotalTimeOffData",dataProviderClass=BluesourceTestData.class)
    public void testTotalVacationDays(String name)
    {
        TimeOff.TimeOffLimits manageLimits, viewLimits, employeeDataLimits;
        LoginPage page = new LoginPage (driver);
        EmployeesPage empl = page.doLogin ("company.admin", "anything");
        NavigationBar nav = new NavigationBar (driver);
        empl.enterInSearch (name);
        EmployeeDataPage data = empl.selectFirstMatchingEmployee ();
        ManageTimeOffPage manageTimeOff = data.gotoManageTimeOff ();
        manageLimits = manageTimeOff.getTimeOffLimits ();
        data = manageTimeOff.goBack ();
        ViewTimeOffPage viewTimeOff = data.gotoViewTimeOff ();
        viewLimits = viewTimeOff.getTimeOffLimits ();
        Assert.assertTrue (manageLimits.equals(viewLimits));
        data = viewTimeOff.goBack ();
        employeeDataLimits = data.getTimeOffLimits ();
        Assert.assertTrue (manageLimits.equals (employeeDataLimits));
        page = nav.doLogout ();
        Assert.assertTrue (page.hasLoginLink ());
    }

    @Test(dataProvider="TestUsedTimeOffData",dataProviderClass=BluesourceTestData.class)
    public void testUsedVacationDays(String name)
    {
        TimeOff.TimeOffUsed manageUsed, viewUsed, employeeDataUsed;
        LoginPage page = new LoginPage (driver);
        EmployeesPage empl = page.doLogin ("company.admin", "anything");
        NavigationBar nav = new NavigationBar (driver);
        empl.enterInSearch (name);
        EmployeeDataPage data = empl.selectFirstMatchingEmployee ();
        ManageTimeOffPage manageTimeOff = data.gotoManageTimeOff ();
        manageUsed = manageTimeOff.getTimeOffUsed ();
        data = manageTimeOff.goBack ();
        ViewTimeOffPage viewTimeOff = data.gotoViewTimeOff ();
        viewUsed = viewTimeOff.getTimeOffUsed ();
        Assert.assertTrue (manageUsed.equals(viewUsed));
        data = viewTimeOff.goBack ();
        employeeDataUsed = data.getTimeOffUsed ();
        Assert.assertTrue (manageUsed.equals (employeeDataUsed));
        page = nav.doLogout ();
        Assert.assertTrue (page.hasLoginLink ());
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import com.github.arven.bluesourcetests.pages.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import org.testng.Assert;

/**
 * Tests for the BlueSource Web Application
 * 
 * @author brian.becker
 */
public class BluesourceTests extends BaseWebTest {

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
        empl.enterInSearch(first + " " + last);
        EmployeeDataPage data = empl.selectFirstMatchingEmployee();
        data.SyncElement(By.cssSelector("#content > h1"));
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
            float time = timeOff.getVacationDays (start);
            timeOff.trashVacationInfo (start);
            Assert.assertTrue(time == days);
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

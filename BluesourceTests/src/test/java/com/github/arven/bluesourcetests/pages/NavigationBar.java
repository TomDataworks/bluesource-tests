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

/**
 *
 * @author brian.becker
 */
public class NavigationBar extends BaseWebPage
{
        @FindBy(how = How.XPATH, using = "//a[@href=\"/logout\"]")
        private WebElement logout;

        @FindBy(how = How.LINK_TEXT, using = "Admin")
        private WebElement admin;

        @FindBy(how = How.LINK_TEXT, using = "Employees")
        private WebElement employees;

        @FindBy(how = How.LINK_TEXT, using = "Departments")
        private WebElement departments;

        @FindBy(how = How.LINK_TEXT, using = "Titles")
        private WebElement titles;

        public NavigationBar ( WebDriver driver ) {
            super(driver);
        }

        public boolean HasLogoutLink() {
                return SyncElement (By.xpath("//a[@href=\"/logout\"]"));
        }

        public boolean HasAddedEmployeeText() {
                return SyncElement (By.xpath ("//div[contains(., \"Employee successfully created.\")]"));
        }

        public boolean HasAddedDepartmentText() {
                return SyncElement (By.xpath ("//div[contains(., \"Department successfully created.\")]"));
        }

        public boolean HasAddedTitleText() {
                return SyncElement (By.xpath ("//div[contains(., \"Title successfully created.\")]"));
        }

        public LoginPage DoLogout() {
                logout.click();
                return new LoginPage (driver);
        }

        public EmployeesPage GotoEmployees() {
                employees.click (); 
                return new EmployeesPage (driver);
        }

        public DepartmentsPage GotoDepartments() {
                admin.click ();
                departments.click ();
                return new DepartmentsPage (driver);
        }

        public TitlesPage GotoTitles() {
                admin.click ();
                titles.click ();
                return new TitlesPage (driver);
        }
}

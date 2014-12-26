/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import java.util.Collection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 *
 * @author brian.becker
 */
public class EmployeesPage extends BaseWebPage
{
        @FindBy(how = How.XPATH, using = "//button[contains(., \"Add\")]")
        private WebElement addEmployee;

        @FindBy(how = How.CSS, using = "input#search-bar")
        private WebElement search_bar;

        public EmployeesPage ( WebDriver driver ) {
            super(driver);
        }

        public AddEmployeePage gotoAddEmployee() {
                addEmployee.click ();
                return new AddEmployeePage (driver);
        }

        public EmployeesPage enterInSearch(String name) {
                SyncElement (By.cssSelector("#resource-content .ng-binding"));
                search_bar.sendKeys(name);
                return new EmployeesPage (driver);
        }

        public Collection<WebElement> getMatchingEmployees() {
                return driver.findElements (By.cssSelector ("div#resource-content div table tbody tr.ng-scope a"));
        }

        public EmployeeDataPage selectFirstMatchingEmployee() {
                for (WebElement we : getMatchingEmployees()) {
                        we.click(); break;
                }
                return new EmployeeDataPage(driver);
        }
}
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

public class DepartmentsPage extends BaseWebPage
{
        @FindBy(how = How.LINK_TEXT, using = "Add Department")
        private WebElement addDepartment;

        public DepartmentsPage ( WebDriver driver ) {
            super(driver);
        }

        public AddDepartmentPage gotoAddDepartment() {
                addDepartment.click ();
                return new AddDepartmentPage (driver);
        }

        public WebElement findDepartmentByName( String name ) {
                return driver.findElement (By.xpath ("//li[contains(., '" + name + "')]"));
        }

        public DepartmentsPage TrashDepartment( String name ) {
                findDepartmentByName(name).findElement(By.cssSelector("span.glyphicon.glyphicon-trash")).click();
                this.waitAndAcceptAlert();
                return new DepartmentsPage (driver);
        }
}

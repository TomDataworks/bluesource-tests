/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddDepartmentPage extends BaseWebPage
{
    private WebElement department_name;
    private WebElement department_department_id;

    public AddDepartmentPage ( WebDriver driver ) {
        super(driver);
    }

    public DepartmentsPage AddDepartment(String name) {
        if (SyncElement (By.id("department_name"))) {
                department_name.sendKeys (name);
                department_department_id.findElement (By.xpath ("//option[@value != '']")).click ();
                department_name.submit ();
        }
        return new DepartmentsPage (driver);
    }
}
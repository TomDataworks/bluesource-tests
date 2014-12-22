/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;

public class AddEmployeePage extends BaseWebPage
{
        private WebElement employee_username;
        private WebElement employee_first_name;
        private WebElement employee_last_name;
        private WebElement employee_title_id;
        private WebElement employee_role;
        private WebElement employee_manager_id;
        private WebElement employee_status;
        private WebElement employee_bridge_time;
        private WebElement employee_location;
        private WebElement employee_start_date;
        private WebElement employee_cell_phone;
        private WebElement employee_office_phone;
        private WebElement employee_email;
        private WebElement employee_im_name;
        private WebElement employee_im_client;
        private WebElement employee_department_id;

        public AddEmployeePage ( WebDriver driver ) {
            super(driver);
        }

        public EmployeesPage AddEmployee(String name, String first, String last) {
                SyncElement (employee_username);
                this.employee_username.click ();
                this.employee_username.sendKeys (name);
                this.employee_first_name.sendKeys (first);
                this.employee_last_name.sendKeys (last);
                this.employee_username.submit ();

                return new EmployeesPage (driver);
        }
}
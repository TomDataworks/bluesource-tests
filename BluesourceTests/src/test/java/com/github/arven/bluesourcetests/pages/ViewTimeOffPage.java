/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

public class ViewTimeOffPage extends BaseWebPage {
        @FindBy(how = How.CLASS_NAME, using = "vacation-summary-table")
        private WebElement vacation_summary;

        public ViewTimeOffPage ( WebDriver driver ) {
            super(driver);
        }

        public TimeOff.TimeOffLimits GetTimeOffLimits() {
                TimeOff.TimeOffLimits limits = new TimeOff.TimeOffLimits ();
                limits.sick =  Float.parseFloat( StringUtils.split(StringUtils.split(vacation_summary.findElement (By.xpath("span[2]")).getText(), '/')[1], ' ')[0] );
                limits.vacation = Float.parseFloat( StringUtils.split(StringUtils.split(vacation_summary.findElement (By.xpath ("span[3]")).getText(), '/')[1], ' ')[0] );
                limits.floating = Float.parseFloat( StringUtils.split(StringUtils.split(vacation_summary.findElement (By.xpath("span[4]")).getText(), '/')[1], ' ')[0] );
                return limits;
        }

        public TimeOff.TimeOffUsed GetTimeOffUsed() {
                TimeOff.TimeOffUsed used = new TimeOff.TimeOffUsed ();
                //Console.WriteLine (vacation_summary.FindElement(By.XPath("span[position() > 1]")).Text);
                used.sick =  Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath("span[2]")).getText(), '/')[0] );
                used.vacation = Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath ("span[3]")).getText(), '/')[0] );
                used.floating = Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath("span[4]")).getText(), '/')[0] );
                return used;
        }

        public EmployeeDataPage GoBack() {
                driver.navigate ().back ();
                return new EmployeeDataPage (driver);
        }
}
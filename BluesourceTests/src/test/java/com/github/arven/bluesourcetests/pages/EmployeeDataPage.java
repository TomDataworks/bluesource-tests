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

/**
 *
 * @author brian.becker
 */
public class EmployeeDataPage extends BaseWebPage
{
        @FindBy(how = How.XPATH, using = "//div/h4[contains(., \"Time Off Info\")]/../div/a[contains(., \"Manage\")]")
        private WebElement manageTimeOff;

        @FindBy(how = How.XPATH, using = "//div/h4[contains(., \"Time Off Info\")]/../div/a[contains(., \"View\")]")
        private WebElement viewTimeOff;

        @FindBy(how = How.ID, using = "panel_body_3")
        private WebElement vacation_summary;

        public EmployeeDataPage ( WebDriver driver ) {
            super(driver);
        }

        public ManageTimeOffPage gotoManageTimeOff() {
            SyncElement (By.xpath ("//div/h4[contains(., \"Time Off Info\")]/../div/a[contains(., \"Manage\")]"));
            manageTimeOff.click ();
            return new ManageTimeOffPage (driver);
        }

        public ViewTimeOffPage gotoViewTimeOff() {
            SyncElement (By.xpath ("//div/h4[contains(., \"Time Off Info\")]/../div/a[contains(., \"View\")]"));
            viewTimeOff.click ();
            return new ViewTimeOffPage (driver);
        }

        public TimeOff.TimeOffLimits getTimeOffLimits() {
            TimeOff.TimeOffLimits used = new TimeOff.TimeOffLimits ();
            //Console.WriteLine (vacation_summary.FindElement(By.XPath("span[position() > 1]")).Text);
            used.sick =  Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath("div/table/tbody/tr[1]/td[2]")).getText(), '/')[1] );
            used.vacation = Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath ("div/table/tbody/tr[2]/td[2]")).getText(), '/')[1] );
            used.floating = Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath("div/table/tbody/tr[3]/td[2]")).getText(), '/')[1] );
            return used;
        }

        public TimeOff.TimeOffUsed getTimeOffUsed() {
            TimeOff.TimeOffUsed limits = new TimeOff.TimeOffUsed ();
            //Console.WriteLine (vacation_summary.FindElement(By.XPath("span[position() > 1]")).Text);
            limits.sick =  Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath("div/table/tbody/tr[1]/td[2]")).getText(), '/')[0] );
            limits.vacation = Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath ("div/table/tbody/tr[2]/td[2]")).getText(), '/')[0] );
            limits.floating = Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath("div/table/tbody/tr[3]/td[2]")).getText(), '/')[0] );
            return limits;
        }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 *
 * @author brian.becker
 */
public class ManageTimeOffPage extends BaseWebPage
{
        @FindBy(how = How.NAME, using = "new[vacation][start_date]")
        private WebElement start_date;

        @FindBy(how = How.NAME, using = "new[vacation][end_date]")
        private WebElement end_date;

        @FindBy(how = How.NAME, using = "new[vacation][vacation_type]")
        private WebElement vacation_type;

        @FindBy(how = How.NAME, using = "new[vacation][reason]")
        private WebElement other_reason;

        @FindBy(how = How.ID, using = "new_vacation_vacation_type")
        private WebElement dropdown;

        @FindBy(how = How.XPATH, using = "//label[contains(., 'Half Day')]")
        private WebElement vacation_half_day;

        @FindBy(how = How.CLASS_NAME, using = "vacation-summary-table")
        private WebElement vacation_summary;

        @FindBy(how = How.LINK_TEXT, using = "Back")
        private WebElement back_link;

        public ManageTimeOffPage ( WebDriver driver ) {
            super(driver);
        }

        public ManageTimeOffPage setVacationInfo( Date start, Date end, String type, String reason, boolean halfday ) throws Exception {
                Actions mouse = new Actions(driver);
                
                SyncElement (By.name ("new[vacation][start_date]"));
                SimpleDateFormat fmt = new SimpleDateFormat("MMddyyyy");
                
                String start_s = fmt.format(start); //.ToString ("yyyy-MM-dd", CultureInfo.InvariantCulture);
                String end_s = fmt.format(end); //.ToString ("yyyy-MM-dd", CultureInfo.InvariantCulture);
                
                this.helper.setDate(start_date, start);
                this.helper.setDate(end_date, end);
                
                vacation_type.findElement (By.xpath ("//option[contains(., \"" + type + "\")]")).click();
                if (reason != null && !reason.isEmpty()) {
                        mouse.moveToElement (dropdown).build ().perform ();
                        other_reason.sendKeys (reason);
                }
                if (halfday) {
                        vacation_half_day.click ();
                }
                start_date.submit ();
                return new ManageTimeOffPage (driver);
        }

        public WebElement getVacationInfo( Date start ) {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            String start_s = fmt.format(start);
            return driver.findElement (By.xpath ("//input[@value = '" + start_s + "']/../.."));
        }

        public void trashVacationInfo( Date start ) {
                getVacationInfo(start).findElement (By.xpath("//a[@data-method = 'delete']")).click();
                driver.switchTo().alert().accept();
        }

        public Collection<WebElement> getAllTimeOff() {
                return driver.findElements (By.xpath ("//tr/td/span"));
        }

        public TimeOff.TimeOffLimits getTimeOffLimits() {
                TimeOff.TimeOffLimits limits = new TimeOff.TimeOffLimits ();
                //Console.WriteLine (vacation_summary.FindElement(By.XPath("span[position() > 1]")).Text);
                limits.sick =  Float.parseFloat( StringUtils.split(StringUtils.split(vacation_summary.findElement (By.xpath("span[2]")).getText(), '/')[1], ' ')[0] );
                limits.vacation = Float.parseFloat( StringUtils.split(StringUtils.split(vacation_summary.findElement (By.xpath ("span[3]")).getText(), '/')[1], ' ')[0] );
                limits.floating = Float.parseFloat( StringUtils.split(StringUtils.split(vacation_summary.findElement (By.xpath("span[4]")).getText(), '/')[1], ' ')[0] );
                return limits;
        }

        public TimeOff.TimeOffUsed getTimeOffUsed() {
                TimeOff.TimeOffUsed used = new TimeOff.TimeOffUsed ();
                //Console.WriteLine (vacation_summary.FindElement(By.XPath("span[position() > 1]")).Text);
                used.sick =  Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath("span[2]")).getText(), '/')[0] );
                used.vacation = Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath ("span[3]")).getText(), '/')[0] );
                used.floating = Float.parseFloat( StringUtils.split(vacation_summary.findElement (By.xpath("span[4]")).getText(), '/')[0] );
                return used;
        }

        public EmployeeDataPage goBack() {
                back_link.click ();
                return new EmployeeDataPage (driver);
        }
}
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

public class TitlesPage extends BaseWebPage
{
        public TitlesPage ( WebDriver driver ) {
            super(driver);
        }

        @FindBy(how = How.LINK_TEXT, using = "New Title")
        private WebElement addTitle;

        public AddTitlePage GotoAddTitle() {
                addTitle.click ();
                return new AddTitlePage (driver);
        }

        public WebElement FindTitleByName( String name ) {
                return driver.findElement (By.xpath ("//tr[contains(., '" + name + "')]"));
        }

        public TitlesPage TrashTitle( String name ) {
                FindTitleByName(name).findElement(By.cssSelector("span.glyphicon.glyphicon-trash")).click();
                driver.switchTo ().alert ().accept ();
                return new TitlesPage (driver);
        }
}
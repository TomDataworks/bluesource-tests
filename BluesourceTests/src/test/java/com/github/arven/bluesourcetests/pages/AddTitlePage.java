/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class AddTitlePage extends BaseWebPage
{
    private WebElement title_name;

    public AddTitlePage ( WebDriver driver ) {
        super(driver);
    }

    public TitlesPage AddTitle(String name) {
        if (SyncElement (title_name)) {
            title_name.sendKeys (name);
            title_name.submit ();
        }
        return new TitlesPage (driver);
    }
}

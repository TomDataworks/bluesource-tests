/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import org.openqa.selenium.WebDriver;

/**
 *
 * @author brian.becker
 */
public class WebHelperFactory {
    public static WebHelper getFromDriver(WebDriver drv) {
        if(drv.getClass() == org.openqa.selenium.chrome.ChromeDriver.class ) {
            return new ChromeWebHelper();
        } else if(drv.getClass() == org.openqa.selenium.ie.InternetExplorerDriver.class ) {
            return new IEWebHelper();
        } else {
            return new FirefoxWebHelper();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.openqa.selenium.WebElement;

/**
 *
 * @author brian.becker
 */
public class FirefoxWebHelper implements WebHelper {
    @Override
    public void setDate(WebElement element, Date value) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        String dstring = fmt.format(value);
        element.clear();
        element.sendKeys(dstring);
    }
}

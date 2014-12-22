package com.github.arven.bluesourcetests;

import java.util.Date;
import org.openqa.selenium.WebElement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author brian.becker
 */
public interface WebHelper {
    public void setDate(WebElement element, Date value);
}

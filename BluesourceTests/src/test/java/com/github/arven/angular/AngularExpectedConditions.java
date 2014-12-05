/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.angular;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class AngularExpectedConditions {

    public static ExpectedCondition angularHasFinishedProcessing() {
        return new ExpectedCondition() {
            @Override
            public Object apply(Object f) {
                if(f instanceof JavascriptExecutor) {
                    JavascriptExecutor js = (JavascriptExecutor) f;
                    return Boolean.valueOf(js.executeScript("return jQuery.active;").toString());
                } else {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }
            }
        };
    }
}

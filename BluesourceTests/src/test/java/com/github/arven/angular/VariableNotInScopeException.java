/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.angular;

import org.openqa.selenium.WebDriverException;

public class VariableNotInScopeException extends WebDriverException {

    public VariableNotInScopeException(String msg) {
        super(msg);
    }
}

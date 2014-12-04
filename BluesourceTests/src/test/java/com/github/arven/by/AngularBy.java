/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.by;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 *
 * @author brian.becker
 */
public class AngularBy implements ElementLocator {
//    return driver.findElements(
//webdriver.By.js(clientSideScripts.findBindings,
//bindingDescriptor, false, using, rootSelector));
//
    private final SearchContext searchContext;
    private final boolean shouldCache;
    private WebElement cachedElement;
    private List<WebElement> cachedElementList;
    
    /**
    * Creates a new element locator.
    *
    * @param searchContext The context to use when finding the element
    * @param field
    */
    public AngularBy(SearchContext searchContext, Field field) {
        this.searchContext = searchContext;
        this.shouldCache = false;
    }
    
    @Override
    public WebElement findElement() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<WebElement> findElements() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

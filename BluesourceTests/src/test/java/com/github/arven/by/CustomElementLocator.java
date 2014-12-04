/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.by;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 *
 * @author brian.becker
 */
public class CustomElementLocator implements ElementLocator {
    private final OtherFindBy ann;
    private ElementLocator locator;
    private String binding;
    
    public CustomElementLocator(SearchContext searchContext, Field field) {
        this.ann = field.getAnnotation(OtherFindBy.class);
        try {
            this.locator = (ElementLocator) this.ann.with().getConstructor(new Class[]{SearchContext.class, Field.class}).newInstance(new Object[]{searchContext, field});
            this.binding = this.ann.using();
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InstantiationException | InvocationTargetException ex) {
            Logger.getLogger(CustomElementLocator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if(o instanceof AngularBy) {
            return this.toString().equals(o.toString());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.binding);
        return hash;
    }
    
    @Override
    public String toString() {
        return this.locator.getClass().getName() + "(" + this.binding + ")";
    }

    @Override
    public WebElement findElement() {
        return this.locator.findElement();
    }

    @Override
    public List<WebElement> findElements() {
        return this.locator.findElements();
    }
}

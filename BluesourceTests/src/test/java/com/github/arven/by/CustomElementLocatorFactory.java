/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.by;

import org.openqa.selenium.SearchContext;
import java.lang.reflect.Field;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.pagefactory.DefaultElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocator;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

/**
 *
 * @author brian.becker
 */
public class CustomElementLocatorFactory implements ElementLocatorFactory {
    private final SearchContext searchContext;

    public CustomElementLocatorFactory(SearchContext searchContext) {
        this.searchContext = searchContext;
    }

    @Override
    public ElementLocator createLocator(Field field) {
        if(field.isAnnotationPresent(FindBy.class)) {
            return new DefaultElementLocator(searchContext, field);
        } else { // if(field.isAnnotationPresent(MoreFindBy.class)){
            return new CustomElementLocator(searchContext, field);
        }
    }
}
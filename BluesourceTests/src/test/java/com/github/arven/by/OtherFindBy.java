/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.by;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.openqa.selenium.support.pagefactory.ElementLocator;

/**
 *
 * @author brian.becker
 */
@Retention(RetentionPolicy.RUNTIME) // Make this annotation accessible at runtime via reflection.
@Target({ElementType.FIELD})       // This annotation can only be applied to class methods.
public @interface OtherFindBy {
    public abstract Class<? extends ElementLocator> with();
    public abstract String using();
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.by;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 *
 * @author brian.becker
 * @param <T>
 */
public class CustomPageFactory {
  public static <T> T initElements(WebDriver driver, Class<T> klazz) {
      try {
          T page = klazz.getConstructor(new Class[] {WebDriver.class}).newInstance(driver);
          //        System.out.println(klazz.getConstructor(new Class[]{WebDriver.class}).newInstance(new Object[]{driver}).getClass().toString());
          PageFactory.initElements(new CustomElementLocatorFactory(driver), page);
          //Constructor<T> constructor =  //(Constructor<T>) klazz.getClass getConstructor(new Class[]{WebDriver.class});
          return page;
          //return constructor.newInstance(driver);
      } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
          Logger.getLogger(CustomPageFactory.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
  }
}

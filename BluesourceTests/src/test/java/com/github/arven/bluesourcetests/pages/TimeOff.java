/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests.pages;

/**
 *
 * @author brian.becker
 */
public class TimeOff
{
        public static class TimeOffLimits extends TimeOff {}
        public static class TimeOffUsed extends TimeOff {}

        public float sick;
        public float vacation;
        public float floating;

        public TimeOff ()
        {
        }

        @Override
        public boolean equals(Object obj) 
        {
                // Check for null values and compare run-time types.
            if(obj == null)
                return false;
            
            if (!this.getClass().equals(obj.getClass())) 
                return false;

            TimeOff p = (TimeOff)obj;
            return (sick == p.sick) && (vacation == p.vacation) && (floating == p.floating);
        }

        @Override
        public int hashCode() 
        {
                return (int) (sick + vacation + floating);
        }

        @Override
        public String toString()
        {
                return "Sick Days: " + sick + ", Vacation Days: " + vacation + ", Floating Holidays: " + floating;
        }
}
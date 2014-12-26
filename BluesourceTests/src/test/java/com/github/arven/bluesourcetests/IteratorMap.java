/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.bluesourcetests;

import java.util.Iterator;

/**
 *
 * @author brian.becker
 * @param <In>
 * @param <Out>
 */
public abstract class IteratorMap<In, Out> implements Iterator<Out> {
    private final Iterator<In> iter; // = parser.iterator();
    private In record;
    
    public IteratorMap(Iterator<In> iter) {
        this.iter = iter;
    }
    
    public IteratorMap(Iterable<In> iter) {
        this.iter = iter.iterator();
    }

    @Override
    public boolean hasNext() {
        return iter.hasNext();
    }
    
    public abstract Out map(In in);

    @Override
    public Out next() {
        this.record = iter.next();
        return map(this.record);
    }

    @Override
    public void remove() {
        // null operation
        throw new UnsupportedOperationException("Read-only collection"); //To change body of generated methods, choose Tools | Templates.
    }
}

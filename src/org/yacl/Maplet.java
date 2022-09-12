package org.yacl;

/**
If X and Y are sets, then X &harr; Y is the set of binary relations between X
and Y. Each such relation is a subset of X x Y . The 'maplet' notation from x to y 
is another way of expressing the ordered pair (x, y).

@author Brad Long
**/
public class Maplet<T1, T2> {
    
    protected T1 x;
    protected T2 y;
    
    protected Maplet () {
        super();
    }
    
    public Maplet (T1 x, T2 y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Returns x in (x,y).
    **/
    public T1 first() {
        return x;
    }
    
    /**
     * Returns y in (x,y).
    **/
    public T2 second() {
        return y;
    }
    
    /**
     * Returns x in (x,y).
    **/    
    public T1 x() {
        return x;
    }

    /**
     * Returns y in (x,y).
    **/    
    public T2 y() {
        return y;
    }
    
    public boolean equals (Object o) {
        Maplet m = (Maplet)o;
        return (x.equals(m.x()) && y.equals(m.y()));
    }
    
    public int hashCode() {
        return (x.hashCode() * 7 + y.hashCode() * 13);
    }    
}



package org.yacl;


/**
This class provides similar functionality to <code>java.util.Map</code>, however,
it is properly descended from a Set (<code>au.edu.jcu.Set</code>, hence
implements the <code>java.util.Set</code> interface). It does
not implement the <code>java.util.Map</code> interface because of conflicts between the
<code>java.util.Map</code> and <code>java.util.Set</code> interfaces.

If X and Y are sets, X &rarr; Y is the set of partial functions from X to Y.
These are relations which relate each member x of X to at most one member
of Y.

@author Brad Long
**/
public interface Function<K, V> extends Relation<K, V> {
    
    
    /**
	 * Adds a maplet to this function. Existing maplets with the same x 
	 * in (x,y) are overwritten with the given maplet.
	 *
	 * @param m	the maplet to add to the function
	 * @return the object, y in (x,y), that was replaced or <code>null</code> if 
	 * there was no maplet having a key of x in the function.
	**/
    public V put(Maplet<K, V> m);
    
    /**
	 * Adds two objects representing a maplet to this function.
	 * Existing maplets with the same x 
	 * in (x,y) are overwritten with the given maplet. 
	 *
	 * @param m	the maplet to add to the function
	 * @return the object, y in (x,y), that was replaced or <code>null</code> if 
	 * there was no maplet having a key of x in the function.
	**/
    public V put(K key, V value);

	public boolean add(Maplet<K, V> m);
    
    /**
     * Returns the maplet that a given key participates in.
     * @param key	the key (x) with which to obtain its corresponding (y) value
     * @return the maplet that the given key participates in, or <code>null</code>
     * if no maplet exists with the given key.
     * 
    **/
    public Maplet<K, V> getMaplet(K key);
    
    public V getValue(K key);
    
    /**
     * An alternative for <code>range().contains(value)</code>.
     * @param value	the object to check for existence in the range of this function
     * @return <code>true</code> if a given value, y, is contained in the range of this
     * function
    **/
    public boolean containsValue(V value);
    
    
    /**
     * An alternative for <code>domain().contains(key)</code>.
     * @param key	the object to check for existence in the domain of this function
     * @return <code>true</code> if a given value, x, is contained in the domain of
     * this function
    **/
    public boolean containsKey(K key);
}

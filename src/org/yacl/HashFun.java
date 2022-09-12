package org.yacl;

import java.util.Collection;
import java.util.Map;
import java.util.Iterator;

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
public class HashFun<T1, T2> extends HashRel<T1, T2> implements Function<T1, T2> {
    
    private Lambda lambda = null;
    private Set<Maplet<T1, T2>> vDom = null;
    
    public HashFun() {
        super();
    }
    
	/**
	 * Constructs a function between two collections. Each maplet is created
	 * by traversing the iterators of each collection and associating
	 * each element of c1 with an element from c2. Duplicate elements in c1 are ignored, therefore
	 * requiring the number of <i>unique</i> elements in c1 to equal the number of
	 * elements in c2. This provides the same logical abstraction as <code>java.util.Map</code> but
	 * is properly contained in the collections hierarchy.
	 *
	 * @param c1		any collection
	 * @param c2     	any collection
	 * @throws IllegalArgumentException if the collections do not contain the same number of unique elements
	**/
    public HashFun(Collection<T1> c1, Collection<T2> c2) {
    	Set<T1> s = new HashSet<>(c1);
        if (s.size() != c2.size()) throw new IllegalArgumentException("The number of unique elements in c1 must equal the number of elements in c2");
		super.buildRelation(s,c2);
    }

	/**
	 * Constructs a function containing all elements in the supplied collection
	 * of maplets.
	 * @throws ClassCastException if the collection does not contain maplets
	**/
	public HashFun(Collection<Maplet<T1, T2>> c) {
		super(c);
	}

    /**
     * Constructs a function from a given <code>java.util.Map</code>.
    **/
	public HashFun(Map<T1, T2> m) {
		super(m);
	}

   /**
     * Constructs a function from a given function.
    **/	
	public HashFun(Function<T1, T2> f) {
		super(f);
	}
	
   /**
     * Constructs a function from a lambda.
    **/		
	public HashFun(Lambda l) {
	    lambda = l;
	}
	
	public HashFun(Lambda l, Set validDomain) {
	    lambda = l;
	    vDom = validDomain;
	}
	
	protected Function<T1, T2> getInstance() {
	    return new HashFun<>();
    }

	/**
	 * Adds a maplet to this function. An exception is thrown
	 * if a maplet already exists with the same x 
	 * in (x,y).
	 *
	 * @param m    the maplet to add to the function
	 * @return <code>true</code> if the maplet was added to this function
	 * @throws DuplicateKeyException if the maplet already exists
	**/
    public boolean add(Maplet<T1, T2> m) {
        Maplet<T1, T2> throwAway = this.getMaplet(m.x());
        if (throwAway != null) throw new RuntimeException("Duplicate Key"); //DuplicateKeyException();
        return super.add(m);
    }

    /**
	 * Adds two objects representing a maplet to this function.
	 * Existing maplets with the same x 
	 * in (x,y) are overwritten with the given maplet.
	 *
	 * @param x	the key to add to the function; x in the maplet (x,y)
	 * @param y the value to add to the function; y in the maplet (x,y)
	 * @return the object, y in (x,y), that was replaced or <code>null</code> if 
	 * there was no maplet having a key of x in the function.
	**/
    public T2 put(T1 x, T2 y) {
        return this.put(new Maplet<>(x,y));
    }
    
    /**
	 * Adds a maplet to this function. Existing maplets with the same x 
	 * in (x,y) are overwritten with the given maplet.
	 *
	 * @param m	the maplet to add to the function
	 * @return the object, y in (x,y), that was replaced or <code>null</code> if 
	 * there was no maplet having a key of x in the function.
	**/
    public T2 put(Maplet<T1, T2> m) {
        Maplet<T1, T2> maplet = this.getMaplet(m.x());
        if (maplet != null) {
            remove(maplet);
        }
        // We can call super.add here since we
        // doing all the work of add anyway.
        super.add(m);
        return (maplet == null ? null : maplet.y());
    }
    
    /**
     * Returns the value that a given key maps to.
     * @param key	the key (x) with which to obtain its corresponding (y) value
     * @return the object that the given key maps to
     * 
    **/
    public T2 getValue(T1 key) {
        Maplet<T1, T2> m = this.getMaplet(key);
        if (m == null) return null;
        return m.y();
    }
    
    /**
     * Returns the maplet that a given key participates in.
     * @param key	the key (x) with which to obtain its corresponding (y) value
     * @return the maplet that the given key participates in
     * 
    **/
    public Maplet<T1, T2> getMaplet(T1 key) {
        for (Iterator<Maplet<T1, T2>> iter = super.iterator(); iter.hasNext();) {
            Maplet<T1, T2> m = iter.next();
            if (m.x().equals(key)) return m;
        }
        //if (lambda!=null) return new Maplet<K, V>(key, (V)lambda.expression(key));
        return null;
    }

	/**
	 * An alternative for <code>range().contains(value)</code>.
	 * @param value	the object to check for existence in the range of this function
	 * @return <code>true</code> if a given value, y, is contained in the range of this
	 * function
	 **/
	public boolean containsValue(T2 value) {
		return super.range().contains(value);
	}


	/**
	 * An alternative for <code>domain().contains(key)</code>.
	 * @param key	the object to check for existence in the domain of this function
	 * @return <code>true</code> if a given value, x, is contained in the domain of
	 * this function
	 **/
	public boolean containsKey(T1 key) {
		return super.domain().contains(key);
	}
}


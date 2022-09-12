package org.yacl;

import java.util.Collection;

/**
 A collection that contains no duplicate elements. As implied by
 its name, this class models the mathematical <i>set</i> abstraction.<p>

 @author Brad Long
**/
public class HashSet<T> extends java.util.HashSet implements Set<T> {
	
	public HashSet() {
		super();
	}
	
	/**
	 * Constructs a set containing all elements in the supplied collection
	**/
	public HashSet(Collection c) {
		super(c);
	}

    protected Set<T> getInstance() {
        return new HashSet<T>();
    }
    
    public Set<T> addElement(T el) {
        Set s = getInstance();
        s.add(el);
        return s;
    }

    /**
     * Constructs the union of a given set with this set.
	 * The members of <code>S.union(T)</code> are those objects which are members of
	 * <code>S</code> or <code>T</code> or both. 
	 *
	 * @param t        the set to union with this set
	 * @return  a set being the union of <code>this</code> and <code>t</code>
	 * @see java.util.Set#addAll(Collection c)
    **/
    public Set<T> union (Set<T> t) {
        Set s = getInstance();
        s.addAll(this);
        s.addAll(t);
        return s;
    }        
    
    /**
     * Returns the difference of a given set with this set.
     * The members of <code>S.subtract(T)</code> are those objects which are members of
     * <code>S</code> but not of <code>T</code>.
     *
     * @param t        the set to subtract from this set
     * @return  a set being the difference between <code>this</code> and <code>t</code>
     * @see java.util.Set#removeAll(Collection c)
    **/
    public Set<T> difference (Set<T> t) {
        Set<T> s = getInstance();
        s.addAll(this);
        s.removeAll(t);
        return s;
    }
    
    
    /**
     * Returns the intersection of this set with a given set.
     * The members of <code>S.intersect(T)</code> are those objects which are members of both
     * <code>S</code> and <code>T</code>.
     * 
     * @param t        the set with which to intersect
     * @return  a set being the intersection of <code>this</code> and <code>t</code>
     * @see java.util.Set#retainAll(Collection c)
    **/   
    public Set<T> intersection (Set<T> t) {
        Set<T> s = getInstance();
        s.addAll(this);
        s.retainAll(t);
        return s;   
    }

    /**
     * Determines whether a given set contains at least the elements in this set.
     *
     * @param t        the given set with which to compare
     * @return <code>true</code>    if this set is a subset of <code>t</code>
    **/
    public boolean isSubsetOf (Set<T> t) {
        return t.containsAll(this);
    }
    
    /**
     * Determines whether this set is a proper subset of a given set.
     *
     * @param t        the given set with which to compare
     * @return <code>true</code>    if this set is a subset of <code>t</code>
    **/
    public boolean isProperSubsetOf (Set<T> t) {
        return (t.containsAll(this) && size() < t.size());
    }
    
    /**
     * Constructs a relation mapping each value in this set onto itself.
     * 
     * @return the identity <code>Relation</code> for this set
    **/
    public Relation<T, T> identity () {
        Relation<T, T> r = new HashRel<T, T>();
        int i=0;
        for (Object[] o = toArray(); i<o.length; ++i) {
            Maplet m = new Maplet(o[i], o[i]);
            r.add(m);
        }
        return r;
    }
    
    /** 
     * Constructs the cartesian product (X x Y) of two sets X and Y.
     * @param y the set Y in X x Y 
     * @return the cartesian product of this set and the given set.
    **/
    public Relation cartesianProduct(Set y) {
        Relation r = new HashRel();
        int i=0;
        for (Object[] o = toArray(); i < o.length; ++i) {
            int j=0;
            for (Object[] o2 = y.toArray(); j < o2.length; ++j) {
                r.add(new Maplet(o[i],o2[j]));
            }
        }
        return r;
    }
        
}

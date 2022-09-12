package org.yacl;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;


/**
  A relation is a set of relationships between two objects. Each (x,y)
  is termed a binary relation (or maplet) relating some x of set X to 
  some y of set Y, contained within the relation X &harr; Y.<p>
  
  Note that this is not equivalent to a <code>java.util.Map</code> which
  enforces a unique set of keys (properly called a function). In other words,
  a relation allows x of X to appear multiple times so long as each x relates
  to a different y of Y.<p>

  If X and Y are sets, then X &harr; Y is the set of binary relations between X
  and Y. Each such relation is a subset of X x Y. A 'maplet' from x to y
  is another way
  of expressing the ordered pair (x, y).<p>
  
  For example, the following are valid binary relations (maplets, ordered pairs)
  within a Relation R:<br>
  <br>
  (tom, mary)<br>
  (fred, jane)<br>
  (tom, jane)<br>
  <p>
  
  @author Brad Long
**/
public class HashRel<T1, T2> extends HashSet<Maplet<T1,T2>> implements Relation<T1, T2> {
    
    public HashRel() {
        super();
    }

    protected Relation<T1, T2> getInstance() {
        return new HashRel<T1, T2>();
    }        

	/**
	 * Constructs a relation between two collections. Each maplet is created
	 * by traversing the iterators of each collection and associating
	 * each x of X with y of Y.
	 *
	 * @param x		any collection
	 * @param y 	any collection
	 * @throws IllegalArgumentException if the collections are not the same size
	**/
    public HashRel(Collection<T1> x, Collection<T2> y) {
		buildRelation(x, y);
    }
    
    protected void buildRelation(Collection<T1> x, Collection<T2> y) {
        if (x.size() != y.size()) throw new IllegalArgumentException("Collections must be the same size");
        Iterator<T1> xi = x.iterator();
        Iterator<T2> yi = y.iterator();
        while (xi.hasNext()) {
            Maplet<T1, T2> maplet = new Maplet<>((T1)xi.next(), (T2)yi.next());
            super.add(maplet);
        }    	
    }

    /**
     * Constructs a relation from a given <code>java.util.Collection</code>.
    **/
    public HashRel(Collection<Maplet<T1, T2>> c) {
        this.addAll(c);
    }

    /**
     * Constructs a relation from a given <code>java.util.Map</code>.
    **/
    public HashRel(Map<T1, T2> m) {
        this.addAll(m);
    }
    
    /**
     * Constructs a relation from the given relation R.
    **/
    public HashRel(Relation<T1, T2> r) {
        super(r);
    }
    
    /**
     * Returns a set containing the unique objects in X of the relation X &harr; Y.<p>
     *
     * If R is a binary relation between X and Y, then the domain of R is
     * the set of all members of X which are related to at least one member of Y by
     * R.
     *
     * @return the set of unique objects contained within X
    **/
    public Set<T1> domain() {
        Set<T1> domain = new HashSet<T1>();
        int i=0;
        for (Object[] o = this.toArray(); i < o.length; ++i) {
            Maplet<T1, T2> m = (Maplet<T1, T2>) o[i];
            domain.add(m.x());
        }
        return domain;
    }
    
    /**
     * Returns a set containing the unique objects in Y of the relation X &harr; Y.<p>
     *
     * If R is a binary relation between X and Y, then the range of R is
     * the set of all members of Y to which at least one member of X is related by R.
    **/
    public Set<T2> range () {
        Set<T2> range = new HashSet<T2>();
        int i=0;
        for (Object[] o = this.toArray(); i < o.length; ++i) {
            Maplet<T1, T2> m = (Maplet<T1, T2>) o[i];
            range.add(m.y());
        }
        return range;
    }

    /**
     * Constructs the union of a given set with this relation.
     * The members of <code>R.union(S)</code> are those objects which are members of
     * <code>R</code> or <code>S</code> or both.
     *
     * @param s        the set to union with this relation
     * @return  a relation being the union of <code>this</code> and <code>s</code>
     * @see java.util.Set#addAll(Collection c)
     **/
    public Relation<T1, T2> union (Set<Maplet<T1, T2>> s) {
        Relation<T1, T2> r = getInstance();
        r.addAll(this);
        r.addAll(s);
        return r;
    }

    /**
     * Overrides <code>add</code> in <code>Set</code> to ensure only
     * maplets are added.
     *
     * @param m        the maplet to be added
     * @return <code>true</code> if the object was added to this relation
     * @throws ClassCastException if the object is not a <code>Maplet</code>
    **/
    public boolean add(Maplet<T1, T2> m) {
    	return super.add(m);
    }

    /**
     * Overrides <code>add</code> in <code>Set</code> to ensure only
     * maplets are added.
     *
     * @param t1        the x-value of the maplet to be added
     * @param t2        the y-value of the maplet to be added
     * @return <code>true</code> if the object was added to this relation
     * @throws ClassCastException if the object is not a <code>Maplet</code>
     **/
    public boolean add(T1 t1, T2 t2) {
        return add(new Maplet<>(t1, t2));
    }
    
    /**
     * Adds all entries in a <code>java.util.Collection</code> to this relation. Overrides <code>addAll</code>
     * in <code>Set</code> to ensure only <code>Maplet</code>s are added.
     * @param c	any collection
     * @return <code>true</code> if any objects of the collection were added to this relation
     * @throws ClassCastException if any element is not a maplet
    **/
    public boolean addAll(Collection<? extends Maplet<T1, T2>> c) {
        boolean changed = false;
        Object[] o = c.toArray();
        for (Object value : o) {
            changed = this.add((Maplet<T1, T2>) value) || changed;
        }
        return changed;
    }
 
    /**
     * Adds all entries in a <code>java.util.Map</code> to this relation.
     * @param m	any <code>java.util.Map</code>
     * @return <code>true</code> if any objects of the map were added to this relation
    **/   
    public boolean addAll(Map<T1,T2> m) {
    	boolean changed = false;
        Object[] o = m.keySet().toArray();
        for (Object key : o) {
            changed = this.add(new Maplet<T1, T2>((T1) key, (T2) m.get(key))) || changed;
        }
        return changed;
    }    
    
    
    /**
     * Returns a relation being only those maplets in this relation whose x in (x,y) appear
     * in the given set.<p>
     * The domain restriction R.domainRestriction(S) of this relation R to a set S,
     * relates x to y if and only if R relates x to y and x is a member of S.
     * @param s	the set of objects with which to restrict the domain of this relation
     * @return a relation being this relation restricted to elements in the domain contained in <code>s</code>
    **/ 
    public Relation<T1, T2> domainRestriction (Set<T1> s) {
        Relation<T1, T2> r = new HashRel<>();
        for (Maplet<T1, T2> m : this) {
            for (T1 t1 : s) {
                if (m.x().equals(t1)) r.add(m);
            }
        }
        return r;
    }
    
    
    /**
     * Returns a relation being those maplets in this relation whose x in (x,y) 
     * do not appear in the given set.<p>
     * This operation is the complemented counterpart of the domain restriction
     * operation. An object x is related to an object y by the relation 
     * R.domainAntiRestrict(S) if and only if x is related to y by R and x is
     * not a member of S.
     *
     * @param s	the set of objects with which to anti-restrict this relation.
     * By definition, these objects will not appear in the domain of the restricted relation.
     * @return a relation containing all maplets in this relation that are not in <code>s</code>
    **/
    public Relation<T1, T2> domainAntiRestriction (Set<T1> s) {
        Relation<T1, T2> r = new HashRel<>();
        for (Maplet<T1, T2> m : this) {
            boolean match = false;
            for (T1 t1 : s) {
                if (m.x().equals(t1)) match = true;
            }
            if (!match) r.add(m);
        }
        return r; 
    }
    
    /**
     * Returns a relation containing only those maplets of this relation
     * whose y in (x,y) appear in the given set.<p>
     * The range restriction R.rangeRestrict(T) of R to a set T
     * relates x to y if and only if
     * R relates x to y and y is a member of T.
     * @param t	the set of objects with which to restrict the range of this relation
     * @return a relation consisting of this relation restricted to maplets with y in (x,y) in <code>t</code>
    **/
    public Relation<T1, T2> rangeRestriction (Set<T2> t) {
        Relation<T1, T2> r = new HashRel<>();
        for (Maplet<T1, T2> m : this) {
            for (T2 t2 : t) {
                if (m.y().equals(t2)) r.add(m);
            }
        }
        return r;
    }
   

    /**
     * Returns a relation containing those maplets in this relation
     * whose y in (x,y) do not appear in the given set.<p>
     * This operation is the complemented counterpart of the domain restriction
     * operation. An object x is related to an object y by the relation 
     * R.rangeAntiRestrict(T) if and only if x is related to y by R and y is
     * not a member of T.
     * @param t	the set of objects with which to anti-restrict this relation.
     * By definition, these objects will not appear in the range of the restricted relation.
     * @return a relation containing maplets in this relation whose y in (x,y) do not appear
     * in <code>t</code>
     * 
    **/
    public Relation<T1, T2> rangeAntiRestriction (Set<T2> t) {
        Relation<T1, T2> r = new HashRel<>();
        for (Maplet<T1, T2> m : this) {
            boolean match = false;
            for (T2 t2 : t) {
                if (m.y().equals(t2)) match = true;
            }
            if (!match) r.add(m);
        }
        return r;
    }
    
    /**
     * Returns the relational inverse of this relation, thus each maplet (x,y) is inverted to (y,x).<p>
     * An object y is related to an object x by the relational inverse R&sim; of R if and
     * only if x is related to y by R.
     * @return a relation being the inverse of this relation
     * 
    **/
    public Relation<T2, T1> inverse() {
        Relation<T2, T1> r = new HashRel<>();
        int i=0;
        for (Object[] o = this.toArray(); i < o.length; ++i) {
            Maplet<T1, T2> m = (Maplet<T1, T2>) o[i];
            Maplet<T2, T1> n = new Maplet<>(m.y(), m.x());
            r.add(n);
        }
        return r;
    }
   
    
    /**
     * Returns the relational composition of this relation and another given relation, thus
     * if (x,y) exists in this relation Q and (y,z) exists in R then (x,z) appears in the composed relation.<p>
     * The composition Q.compose(R) of two relations Q: X &harr; Y and R: Y &harr; Z relates a
     * member x of X to a member z of Z if and only if there is at least one element
     * y of Y to which x is related by Q and which is itself related to z by R.
     * @param r	the relation R with which to perform the composition
     * @return a relation composed of this relation with <code>r</code>
    **/
    public Relation<T1, T2> composition (Relation<T1, T2> r) {
        Relation<T1, T2> rel = new HashRel<T1, T2>();
        int i=0;        
        for (Object[] o = this.toArray(); i < o.length; ++i) {
            Maplet<T1, T2> m1 = (Maplet<T1, T2>) o[i];
            int j=0;
            for (Object[] o2 = r.toArray(); j<o2.length; ++j) {
                Maplet<T1, T2> m2 = (Maplet<T1, T2>) o2[j];
                if (m1.y().equals(m2.x())) {
                    Maplet<T1, T2> m = new Maplet<>(m1.x(), m2.y());
                    rel.add(m);
                }
            }
        }
        return rel;
    }

    
    /**
     * Returns a relation being this relation overridden with a given relation.
     * The relation Q.override(R) relates everything in the domain of R to the same objects
     * as R does, and everything else in the domain of Q to the same objects as Q does.
     * @param r	the relation to override this relation
     * @return a relation being the composition of this relation with <code>r</code>
    **/
    public Relation<T1, T2> override (Relation<T1, T2> r) {
     	Relation<T1, T2> rel = new HashRel<>();
        rel.addAll(this.domainAntiRestriction(r.domain()));
//        System.out.println("relation: " + rel);
        rel.addAll(r);
        return rel;
    }

    
    /**
     * Returns the transitive closure of this relation.<p>
     * If R is a relation from a set X to itself, R.transitiveClosure(),
     * or R+, is the strongest or smallest relation
     * containing R which is transitive.<p>
     *
     * Example:<br><br>
     * if R = { (a,b), (b,c), (a,e), (c,d) }<br>
     * then R+ = { (a,b), (b,c), (a,e), (c,d), (a,c), (a,d), (b,d) }<br><br>
     * If a relation Relation.transitiveClosure(r).intersect(Relation.identity(r)) is
     * not empty, then the transitive closure contains one or more cycles.
     *
     * @return a relation being the transitive closure of this relation
    **/
    public Relation<T1, T2> transitiveClosure () {
        Relation<T1, T2> rel = new HashRel<>(this);
        int s = 0, t = 0;
        do {
            Relation<T1, T2> r = new HashRel<>(rel);
            r = new HashRel<>(r.composition(rel));
            s = rel.size();
            rel.addAll(r);            
            t = rel.size();
        } while (s != t);
        return rel;
    }
        
    
    /**
     * Returns a set containing the range of a domain restriction on this relation,
     * that is, returns a set of all y of Y for which x of X is related in X &harr; Y.
     * The relational image R.image(S) of a set S through a relation R is the set of all
     * objects y to which R relates some member x of S.<p>
     * The operation does not modify this relation.
     * @return the range of a relation formed by restricting the domain of this relation to
     * the given set
    **/
    public Set<T2> image (Set<T1> s) {
        return domainRestriction(s).range();
    }
    
    /**
     * @return <code>true</code> if elements in the domain of this relation
     * are unique
    **/
    public boolean isFunction () {
        return (domain().size() == size());
    }
    
    /**
     * @return <code>true</code> if elements in the domain of this relation
     * are unique and none of them map to the same object.
    **/
    public boolean isInjection () {
        return (isFunction() && domain().size() == range().size());
    }
    
    /**
     * @return <code>true</code> if all elements in the domain of this relation
     * are at least related to themselves in the range of this relation.
    **/
    public boolean isReflexive () {
        Set<T1> dom = this.domain();
        Relation<T1, T2> r = new HashRel<T1, T2>(dom, (Set<T2>)dom);
        return r.isSubsetOf(this);
    }
    
    /**
     * @return a <code>String</code> representation of this relation
    **/
    public String toString() {
        String str = "[";
        Object[] o = toArray();
        for (int i=0; i<o.length; ++i) {
            Maplet<T1, T2> m = (Maplet<T1, T2>) o[i];
            str = str + (i==0?"":", ") + m.x() + "->" + m.y();
        }
        str = str + "]";
        return str;
    }
    
}

package org.yacl;

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
public interface Relation<T1, T2> extends Set<Maplet<T1, T2>> {
    
 
    /**
     * Returns a set containing the unique objects in X of the relation X &harr; Y.<p>
     *
     * If R is a binary relation between X and Y, then the domain of R is
     * the set of all members of X which are related to at least one member of Y by
     * R.
     *
     * @return the set of unique objects contained within X
    **/
    public Set<T1> domain();

    
    /**
     * Returns a set containing the unique objects in Y of the relation X &harr; Y.<p>
     *
     * If R is a binary relation between X and Y, then the range of R is
     * the set of all members of Y to which at least one member of X is related by R.
    **/
    public Set<T2> range ();
    
 
    /**
     * Adds all entries in a <code>java.util.Map</code> to this relation.
     * @param m	any <code>java.util.Map</code>
     * @return <code>true</code> if any objects of the map were added to this relation
     * @see Set#addAll(Collection c)
    **/   
    public boolean addAll(Map<T1, T2> m);

    public boolean add(T1 t1, T2 t2);

    /**
     * Returns a relation being only those maplets in this relation whose x in (x,y) appear
     * in the given set.<p>
     * The domain restriction R.domainRestriction(S) of this relation R to a set S,
     * relates x to y if and only if R relates x to y and x is a member of S.<p>
     * Example:<p>
     * if r = { (a,b), (a,c), (b,d) } and s = {b}<br>
     * then r.domainRestriction(s) = { (b,d) }
     * @param s	the set of objects with which to restrict the domain of this relation
     * @return a relation being this relation restricted to elemnets in the domain contained in <code>s</code>
    **/ 
    public Relation<T1, T2> domainRestriction (Set<T1> s);
    
    
    /**
     * Returns a relation being those maplets in this relation whose x in (x,y) 
     * do not appear in the given set.<p>
     * This operation is the complemented counterpart of the domain restriction
     * operation. An object x is related to an object y by the relation 
     * R.domainAntiRestrict(S) if and only if x is related to y by R and x is
     * not a member of S.<p>
     * Example:<p>
     * if r = { (a,b), (a,c), (b,d) } and s = {b}<br>
     * then r.domainAntiRestriction(s) = { (a,b), (a,c) }
     *
     * @param s	the set of objects with which to anti-restrict this relation.
     * By definition, these objects will not appear in the domain of the restricted relation.
     * @return a relation containing all maplets in this relation that are not in <code>s</code>
    **/
    public Relation<T1, T2> domainAntiRestriction (Set<T1> s);

    
    /**
     * Returns a relation containing only those maplets of this relation
     * whose y in (x,y) appear in the given set.<p>
     * The range restriction R.rangeRestrict(T) of R to a set T
     * relates x to y if and only if
     * R relates x to y and y is a member of T.<p>
     * Example:<p>
     * if r = { (a,b), (a,c), (b,d) } and t = {d}<br>
     * then r.rangeRestriction(t) = { (b,d) }
     * @param t	the set of objects with which to restrict the range of this relation
     * @return a relation consisting of this relation restricted to maplets with y in (x,y) in <code>t</code>
    **/
    public Relation<T1, T2> rangeRestriction (Set<T2> t);


    /**
     * Returns a relation containing those maplets in this relation
     * whose y in (x,y) do not appear in the given set.<p>
     * This operation is the complemented counterpart of the domain restriction
     * operation. An object x is related to an object y by the relation 
     * R.rangeAntiRestrict(T) if and only if x is related to y by R and y is
     * not a member of T.<p>
     * Example:<p>
     * if r = { (a,b), (a,c), (b,d) } and t = {d}<br>
     * then r.rangeAntiRestriction(t) = { (a,b), (a,c) }
     * @param t	the set of objects with which to anti-restrict this relation.
     * By definition, these objects will not appear in the range of the restricted relation.
     * @return a relation containing maplets in this relation whose y in (x,y) do not appear
     * in <code>t</code>
     * 
    **/
    public Relation<T1, T2> rangeAntiRestriction (Set<T2> t);
    

    /**
     * Returns the relational inverse of this relation, thus each maplet (x,y) is inverted to (y,x).<p>
     * An object y is related to an object x by the relational inverse R&sim; of R if and
     * only if x is related to y by R.<p>
     * Example:<p>
     * if r = { (a,b), (a,c), (b,d) }<br>
     * then r.inverse() = { (b,a), (c,a), (d,b) }
     * @return a relation being the inverse of this relation
     * 
    **/
    public Relation<T2, T1> inverse();
      
    
    /**
     * Returns the relational composition of this relation and another given relation, thus
     * if (x,y) exists in this relation Q and (y,z) exists in R then (x,z) appears in the composed relation.<p>
     * The composition Q.composition(R) of two relations Q: X &harr; Y and R: Y &harr; Z relates a
     * member x of X to a member z of Z if and only if there is at least one element
     * y of Y to which x is related by Q and which is itself related to z by R.<p>
     * Example:<p>
     * if q = { (a,b), (a,c), (b,d) } and r = { (b,e), (b,f), (d,g) }<br>
     * then q.composition(r) = { (a,e), (a,f), (b,g) }
     * @param r	the relation R with which to perform the composition
     * @return a relation composed of this relation with <code>r</code>
    **/
    public Relation<T1, T2> composition (Relation<T1, T2> r);


    /**
     * Returns a relation being this relation overridden with a given relation.
     * The relation Q.override(R) relates everything in the domain of R to the same objects
     * as R does, and everything else in the domain of Q to the same objects as Q does.<p>
     * Example:<p>
     * if q = { (a,b), (a,c), (b,d) } and r = { (a,d), (c,e) }<br>
     * then q.override(r) = { (a,d), (b,d), (c,e) }
     * @param r	the relation to override this relation
     * @return a relation being the composition of this relation with <code>r</code>
    **/
    public Relation<T1, T2> override (Relation<T1, T2> r);

    
    /**
     * Returns the transitive closure of this relation.<p>
     * If R is a relation from a set X to itself, R.transitiveClosure(),
     * or R+, is the strongest or smallest relation
     * containing R which is transitive.<p>
     * Example:<p>
     * if r = { (a,b), (a,c), (b,d), (d,e) }<br>
     * then r.transitiveClosure() = { (a,b), (a,d), (a,e), (a,c), (b,d), (b,e), (d,e) }<p>
     *
     * If a relation Relation.transitiveClosure(r).intersect(Relation.identity(r)) is
     * not empty, then the transitive closure contains one or more cycles.<p>     
     * @return a relation being the transitive closure of this relation
    **/
    public Relation<T1, T2> transitiveClosure ();

    
    /**
     * Returns a set containing the range of a domain restriction on this relation,
     * that is, returns a set of all y of Y for which x of X is related in X &harr; Y.
     * The relational image R.image(S) of a set S through a relation R is the set of all
     * objects y to which R relates some member x of S.<p>
     * Example:<p>
     * if r = { (a,b), (a,c), (b,d) } and s = {a}<br>
     * then r.image(s) = {b,c}
     * @return the range of a relation formed by restricting the domain of this relation to
     * the given set
    **/
    public Set<T2> image (Set<T1> s);
    
    /**
     * @return <code>true</code> if elements in the domain of this relation
     * are unique
    **/
    public boolean isFunction ();
    
    /**
     * @return <code>true</code> if elements in the domain of this relation
     * are unique and none of them map to the same object.
    **/
    public boolean isInjection ();
    
    /**
     * @return <code>true</code> if all elements in the domain of this relation
     * are at least related to themselves in the range of this relation.
    **/
    public boolean isReflexive ();

}

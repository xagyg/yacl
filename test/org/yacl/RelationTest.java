package org.yacl;

import junit.framework.*;
import java.util.Arrays;

public class RelationTest extends TestCase {
    
 
    public void testAdd() {
        
        Relation r = new HashRel();
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","jane"));
       
        assertTrue(r.size()==2);
        r.add(new Maplet("fred","mary"));
        assertTrue(r.size()==2);
        r.add(new Maplet("tom","jane"));
        assertTrue(r.size()==2);
        r.add(new Maplet("fred","jane"));
        assertTrue(r.size()==3);
    }
   
    public void testDomainAndRange() {
        
        Relation r = new HashRel();
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","jane"));
        
        Set boys = new HashSet();
        boys.add("fred");
        boys.add("tom");
        
        Set girls = new HashSet();
        girls.add("mary");
        girls.add("jane");
        
        assertEquals("1. Domain should contain boys",r.domain(),boys);
        assertEquals("2. Range should contain girls",r.range(),girls);
        
        assertTrue("3. Domain should contain \"fred\"",r.domain().contains("fred"));
        assertTrue("4. Domain erroneously contains \"larry\"",!r.domain().contains("larry"));
        assertTrue("5. Range should contain \"mary\"",r.range().contains("mary"));
        assertTrue("6. Range erroneously contains \"kim\"",!r.range().contains("kim"));
        
        assertTrue("7. Domain restricted relation should not contain \"tom\"",!r.domainRestriction(new HashSet().addElement("fred")).domain().contains("tom"));
        assertTrue("8. Domain restricted relation should contain \"fred\"",r.domainRestriction(new HashSet().addElement("fred")).domain().contains("fred"));
        assertTrue("9. Domain anti-restricted relation should not contain \"fred\"",!r.domainAntiRestriction(new HashSet().addElement("fred")).domain().contains("fred"));
        assertTrue("10. Domain anti-restricted relation should contain \"tom\"",r.domainAntiRestriction(new HashSet().addElement("fred")).domain().contains("tom"));
        assertTrue("11. Range restricted relation should not contain \"jane\"",!r.rangeRestriction(new HashSet().addElement("mary")).range().contains("jane"));
        assertTrue("12. Range restricted relation should contain \"mary\"",r.rangeRestriction(new HashSet().addElement("mary")).range().contains("mary"));
//        System.out.println("range anti " + r.rangeAntiRestriction(new HashSet().addElement("mary")));
        assertTrue("13. Range anti-restricted relation should contain \"jane\"",r.rangeAntiRestriction(new HashSet().addElement("mary")).range().contains("jane"));
        assertTrue("14. Range anti-restricted relation should not contain \"mary\"",!r.rangeAntiRestriction(new HashSet().addElement("mary")).range().contains("mary"));

        
        r.add(new Maplet("tom","mary"));
        assertTrue("7. Domain restricted relation should not contain \"tom\" maplets",!r.domainRestriction(new HashSet().addElement("fred")).domain().contains("tom"));
    }
  
    public void testInverse() {
    
        assertTrue(new HashRel().inverse().isEmpty());
        Relation r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","kim"));
        
        Relation q = new HashRel();
        q.add(new Maplet("jane","tom"));
        q.add(new Maplet("mary","fred"));
        q.add(new Maplet("kim","tom"));
        
        assertTrue(r.inverse().equals(q));
     
    }

    public void testReflexivity() {
        Relation r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","kim"));
        assertFalse(r.isReflexive());
        r = new HashRel();
        r.add(new Maplet("tom","tom"));
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","kim"));
        assertFalse(r.isReflexive());
        r = new HashRel();
        r.add(new Maplet("tom","tom"));
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","fred"));
        r.add(new Maplet("tom","kim"));
        assertTrue(r.isReflexive());
    }

    public void testFunction() {
        Relation r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","kim"));
        assertFalse(r.isFunction());
        r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("alice","mary"));
        assertTrue(r.isFunction());
    }

    public void testInjection() {
        Relation r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","kim"));
        assertFalse(r.isInjection());
        r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("alice","mary"));
        assertFalse(r.isInjection());
        r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("alice","fred"));
        assertTrue(r.isInjection());
    }
    
    public void testCompose() {
 
        Relation r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","kim"));
        
        Relation q = new HashRel();
        q.add(new Maplet("jane","car"));
        q.add(new Maplet("mary","truck"));
        q.add(new Maplet("kim","train"));
        
        // Expected result:
        // tom,car
        // fred,truck
        // tom,train
        Maplet[] maplets = { new Maplet("tom","car"),
                             new Maplet("fred","truck"),
                             new Maplet("tom","train")
                           };
        Relation expected = new HashRel(Arrays.asList(maplets));
        
        Relation actual = r.composition(q);
        assertEquals(actual.size(),3);
        assertEquals(expected, actual);
    }

    public void testOverride() {
                 
        Relation r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","kim"));
        r.add(new Maplet("harry","eve"));
        
        Relation q = new HashRel();
        q.add(new Maplet("jane","car"));
        q.add(new Maplet("fred","truck"));
        q.add(new Maplet("tom","train"));
        
        // Expected result:
        // tom,train
        // fred,truck
        // harry,eve
        // jane,car
        Maplet[] maplets = { new Maplet("tom","train"),
                             new Maplet("fred","truck"),
                             new Maplet("harry","eve"),
                             new Maplet("jane","car")
                           };
        Relation expected = new HashRel(Arrays.asList(maplets));
        
        Relation actual = r.override(q);
        assertEquals(4, actual.size());
        assertEquals(expected, actual);
    }
    
    public void testClosure() {
    	Relation r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","kim"));
        r.add(new Maplet("mary","eve"));
        r.add(new Maplet("eve","ron"));
        
        // Expected result:
        // tom,jane
        // fred,mary
        // fred,eve
        // fred,ron
        // tom,kim
        // mary,eve
        // mary,ron
        // eve, ron

        Maplet[] maplets = new Maplet[] { new Maplet("tom","jane"),
        					  new Maplet("fred","mary"),
        					  new Maplet("fred","eve"),
        					  new Maplet("fred","ron"),
        					  new Maplet("tom","kim"),
        					  new Maplet("mary","eve"), 
        					  new Maplet("mary","ron"),
        					  new Maplet("eve","ron")
        					};
        Relation expected = new HashRel(Arrays.asList(maplets));
        
        Relation actual = r.transitiveClosure();
//        System.out.println(actual);
        assertEquals(8,actual.size());
        assertEquals(expected, actual);
    }
    
    public void testChaining() {
        Relation r = new HashRel();
        r.add(new Maplet("tom","jane"));
        r.add(new Maplet("fred","mary"));
        r.add(new Maplet("tom","kim"));
        r.add(new Maplet("harry","eve"));
        
        Relation q = new HashRel();
        q.add(new Maplet("jane","car"));
        q.add(new Maplet("fred","truck"));
        q.add(new Maplet("tom","train"));
        
        assertTrue(r.domainRestriction(new HashSet().addElement("tom")).union(q).contains(new Maplet("jane","car")));
        assertTrue(((Relation)r.domainRestriction(new HashSet().addElement("tom")).union(q)).rangeRestriction(new HashSet().addElement("car")).contains(new Maplet("jane","car")));
    }


    public void testCasting() {
        Relation q = new HashRel();
        q.add("larry","jane");
        q.add("jeff","mary");
        q.add("jules","kim");
        
        Relation r = new HashRel();
        r.add("tom","jane");
        r.add("fred","mary");
        r.add("tom","kim");
        
        Set s = new HashSet();
        s.add(new Maplet("jack", "jill"));
       // s.add("ron");
        
        Set actual = r.union(s);
        assertEquals(actual.size(),4);
        assertTrue(actual instanceof Set);
        assertTrue(actual instanceof Relation);
    
        actual = s.union(r);
        assertEquals(actual.size(),4);
        assertTrue(actual instanceof Set);
        assertFalse(actual instanceof Relation);
        
        actual = q.union(r);
        assertEquals(actual.size(),6);
        assertTrue(actual instanceof Relation);
    }

}
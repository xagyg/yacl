package org.yacl;

import junit.framework.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SetTest extends TestCase {
    
 
    public void testAdd() {
        
        Set s = new HashSet();
        assertTrue(s.size()==0);
        s.add("mary");
        assertTrue(s.contains("mary"));
        assertTrue(s.size()==1);
        s.add("tom");
        assertTrue(s.size()==2);
        s.add("tom");
        assertTrue(s.size()==2);
    }
    
    public void testIntersect() {
    
        assertTrue(new HashSet().intersection(new HashSet()).isEmpty());
        
        Set s = new HashSet();
        s.add("mary");
        s.add("tom");
        s.add("harry");
        
        Set t = new HashSet();
        
        assertTrue(new HashSet().intersection(t).isEmpty());
        
        t.add("mary");
        assertTrue(s.intersection(t).size()==1);
        assertTrue(s.intersection(t).contains("mary"));
        
        t.add("tom");
        assertTrue(s.intersection(t).size()==2);
        assertTrue(s.intersection(t).contains("mary"));
        assertTrue(s.intersection(t).contains("tom"));
        
        t.add("fred");
        assertTrue(s.intersection(t).size()==2);
        assertTrue(!s.intersection(t).contains("fred"));
    }
    
    public void testUnion() {
        assertTrue(new HashSet().union(new HashSet()).isEmpty());
        
        Set s = new HashSet();
        s.add("mary");
        s.add("tom");
        s.add("harry");
        
        Set t = new HashSet();
        
        assertTrue(s.union(t).size()==3);
        
        t.add("mary");
        assertTrue(s.union(t).size()==3);
        
        t.add("tom");
        assertTrue(s.union(t).size()==3);
        
        t.add("fred");
        assertTrue(s.union(t).size()==4);
    }
    
    public void testDifference() {
        assertTrue(new HashSet().difference(new HashSet()).isEmpty());
        
        Set s = new HashSet();
        s.add("mary");
        s.add("tom");
        s.add("harry");
        
        Set result = s.difference(new HashSet(List.of("tom")));
        assertTrue(!result.contains("tom"));
        assertEquals(result, new HashSet(Arrays.asList("mary","harry")));
    }

}
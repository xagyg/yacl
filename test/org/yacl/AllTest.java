package org.yacl;

import junit.framework.*;

public class AllTest extends TestCase {
 
  public static void main(String[] args)
  {
    junit.textui.TestRunner aTestRunner = new junit.textui.TestRunner();
    aTestRunner.doRun(suite(), false);    
  }      
      
  public static Test suite()
  {
    TestSuite suite = new TestSuite();
    
    suite.addTestSuite(SetTest.class);
    suite.addTestSuite(RelationTest.class);
    suite.addTestSuite(FunctionTest.class);
    return suite;
  }
}

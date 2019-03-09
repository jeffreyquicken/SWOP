# SWOP

Project for SWOP @KUL

# Short JUnit tutorial
**JUnit** is a unit testing framework for Java. JUnit has been important in the development of Test-driven development.
[JUnit Documentation](https://junit.org/junit5/docs/current/user-guide/)

## Writing tests

The following example provides a glimpse at the minimum requirements for writing a test in JUnit:

    class MyFirstJUnitTests {    
          
        @Test
        void clickingUnderTableShouldReturnZero() {
            assertEquals(expected, actual);
        }
    
    }
Every test method should be annotated with `@Test`. The method should be named in a way that tells the reader what should happen when the method is executed. The assertEquals method can be used to test values, as well as a bunch of other functions that can be found in the [JUnit Documentation](https://junit.org/junit5/docs/current/user-guide/).
## Annotations

 - @Test: This annotation is a replacement of org.junit.TestCase which  
   indicates that public void method to which it is attached can be     
   executed as a test Case.
     
  - @Before: This annotation is used if you want to execute some statement such as    preconditions before each test case.

  
 - @BeforeClass: This annotation is used if you want to execute some statements before    all the test cases for e.g. test connection must be    executed before    all the test cases.
  
 - @After: This annotation can be used if you want to execute some statements    after each test case for e.g    resetting    variables, deleting temporary files ,variables, etc.
  
 - @AfterClass: This annotation can be used if you want to execute some statements    after all test cases for e.g. Releasing resources after    executing all    test cases.
  
 - @Ignores: This annotation can be used if you want to ignore some statements    during test execution for e.g. disabling some test    cases during test    execution.
  
- @Test(timeout=500): This annotation can be used if you want to set some timeout during    test execution for e.g. if you are working under some SLA    (Service    level agreement), and tests need to be completed within    some    specified time.
  
 - @Test(expected=IllegalArgumentException.class): This annotation can be used if you want to handle some exception    during test execution. For, e.g., if you want to check    whether a    particular method is throwing specified exception or    not.

## Assertions

[More information about Assertions](https://www.guru99.com/junit-assert.html)

## Creating a Test Suite

[More information about Test Suites](https://www.guru99.com/create-junit-test-suite.html)

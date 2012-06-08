package nl.saxion.ein1b2.tests;

import junit.framework.Test;
import junit.framework.TestSuite;
import android.test.suitebuilder.TestSuiteBuilder;

/**
 * A test suite containing all tests for my application.
 */
public class JUnitTests extends TestSuite {
    public static Test suite() {
        return new TestSuiteBuilder(JUnitTests.class).includeAllPackagesUnderHere().build();
    }
}
package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        ObjectCreationTest.class,
        DeserializeTest.class,
        VisualizerOutputTest.class
})

public class TestSuite {
}

package utils;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * Created by David on 01/01/2017.
 */

@RunWith(Cucumber.class)
@CucumberOptions(features={"src/test/java/features"}, glue = "steps")
public class TestRunner {
}

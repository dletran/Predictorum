package steps;

import cucumber.api.Scenario;
import utils.BaseUtils;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.HashMap;

/**
 * Created by David on 23/12/2016.
 */
public class Hook extends BaseUtils{

    private BaseUtils base;

    public Hook(BaseUtils base) {
        this.base = base;
    }

    @Before
    public void initializeTest(Scenario scenario){
        base.data = new HashMap<String, Object>();
    }

    @After
    public void tearDownTest(){
        //Close web browser
        System.out.print("\nClosing the browser:"+base.data.get("browser"));
        base.driver.quit();
    }
}

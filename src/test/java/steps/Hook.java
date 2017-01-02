package steps;

import cucumber.api.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.BaseUtils;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public void initializeTest(){
        base.data = new HashMap<String, Object>();
    }

    @After
    public void tearDownTest(Scenario scenario){
        //Check failure
        if(scenario.isFailed()){
            File screenshot =  ((TakesScreenshot)base.driver).getScreenshotAs(OutputType.FILE);
            Calendar calendar = Calendar.getInstance();
            String dateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss").format(calendar.getTime());
            String filename = scenario.getName()+"_"+dateFormat+".png";
            try {
                FileUtils.copyFile(screenshot, new File("failure_screenshots\\"+filename));

            } catch (IOException e) {
                System.out.println("Failure taking screenshot for test: "+scenario.getName());
            }
        }

        //Delete cookies
        base.driver.manage().deleteAllCookies();
        //Close web browser
        System.out.print("\nClosing the browser:"+base.data.get("browser"));
        base.driver.quit();
    }
}

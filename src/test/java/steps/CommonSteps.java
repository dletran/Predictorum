package steps;

import cucumber.api.java.en.And;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.BaseUtils;

import java.lang.reflect.Field;

/**
 * Created by David on 31/12/2016.
 */
public class CommonSteps extends BaseUtils {

    private BaseUtils base;

    public CommonSteps(BaseUtils base) {
        this.base = base;
    }

    @And("^a browser (\\w*)$")
    public void aBrowser(String browser) throws Throwable {
        base.data.put("browser", browser);
        System.out.print("\nOpening the browser: " + browser);
        switch (browser) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\webdrivers\\chromedriver.exe");
                base.driver = new ChromeDriver();
                break;
            case "firefox":
                DesiredCapabilities capabilitiesFirefox = DesiredCapabilities.firefox();
                capabilitiesFirefox.setCapability("marionette", true);
                System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\webdrivers\\geckodriver.exe");
                base.driver = new FirefoxDriver(capabilitiesFirefox);
                break;
            case "explorer":
                DesiredCapabilities capabilitiesExplorer = DesiredCapabilities.internetExplorer();
                capabilitiesExplorer.setCapability("initialBrowserUrl","www.bing.com");
                System.setProperty("webdriver.ie.driver", "src\\test\\resources\\webdrivers\\IEDriverServer32.exe");
                base.driver = new InternetExplorerDriver(capabilitiesExplorer);
                break;
        }
        base.driver.manage().window().maximize();
        base.driver.manage().deleteAllCookies();
    }

    @And("^user sets (.*) field with value (\\w*)$")
    public void userSetsFieldWithValue(String field, String value) throws Throwable {
        //Retrieving value
        String finalValue = findValue(value);
        //Printing field to set and value
        System.out.print("\nSets " + field + " field with value " + finalValue);
        //Waiting time to ensure field is available
        WebDriverWait wait = new WebDriverWait(base.driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-ng-model='" + field + "']")));
        //Setting field with value
        base.driver.findElement(By.xpath("//input[@data-ng-model='" + field + "']")).sendKeys(finalValue);
    }


    //Support methods

    /**
     * Returns a String object with the value from input, in case input
     * follows format 'class_attribute', it would look for class.attribute
     * value. i.e.: 'user_password' would have the same behaviour as if
     * having an user object with a password attribute: user.getPassword().
     * <p>
     * In case input does NOT follows format 'class_attribute', being 'value' instead,
     * it will return a String object with the value from input. i.e.: 'password' would
     * return 'value'.
     *
     * @param input value to retrieve
     * @return the value of the specified input
     */
    private String findValue(String input) throws NoSuchFieldException, IllegalAccessException {
        //Defining default result as empty string
        String res = "";
        //Split input in case it refers to an attribute, then format would be class_attribute
        String[] values = input.split("_");
        //If length > 1 it would mean input is in format class_attribute so attribute value needs to be retrieved
        if (values.length > 1) {
            //Saving class in abstract object using first split value from class_attribute -> class
            Object o = base.data.get(values[0]);
            Class<?> c = o.getClass();
            //Saving field in abstract object using second split value from class_attribute -> attribute
            Field f = c.getDeclaredField(values[1]);
            f.setAccessible(true);
            //Casting to expected type
            res = (String) f.get(o);
        } else {
            //In case format is NOT class_attribute result would be input
            res = input;
        }
        return res;
    }
}

package steps;

import utils.BaseUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import domainTest.User;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.lang.reflect.Field;

/**
 * Created by David on 23/12/2016.
 */
public class SignInSteps extends BaseUtils{

    private BaseUtils base;

    public SignInSteps(BaseUtils base) {
        this.base = base;
    }

    @When("^user navigates to home page$")
    public void userNavigatesToHomePage() throws Throwable {
        base.driver.navigate().to("http://predictorum-dlgmanro.rhcloud.com/");
    }

    @Then("^user is logged correctly$")
    public void userIsLoggedCorrectly() throws Throwable {
        //Waiting time to ensure field is available
        WebDriverWait wait = new WebDriverWait(base.driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='logout']")));
        //Checking logout button is displayed, therefore user is logged in
        base.driver.findElement(By.xpath("//a[contains(@href,'logout')]")).isDisplayed();
    }

    @And("^menu is displayed$")
    public void menuIsDisplayed() throws Throwable {
        //Defining menu element
        WebElement menu = base.driver.findElement(By.id("svg-menu"));
        //Waiting to ensure menu is available
        WebDriverWait wait = new WebDriverWait(base.driver, 15);
        wait.until(ExpectedConditions.visibilityOf(menu));
        //Checking menu is displayed
        Assert.assertTrue(menu.isDisplayed());
    }

    @Given("^a registered user$")
    public void aRegisteredUser() throws Throwable {
        System.out.print("\nDefault user is David/David");
        //Creating default test user and adding it to data
        User user = new User("David","David");
        base.data.put("user",user);
    }



    @And("^user clicks on welcome button$")
    public void userClicksOnWelcomeButton() throws Throwable {
        WebDriverWait wait = new WebDriverWait(base.driver, 15);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("page-mask")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@scroll-to='welcome'and @ng-show='!showMenu']")));
        base.driver.findElement(By.xpath("//a[@scroll-to='welcome'and @ng-show='!showMenu']")).click();
    }

    @And("^user clicks on signIn button$")
    public void userClicksOnSignInButton() throws Throwable {
        WebDriverWait wait = new WebDriverWait(base.driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@scroll-to='welcome'and @ng-show='!showMenu']")));
        base.driver.findElement(By.xpath("//button[.='Entrar']")).click();
    }

}

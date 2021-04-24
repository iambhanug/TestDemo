package com.hcl.mortgage;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
	public WebDriver driver =null; 
	public Properties prop =null;
	public FileInputStream fi = null;
	public TestBase(){
		try{
		fi = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\prop.properties");
		prop=new Properties();
		prop.load(fi);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public WebDriver launchBrowser(String browserName) {

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\src\\test\\resources\\chromedriver.exe");
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--disable-notifications");
		ops.addArguments("--start-maximized");
		driver = new ChromeDriver(ops);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		return driver;
	}
	
	public void launchURL(String url){
		driver.get(prop.getProperty(url));
	}
	
	public WebElement elementfind(String locator){
		WebElement e=null;
		
		if(locator.endsWith("_xpath"))
			e = driver.findElement(By.xpath(prop.getProperty(locator)));
		else if(locator.endsWith("_css"))
			e = driver.findElement(By.cssSelector(prop.getProperty(locator)));
		
		return e;
	}
	
	public List<WebElement> elementsfind(String locator){
		List<WebElement> list=null;
		
		if(locator.endsWith("_xpath"))
			list = driver.findElements(By.xpath(prop.getProperty(locator)));
		else if(locator.endsWith("_css"))
			list = driver.findElements(By.cssSelector(prop.getProperty(locator)));
		
		return list;
	}

	public void click(String locator){
			exWait(locator).click();
	}

	public void click(String locator,String location){
		int x = elementfind(location).getLocation().getX();
		int y = elementfind(location).getLocation().getY();
		((JavascriptExecutor)driver).executeScript("window.scrollBy("+x+","+y+")");

		WebElement element = elementfind(locator);
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().perform();
	}
	
	
	public void enterText(String locator,String txt){
		exWait(locator).clear();
		exWait(locator).sendKeys(txt);
	}
	
	public void dropdownValueSelect(String locator, String value){
		Select drp = new Select(exWait(locator));
		drp.selectByVisibleText(value);
	}
	
	public void radioBtnSelectByTitle(String locator,String title){

		List<WebElement> radioRepoAmo = exWaitforMultiple(locator);
		
		for(int i =0;i<radioRepoAmo.size();i++){
			if(radioRepoAmo.get(i).getAttribute("title").contains(title)){
				radioRepoAmo.get(i).click();
			}
		}
	}
	
	public WebElement exWait(String locator){
		WebElement e=null;
		
		WebDriverWait wait = new WebDriverWait(driver, 20000);
		e = wait.until(ExpectedConditions.visibilityOf(elementfind(locator)));
		return e;
	}
	public WebElement exWaitForClickable(String locator){
		WebElement e=null;
		
		WebDriverWait wait = new WebDriverWait(driver, 20000);
		e = wait.until(ExpectedConditions.elementToBeClickable(elementfind(locator)));
		return e;
	}
	public List<WebElement> exWaitforMultiple(String locator){
		List<WebElement> e=null;
		
		WebDriverWait wait = new WebDriverWait(driver, 20000);
		e = wait.until(ExpectedConditions.visibilityOfAllElements(elementsfind(locator)));
		return e;
	}
	
}

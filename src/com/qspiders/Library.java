package com.qspiders;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class Library {
	public static String scriptStatus;
		
	public WebDriver driver;
	public Logger logger;
	public String keyword1,keyword2,keyword3,keyword4;
	
	public Library(WebDriver driver)
	{
		this.driver=driver;
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	public By getLocator() {
		By b=null;
		String FQCN="org.openqa.selenium.By";
		try {
			b=(By)Class.forName(FQCN).getMethod(keyword2,String.class).invoke(null,keyword3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	
	public boolean elementExist()
	{  
		try
		{
			
		 driver.findElement(getLocator());
		 logger.info("Element found with "+keyword2+" " +keyword3);
		 return true;
		}
		catch(NoSuchElementException ex)
		{
			return false;
		}
	}
	
	public void verifyTitle()
	{
		String actualTitle=driver.getTitle();
		if(actualTitle.equals(keyword2))
		{
			logger.info("Title is correct:"+keyword2);
		}
		else
		{  
			scriptStatus="FAIL";
			logger.error("FAIL:Title is not correct:Expected:"+keyword2+" Actual:"+actualTitle);
			
		}
	}
	
	public void verifyText()
	{ 
		if(elementExist())
		{
		 String actualText=driver.findElement(getLocator()).getText();
		 if(actualText.equalsIgnoreCase(keyword4))
		  {
			 logger.info("Text is correct:"+keyword4);
		  }
		  else
		  {
			 scriptStatus="FAIL";
			 logger.error("FAIL:Text is not correct:Expected:"+keyword4+" Actual:"+actualText);
		   }
		}
		else
		{ 
			scriptStatus="FAIL";
			logger.error("FAIL:verifyText();Element Not Found with the locator" );
		}
	}
	
	public void enter()
	{
		
		if(elementExist())
		{
			driver.findElement(getLocator()).sendKeys(keyword4);
			logger.info("Entering \""+keyword4+"\"");
		}
		else
		{
			scriptStatus="FAIL";
			logger.error("FAIL:sendKeys();Element Not Found ");
		}
	}
	
	public void click()
	{
		
		if(elementExist())
		{
			driver.findElement(getLocator()).click();
			
		}
		else
		{
			scriptStatus="FAIL";
			logger.error("FAIL:click(): Element Not Found ");
		}
	}
	
	  public void clear()
	  {
		  if(elementExist())
			{
				driver.findElement(getLocator()).clear();
			}
			else
			{
				scriptStatus="FAIL";
				logger.error("FAIL:clear();Element Not Found ");
			}
	  }
	
	public void waitForSeconds()
	{
		try
		{  
		    int seconds = Integer.parseInt(keyword2);
			Thread.sleep(1000 * seconds);
			 logger.info("Wait for "+ seconds + " seconds");
		}
		catch(InterruptedException ex)
		{
			logger.error("invalid input for: waitForSeconds ");
		}
	}
	
	
	public void checkAlertDisplayed()
	{
		try
		{
			driver.switchTo().alert();
			logger.info("Alert popup displayed: ");
		}
		catch(Exception ex)
		{
			scriptStatus="FAIL";
			logger.error("FAIL:checkAlertDisplayed();Alert popup not displayed: ");
		}
	}
	
	public void acceptAlertPopup()
	{
		try
		{
			driver.switchTo().alert().accept();
			logger.info("Alert popup accepted: ");
		}
		catch(Exception ex)
		{
			scriptStatus="FAIL";
			logger.error("FAIL:acceptAlertPopup();Accept Alert popup failed:");
		}
	}
	
	public void dismissAlertPopup()
	{
		try
		{
			driver.switchTo().alert().dismiss();
			logger.info("Alert popup dismissed: ");
		}
		catch(Exception ex)
		{
			scriptStatus="FAIL";
			logger.error("FAIL:dismissAlertPopup();dismiss Alert popup failed:");
		}
	}
 
	public void verifyElementPresent()
	{
		if(elementExist())
		{
			
			 logger.info("Element found ");
		}
		else
		{
			scriptStatus="FAIL";
			logger.error("Element not found");
		}
	}
	
	
}

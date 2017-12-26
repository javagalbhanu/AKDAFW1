package com.qspiders;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class KeyDriver 
{
	public Logger logger;
	public Generic g=new Generic();
	public WebDriver driver;
	public Library lib;
	public int passCount=0;
	public int failCount=0;
	public int skipCount=0;
	
	public String controller="./scripts/Controller.xlsx";
	public String scenarios="./scripts/Scenarios.xlsx";
	public String logPath;
	public KeyDriver()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		Date date = new Date();
		String startTime=dateFormat.format(date);
		logPath="./logs/"+startTime+".log";
		System.setProperty("logfile.name","./logs/"+startTime+".log");
		logger = Logger.getLogger(this.getClass().getName());
	}
	
	public void controllerExecutor()
	{	
		logger.info("**********************Starting Frame Work**********************");
		String url=g.getExcelCellValue(controller,"Configuration", 0, 1);
		String timeOut=g.getExcelCellValue(controller,"Configuration", 1, 1);
		driver=new ChromeDriver();
		System.out.println(Integer.parseInt(timeOut));
		driver.manage().timeouts().implicitlyWait(Integer.parseInt(timeOut),TimeUnit.SECONDS);
		driver.get(getURL(url));
		lib=new Library(driver);
		
	  int scenarioCount=g.getExcelRowCount(controller,"ScenarioList");
	  logger.info("Total Number of Scenarios:"+scenarioCount);
	  for(int i=1;i<=scenarioCount;i++)
	  {
		  String scenarioName=g.getExcelCellValue(controller,"ScenarioList", i, 0);
		  String executeScenario=g.getExcelCellValue(controller,"ScenarioList", i, 1);
		  if(executeScenario.equalsIgnoreCase("yes"))
		  {
			  Library.scriptStatus="PASS";
			  logger.info("======================================================");
			  logger.info("Starting scenario:"+scenarioName);
			  scenarioExecutor(scenarioName);
			  logger.info("Ending scenario:"+scenarioName);
			  if(Library.scriptStatus.equals("PASS"))
			  {
				  passCount++;
			  }
			  else
			  {
				  failCount++;
			  }
			  logger.info("!!!! Execution Status of above scenario:"+ Library.scriptStatus+" !!!!");
			  logger.info("======================================================");
		  }
		  else
		  {
			  skipCount++;
			  logger.info("_________________________________________________________");
			  logger.warn("Skipping scenario:"+scenarioName);
			 
		  }
	  }
	  driver.quit();
	  logger.info("Total Pass:"+passCount+" ; Total Fail:"+failCount+" ; Total Skip:"+skipCount);
	  logger.info("**********************Ending Frame Work**********************");
	  
	}
	
	public void scenarioExecutor(String scenarioName) 
	{
		int StepCount=g.getExcelRowCount(scenarios, scenarioName);
		logger.info("StepCount:"+StepCount);
			for(int k=1;k<=StepCount;k++)
			{
				String desc=g.getExcelCellValue(scenarios, scenarioName, k, 0);
				String keyword1=g.getExcelCellValue(scenarios, scenarioName, k, 1);
				String keyword2=g.getExcelCellValue(scenarios, scenarioName, k, 2);
				String keyword3=g.getExcelCellValue(scenarios, scenarioName, k, 3);
				String keyword4=g.getExcelCellValue(scenarios, scenarioName, k, 4);
				logger.info(desc);
				System.out.println(keyword1);
				System.out.println(keyword2);
				System.out.println(keyword3);
				System.out.println(keyword4);
				lib.keyword1=keyword1;
				lib.keyword2=keyword2;
				lib.keyword3=keyword3;
				lib.keyword4=keyword4;
				try {
				lib.getClass().getMethod(keyword1).invoke(lib);
				}
				catch(Exception e){
					logger.error("Invalid keyword:"+keyword1);
					Library.scriptStatus="FAIL";
					e.printStackTrace();
				}
			}
			
	}
	
	static {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver.exe");
	}
	
	public static void main(String[] args)
	{		
		KeyDriver d=new KeyDriver();
		d.controllerExecutor();
		System.out.println("-------------------------------------------");
		System.out.println("Refresh Project & See the Following Log File for details");
		System.out.println(d.logPath);
		System.out.println("-------------------------------------------");
	}

	public String getURL(String url) {
		/**This method is developed to return URL of app 
		 * if the url is 'sample.html' then it returns complete url of sample.html
		 * else it returns the URL specified in the Controller.xlsx
		 */
		
		if(url.equalsIgnoreCase("sample.html")) {
			String demoURL=new File("./Sample.html").getAbsolutePath();
			return demoURL;
		}
		else {
			return url;
		}
		
	}
}

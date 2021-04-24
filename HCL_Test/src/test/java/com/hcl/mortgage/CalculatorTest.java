package com.hcl.mortgage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest extends TestBase{
	
	@Before
	public void beforetest(){
		launchBrowser("Chrome");
		launchURL("app_url");
	}
	
	@Test
	public void MortageCalcTest(){
		
		click("modelWindow_xpath");
		click("calBtn_css","clickLocation_xpath");
		enterText("loanAmtTxtBx_xpath", "200000");
		
		dropdownValueSelect("termDrpDn_css", "30 years");
		enterText("intrateTxtBx_css", "5");
		radioBtnSelectByTitle("RepAmortizationRadioBtn_xpath", "Annually");
		
		click("calBtn_css","clickLocation_xpath");
		
		String MonthlyPayment = "$1,073.64";
		String TotalPayments = "$386,513";
		String TotalInterest = "$186,513";
		
		assertTrue(elementfind("MonthlyPayment_xpath").getText().contains(MonthlyPayment));
		assertTrue(elementfind("TotalPayments_xpath").getText().contains(TotalPayments));
		assertTrue(elementfind("TotalInterest_xpath").getText().contains(TotalInterest));
		
		
	}
	@After
	public void afterTest(){
		System.out.println("End");
		driver.quit();
	}

}

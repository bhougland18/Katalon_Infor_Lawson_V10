package z_selenium

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords

import internal.GlobalVariable

import MobileBuiltInKeywords as Mobile
import WSBuiltInKeywords as WS
import WebUiBuiltInKeywords as WebUI

//custom imports
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

//import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.driver.DriverFactory

public class functionFactory {
	
	List<String> getElementContentsAsList(String xpath4elements) {
		WebDriver webDriver = DriverFactory.getWebDriver()
		List<WebElement> elements = webDriver.findElements(By.xpath(xpath4elements))
		def contents = new ArrayList<String>()
		for (WebElement el : elements) {
			def content = el.getText()
			if (content != null) {
				contents.add(content)
			} else {
				contents.add('')
			}
		}
		return contents
	}

	@Keyword
	def getAttributeValuesAsList(String xpath4elements, String attributeName) {
		def webDriver = DriverFactory.getWebDriver()
		def elements = webDriver.findElements(By.xpath(xpath4elements))
		def values = []
		for (el in elements) {
			def value = el.getAttribute(attributeName)
			if (value != null) {
				values.add(value)
				println(value)
			} else {
				values.add('')
			}
		}
		return values
	}
	
	@Keyword
	def getWebElementsAsList(String xpath4elements) {
		def webDriver = DriverFactory.getWebDriver()
		def elements = webDriver.findElements(By.xpath(xpath4elements))
		
		return elements
	}
	
	
}

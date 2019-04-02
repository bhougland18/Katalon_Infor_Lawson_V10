package scm

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
// custom imports
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.logging.KeywordLogger

public class rqc {
	//TODO: Create a descriptive function for the requidition center sub menu it nav
	//@Keyword
	//	def navigateRQC()



	@Keyword
	def goToRQCMenuLink(String menu_label, String takeScreenShot = 'YES') {
		WebUI.delay(2)
		WebUI.waitForPageLoad(25)
		WebUI.waitForElementVisible(findTestObject('RQC/shopping/0_v_main_links_bar',[('menu_label') : menu_label]), 25)
		WebUI.click(findTestObject('RQC/shopping/0_v_main_links_bar',[('menu_label') : menu_label]))
		//add in waiting for page to load
		//using function from the utilities package
		(new utilities.core()).takeScreenShot(takeScreenShot)
	}


	@Keyword
	def shoppingSubmitSearch(String search_text, String takeScreenShot = 'YES') {
		WebUI.waitForPageLoad(25)
		WebUI.delay(2)
		WebUI.waitForElementVisible(findTestObject('RQC/shopping/4_search_catalog/input_search'), 30)
		WebUI.sendKeys(findTestObject('RQC/shopping/4_search_catalog/input_search'), search_text)
		WebUI.click(findTestObject('RQC/shopping/4_search_catalog/button_inforSearch'))

		(new utilities.core()).takeScreenShot(takeScreenShot)

	}

	@Keyword
	def shoppingAddLine(int line = 1) {
		WebUI.waitForPageLoad(10)
		WebUI.delay(3)
		WebUI.click(findTestObject('RQC/shopping/4_search_catalog/button_line_add',
				[('line') : line]))
	}


	def getShoppingReqID() {
		WebUI.delay(2)
		WebUI.waitForElementVisible(findTestObject('RQC/shopping/0_text_RecordID'), 30)
		//the element returns the following (ex): 'Requisition: 805516       '
		def elem_text = (WebUI.getText(findTestObject('RQC/shopping/0_text_RecordID')))
		//split the text by ":", take the second element, and trim the text
		def reqId = (elem_text.split(":")[1].trim())

		return reqId
	}



}

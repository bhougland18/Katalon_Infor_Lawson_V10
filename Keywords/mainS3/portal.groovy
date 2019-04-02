package mainS3

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
import com.kms.katalon.core.testcase.TestCaseBinding
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
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
import com.kms.katalon.core.logging.KeywordLogger

public class portal {



	//@Keyword
	//def login_orig(String url, String username, String password) {
	//	WebUI.openBrowser(url)
	//	WebUI.waitForPageLoad(15)
	//	WebUI.delay(2)
	//	WebUI.waitForElementVisible(findTestObject('mainS3/1_Login/input__ssoUser'), 35)
	//	WebUI.sendKeys(findTestObject('mainS3/1_Login/input__ssoUser'), username)
	//	WebUI.sendKeys(findTestObject('mainS3/1_Login/input__ssoPass'), password)
	//	WebUI.click(findTestObject('mainS3/1_Login/button_Login'))
	//}

	@Keyword
	def login(String url, String username, String password, int tries = 1) {
		WebUI.openBrowser(url)
		WebUI.waitForPageLoad(15)
		//should be 'INFOR'
		def page_title_orig = WebUI.getWindowTitle(FailureHandling.STOP_ON_FAILURE)
		println("***  Page title: ${page_title_orig}")
		WebUI.delay(2)
		WebUI.waitForElementVisible(findTestObject('mainS3/1_Login/input__ssoUser'), 5)
		multiTrySendKeys('mainS3/1_Login/input__ssoUser', username, tries)
		multiTrySendKeys('mainS3/1_Login/input__ssoPass', password, tries)
		WebUI.click(findTestObject('mainS3/1_Login/button_Login'))
		WebUI.delay(2)
		WebUI.waitForPageLoad(15)
		//this section is checking to see if after the login submission the page is still the login screen
		//if so, it checks the login status text and throws an error
		//'Infor Sign In Dialog'
		def page_title = getNonLoadingWindowTitle()
		if (page_title == 'Infor Sign In Dialog') {
			def login_status = WebUI.getText(findTestObject('mainS3/1_Login/text_login_status'))
			throw new Exception(login_status)
		} else {
			KeywordLogger logger = new KeywordLogger()
			logger.logInfo("Successful login to: ${page_title}")
		}
	}

	@Keyword
	def login_w_errors(String url, String username, String password, int tries = 1) {
		WebUI.openBrowser(url)
		WebUI.delay(2)
		WebUI.waitForPageLoad(15)
		//should be 'INFOR'
		def page_title_orig = WebUI.getWindowTitle(FailureHandling.STOP_ON_FAILURE)
		println("***  Page title: ${page_title_orig}")
		WebUI.delay(2)
		WebUI.waitForElementVisible(findTestObject('mainS3/1_Login/input__ssoUser'), 5)
		multiTrySendKeys('mainS3/1_Login/input__ssoUser', username, tries)
		multiTrySendKeys('mainS3/1_Login/input__ssoPass', password, tries)
		WebUI.click(findTestObject('mainS3/1_Login/button_Login'))
		WebUI.delay(10)
		WebUI.verifyAlertPresent(10)
		WebUI.acceptAlert()
		WebUI.verifyAlertPresent(10)
		WebUI.acceptAlert()
		//this section is checking to see if after the login submission the page is still the login screen
		//if so, it checks the login status text and throws an error
		//'Infor Sign In Dialog'
		//def page_title = getNonLoadingWindowTitle()
		//if (page_title == 'Infor Sign In Dialog') {
		//	def login_status = WebUI.getText(findTestObject('mainS3/1_Login/text_login_status'))
		//	throw new Exception(login_status)
		//} else {
		//	KeywordLogger logger = new KeywordLogger()
		//	logger.logInfo("Successful login to: ${page_title}")

	}

	@Keyword
	def goToForm(String token) {
		WebUI.waitForElementVisible(findTestObject('mainS3/2_portal/input_findText'), 30)
		WebUI.sendKeys(findTestObject('mainS3/2_portal/input_findText'), token)
		WebUI.click(findTestObject('mainS3/2_portal/button_LAWMENUBTNSEARCH'))
		WebUI.waitForPageLoad(10)
	}

	@Keyword
	def navigateBookmarks(List menu_path,
			boolean switchFocusNewWindow = true,
			String portal_suffix = "/lawson/portal/index.htm#",
			String takeScreenShot = 'YES') {

		def path_depth = menu_path.size() - 1
		def z = 0
		while (z < path_depth) {
			WebUI.waitForElementVisible(findTestObject('mainS3/2_portal/Bookmarks_menu_link',[('menu_label') : menu_path[z]]), 3)
			WebUI.mouseOver(findTestObject('mainS3/2_portal/Bookmarks_menu_link',[('menu_label') : menu_path[z]]))
			z++
		}
		//The last element in the list will be clicked instead of using the mouseOver action
		WebUI.waitForElementVisible(findTestObject('mainS3/2_portal/Bookmarks_menu_link',[('menu_label') : menu_path[z]]), 3)
		WebUI.click(findTestObject('mainS3/2_portal/Bookmarks_menu_link',[('menu_label') : menu_path[z]]))
		//have webbot focus on the new window opened by the click action
		if (switchFocusNewWindow == true) {
			def String base_url = WebUI.getUrl() - portal_suffix

			def action_element = WebUI.getAttribute(findTestObject('mainS3/2_portal/Bookmarks_menu_link',
					[('menu_label') : menu_path[z]]),
					'action')
			//the url is the second agrument in the Lawson openBookmarkWindow function used by the action class
			//the split method returns the url suffix, which we then add to the base lawson url to get the full url
			def String suffix_url = (action_element.split("'")[3])
			def String bookmark_url = base_url + suffix_url

			WebUI.switchToWindowUrl(bookmark_url)
			WebUI.waitForPageLoad(15)
			WebUI.delay(2)
			WebUI.maximizeWindow()
			//using function from the utilities package
			(new utilities.core()).takeScreenShot(takeScreenShot)
			return bookmark_url
		}

	}

	//created because it was difficult to time the actual title and not the loading title
	def getNonLoadingWindowTitle(int tries = 5) {
		def String title = WebUI.getWindowTitle(FailureHandling.STOP_ON_FAILURE).substring(0,7)
		def z = 0
		while (title == 'Loading' && z < tries) {
			WebUI.delay(5)
			WebUI.waitForPageLoad(10)
			title = WebUI.getWindowTitle(FailureHandling.STOP_ON_FAILURE).substring(0,7)
			//this is for debugging
			println("***  Page title: ${title}")
			z++
		}
		//once the title is no longer "Loading", we return the full page title
		return WebUI.getWindowTitle(FailureHandling.STOP_ON_FAILURE)
	}

	//created because the send keys were often getting truncated.
	//in that instance set the tries argument to a integer greater than 1
	//TODO: find a better way to do this.  I hate this function
	def multiTrySendKeys(String testObjectRelativeId, String field_value, int tries = 1) {
		WebUI.sendKeys(findTestObject(testObjectRelativeId), field_value)
		int z = 1
		while(z < tries) {
			WebUI.clearText(findTestObject(testObjectRelativeId))
			WebUI.sendKeys(findTestObject(testObjectRelativeId), field_value)
			z++
		}
	}

}


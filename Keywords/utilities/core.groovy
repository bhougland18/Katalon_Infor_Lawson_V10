package utilities

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

public class core {

	@Keyword
	static def takeScreenShot(String pref = 'YES', String path = GlobalVariable.G_screen_shot_path) {
		if (pref.toUpperCase() == 'YES' && path == null){

			WebUI.takeScreenshot()
		} else if(pref.toUpperCase() == 'YES'){
			//TODO: Takescreenshot - create smart file names

			WebUI.takeScreenshot()
		}
	}
//this is messed up because the findtestobject doesn't work when it can't find it
	@Keyword
	def clickButton(String button_text) {
		WebUI.switchToDefaultContent()
		def vis_status = WebUI.verifyElementVisible(findTestObject('core/v_button',[('text') : button_text]), FailureHandling.OPTIONAL)
		if (vis_status == true) {
			WebUI.click(findTestObject('core/v_button',[('text') : button_text] ))
		} else {
		    def List iframes_att_lst = (new z_selenium.functionFactory()).getAttributeValuesAsList("//iframe", "name")
			def counter = 0
			while (vis_status == false && counter <= iframes_att_lst.size()) {
				WebUI.switchToDefaultContent()
				WebUI.switchToFrame(findTestObject('core/v_iframe', [('value') : iframes_att_lst[counter]]), 5)
				vis_status = WebUI.verifyElementVisible(findTestObject('core/v_button',[('text') : button_text] ), FailureHandling.OPTIONAL)
				counter++
			}
		WebUI.click(findTestObject('core/v_button',[('text') : button_text] ))
		}	
	}
}

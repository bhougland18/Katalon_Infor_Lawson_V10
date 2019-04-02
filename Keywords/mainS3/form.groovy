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
import com.kms.katalon.core.testcase.TestCaseFactory
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testdata.TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords
//custom import for report comments
import com.kms.katalon.core.logging.KeywordLogger

import internal.GlobalVariable

import MobileBuiltInKeywords as Mobile
import WSBuiltInKeywords as WS
import WebUiBuiltInKeywords as WebUI
// custom import for exceptions
import com.kms.katalon.core.exception.StepErrorException as StepErrorException
public class form {




	@Keyword
	def populateHeaderFields(Map fieldValuesMap, String takeScreenShot = 'YES') {
		println("***  Starting populateHeaderField process.")
		//need to add take picture functionality
		def sortedFieldMap = fieldSorter(fieldValuesMap)
		sortedFieldMap.each {tabRef, fieldMap ->
			if(tabRef == '0') {
				fieldMap.each {fieldId, fieldValue ->
					WebUI.sendKeys(findTestObject('mainS3/3_Forms/v_main_field_selector',
							[('attributeId') : fieldId,
								('attributeNm') : 'id',
								('occurance') : '1']), fieldValue)
				}

				//using function from the utilities package
				(new utilities.core()).takeScreenShot(takeScreenShot)

			} else {
				//Click tab to make fields visible before sending gimeValues
				WebUI.click(findTestObject('mainS3/3_Forms/v_tab_button_select',
						[('tabNum') : tabRef]))
				fieldMap.each {fieldId, fieldValue ->
					WebUI.sendKeys(findTestObject('mainS3/3_Forms/v_main_field_selector',
							[('attributeId') : fieldId,
								('attributeNm') : 'id',
								('occurance') : '1']), fieldValue)
				}

				(new utilities.core()).takeScreenShot(takeScreenShot)
			}
		}
	}

	@Keyword
	def populateLineFields(String lineNumber, Map fieldValues, String takeScreenShot = 'YES') {
		println("***  Starting populateLineFields process.")
		//need to add take picture functionality ++ sort function on
		def sortedFieldMap = fieldSorter(fieldValues = fieldValues, lineNumber = lineNumber)
		sortedFieldMap.each {tabRef, fieldMap ->
			if (tabRef == '0') {
				fieldMap.each {fieldId, fieldValue ->

					WebUI.sendKeys(findTestObject('mainS3/3_Forms/v_main_field_selector',
							[('attributeId') : fieldId,
								('attributeNm') : 'id',
								('occurance') : '1']), fieldValue)
				}

				(new utilities.core()).takeScreenShot(takeScreenShot)

			} else {
				//Click tab to make fields visible before sending values
				WebUI.click(findTestObject('mainS3/3_Forms/v_tab_button_select',
						[('tabNum') : tabRef]))
				fieldMap.each {fieldId, fieldValue ->
					WebUI.sendKeys(findTestObject('mainS3/3_Forms/v_main_field_selector',
							[('attributeId') : fieldId,
								('attributeNm') : 'id',
								('occurance') : '1']), fieldValue)
				}

				(new utilities.core()).takeScreenShot(takeScreenShot)
			}
		}
	}

	@Keyword
	def formActionWithStatus(String fc_action, String takeScreenShot = 'YES') {
		//todo: add arguments for picture, document field
		//todo: this function needs more thought - do I want to return a map?, will that be too hard for end user?
		println("***  Starting formActionWithStatus process.")
		def form_values = [:].withDefault{[:]}

		WebUI.click(findTestObject('mainS3/3_Forms/v_toolbar_action',[('fc_action') : fc_action]))
		WebUI.waitForPageLoad(3)
		//get document number, ex. requisition number
		def String doc = "HI"
		//get form status message
		def String status_msg = WebUI.getText(findTestObject('mainS3/3_Forms/v_status_message'))
		form_values.put("status",status_msg)
		form_values.put("doc",doc)
		//below console text is mainly for testing, may remove
		WebUI.comment("values are: ${doc}, ${status_msg}")
		(new utilities.core()).takeScreenShot(takeScreenShot)

		return form_values
	}

	@Keyword
	def validateStatusMessage(String expected_value, String takeScreenShot = 'YES') {
		println("***  Starting validateStatusMessage process.")
		def String status_msg = getStatusMsgText()
		if (status_msg != expected_value) {
			throw new Exception("Status Message equals: ${status_msg}, and does not equal expected value of: ${expected_value}")
		} else {
			KeywordLogger logger = new KeywordLogger()
			logger.logInfo("Status Message equals expected value of: ${expected_value}")
		}
		(new utilities.core()).takeScreenShot(takeScreenShot)
		return status_msg == expected_value
	}


	def getStatusMsgText() {
		def String status = WebUI.getText(findTestObject('mainS3/3_Forms/v_status_message'))
		def z = 0
		while (status == 'Ready' && z < 5) {
			WebUI.waitForPageLoad(3)
			status = WebUI.getText(findTestObject('mainS3/3_Forms/v_status_message'))
			z++
		}
		//take only the text before the '(', which starts the userid
		def String status_msg = status.substring(0,status.indexOf('('))
		return status_msg
	}

	def fieldLocator(String attributeId, String attributeNm='nm', String lineNumber='1') {
		//this is a helper function that returns the fieldID, including the dropdown extension (if necessary) and line number in the
		//first position of a list, the second position is the tab number if it exist.

		//find the attribute class value
		attributeId = attributeId.trim()
		def attributeType = WebUI.getAttribute(findTestObject('mainS3/3_Forms/v_main_field_selector',
				[('attributeId') : attributeId,
					('attributeNm') : 'nm',
					('occurance') : '1']), 'class')
		//find the field id from name
		def fieldIdValue = WebUI.getAttribute(findTestObject('mainS3/3_Forms/v_main_field_selector',
				[('attributeId') : attributeId,
					('attributeNm') : 'nm', //attributeNm,
					('occurance') : lineNumber]), 'id')
		//find tab number the field lives on
		def tabName = WebUI.getAttribute(findTestObject('mainS3/3_Forms/v_main_field_selector',
				[('attributeId') : attributeId,
					('attributeNm') : 'nm', //attributeNm,
					('occurance') : lineNumber]), 'name')

		//transform the field Id if it is a drop down list --append shdo
		def textIdValue
		if (attributeType == 'inforDropDownList') {
			//modify field id to equal the drop down element that accepts text input
			textIdValue = "${fieldIdValue}-shdo"
		} else {
			textIdValue = fieldIdValue
		}

		//transform header fields to tab 0, else keep value the same
		def tabValue
		if (['form', 'DT0'].contains(tabName)) {
			tabValue = "0"
		} else {
			tabValue = tabName
		}

		def fieldLocation = [tabValue, textIdValue]
		//return list of values
		return fieldLocation
	}

	//the purpose of the sorter is sort fields by tab number so the script populates them in order
	//this helps when adding screenshots to scripts using this function
	def fieldSorter(Map fieldValues, String lineNumber = '1') {

		def sortedFieldMap = [:].withDefault{[:]}

		fieldValues.each {fieldName, fieldValue ->
			//fieldLocator returns a list [tabValue, fieldId]
			def fieldLocation = fieldLocator(fieldName, lineNumber = lineNumber)
			def tabValue = fieldLocation[0]
			def fieldId = fieldLocation[1]

			sortedFieldMap[tabValue].put(fieldId, fieldValue)



		}
		sortedFieldMap = sortedFieldMap.sort()
		return sortedFieldMap

	}
}

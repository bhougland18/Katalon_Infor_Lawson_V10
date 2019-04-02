import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.checkpoint.CheckpointFactory as CheckpointFactory
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as MobileBuiltInKeywords
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testcase.TestCaseFactory as TestCaseFactory
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testdata.TestDataFactory as TestDataFactory
import com.kms.katalon.core.testobject.ObjectRepository as ObjectRepository
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WSBuiltInKeywords
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUiBuiltInKeywords
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW

CustomKeywords.'mainS3.portal.login_w_errors'('http://btlawlsf.us.chs.net/lawson/portal/index.htm', 'bhouglan', 'Ze18gna19*', 
    2)

'Navigate to S3 form ${token}'
CustomKeywords.'mainS3.portal.goToForm'(token)

'Populate line fields with values'
CustomKeywords.'mainS3.form.populateLineFields'('1', [('ZIL-SRC-UOM-CONV') : '5', ('ZIL-SRC-UOM') : 'BX', ('ZIL-STOCK-UOM') : 'EA'
        , ('ZIL-VEN-PRICE-AMT') : '50', ('ZIL-BUY-UOM-CONV') : '10', ('ZIL-BUY-UOM') : 'CA', ('ZIL-COMPANY') : '1489', ('ZIL-VEN-ITEM') : '12149LA'
        , ('ZIL-VENDOR') : '1001230', ('ZIL-MANUF-NBR') : '12149LA', ('ZIL-MANUF-DIVISION') : 'DENT', ('ZIL-MANUF-CODE') : '3M'
        , ('ZIL-DESCRIPTION') : 'Katalon-test', ('LINE-FC') : 'A - Add'], 'YES')

'Add new record (Add Action)\r\n'
CustomKeywords.'mainS3.form.formActionWithStatus'('A')

'Ensures the status is equal to input (expected result)\r\n'
CustomKeywords.'mainS3.form.validateStatusMessage'('Add Complete - Continue')

not_run: WebUI.takeScreenshot('C:\\Users\\Benjamin\\Pictures\\katalon\\IC11_TEST1.png')


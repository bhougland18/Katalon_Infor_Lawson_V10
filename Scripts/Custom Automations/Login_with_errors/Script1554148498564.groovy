import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import internal.GlobalVariable as GlobalVariable

not_run: CustomKeywords.'mainS3.portal.login'('http://btlawlsf.us.chs.net/lawson/portal/index.htm', 'bhouglan', 'Ze18gna19*', 
    2)

WebUI.openBrowser('http://btlawlsf.us.chs.net/lawson/portal/index.htm')

WebUI.delay(20)

not_run: WebUI.acceptAlert()

WebUI.authenticate('http://btlawlsf.us.chs.net/lawson/portal/index.htm', 'bhouglan', 'Ze18gna19*', 100)

WebUI.delay(20)

WebUI.waitForAlert(30)

WebUI.acceptAlert()

not_run: WebUI.verifyAlertPresent(15)

WebUI.acceptAlert()

not_run: WebUI.verifyAlertNotPresent(10)

not_run: WebUI.acceptAlert()


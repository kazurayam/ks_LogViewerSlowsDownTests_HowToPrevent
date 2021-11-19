package my

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

public class Verifier {
	
	@Keyword
	static void printID(String id) {
		WebUI.comment("ID=${id}")
	}
	
	@Keyword
	static void verifySomething(String id) {
		
		WebUI.comment("start verifySomething with ID=${id}")
		
		WebUI.openBrowser('')
		
		WebUI.navigateToUrl('https://katalon-demo-cura.herokuapp.com/')
		
		WebUI.click(findTestObject('Object Repository/Page_CURA Healthcare Service/a_Make Appointment'))
		
		WebUI.verifyElementPresent(findTestObject('Object Repository/Page_CURA Healthcare Service/input_Username_username'), 5)
		
		WebUI.setText(findTestObject('Object Repository/Page_CURA Healthcare Service/input_Username_username'), 'John Doe')
		
		WebUI.setEncryptedText(findTestObject('Object Repository/Page_CURA Healthcare Service/input_Password_password'), 'g3/DOGG74jC3Flrr3yH+3D/yKbOqqUNM')
		
		WebUI.click(findTestObject('Object Repository/Page_CURA Healthcare Service/button_Login'))
		
		WebUI.verifyElementPresent(findTestObject('Object Repository/Page_CURA Healthcare Service/button_Book Appointment'), 5)
		
		WebUI.closeBrowser()
		
	}
}

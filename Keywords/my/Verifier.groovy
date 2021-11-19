package my

import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.testdata.TestData as TestData

public class Verifier {

	@Keyword
	static void printID(TestData data, int i) {
		// find value of the 'ID' column
		String id = data.getValue('ID', i)
		if (id != null & id.length() > 0) {
			// do whatever you want
			WebUI.comment("ID=${id}")
		}
	}
}

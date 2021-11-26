package my

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

import com.kms.katalon.core.annotation.Keyword

public class Util {
	
	@Keyword
	static void printID10times(String id) {
		for (int i in 0..10) {
			WebUI.comment("ID=${id}")
		}
	}
}

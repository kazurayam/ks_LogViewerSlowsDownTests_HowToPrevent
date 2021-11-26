package com.kazurayam.ks.logging

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import internal.GlobalVariable
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.transform.TransformerFactory
import javax.xml.transform.Transformer
import javax.xml.transform.Source
import javax.xml.transform.Templates
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.dom.DOMSource
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.DocumentBuilder
import org.xml.sax.InputSource
import org.w3c.dom.Document

public class XMLLogAnalyzer {

	Transformer transformer

	public enum Format {
		MARKDOWN
	}

	XMLLogAnalyzer() {}

	void setStylesheet(Reader reader) {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document dom = db.parse(new InputSource(reader));
		TransformerFactory tf = TransformerFactory.newInstance()
		Templates templates = tf.newTemplates(new DOMSource(dom))
		this.setStylesheet(templates)
	}



	void setStylesheet(Templates templates) {
		Objects.requireNonNull(templates)
		transformer = templates.newTransformer()
	}

	void transform(Path input, Path output, Format format) {
		Objects.requireNonNull(input)
		Objects.requireNonNull(output)
		assert Files.exists(input)
		assert Files.exists(output.getParent())
		StreamSource source = new StreamSource(input.toFile())
		StreamResult result = new StreamResult(output.toFile())
		assert transformer != null : "tranformer is null; need to peform setTemplate(Template)"
		transformer.transform(source, result)
	}
}

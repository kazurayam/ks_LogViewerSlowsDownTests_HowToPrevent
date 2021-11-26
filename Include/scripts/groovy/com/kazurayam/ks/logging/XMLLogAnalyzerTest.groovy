package com.kazurayam.ks.logging

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*

import org.junit.BeforeClass
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.kms.katalon.core.logging.KeywordLogger
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import java.util.concurrent.ConcurrentHashMap
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import com.kms.katalon.core.configuration.RunConfiguration
import java.nio.file.SimpleFileVisitor
import java.nio.file.FileVisitResult
import java.nio.file.attribute.BasicFileAttributes
import javax.xml.transform.Templates
import javax.xml.transform.stream.StreamSource
import javax.xml.transform.TransformerFactory

@RunWith(JUnit4.class)
public class XMLLogAnalyzerTest {
	
	static Path projectDir
	static Path reportsDir
	static Path classOutputDir
	static TransformerFactory tf
	 
	@BeforeClass
	static void setupClass() {
		projectDir = Paths.get(RunConfiguration.getProjectDir())
		reportsDir = projectDir.resolve("Reports")
		classOutputDir = projectDir.resolve("build/tmp/testOutput").resolve(XMLLogAnalyzerTest.class.getName())
		Files.createDirectories(classOutputDir)
		tf = TransformerFactory.newInstance()
	}

	@Test
	void test_smoke() {
		List<Path> inputList = findXML(reportsDir, "20211125_171442")
		assert inputList.size() > 0
		Path output = classOutputDir.resolve("test_smoke").resolve("out.md")
		Files.createDirectories(output.getParent())
		XMLLogAnalyzer analyzer = new XMLLogAnalyzer()
		analyzer.setStylesheet(new StringReader(identityTemplates()))
		analyzer.transform(inputList.get(0), output, XMLLogAnalyzer.Format.MARKDOWN)
		assert Files.exists(output)
		assert output.size() > 0
	}
	
	List<Path> findXML(Path reportsDir, String timestamp) {
		List<Path> result = new ArrayList<Path>()
		Files.walkFileTree(reportsDir, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			  if (file.getFileName().toString() == 'execution0.log') {
				  Path parent = file.getParent()
				  if (parent.getFileName().toString() == timestamp) {
					  result.add(file)
				  }
			  }
			  return FileVisitResult.CONTINUE
			}
		  });
	  return result
	}

	
	String identityTemplates() {
		return '''<xsl:stylesheet version="1.0"
 xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
 <xsl:output omit-xml-declaration="yes" indent="yes"/>
 <xsl:strip-space elements="*"/>

 <xsl:template match="node()|@*">
     <xsl:copy>
       <xsl:apply-templates select="node()|@*"/>
     </xsl:copy>
 </xsl:template>

 <xsl:template match="product|*[starts-with(name(), 'item')]">
  <action>
   <xsl:apply-templates select="node()|@*"/>
  </action>
 </xsl:template>

 <xsl:template match="product/@id"/>
</xsl:stylesheet>
'''
	}
	
}

import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.testdata.TestData

// read the Data from the CSV file
TestData data = findTestData("IDs")

// iterate over the rows in the table body
for (int i: 1..data.getRowNumbers()) {
	CustomKeywords.'my.Verifier.printID'(data, i)
}
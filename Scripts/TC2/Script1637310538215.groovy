import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import com.kms.katalon.core.testdata.TestData

// read the Data from the CSV file
TestData data = findTestData("IDs")

// iterate over the rows in the table body
for (int i: 1..data.getRowNumbers()) {
	// find value of the 'ID' column
	String id = data.getValue('ID', i)
	if (id != null & id.length() > 0) {
		// do whatever you want
		CustomKeywords.'my.Verifier.printID'(id)
	}
}
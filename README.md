# Analysis of overhead for processing a large data set in Katalon Studio

## Introduction

## Fixtures

### Test Case `printID`
I made a Test Case [`Test Case/printID`](Scripts/printID/Script1637309731921.groovy), which is minimal:
```
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.comment("ID=${ID}")
```
The Test Case `printID` declares a variable `ID` as:
![printID_variable](docs/images/printID_variable.png)

### `data.csv` file

I made a CSV file. It contains 1000 lines. My test reads this, iterate all rows, find the value of `ID` variable which is passed to test case.
[data.csv](./data.csv)
```
ID
1
2
3
...
1000
```

### Test Suite `TS1`

I made a Test Suite `TS1`, which applies "A. Execution from test suites" as described in the article ["Data-driven testing approach with Katalon Studio"](https://medium.com/katalon-studio/data-driven-testing-approach-with-katalon-studio-b835c9e491dd). `TS1` calls the Test Case `printID` for all rows in the `data.csv` file. So the `printID` will be executed 1000 times, with ID values 1, 2, 3, ..., 1000.

![TS1](docs/images/TS1.png)

### Test Case `TC1`

I made a Test Case [`TC1`](Scripts/TC1/Script1637310193849.groovy), which applies "B. Execute by test case" as alos described in the article ["Data-driven testing approach with Katalon Studio"](https://medium.com/katalon-studio/data-driven-testing-approach-with-katalon-studio-b835c9e491dd). `TC1` calls the Test Case `printID` for all rows  in the `data.csv` file. So the `printID` will be executed 1000 times, with ID values 1, 2, 3, ..., 1000. `TC1` uses `WebU.callTestCase()` keyword to call the `printID`.

Partial quote from the source:
```
// read the Data from the CSV file
TestData data = findTestData("IDs")

// iterate over the rows in the table body
for (int i: 1..data.getRowNumbers()) {
	// find value of the 'ID' column
	String id = data.getValue('ID', i)
	if (id != null & id.length() > 0) {
		// do whatever you want
		WebUI.callTestCase(findTestCase("printID"), ["ID": id])
	}
}
```

### Test Case `TC2` and custom keyword

I made a Test Case [`TC2`](Scripts/TC2/Script1637310538215.groovy), which applies "B. Execute by test case" as alos described in the article ["Data-driven testing approach with Katalon Studio"](https://medium.com/katalon-studio/data-driven-testing-approach-with-katalon-studio-b835c9e491dd). `TC2` calls a custom keyword `my.Verifier.printID` for all rows in the `data.csv` file. So the `printID` will be executed 1000 times, with ID values 1, 2, 3, ..., 1000. 

Partial quote from the source:
```
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
```

## Statistics

### Measurement A

#### TS1

- START logged in the Console: `2021-11-19 20:33:21.753`
- END logged in the Console: `2021-11-19 20:37:50.881`
- Test duration: 4 min 29 seconds
- Log Viewer finished displaying all messages after : 6 min 30 seconds

#### TC1

- START logged in the Console: `2021-11-19 20:46:20.079`
- END logged in the Console: `2021-11-19 21:00:11.473`
- Test duration: 13 min 51 seconds
- Log Viewer finished displaying all messages after: 18 min 15 seconds

#### TC2

- START logged in the Console: `2021-11-19 21:10:53`
- END logged in the Console: `2021-11-19 21:12:19`
- Test duration: 2 min 26 seconds
- Log Viewer finished displaying all messages after: 3 min 8 seconds

#### Mesurement B

Log Viewer has 2 format. I would call them: Tree view and Table view. In the table, you can set the Log Level to be displayed: "All", "Info", "Passed", "Failed", "Error", "Warning". 

When you choose "All", you would see 2 other types of logs: "START", "END". These logs are called "step execution log". Katalon Studio traces all lines in a Test Case and logs its START and END. Therefore, you will find quite a log of START and END logs when you choose "All" for emission criteria.

##### TS1

- tree-view : 6 min 30 sec (as Measurement A)
- table-view "All": 4 min 23 sec
- table-view "INFO" only: 1 min 48 sec

##### TC1

- tree-view : 18 min 14 sec
- table-view "All": 4 min 3,  sec
- table-view "INFO" only: 1 min 16 sec

##### TC2

- tree-view : 3 min 8 sec
- table-view "All": 4 min 19 sec
- table-view "INFO" only: 0 min 41 sec

#### Measurement C

Log Viewer has `Scroll lock` button. It is off by default. You can turn it "Locked". It has quite significant performance implication.

##### TS1

with "Scroll Locked"

- tree-view :  min  sec
- table-view "All":  min  sec
- table-view "INFO" only:  min  sec

##### TC1

with "Scroll Locked"

- tree-view :  min  sec
- table-view "All":  min  sec
- table-view "INFO" only:  min  sec

##### TC2

with "Scroll Locked"

- tree-view :  min  sec
- table-view "All":  min  sec
- table-view "INFO" only:  min  sec

## Conclusion



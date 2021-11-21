# Log Viewer slows down Tests - How to prevent it?


## Problem to solve

I have ever made a long running test in Katalon Studio. My test took screenshots of 6000 pages and did image comparison. See [this](https://forum.katalon.com/t/executionprofilesloader/51515). My test ran 17 hours. I thought it too long, too slow.

Also, in the Katalon User Forum, you can find a few posts about long-running test that ran overnights or over-days! ; for example [this](https://forum.katalon.com/t/ks-8-1-0-stucked-in-running-after-more-than-5-days-of-running-long-haul-test-need-help-to-generate-partial-report/59799/64).

The 1st question I had was this: **Why tests in Katalon Studio, in some cases, run so slow? Is there any way how to make it faster?**




## In short, you want to ..

Katalon GUI has a GUI component called "Log Viewer".
![LogViewer](docs/images/LogViewer.png)










## Fixtures

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

### Test Case `printID`
I made a Test Case [`Test Case/printID`](Scripts/printID/Script1637309731921.groovy), which is minimal:
```
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

WebUI.comment("ID=${ID}")
```
The Test Case `printID` declares a variable `ID` as:
![printID_variable](docs/images/printID_variable.png)

### Test Suite `TS1`

I made a Test Suite `TS1`, which applies "A. Execution from test suites" as described in the article ["Data-driven testing approach with Katalon Studio"](https://medium.com/katalon-studio/data-driven-testing-approach-with-katalon-studio-b835c9e491dd). `TS1` calls the Test Case `printID` for all rows in the `data.csv` file. So the `printID` will be executed 1000 times, with ID values 1, 2, 3, ..., 1000.

![TS1](docs/images/TS1.png)

| Log Viewer mode  | level | scroll  | duration | |
| :---- | :---- | ------: | :------------ | :----------------------------------- |
| Tree  |       | enabled | 5 min 37 secs | `##################################` |
| Tree  |       | lockeed | 3 min 25 secs | `#####################` |
| Table | All   | enabled | 3 min  4 secs | `##################` |
| Table | All   | locked  | 1 min 36 secs | `#########` |
| Table | F+E+W | enabled | 56 secs | `######` |
| Table | F+E+W | locked  | 1 min 10 secs | `#######` |
| Detached | - | - | 25 secs | `###` |

# Log Viewer slows down tests in Katalon Studio - how to prevent it?


## Problem to solve

Katalon Studio's "Log Viewer" slows down test execution siginifantly. It is a fact. Are you aware of it? May be not. So I will explain it here. And I will also explain how to make it better.

## Measurement result

Let me go straight to the point. The following table shows the result I measured how a Test Suite took to finish. I used just the same test code, with several variation of the "Log Viewer" setup.

| case | Widget is | Mode  | log level | scroll  | duration | scale |
| ---: | :-------- | :---- | :---- | ------: | :------------ | :----------------------------------- |
| 1    | Attached | Tree  | -     | enabled | 5 min 37 secs | `##################################` |
| 2    | Attached | Tree  | -     | locked | 3 min 25 secs | `#####################` |
| 3    | Attached | Table | All   | enabled | 3 min  4 secs | `##################` |
| 4    | Attached | Table | All   | locked  | 1 min 36 secs | `#########` |
| 5    | Attached | Table | F+E+W | enabled | 56 secs | `######` |
| 6    | Attached | Table | F+E+W | locked  | 1 min 10 secs | `#######` |
| 7    | Detached | Tree  | -     | enabled | 5 min 35 secs | `##################################` |
| 8    | Detached | Tree  | -     | locked  | 4 min 15 secs | `##########################` |
| 9    | Closed   | Tree  | -     | -       | 25 secs | `###` |

In one case, my test suite took 5 minutes 37 seconds to finish. But In another case, the same code finished just in 25 seconds. This proves that the "Log Viewer" slows down your tests!

In the following sections, I will explain how I classified "Log Viewer" setups.

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

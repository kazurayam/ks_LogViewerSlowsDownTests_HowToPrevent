# Log Viewer slows down tests in Katalon Studio - how to prevent it?


## Problem to solve

Katalon Studio's "Log Viewer" slows down test execution siginifantly. It is a fact. Are you aware of it? May be not. So I will explain it here. And I will also explain how to make it better.

## Measurement result

Let me go straight to the point. The following table shows the result I measured how long a Test Suite took to finish running. I used just the same test code, with several variation of the "Log Viewer" setups.

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

In one case, my test suite took 5 minutes 37 seconds to finish. But In another case, the same code finished just in 25 seconds. Big difference! This proves that the "Log Viewer" may slow down your tests!

In the following sections, I will explain how I classified "Log Viewer" setups, and how I measured the duration. In other words, you can find ways to make your tests run faster by setting up "Log Viewer" nicely.

## Code to run

### Test Suite `TS1`

I made a Test Suite `TS1`, which applies "A. Execution from test suites" as described in the article ["Data-driven testing approach with Katalon Studio"](https://medium.com/katalon-studio/data-driven-testing-approach-with-katalon-studio-b835c9e491dd). 

`TS1` calls the Test Case `printID` for all rows in the `data.csv` file, which contains 1000 lines. So it will repeat calling the `printID` 1000 times.

![TS1](docs/images/TS1.png)

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
#0000
#0001
#0002
#0003
#0004
...
#0998
#0999
```

## How to measure the duration

When I say "the TS1 took 5 minutes 37 seconds", how do I measure the start and end of duration?

As soon as I start the Test Suite `TS1` by clicking the run button ![run button](docs/images/run_katalon_test.png), "Job Progress" modal window will open.

![Job Progress](docs/images/JobProgress.png.png)

In the "Job Progress" window, I find a moving figure, like `37/1000`. This means, TS1 is going to repeat calling `PrintID` for 1000 times, and it has finished 37 times.

I used Timer app of my Android mobile device to measure the duration. I started the stop watch as soon as I clicked the run button, and wait a while. When the "Job Progress" showd `1000/1000`, I stopped the stop watch. This is the way how I measured the duration of TS1.
# Advent of Code 2022 Solutions

[![Build Status](https://github.com/akaritakai/AdventOfCode2022/actions/workflows/main.yml/badge.svg)](https://github.com/akaritakai/AdventOfCode2022/actions)
[![Code Coverage](https://img.shields.io/codecov/c/github/akaritakai/AdventOfCode2022.svg)](https://codecov.io/gh/akaritakai/AdventOfCode2022)
![Stars](https://img.shields.io/badge/stars%20⭐-16-yellow)
![Days Completed](https://img.shields.io/badge/days%20completed-8-green)

This repo contains my Advent of Code 2022 solutions in Java 17. After providing it with your puzzle inputs (or your
session token), running the program will print out the answers to all days of the puzzle. A Docker image is provided to 
ensure compatibility with machines that do not want to install the Java 17 runtime.

The goal of this repo is to provide fast, highly tested, and easy-to-use solutions.

This repo may see changes in the future to improve runtime. If you have any suggestions, issues running the code, or
find a correctness error: please open an issue or pull request.

### Example output:
```
Day 01 Part 1: 75622
Day 01 Part 2: 213159
Day 02 Part 1: 15572
Day 02 Part 2: 16098
Day 03 Part 1: 7875
Day 03 Part 2: 2479
Day 04 Part 1: 540
Day 04 Part 2: 872
Day 05 Part 1: FJSRQCFTN
Day 05 Part 2: CJVLJQPHS
Day 06 Part 1: 1855
Day 06 Part 2: 3256
Day 07 Part 1: 1307902
Day 07 Part 2: 7068748
Day 08 Part 1: 1779
Day 08 Part 2: 172224
```

## Providing Your Puzzle Input

There are two supported methods for inputting your puzzle data into this application.

### Automatic Puzzle Fetcher (via Session Cookie)

First, get your cookie session data.

You will need to log into the Advent of Code website and then inspect your cookies.
If you are using Chrome, you can follow the directions [here](https://developers.google.com/web/tools/chrome-devtools/storage/cookies).

You will be looking for a cookie called `session`. It will contain a long sequence of hexadecimal digits.

Place that data into a file called `cookie.txt` in the project directory.

The application will use that data to automatically fetch your puzzle input for each day.

### Manual Input

This code will also look in a particular location on your local machine for puzzle input.

In the project directory, it will check a directory called `puzzle`.
Within that directory it will expect Day 1's input to be in a file called `1`, Day 2's input to be in a file called `2`, etc.

You can find your puzzle input for a given day by logging into the Advent of Code website and then navigating to the URL
for that puzzle's input.

The URL for your puzzle input will be at:
```
https://adventofcode.com/2022/day/${DAY}/input
```
where `${DAY}` is the day number of the puzzle.

As an example, Day 1's input is at https://adventofcode.com/2022/day/1/input,
Day 2's input is at https://adventofcode.com/2022/day/2/input, etc.

## Docker Instructions (Mac/Linux)

1. Follow the instructions above for providing your puzzle input.
2. Run `docker run --rm -it $(docker build -q .)`

## Windows Instructions

1. Follow the instructions above for providing your puzzle input.
2. Install JDK 17. You can follow the installation instructions [here](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-microsoft-windows-platforms.html).
3. Open a command prompt and navigate to the project directory.
4. Run `.\gradlew.bat run`

## Mac Instructions

1. Follow the instructions above for providing your puzzle input.
2. Install JDK 17. You can follow the installation instructions [here](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-macos.html).
3. Open Terminal and navigate to the project directory.
4. Run `./gradlew run`


## Linux Instructions

1. Follow the instructions above for providing your puzzle input.
2. Install JDK 17. You can follow the generic installation instructions [here](https://docs.oracle.com/en/java/javase/17/install/installation-jdk-linux-platforms.html).
   Alternatively, you may want to search on Google for installation instructions specific to your distro.
3. Open a shell and navigate to the project directory.
4. Run `./gradlew run`

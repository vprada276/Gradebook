# Gradebook
Java Gradebook Manager

Console-based Java program to manage student grades.
The program allows users to add students, compute grade statistics, search by PID, modify grades, and import/export data using CSV files.

# Features

Add students with first name, last name, PID, and score

Prevent duplicate PIDs

Modify student grades

Calculate statistics:

Minimum score

Maximum score

Average score

Median score

Search students by PID

Display tables of scores or letter grades

Import and export student records using CSV files

# Commands

After entering students and typing DONE, the following commands are available:

min score
min letter
max score
max letter
average score
median score
average letter
median letter

letter XXXXXXX
name XXXXXXX

change XXXXXXX YY

tab scores
tab letters

save filename.csv
load filename.csv

help
quit
Input Format

Students must be entered in the following format:

FirstName LastName PID Score

Example:

Victor Prada 1234567 92
Maria Lopez 2345678 85
John Smith 3456789 77

# Rules:

First name must start with a capital letter

Last name must start with a capital letter

PID must be a 7-digit number

Score must be between 0 and 100

# Project Structure
Main.java       → Handles user input and commands
Gradebook.java  → Stores students and computes statistics
Student.java    → Student data structure
Grade.java      → Converts scores to letter grades
Technologies Used

Java

Object-Oriented Programming

ArrayList

File I/O (CSV)

Command-line interface

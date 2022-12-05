# Tau Compilation course 2022a private repository
##### Author: Boaz Yakubov
#### Course Instructor: David Trabish
based on his repo:  https://github.com/davidtr1037/compilation-tau/

to test and run this program, im using Windows subsystem for linux:
- simply type "wsl" in powershell in the directory of the exercise and continue from there, rm command and make will work.

### Exercise 1:
A lexical scanner based on JFlex.
**input**: A text file containing a program as described in the exercise pdf.
**output**: A text file containing a tokenized representation of the input.

### Exercise 2:
A CUP based parser on top of the JFlex scanner from Ex 1.
**input**: A text file containing a program as described in the exercise pdf.
**output**:A text file indicating whether the input is Syntactically valid or not, and if so, the "Abstract Syntax Tree" for the program is created *internally*. it will be needed for ex 3.

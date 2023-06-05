# Tau Compilation course 2022a repository
##### Author: Boaz Yakubov
#### Course Instructor: David Trabish
based on his repo:  https://github.com/davidtr1037/compilation-tau/

to test and run this program, im using Windows subsystem for linux:
- simply type "wsl" in powershell in the directory of the exercise and continue from there, rm and make commands will work.

### Exercise 1:
A lexical scanner based on JFlex.

**input**: A text file containing a program as described in the exercise pdf.
**output**: A text file containing a tokenized representation of the input.

### Exercise 2:
A CUP based parser on top of the JFlex scanner from Ex 1.

**input**: A text file containing a program as described in the exercise pdf.
**output**:A text file indicating whether the input is Syntactically valid or not, and if so, the "Abstract Syntax Tree" for the program is created *internally*. it will be needed for ex 3.

### Exercise 3:
Implements a semantic analyzer that recursively scans the
AST produced by CUP, and checks if it contains any semantic errors.

**input**: The input for the semantic analyzer is a (single) text file containing a L program 
**output**: The output is a (single) text file indicating whether the input program is semantically valid or not. In addition to that, whenever the input program is valid semantically, the semantic analyzer will add meta data to the abstract syntax tree, which is needed for later phases (code generation and optimization).

### Exercise 4:
The fourth (and last) exercise implements the code generation phase for L programs. The chosen destination language is MIPS assembly, favoured for it straightforward syntax, complete toolchain and available tutorials. The exercise can be roughly divided into three parts as follows: 
(1) recursively traverse the AST to create an intermediate representation (IR) of the program. 
(2) Translate IR to MIPS instructions, but use an unbounded number of temporaries instead of registers.
(3) Perform liveness analysis, build the interference graph, and allocate those hundreds (or so) temporaries into 10 physical registers.

**input**: The input for this last exercise is a (single) text file, containing a L program,
**output**: Output is a (single) text file that contains the translation of the input program into MIPS assembly

# A2_prog_assignment

# Details

- **Due Date**: 11:59pm, Friday 13th October
- **Components**: Programming Task & Report
- **Length**: 
  - **Program**: Usually no more than 1000 Lines of Code
  - **Report**: No more than 10 pages including diagrams.
- **Submission**: Electronic through EdStem and Canvas (as per Assignment 1). Please note that you will need to download your codes and submit on Canvas too.
- **Weighting**: 30%
- **Group Size**: 2-3

## Preamble

Parsers typically represent the second fundamental level of computational power. Although we present this in terms of compiling a programming language, parsing is actually a very general process and relates to the structural comprehension of any type of data. What we gain from parsing is what is called syntactical information - whether the data matches an expected structure. Having correct syntactical structure is the next step toward developing meaning: lexical correctness can be thought of as "using real words", syntactic correctness is "putting the words in the right order". Without these two steps, it is much more difficult to extract meaning from anything.

## Your Task

This assignment consists in two components, a program and a report. 

### The Program (Weighting: 70%)

For the programming component, you will implement a parser (`SyntaxAnalyser.java`) that checks the syntactic correctness of a simplified subset of the Java programming language and builds a parse tree representing the program (if it is correct, and throws an exception if it's not). We will use just enough Java to build simple programs (but not quite enough to do tricky things).

The parser will be implemented in Java, based on the supplied skeleton. You may implement any type of parser you like as long as it correctly passes the tests, and does not use any 3rd party tools (including but not limited to compiler generators such as Bison or Yacc - when in doubt, write it yourself, or ask the Subject Coordinator).

You are provided with the grammar of "Simple Java", along with a list of language symbols and reserved words that constitute most of the terminals of the language.

> **Note**: Alongside matching the tests, you should use common sense when interpreting these symbols - you know what a variable name looks like, match that. Any attempts to "rules lawyer" your way out of doing what is a straightforward task will annoy the Subject Coordinator and may result in the assignment being made harder.

### The Report (Weighting: 30%)

The report is to be a simple report recording how your program utilizes the material from the subject.

**Mandatory for your report**:
- The group members of your group.
- Briefly describe each group member's contribution to the project. Some examples are:
  - Each group member contributes equally to the project.
  - We worked closely together and each group member contributed to this project.
  - Alice contributes mainly to the theoretical model of this project, while Bob and Charlie contributes to the coding part. We three work closely to ensure the delivery of the project.

The following questions help to organize the rest of your report:
- What type of parser you decided to use and why.
- How you constructed the components of the parser.
- Which theoretical computation model each (significant) component of your code implements or uses.
- If your code efficiently implements the algorithms you selected, and whether any improvement can be made.
- Any improvements, limitations, constraints &c. that illuminate your understanding of the subject material.

### Some tips
As in assignment 1, one approach to complete this assignment is to build the theory model first, and then implement the theory in your code. Consider using the LL(1) parser method as taught in class.

- The outcome of the theory model would be a parsing table.
- Use the parsing table as a PDA to parse a list of tokens.
- The output of the PDA would be a parse tree.
- The PDA uses a stack, and in Java, a stack is implemented via the `ArrayDeque` data structure.
- The `ArrayDeque` may store a symbol together with a tree node to help you track where the symbol is in the tree.
- Implement the parsing table in your program by storing it in a data structure such as `Map`.

## Marking

### The Program

The marking for the program will be based on automated unit tests, provided with the skeleton.

> **Note**: The copy of the tests provided with the skeleton is not the one that's actually run, so there's no danger in changing anything. Tests will use JUnit, and you are encouraged to write your own tests. Reading through the tests will give you insight into your code's expected input and output (read the tests in `SyntacticalAnalysisTests.java`, they're there to help). If you use 3rd party parser generator tools, you will receive 0 for the program regardless of functionality.

### The Report

The report will be marked over three components: demonstration and understanding of subject material, accurate reportage of the content of the submitted code, clarity, and precision of writing. The marking criteria are cumulative; e.g., to achieve a credit, you must first meet the criteria for a pass.

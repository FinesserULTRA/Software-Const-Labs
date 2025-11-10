# Software Construction Lab 9: Advanced Recursion

This lab explores helper methods, mutual recursion, and recursive data structures.

## Task Structure

### Task 1: Integer-to-String Conversion
- **IntegerToString.java** - Basic implementation (bases 2-16)
- **IntegerToStringEnhanced.java** - Enhanced with bases 2-36 and performance comparison

### Task 2: Recursive File Size Calculator
- **FileSize.java** - Basic directory size calculation
- **FileSizeEnhanced.java** - Mutual recursion with file type exclusion

### Task 3: Mutual Recursion - Even/Odd Checker
- **EvenOddChecker.java** - Basic mutual recursion
- **EvenOddCheckerEnhanced.java** - Handles negative numbers
- **EvenOddCheckerTest.java** - JUnit test cases (requires JUnit dependency)

### Task 4: Reentrant Recursive Sum
- **ReentrantSum.java** - Basic reentrant implementation
- **ReentrantSumEnhanced.java** - Comparison with non-reentrant and synchronized versions

## Running the Code

Each task can be compiled and run individually:

```bash
# Task 1
javac Task1/IntegerToString.java
java Task1/IntegerToString

javac Task1/IntegerToStringEnhanced.java
java Task1/IntegerToStringEnhanced

# Task 2
javac Task2/FileSize.java
java Task2/FileSize /path/to/directory

javac Task2/FileSizeEnhanced.java
java Task2/FileSizeEnhanced /path/to/directory .tmp .log

# Task 3
javac Task3/EvenOddChecker.java
java Task3/EvenOddChecker

javac Task3/EvenOddCheckerEnhanced.java
java Task3/EvenOddCheckerEnhanced

# Task 4
javac Task4/ReentrantSum.java
java Task4/ReentrantSum

javac Task4/ReentrantSumEnhanced.java
java Task4/ReentrantSumEnhanced
```

## Key Concepts Demonstrated

1. **Recursive Decomposition** - Breaking problems into smaller subproblems
2. **Helper Methods** - Managing intermediate results and state
3. **Mutual Recursion** - Functions calling each other recursively
4. **Recursive Data Structures** - Traversing directory trees
5. **Reentrancy** - Thread-safe recursive functions
6. **Performance Analysis** - Comparing recursive vs iterative approaches

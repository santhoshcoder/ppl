*************************************************
Instructions to execute the program from scratch
*************************************************
1) jjtree prog2.jjt
2) javacc prog2.jj
3) Add the following statements in ASTVariable.java and ASTNumber.java
/* Add the below statements line:20 to line:28 */
  private String name;
  public void setName(String n)
  {
    name = n;
  }
  public String toString()
  {
    return name;
  }
4) Add the following import statement in prog3.java

import java.util.*

5) Compile the program

  javac *.java

6) Run the program 

  java prog3

7) Enter the input
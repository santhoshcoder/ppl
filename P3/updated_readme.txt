*************************************************
Instructions to execute the program from the provided files
*************************************************
1) java prog3 
2) Enter the input 

Note: java prog3 < input.txt [ or anyother input file doesn't work]





*************************************************
Instructions to execute the program from scratch
*************************************************

1) jjtree prog3.jjt

2) javacc prog3.jj

3) Add the following statements in ASTVariable.java and ASTNumber.java

	private String name;
  	public void setName(String n)
  	{
    	name = n;
  	}
  	public String toString()
  	{
   	 	return name;
  	}

4) Add the following import statement in prog3.java and SimpleNode.java

	import java.util.* 

5) Add the following statements in SimpleNode.java



6) Now compile the program 

  javac *.java

7) Run the program

  java prog3

8) Type the input [ < doesn't work ]
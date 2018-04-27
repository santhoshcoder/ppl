********************************************
Instructions to run the program
********************************************
1) Download the tar file
2) Open Terminal and goto the folder when it is downloaded
3) run the following command
   tar -xf Prog2.tat
4) to see the output run the below command
   java prog2 < input.txt
5) input.txt can be changed with other testcases [test.txt which is also in the current directory] or different file


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

4) Add the following import statement in SimplNode.java
   
  import java.util.*

5) Add the following statements in SimpleNode.java
/* Add the below statements line:37 to line:165 */

/* Added Functions for Questions 3,4  -- Start */
  public void dumpFV(String prefix)
  {
    System.out.print(toString(prefix));
    //print the free variables after this
    //System.out.println("\t"+freeVars());
    Set<String> values3 = new HashSet<String>(freeVars());
    Object[] myArr = values3.toArray();
    System.out.print("\t{");
    for(int i=0;i<myArr.length;i++)
    {
      System.out.print(myArr[i]);
      if(i+1 !=myArr.length)
        System.out.print(", ");
    }
    System.out.println("}");
    if (children != null) 
    {
      for (int i = 0; i < children.length; ++i) 
      {
        SimpleNode n = (SimpleNode)children[i];
        if (n != null) {
          n.dumpFV(prefix + " ");
        }
      }
    }
  }
  Set<String> freeVars()
  {
      if(toString().equals("lamb"))
      {
        Set<String> values3 = new HashSet<String>();
        values3.addAll(((SimpleNode)jjtGetChild(1)).freeVars()); //adding right child's free variable
        values3.removeAll(((SimpleNode)jjtGetChild(0)).freeVars());
        return values3;
      }
      else if(toString().equals("appl"))
      {
        Set<String> values3 = new HashSet<String>();;
        values3.addAll(((SimpleNode)jjtGetChild(0)).freeVars());
        values3.addAll(((SimpleNode)jjtGetChild(1)).freeVars());
        return values3;
      }
      else if(toString().equals("add") || toString().equals("sub") || toString().equals("mul") || toString().equals("div") || toString().equals("mod"))
      {
        Set<String> values3 = new HashSet<String>();;
        return values3;
      }
      else if(isNumeric(toString()))
      {
        //Check if it is a number. If YES return empty set
        Set<String> values3 = new HashSet<String>();
        return values3;
      }
      //you found a variable so add it to the HashSet and return the HashSet
      Set<String> values3 = new HashSet<String>();
      values3.add(toString());
      return values3;
  }
  public boolean isNumeric(String str)  
  {  
    try  
    {  
      int d = Integer.parseInt(str);  
    }   
    catch(NumberFormatException nfe)  
    {  
      return false;  
    }  
    return true;  
  }

  /* Added Functions for Questions 3,4  -- End */

  /* Added Functions for Questions 2  -- Start */
  
  public void printExpr()
  {
    if(toString().equals("appl"))
    {
      System.out.print("(");
      //this node may have 1 or more children
      if(jjtGetNumChildren() == 1)
      {
        ((SimpleNode)jjtGetChild(0)).printExpr();
      }
      else if(jjtGetNumChildren() == 2)
      {
        ((SimpleNode)jjtGetChild(0)).printExpr();
        ((SimpleNode)jjtGetChild(1)).printExpr();
      }
      System.out.print(")");
    }
    else if(toString().equals("lamb"))
    {
      System.out.print("(");
      System.out.print("L ");
      ((SimpleNode)jjtGetChild(0)).printExpr();
      System.out.print(". ");
      ((SimpleNode)jjtGetChild(1)).printExpr();
      System.out.print(")");
    }
    else if(toString().equals("add"))
    {
      System.out.print("+ ");
    }
    else if(toString().equals("sub"))
    {
      System.out.print("- ");
    }
    else if(toString().equals("mul"))
    {
      System.out.print("* ");
    }
    else if(toString().equals("div"))
    {
      System.out.print("/ ");
    }
    else if(toString().equals("mod"))
    {
      System.out.print("% ");
    }
    else
    {
      System.out.print(" " + toString() + " ");
    }
  }

  /* Added Functions for Questions 2  -- End */


6) Compile the program

  javac *.java

7) Run the program
  
  java prog2 < test.txt

//where test.txt is my input file
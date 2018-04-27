import java.util.*;
public
class SimpleNode implements Node {

  protected Node parent;
  protected Node[] children;
  protected int id;
  protected Object value;
  protected prog2 parser;

  public SimpleNode(int i) {
    id = i;
  }

  public SimpleNode(prog2 p, int i) {
    this(i);
    parser = p;
  }

  public void jjtOpen() {
  }

  public void jjtClose() {
  }

  public void jjtSetParent(Node n) { parent = n; }
  public Node jjtGetParent() { return parent; }

  public void jjtAddChild(Node n, int i) {
    if (children == null) {
      children = new Node[i + 1];
    } else if (i >= children.length) {
      Node c[] = new Node[i + 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = n;
  }

  public Node jjtGetChild(int i) {
    return children[i];
  }

  public int jjtGetNumChildren() {
    return (children == null) ? 0 : children.length;
  }

  public void jjtSetValue(Object value) { this.value = value; }
  public Object jjtGetValue() { return value; }

  /* You can override these two methods in subclasses of SimpleNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

  public String toString() { return prog2TreeConstants.jjtNodeName[id]; }
  public String toString(String prefix) { return prefix + toString(); }

  /* Override this method if you want to customize how the node dumps
     out its children. */

  public void dump(String prefix) {
    System.out.println(toString(prefix));
    if (children != null) {
      for (int i = 0; i < children.length; ++i) {
        SimpleNode n = (SimpleNode)children[i];
        if (n != null) {
          n.dump(prefix + " ");
        }
      }
    }
  }

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
}

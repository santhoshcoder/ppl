import java.util.*;
public class SimpleNode implements Node {

  protected Node parent;
  protected Node[] children;
  protected int id;
  protected Object value;
  protected prog3 parser;

  public SimpleNode(int i) {
    id = i;
  }

  public SimpleNode(prog3 p, int i) {
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

  public String toString() { return prog3TreeConstants.jjtNodeName[id]; }
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
  /*
    Additional Functions -- Start
  */
    public SimpleNode copy(SimpleNode expr){
      SimpleNode n = findClass(expr);
      if(expr.jjtGetNumChildren() >= 1){
        SimpleNode first = copy((SimpleNode)expr.children[0]);
        if(first != null)
          n.jjtAddChild(first,n.jjtGetNumChildren());
      }
      if(expr.jjtGetNumChildren() == 2){
        SimpleNode second = copy((SimpleNode)expr.children[1]);
        if(second != null)
          n.jjtAddChild(second,n.jjtGetNumChildren());
      }
      return n;
    }
    public SimpleNode substitute(String varName, SimpleNode expr){
      System.out.println("In substitution function");
      if(getClass().isInstance(new ASTVariable(8))){
        System.out.println("Variable Found");
        if(toString().equals(varName))
          return copy(expr);
        return this;
      }
      else if(getClass().isInstance(new ASTNumber(7)))
      {
        System.out.println("Number Found");
        return this;
      }
      else if(toString().equals("appl")){
        System.out.println("Application Found");
        SimpleNode left = ((SimpleNode)jjtGetChild(0)).substitute(varName,expr);
        jjtAddChild(left,0);
        SimpleNode right = ((SimpleNode)jjtGetChild(1)).substitute(varName,expr);
        jjtAddChild(right,1);
        return this;
      }
      else if(toString().equals("lamb")){
        System.out.println("Lamb found");
        String variable = jjtGetChild(0).toString();
        if(variable.equals(varName)){
          return this;
        }
        else if(isNotIn(variable,(expr.freeVars())))
        {
          SimpleNode right = ((SimpleNode)jjtGetChild(1)).substitute(varName,expr);
          jjtAddChild(right,1);
          return this;
        }
        else{
            int i = 1;
            do{
              variable = jjtGetChild(0).toString();
              variable += i;
              i++;
            }while(!isNotIn(variable,(expr.freeVars())));
            
            String oldVariableName = jjtGetChild(0).toString();

            //You have a unique name so replace left of the lambda with correct variable name
            ASTVariable newvariableNode = new ASTVariable(8);
            newvariableNode.setName(variable);
            jjtAddChild(newvariableNode,0);

            //substitute the unique name in the right of the lambda expression
            SimpleNode right = ((SimpleNode)jjtGetChild(1)).substitute(oldVariableName,newvariableNode);
            jjtAddChild(right,1);

            return substitute(varName,expr);
        }
      }
      return this;
    }

    /* The below function returns a SimpleNode after finding and substituting a B-redex 
       If B-redex not found then it returns null
    */
    public SimpleNode bRedex(){

      if(toString().equals("lamb")){
        System.out.println("Lambda Found");
        System.out.println("Calling Right: "+((SimpleNode)jjtGetChild(1)).toString());
        SimpleNode right = ((SimpleNode)jjtGetChild(1)).bRedex();
        if(right != null){
          jjtAddChild(right,1);
        }
      }

      else if(toString().equals("appl")){
        System.out.println("Application Found");
        System.out.println("Calling Left: "+((SimpleNode)jjtGetChild(0)).toString());
        SimpleNode leftNode = ((SimpleNode)jjtGetChild(0)).bRedex();
        System.out.println("Calling Right: "+((SimpleNode)jjtGetChild(1)).toString());
        SimpleNode rightNode = (((SimpleNode)jjtGetChild(1)).bRedex());
        
        while(leftNode != null){
          jjtAddChild(leftNode,0);
          leftNode = leftNode.bRedex();
        }

        while(rightNode != null){
          jjtAddChild(rightNode,1);
          rightNode = rightNode.bRedex();
        }

        if(((SimpleNode)jjtGetChild(0)).toString().equals("lamb")){
          System.out.println("appl left is lamb");
          SimpleNode left = ((SimpleNode)jjtGetChild(0)); // it's lamb node
          String variable = ((SimpleNode)left.jjtGetChild(0)).toString(); // lamb bound variable
          System.out.println("Calling Substitution function on Variable: "+variable + " expr root is:"+((SimpleNode)jjtGetChild(1)).toString());
          SimpleNode afterReduction = ((SimpleNode)left.jjtGetChild(1)).substitute(variable,(SimpleNode)jjtGetChild(1));
          System.out.println("After reduction the Node is:");
          afterReduction.dump("");
          System.out.println("Returning from Substitution function");
            return afterReduction;
        }
      }
      return null;
    }

    public SimpleNode applicativeOrderEvaluate(){
      SimpleNode reducedNode = bRedex();
      //System.out.println("After reducion the tree is:");
      //reducedNode.dump("");
      return reducedNode;
    }
    
    public boolean isNotIn(String variable,Set<String>free){
      String[] values = free.toArray(new String[free.size()]);
      for (int i=0;i<values.length;i++) {
        if(variable.equals(values[i])){
          return false;
        }
      }
      return true;
    }

    public SimpleNode findClass(SimpleNode expr){
      SimpleNode n = null;
      if(expr instanceof ASTsub)
        n = new ASTsub(expr.id);
      else if(expr instanceof ASTmul)
        n = new ASTmul(expr.id);
      else if(expr instanceof ASTmod)
        n = new ASTmod(expr.id);
      else if(expr instanceof ASTlamb)
        n = new ASTlamb(expr.id);
      else if(expr instanceof ASTdiv)
        n = new ASTdiv(expr.id);
      else if(expr instanceof ASTappl)
        n = new ASTappl(expr.id);
      else if(expr instanceof ASTadd)
        n = new ASTadd(expr.id);
      else if(expr instanceof ASTVariable){
        ASTVariable n1 = new ASTVariable(expr.id);
        n1.setName(expr.toString());
        n = n1;
      }
      else if(expr instanceof ASTNumber){
        ASTNumber n2 = new ASTNumber(expr.id);
        n2.setName(expr.toString());
        n = n2;
      }
      return n;
    }
    Set<String> freeVars()
    {
      Set<String> values3 = new HashSet<String>();
      if(toString().equals("lamb"))
      {
        values3.addAll(((SimpleNode)children[1]).freeVars());
        values3.removeAll(((SimpleNode)children[0]).freeVars());
        return values3;
      }
      else if(toString().equals("appl"))
      {
        values3.addAll(((SimpleNode)children[0]).freeVars());
        values3.addAll(((SimpleNode)children[1]).freeVars());
        return values3;
      }
      else if(toString().equals("add") || toString().equals("sub") || 
        toString().equals("mul") || toString().equals("div") || 
        toString().equals("mod") || isNumeric(toString()))
      {
        return values3;
      }
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
  /*
    Additional Functions -- End
  */
}
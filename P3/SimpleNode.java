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
      if(getClass().isInstance(new ASTVariable(8))){
        if(toString().equals(varName))
          return copy(expr);
        return this;
      }
      else if(getClass().isInstance(new ASTNumber(7)))
      {
        return this;
      }
      else if(toString().equals("appl")){
        SimpleNode left = ((SimpleNode)jjtGetChild(0)).substitute(varName,expr);
        jjtAddChild(left,0);
        SimpleNode right = ((SimpleNode)jjtGetChild(1)).substitute(varName,expr);
        jjtAddChild(right,1);
        return this;
      }
      else if(toString().equals("lamb")){
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
        SimpleNode right = ((SimpleNode)jjtGetChild(1)).bRedex();
        if(right != null){
          jjtAddChild(right,1);
        }
      }

      else if(toString().equals("appl")){
        SimpleNode leftNode = ((SimpleNode)jjtGetChild(0)).bRedex();
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
          SimpleNode left = ((SimpleNode)jjtGetChild(0)); // it's lamb node
          String variable = ((SimpleNode)left.jjtGetChild(0)).toString(); // lamb bound variable
          SimpleNode afterReduction = ((SimpleNode)left.jjtGetChild(1)).substitute(variable,(SimpleNode)jjtGetChild(1));
          SimpleNode anotherReduction = afterReduction.bRedex();
          if(anotherReduction == null){
            return afterReduction;
          }
          return anotherReduction;
        }
      }
      return null;
    }

    public SimpleNode applicativeOrderEvaluate(){
      SimpleNode reducedNode = bRedex();
      if(reducedNode == null){
        SimpleNode evaluatedNode = this.eval();
        if(evaluatedNode == null){
          return this;
        }
        else
        {
          return evaluatedNode;
        }
      }
      else
      {
        SimpleNode evaluatedNode = reducedNode.eval();
        if(evaluatedNode == null){
         return reducedNode;
        }
        else{
         return evaluatedNode;
        }
      }
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

  public SimpleNode eval(){
    if(toString().equals("lamb")){
      //return null;
      SimpleNode right = ((SimpleNode)jjtGetChild(1)).eval();
      if(right != null){
        jjtAddChild(right,1);
      }
    }
    else if(toString().equals("appl")){
      SimpleNode right = ((SimpleNode)jjtGetChild(1)).eval();
      if(right != null){
        jjtAddChild(right,1);
      }
      
      SimpleNode left = ((SimpleNode)jjtGetChild(0)).eval();
      if(left != null){
        jjtAddChild(left,0);
      }

      SimpleNode leftChild = ((SimpleNode)(jjtGetChild(0)));
      if(!leftChild.toString().equals("appl")){
        return null;
      }
      SimpleNode firstOperand = ((SimpleNode)(leftChild.jjtGetChild(1)));
      SimpleNode operator = ((SimpleNode)(leftChild.jjtGetChild(0)));
      SimpleNode secondOperand = ((SimpleNode)(jjtGetChild(1)));

      if(isNumeric(firstOperand.toString()) && isNumeric(secondOperand.toString()) && isOperator(operator.toString())){
        int first = Integer.parseInt(firstOperand.toString());
        int second = Integer.parseInt(secondOperand.toString());
        ASTNumber valueNode = new ASTNumber(7);
        int answer = 0;
        if(operator.toString().equals("add")){
            answer = first + second;
        }
        else if(operator.toString().equals("sub")){
            answer = first - second;
        }
        else if(operator.toString().equals("mul")){
            answer = first * second;
        }
        else if(operator.toString().equals("div")){
            answer = first / second;
        }
        else{
            answer = first % second;
        }
        valueNode.setName(answer+"");
        return valueNode;
      }
    }
    return null;
  }

  public boolean isOperator(String str){
      return (str.equals("add") || str.equals("sub") || str.equals("mul") || str.equals("div") || str.equals("mod"));
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
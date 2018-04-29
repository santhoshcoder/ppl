public
class SimpleNode implements Node {

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
    System.out.println(toString(prefix)+"\t"+getClass().getName());
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
      if(children.length == 1){
        
      }
    }
    public SimpleNode substitute(String varName, SimpleNode expr){
        //1. Make a copy of expr
        SimpleNode newCopy = copy(expr);
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
        n = new ASTVariable(expr.id);
        n.setName(expr.toString());
      }
      else if(expr instanceof ASTNumber){
        n = new ASTNumber(expr.id);
        n.setName(expr.toString());
      }
      return n;
    }
  /*
    Additional Functions -- End
  */
}
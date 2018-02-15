/* This is a simple TreeNode that implements the Node interface. */

public class TreeNode implements Node 
{
  public static final int NUM = 0;
  public static final int ADD = 1;
  public static final int MUL = 2;

  protected Node[] children;
  protected int id;

  public TreeNode(int i) 
  {
    id = i;
  }

  public void addChild(Node n, int i) 
  {
    if (children == null) 
    {
      //System.out.println("children is Null");
      children = new Node[i + 1];
    } 
    else if (i >= children.length) 
    {
      //System.out.println("children not null");
      Node c[] = new Node[i + 1];
      System.arraycopy(children, 0, c, 0, children.length);
      children = c;
    }
    children[i] = n;
  }

  public Node getChild(int i) 
  {
    return children[i];
  }

  public int getNumChildren() 
  {
    return (children == null) ? 0 : children.length;
  }

  /* You can override these two methods in subclasses of TreeNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

  public String toString() 
  { 
    return " " + id; 
  }

  public String toString(String prefix) 
  { 
    return prefix + toString(); 
  }

  /* Override this method if you want to customize how the node dumps
     out its children. */

  public void dump(String prefix) 
  {
    System.out.println(toString(prefix));
    if (children != null) 
    {
      for (int i = 0; i < children.length; ++i) 
      {
	       TreeNode n = (TreeNode)children[i];
	       if (n != null) 
         {
	         n.dump(prefix + " ");
	       }
      }
    }
  }
}
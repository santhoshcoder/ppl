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
      children = new Node[i + 1];
    } 
    else if (i >= children.length) 
    {
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

  public String value()
  {
    return "-20";
  }

  public int eval(TreeNode temp)
  {
    if(temp.getNumChildren() == 0)
    {
      return Integer.parseInt(temp.value());
    }
    else if(temp.value().equals("-20"))
    {
      return eval((TreeNode)temp.getChild(0));
    }
    else if(temp.value().equals("+"))
    {
      return eval((TreeNode)temp.getChild(0)) + eval((TreeNode)temp.getChild(1));
    }
    else if(temp.value().equals("*"))
    {
      return eval((TreeNode)temp.getChild(0)) * eval((TreeNode)temp.getChild(1));
    }
    return -1;
  }
  /* You can override these two methods in subclasses of TreeNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. */

  public String toString() 
  { 
    return "";
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
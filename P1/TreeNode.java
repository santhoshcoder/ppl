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
 
  public void clone(TreeNode t,TreeNode org)
  {
    if(org.value().equals("-20"))
    {
      TreeNode c1 = (TreeNode)org.getChild(0);
      String c_value = c1.value();
      TreeNode n = check(c_value);
      t.addChild(n,t.getNumChildren());
      clone((TreeNode)t.getChild(0),(TreeNode)org.getChild(0));
    }
    else
    {
      if(org.getNumChildren() != 0)
      {
        TreeNode child0 = (TreeNode) org.getChild(0);
        String c1 = child0.value();
        TreeNode n1 = check(c1);
        t.addChild(n1,t.getNumChildren()); 

        TreeNode child1 = (TreeNode) org.getChild(1);
        String c2 = child1.value();
        TreeNode n2 = check(c2);
        t.addChild(n2,t.getNumChildren());

        clone((TreeNode)t.getChild(0),(TreeNode)org.getChild(0));
        clone((TreeNode)t.getChild(1),(TreeNode)org.getChild(1));
      }
    }
  }

  public TreeNode check(String c_value)
  {
    if(c_value.equals("+"))
    {
      TreeNodeAdd ad = new TreeNodeAdd(3,c_value);
      return (TreeNode) ad;
    }
    else if(c_value.equals("*"))
    {
      TreeNodeMul ml = new TreeNodeMul(4,c_value);
      return (TreeNode) ml;
    }
    //It's a Number
    TreeNodeNum nm = new TreeNodeNum(5,Integer.parseInt(c_value));
    return (TreeNode) nm;
  }

  public void set(String x)
  {
    //do nothing
  }

  public void swapping(TreeNode temp)
  {
    if(temp.value().equals("-20"))
    {
      swapping((TreeNode)temp.getChild(0));
    }
    else if(temp.value().equals("+"))
    {
      temp.set("*");
      swapping((TreeNode)temp.getChild(0));
      swapping((TreeNode)temp.getChild(1));
    }
    else if(temp.value().equals("*"))
    {
      temp.set("+");
      swapping((TreeNode)temp.getChild(0));
      swapping((TreeNode)temp.getChild(1));
    }
  }
  
  /* You can override these two methods in subclasses of TreeNode to
     customize the way the node appears when the tree is dumped.  If
     your output uses more than one line you should override
     toString(String), otherwise overriding toString() is probably all
     you need to do. 
  */
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
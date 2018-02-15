class TreeNodeMul extends TreeNode
{
  String ch;
  TreeNodeMul(int i,String temp)
  {
    super(i);
    ch = temp;
  }
  public String toString() 
  { 
    //System.out.println("TreeNodeMul Class");
    return " " + ch; 
  }
}
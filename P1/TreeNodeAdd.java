class TreeNodeAdd extends TreeNode
{
  String ch;
  TreeNodeAdd(int i,String temp)
  {
    super(i);
    ch = temp;
  }
  public String toString() 
  { 
    return " " + ch; 
  }
}
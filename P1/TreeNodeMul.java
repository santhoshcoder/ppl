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
    return ch; 
  }
  public String value()
  {
    return ch;
  }
  public void set(String x)
  {
    ch = x;
  }
}
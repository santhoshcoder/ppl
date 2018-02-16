class TreeNodeNum extends TreeNode
{
  int number;
  TreeNodeNum(int i,int temp)
  {
    super(i);
    number = temp;
  }
  public String toString() 
  { 
    return " " + number; 
  }
}
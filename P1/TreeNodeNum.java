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
  	//System.out.println("TreeNodeNum Class");
    return " " + number; 
  }
}
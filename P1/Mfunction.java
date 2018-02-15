import java.io.*;
import java.util.*;
class Mfunction
{
	public static void main(String args[])
	{
		TreeNode m = new TreeNode(0);
	    String expression;
	    Scanner sc = new Scanner(System.in);
	    expression = sc.nextLine();
	    String words[] = expression.trim().split(" ");
	    System.out.println(Arrays.toString(words));
	    TreeNodeMul mul;
	    TreeNodeAdd add;
	    TreeNodeNum num; 
	    for(int i = 0; i < words.length;i++)
	    {
	    	if(words[i].equals("*"))
    		{
    			mul = new TreeNodeMul(i+1,words[i]);
    			mul.addChild(m.getChild(m.getNumChildren()-2),0);
    			mul.addChild(m.getChild(m.getNumChildren()-1),1);
                System.out.println("Pushing " + m.getChild(m.getNumChildren()-2) + " into TreeNodeMul");
                System.out.println("Pushing " + m.getChild(m.getNumChildren()-1) + " into TreeNodeMul");
    			TreeNode temp = m ;
    			m = new TreeNode(0);
                System.out.println("Now TreeNode is Empty "+m.getNumChildren());
    			for(int j = 0;j<temp.getNumChildren()-2;j++)
                {
                    m.addChild(temp.getChild(j),j);
                    System.out.println("Pushing " + temp.getChild(j) + " into TreeNode at position: "+j);
                }
                System.out.println("Pushing mul node into TreeNode at position: "+m.getNumChildren());
    			m.addChild(mul,m.getNumChildren());
    		}
    		else if(words[i].equals("+"))
    		{
    			add = new TreeNodeAdd(i+1,words[i]);
    			add.addChild(m.getChild(m.getNumChildren()-2),0);
    			add.addChild(m.getChild(m.getNumChildren()-1),1);
                System.out.println("Pushing " + m.getChild(m.getNumChildren()-2) + " into TreeNodeAdd");
                System.out.println("Pushing " + m.getChild(m.getNumChildren()-1) + " into TreeNodeAdd");
    			TreeNode temp = m ;
    			m = new TreeNode(0);
                System.out.println("Now TreeNode is Empty "+m.getNumChildren());
    			for(int j = 0;j<temp.getNumChildren()-2;j++)
                {
                    m.addChild(temp.getChild(j),j);
                    System.out.println("Pushing " + temp.getChild(j) + " into TreeNode at position: "+j);
                }
                System.out.println("Pushing add node into TreeNode at position: "+m.getNumChildren());
    			m.addChild(add,m.getNumChildren());
    		}
    		else
    		{
    			num = new TreeNodeNum(i+1,Integer.parseInt(words[i]));
    			m.addChild(num,m.getNumChildren());
                System.out.println("Pushing " + num + " into TreeNode at position: "+m.getNumChildren());
    		}   
	    }
        m.dump(" ");
	}
}
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
	    
        TreeNodeMul mul;
	    TreeNodeAdd add;
	    TreeNodeNum num; 

        // Building Tree
	    for(int i = 0; i < words.length;i++)
	    {
	    	if(words[i].equals("*"))
    		{
    			mul = new TreeNodeMul(i+1,words[i]);
    			mul.addChild(m.getChild(m.getNumChildren()-2),0);
    			mul.addChild(m.getChild(m.getNumChildren()-1),1);
                TreeNode temp = m ;
    			m = new TreeNode(0);
    			for(int j = 0;j<temp.getNumChildren()-2;j++)
                {
                    m.addChild(temp.getChild(j),j);
                }
                m.addChild(mul,m.getNumChildren());
    		}
    		else if(words[i].equals("+"))
    		{
    			add = new TreeNodeAdd(i+1,words[i]);
    			add.addChild(m.getChild(m.getNumChildren()-2),0);
    			add.addChild(m.getChild(m.getNumChildren()-1),1);
                TreeNode temp = m ;
    			m = new TreeNode(0);
    			for(int j = 0;j<temp.getNumChildren()-2;j++)
                {
                    m.addChild(temp.getChild(j),j);
                }
                m.addChild(add,m.getNumChildren());
    		}
    		else
    		{
    			num = new TreeNodeNum(i+1,Integer.parseInt(words[i]));
    			m.addChild(num,m.getNumChildren());
    		}   
	    }
        System.out.print("\nThe Tree representation:");
        m.dump("");

        //Evaluating Tree
        System.out.println("\nThe value: "+m.eval(m));

        TreeNode mc = new TreeNode(1);
        
        //Cloning Original Tree m into mc
        m.clone(mc,m);

        //Swap + and * operations in mc
        mc.swapping(mc);
        
        //Printing New Tree (mc)
        System.out.print("\nThe new tree:");
        mc.dump("");

        System.out.println("\nThe value of the new tree: " + mc.eval(mc));

        System.out.println("\nThe value of the original tree: " + m.eval(m));
	}
}
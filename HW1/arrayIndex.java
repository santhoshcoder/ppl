class arrayIndex
{
	private
		static int n = 10; // As specified in the question that the matrix is as n*n integer matrix
		static int [][] m = new int[n][n];
	public
		static void initialize()
		{
			for(int i=0;i<n;i++)
			{
				for(int j=0;j<n;j++)
				{
					if (i == 2)
						m[i][j] = 0;
					else
						m[i][j] = 1;
				}
			}
		}
		static void printIndex()
		{
			int row,column;
			for(row=0; row<n; row++)
			{
				for(column=0; column<n; column++)
				{
					if(m[row][column]!=0)
					{
						column = n+1;
						break;
					}
				}
				if(column != n+1)
				{
					System.out.println("Index of the first all-zero row is: "+row);
					break;
				}
			}
		}
		public static void main(String args[])
		{
			initialize(); // This function stores values in the array
			printIndex(); // This function checks and prints the index of the first all-zero row
		}
}
package edu.njust.sem.log;

public class Cluster {
	public Cluster() {

	}

	public void fenKuai(double[][] matrix) {

	}
	public void juZhenBianHuan(double[][] matrix){
		for(int i = 0,length = matrix.length; i < length - 1;i++){
			//找出matrix 中第i 行从第i + 1 列到第最后一列元素的最大值所在的列k;
			double max = 0;//在第i行中，从第i+1列到最后一列的最大值
			int  k = 0;//最大值max所在的位置(第k列)
			for(int j = i +1;j < matrix[i].length; j++){
				if(max < matrix[i][j]){
					max = matrix[i][j];
					k = j;
					//如果找到该行（第i行）中从第i+1列到最后一列的元素中的最大值（不能重复），则交换第i + 1 行和第k 行，否则不交换。
					double[] temp = null;
					temp = matrix[i+1];
					matrix[i+1] = matrix[k];
					matrix[k] = temp;
					System.out.println("交换第"+(i+1)+"行和第"+k+"行后的矩阵为：");
					print(matrix);
					//交换第i + 1 列和第k 列;
					temp = new double[matrix.length];
					for(int a = 0; a < temp.length; a++){
						temp[a] = matrix[a][k];
						matrix[a][k] = matrix[a][i+1];
						matrix[a][i+1] = temp[a];
					}

					System.out.println("交换第"+(i+1)+"列和第"+k+"列后的矩阵为：");
					print(matrix);
				}
			}
			
		}
	}
	public void print(double[][] matrix){
		System.out.println();
		for(int a = 0; a < matrix.length;a++){
			for(int j = 0; j < matrix[a].length; j++){
				System.out.print(matrix[a][j] + " , ");
			}
			System.out.println();
		}
	}
	public static void main(String[] args) {
		double[][] matrix = new double[][] {
				{ 1 ,.88, 0 ,.88, 0 , 0 , 0 ,.93,.95},
				{.88, 1 , 0 ,.94, 0 , 0 , 0 ,.95,.93},
				{ 0 , 0 , 1 , 0 ,.96, 0 , 0 , 0 , 0 },
				{.88,.94, 0 , 1 , 0 , 0 , 0 ,.92,.95},
				{ 0 , 0 ,.96, 0 , 1 , 0 , 0 , 0 , 0 },
				{ 0 , 0 , 0 , 0 , 0 , 1 ,.99, 0 , 0 },
				{ 0 , 0 , 0 , 0 , 0 ,.99, 1 , 0 , 0 },
				{.93,.95, 0 ,.92, 0 , 0 , 0 , 1 ,.90},
				{.95,.93, 0 ,.95, 0 , 0 , 0 ,.90, 1 }

		};
		Cluster cluster = new Cluster();
		cluster.juZhenBianHuan(matrix);
		
	}
}

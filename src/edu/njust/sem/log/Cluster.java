package edu.njust.sem.log;

public class Cluster {
	public Cluster() {

	}

	public void fenKuai(double[][] matrix) {

	}
	public void juZhenBianHuan(double[][] matrix){
		for(int i = 0,length = matrix.length; i < length - 1;i++){
			//�ҳ�matrix �е�i �дӵ�i + 1 �е������һ��Ԫ�ص����ֵ���ڵ���k;
			double max = 0;//�ڵ�i���У��ӵ�i+1�е����һ�е����ֵ
			int  k = 0;//���ֵmax���ڵ�λ��(��k��)
			for(int j = i +1;j < matrix[i].length; j++){
				if(max < matrix[i][j]){
					max = matrix[i][j];
					k = j;
					//����ҵ����У���i�У��дӵ�i+1�е����һ�е�Ԫ���е����ֵ�������ظ������򽻻���i + 1 �к͵�k �У����򲻽�����
					double[] temp = null;
					temp = matrix[i+1];
					matrix[i+1] = matrix[k];
					matrix[k] = temp;
					System.out.println("������"+(i+1)+"�к͵�"+k+"�к�ľ���Ϊ��");
					print(matrix);
					//������i + 1 �к͵�k ��;
					temp = new double[matrix.length];
					for(int a = 0; a < temp.length; a++){
						temp[a] = matrix[a][k];
						matrix[a][k] = matrix[a][i+1];
						matrix[a][i+1] = temp[a];
					}

					System.out.println("������"+(i+1)+"�к͵�"+k+"�к�ľ���Ϊ��");
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

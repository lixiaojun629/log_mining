package edu.njust.sem.log;

public class Cluster {
	private double[][] jz = null;
	private Similarity sm = new Similarity();

	public Cluster() {
		//jz = sm.caculate();
	}

	public void fenKuai() {
		double max = 0;
		int maxj = 0;
		//
		for (int i = 0; i < jz.length - 1; i++) {
			// �ҳ����Ԫ�ص���ֵ
			for (int j = i + 1; j < jz[i].length; j++) {
				if (jz[i][j] > max) {
					max = jz[i][j];
				}
			}
			// �ҳ����Ԫ�ص�λ��
			for (int j = i + 1; j < jz[i].length; j++) {
				if (jz[i][j] == max) {
					maxj = j;
				}
			}
			// ������i+1�к͵�maxj��
			double[] temp = jz[i + 1];
			jz[i + 1] = jz[maxj];
			jz[maxj] = temp;
			temp = null;
			// ������i+1�����maxj��
			for (int k = 0; k < jz[i + 1].length; k++) {
				double temp1 = jz[k][i + 1];
				jz[k][i + 1] = jz[k][maxj];
				jz[k][maxj] = temp1;
			}
		}
	}

}

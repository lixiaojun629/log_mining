package edu.njust.sem.log;

import java.awt.Toolkit;
import java.io.File;

import javax.swing.SwingWorker;

import edu.njust.sem.log.util.DBUtil;

public class Task extends SwingWorker<Void, Void> {
	private LogTreatment lt = null;
	private File file;
	private static  Task task;
	private Task(){
		
	}
	public static Task getTask(){
		if(task == null){
			task = new Task();
		}
		return new Task();//�������ģʽ
	}
	@Override
	public Void doInBackground() throws Exception {
		System.out.println("Running in background");
		
		lt = LogTreatment.getLogTreatment();
		lt.setFile(file);
		setProgress(1);
		Thread.sleep(2000);
		//lt.clearAllTable();

		setProgress(2);
		Thread.sleep(2000);
		//lt.LogImport();

		setProgress(3);
		Thread.sleep(2000);
		//lt.insertTab1();

		setProgress(4);
		Thread.sleep(2000);
		//lt.createTabFirstNo();

		setProgress(5);
		Thread.sleep(2000);
		//lt.insertTab2();

		setProgress(6);
		Thread.sleep(2000);
		//lt.createTabSession();

		setProgress(7);
		//lt.createTabTrans();
		Thread.sleep(2000);
		setProgress(8);
		//lt.delTrans();

		setProgress(9);
		Thread.sleep(2000);
		setProgress(10);
		Thread.sleep(2000);
		setProgress(11);
		//DBUtil.closeConn();
		// result = lt.getResult();
		//
		// strResult = "��־��������" + result[0] + '\n' + "��ҳ������" + result[1]
		// + '\n' + "�û�������" + result[2] + '\n' + "����·��������" + result[3]
		// + '\n' + "��������ʹ���ʣ�" + result[4] + '\n' + "����Ŀ¼ʹ���ʣ�"
		// + result[5];
		// txtArea.setText(strResult);
		return null;
	}
	@Override
	public void done() {
		Toolkit.getDefaultToolkit().beep();
		//jbtSearch.setEnabled(true);
		//setCursor(null); // turn off the wait cursor

	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	
}
package edu.njust.sem.log.gui;

import javax.swing.table.DefaultTableModel;

public class CustomTableModel extends DefaultTableModel{
	String[] columnNames;
	Class dataType[];
	public  CustomTableModel(int row,int col,String[]cn,Class []dataType){
		super(row,col);
		columnNames=cn;
		this.dataType=dataType;
	}
	public  boolean isCellEditable(int row,int col){
		return false;
	}
	public String getColumnName(int c){
		return columnNames[c];
	}
	public Class getColumnClass(int c){
		return dataType[c];
	}
	
}

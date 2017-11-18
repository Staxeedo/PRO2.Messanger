package cz.uhk.pro2.messenger.gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;


import cz.uhk.pro2.messenger.model.Message;

public class MessagesTableModel extends AbstractTableModel {
	private List<Message> messages = new ArrayList<>();
	
	public MessagesTableModel(List<Message> messages) {
		this.messages=messages; //zapamatujeme si referenci na nas nakupni listek
		
	}
	
	
	public void setMessages(List<Message> messages)
	{
		this.messages=messages;
		// dam vedet, ze se data zmenily
		fireTableDataChanged();
	}


	@Override
	public int getColumnCount() {
		// TODO Auto-generated method stub
		// mam 4 sloupce
		return 4;
	}


	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return messages.size();
	}


	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Message m = messages.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return m.getTime();
		case 1:
			return m.getText();
		case 2:
			return m.getFrom();
		case 3:
			return m.getTo();
		}
		return "";
		}
	
	@Override
	public String getColumnName(int column) {
		switch (column) {
		case 0: return "Time";
		case 1: return "Text";
		case 2: return "From";
		case 3: return "To";
		
		
		}
		// kvuli syntaxi kdyby nahodou switch nic nevratil
		return "";
	}
	}
	

	


package com.atom.android.booklist.beans;

public class OrderInfo {

	private String orderDate;
	private String bookName;
	private int orderNumber;
	private int bookFlag;
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public int getBookFlag() {
		return bookFlag;
	}
	public void setBookFlag(int bookFlag) {
		this.bookFlag = bookFlag;
	}
	
}

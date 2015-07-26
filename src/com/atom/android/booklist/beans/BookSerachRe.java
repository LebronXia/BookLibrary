package com.atom.android.booklist.beans;

import java.io.Serializable;


/**
 * @author zxbpc
 *
 */
public class BookSerachRe implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 //归还书的次数
	private int returnCount;	
	 // 归还时间
	private String returnDate;
	/**
	 * 0：正在借阅
	 * 1：没有借阅，但有借过
	 * 2：没有借阅，也没借过，正在预约
	 * 3:什么都没有
	 */
	private int flag;
	private int mBookId;
	private String mBookName;
	private String mWrite;
	private String mBookType;
	private String mBookNumber;
	private String mBookPublish;
	private int mBookPrice;
	private String mBookDescribe;
	private String mBookLanguage;
	private String mBookVersion;
	private String mBookContent;
	private String mBookImageUrl;
	private String mBookPublishDate;
	private String borrowDate;
	
	public String getBookPublishDate() {
		return mBookPublishDate;
	}
	public void setBookPublishDate(String bookPublishDate) {
		mBookPublishDate = bookPublishDate;
	}
	public String getBorrowDate() {
		return borrowDate;
	}
	public void setBorrowDate(String borrowDate) {
		this.borrowDate = borrowDate;
	}
	public int getReturnCount() {
		return returnCount;
	}
	public void setReturnCount(int returnCount) {
		this.returnCount = returnCount;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getBookId() {
		return mBookId;
	}
	public void setBookId(int bookId) {
		mBookId = bookId;
	}
	public String getBookName() {
		return mBookName;
	}
	public void setBookName(String bookName) {
		mBookName = bookName;
	}
	public String getWrite() {
		return mWrite;
	}
	public void setWrite(String write) {
		mWrite = write;
	}
	public String getBookType() {
		return mBookType;
	}
	public void setBookType(String bookType) {
		mBookType = bookType;
	}
	public String getBookNumber() {
		return mBookNumber;
	}
	public void setBookNumber(String bookNumber) {
		mBookNumber = bookNumber;
	}
	public String getBookPublish() {
		return mBookPublish;
	}
	public void setBookPublish(String bookPublish) {
		mBookPublish = bookPublish;
	}
	public int getBookPrice() {
		return mBookPrice;
	}
	public void setBookPrice(int bookPrice) {
		mBookPrice = bookPrice;
	}
	public String getBookDescribe() {
		return mBookDescribe;
	}
	public void setBookDescribe(String bookDescribe) {
		mBookDescribe = bookDescribe;
	}
	public String getBookLanguage() {
		return mBookLanguage;
	}
	public void setBookLanguage(String bookLanguage) {
		mBookLanguage = bookLanguage;
	}
	public String getBookVersion() {
		return mBookVersion;
	}
	public void setBookVersion(String bookVersion) {
		mBookVersion = bookVersion;
	}
	public String getBookContent() {
		return mBookContent;
	}
	public void setBookContent(String bookContent) {
		mBookContent = bookContent;
	}
	public String getBookImageUrl() {
		return mBookImageUrl;
	}
	public void setBookImageUrl(String bookImageUrl) {
		mBookImageUrl = bookImageUrl;
	}
	
	
}

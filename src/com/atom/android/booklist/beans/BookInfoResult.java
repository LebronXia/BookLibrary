package com.atom.android.booklist.beans;

import java.io.Serializable;

public class BookInfoResult implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	private String mBookImageUrl;
	private String mBookDestribe;
	private String MBookContent;
	private String mBorrowDate;
	private String mreturnDate;
	private String mIsbn;
	private int bookFlag;
	private int bookCounts;
	private int ifCollection;
	public int getBookId() {
		return mBookId;
	}
	public void setBookId(int bookId) {
		mBookId = bookId;
	}
	public String getBookName() {
		return mBookName;
	}
	
	public String getWrite() {
		return mWrite;
	}
	public void setWrite(String write) {
		mWrite = write;
	}
	public void setBookName(String bookName) {
		mBookName = bookName;
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
	public String getBookImageUrl() {
		return mBookImageUrl;
	}
	public void setBookImageUrl(String bookImageUrl) {
		mBookImageUrl = bookImageUrl;
	}
	public String getBookDestribe() {
		return mBookDestribe;
	}
	public void setBookDestribe(String bookDestribe) {
		mBookDestribe = bookDestribe;
	}
	public String getMBookContent() {
		return MBookContent;
	}
	public void setMBookContent(String mBookContent) {
		MBookContent = mBookContent;
	}
	public String getBorrowDate() {
		return mBorrowDate;
	}
	public void setBorrowDate(String borrowDate) {
		mBorrowDate = borrowDate;
	}
	public String getMreturnDate() {
		return mreturnDate;
	}
	public void setMreturnDate(String mreturnDate) {
		this.mreturnDate = mreturnDate;
	}
	public int getBookFlag() {
		return bookFlag;
	}
	public void setBookFlag(int bookFlag) {
		this.bookFlag = bookFlag;
	}
	
	public String getIsbn() {
		return mIsbn;
	}
	public void setIsbn(String isbn) {
		mIsbn = isbn;
	}
	
	public int getIfCollection() {
		return ifCollection;
	}
	public void setIfCollection(int ifCollection) {
		this.ifCollection = ifCollection;
	}
	public int getBookCounts() {
		return bookCounts;
	}
	public void setBookCounts(int bookCounts) {
		this.bookCounts = bookCounts;
	}
	
}

package com.atom.android.booklist.beans;

import java.io.Serializable;

public class UserInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int mUserId;
	private String accoount;
	private String mName;
	private String mPassword;
	private String mTelephone;
	private char mSex;
	private String mArea;
	private String mHeadPicture;
	public int getUserId() {
		return mUserId;
	}
	public void setUserId(int userId) {
		mUserId = userId;
	}
	
	public String getAccoount() {
		return accoount;
	}
	public void setAccoount(String accoount) {
		this.accoount = accoount;
	}
	public String getName() {
		return mName;
	}
	public void setName(String name) {
		mName = name;
	}
	public String getPassword() {
		return mPassword;
	}
	public void setPassword(String password) {
		mPassword = password;
	}
	public String getTelephone() {
		return mTelephone;
	}
	public void setTelephone(String telephone) {
		mTelephone = telephone;
	}
	public char getSex() {
		return mSex;
	}
	public void setSex(char sex) {
		mSex = sex;
	}
	public String getArea() {
		return mArea;
	}
	public void setArea(String area) {
		mArea = area;
	}
	public String getHeadPicture() {
		return mHeadPicture;
	}
	public void setHeadPicture(String headPicture) {
		mHeadPicture = headPicture;
	}
	
}

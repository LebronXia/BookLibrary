package com.atom.android.booklist.adapts;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract  class AdapterBase extends BaseAdapter {

	private Context mContext;
	private List mList;
	private LayoutInflater mLayoutInflater;
	public void setList(List pList){
		mList = pList;
	}
	

	public AdapterBase(Context mContext) {
		super();
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(mContext);
	}
	
	public AdapterBase(Context mContext, List mList) {
		super();
		this.mContext = mContext;
		this.mList = mList;
		mLayoutInflater = LayoutInflater.from(mContext);
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public LayoutInflater getLayoutInflater() {

		return mLayoutInflater;
	}


}

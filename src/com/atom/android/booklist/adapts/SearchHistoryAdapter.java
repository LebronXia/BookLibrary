package com.atom.android.booklist.adapts;

import java.util.ArrayList;
import java.util.List;

import com.atom.android.booklist.R;
import com.atom.android.booklist.beans.SearchHistory;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SearchHistoryAdapter extends AdapterBase{

	private List mList = new ArrayList<String>();
	public SearchHistoryAdapter(Context mContext) {
		super(mContext);
		// TODO Auto-generated constructor stub
	}
	public SearchHistoryAdapter(Context mContext, List mList) {
		super(mContext, mList);
		// TODO Auto-generated constructor stub
		mList = mList;
	}
	private class Holder{
		TextView tv_bookkey;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null){			
			convertView = getLayoutInflater().inflate(R.layout.item_search, null);
			
			holder = new Holder();
			holder.tv_bookkey = (TextView) convertView.findViewById(R.id.tv_bookkey);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		String bookKey =  (String) getItem(position);
		holder.tv_bookkey.setText(bookKey);
		return convertView;
	}

}

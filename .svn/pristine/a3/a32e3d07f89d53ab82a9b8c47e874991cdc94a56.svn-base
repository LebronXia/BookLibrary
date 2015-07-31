package com.atom.android.booklist.adapts;

import java.util.ArrayList;
import java.util.List;

import com.atom.android.booklist.R;
import com.atom.android.booklist.beans.SearchHistory;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AutoCompleAdapter extends AdapterBase{
	private List mList = new ArrayList<String>();
	public AutoCompleAdapter(Context mContext, List mList) {
		super(mContext, mList);
		// TODO Auto-generated constructor stub
		mList = mList;
	}

	private class Holder{
		TextView tv_autoword;		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if(convertView == null){			
			convertView = getLayoutInflater().inflate(R.layout.item_autocomplete, null);
			
			holder = new Holder();
			holder.tv_autoword = (TextView) convertView.findViewById(R.id.tv_autoword);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		String autoword =  (String) getItem(position);
		holder.tv_autoword.setText(autoword);
		return convertView;
	}

}

package com.atom.android.booklist.adapts;

import java.util.ArrayList;
import java.util.List;

import com.atom.android.booklist.R;
import com.atom.android.booklist.beans.BookInfo;
import com.atom.android.booklist.beans.BookSerachRe;

import android.content.Context;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class BookWithoutPicsAdapter extends AdapterBase {
	private Context mContext;
	private List<BookSerachRe> mList = new ArrayList<BookSerachRe>();
	
	public BookWithoutPicsAdapter(Context context, List<BookSerachRe> list) {
		super(context, list);
		mContext = context;
		mList = list;
	}
	
	private class Holder{
		public TextView tv_item_position;
		public TextView tv_booktitle;
		public ImageView ib_bookstates;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder;
		if(convertView == null){
			convertView = getLayoutInflater().inflate(R.layout.item_list_withote_pics, parent, false);
			
			holder = new Holder();
			holder.tv_item_position = (TextView) convertView.findViewById(R.id.tv_item_position);
			holder.tv_booktitle = (TextView) convertView.findViewById(R.id.tv_search_bookname);
			holder.ib_bookstates = (ImageView) convertView.findViewById(R.id.ib_bookstates);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		BookSerachRe bookSerachRe = (BookSerachRe) getItem(position);

		holder.tv_item_position.setText((position+ 1) + "");
		holder.tv_booktitle.setText(bookSerachRe.getBookName());
		switch (bookSerachRe.getFlag()) {
		case 0:
			holder.ib_bookstates.setVisibility(View.VISIBLE);
			holder.ib_bookstates.setBackgroundResource(R.drawable.bookreturn);
			break;
		case 1:
			holder.ib_bookstates.setVisibility(View.VISIBLE);
			holder.ib_bookstates.setBackgroundResource(R.drawable.bookreaded);
			break;
		case 2:
			holder.ib_bookstates.setVisibility(View.VISIBLE);
			holder.ib_bookstates.setBackgroundResource(R.drawable.bookorder);
			break;
		default:
			break;
		}
		return convertView;
	}
	
	
	
	
	
}

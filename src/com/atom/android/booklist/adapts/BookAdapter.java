package com.atom.android.booklist.adapts;

import java.util.ArrayList;
import java.util.List;

import com.atom.android.booklist.R;
import com.atom.android.booklist.beans.BookInfo;



import com.atom.android.booklist.config.URL;
import com.loopj.android.image.SmartImageView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BookAdapter extends AdapterBase{

	private Context mContext;
	private List<BookInfo> mList = new ArrayList<BookInfo>();
	private int currentPosition = -1;
	
	public BookAdapter(Context mContext, List<BookInfo> mList) {
		super(mContext, mList);
		// TODO Auto-generated constructor stub
		this.mContext = mContext;
		this.mList = mList;
		Log.d("BookAdapter", mList.size()+"booklist2的数量");
	}
	public void setCurrentPosition(int currentPosition){
		this.currentPosition = currentPosition;
	}

	private class Holder{
		public SmartImageView  iv_book;
		public TextView tv_booktitle;
		public TextView tv_bookauthor;
		public TextView tv_publish;
		public TextView tv_booknumber;	
		public LinearLayout imageLinearLayout;
		public ImageView ib_borrowed;
		public ImageView ib_return;
		public ImageView ib_order;
		public ImageView ib_favorate;
		
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
			Holder holder;
		if(convertView == null){			
			convertView = getLayoutInflater().inflate(R.layout.item_bookstatus, parent, false);	
			
			holder = new Holder();
			holder.iv_book = (SmartImageView) convertView.findViewById(R.id.iv_bookpicture);
			holder.tv_booktitle = (TextView) convertView.findViewById(R.id.tv_booktitle);
			holder.tv_bookauthor = (TextView) convertView.findViewById(R.id.tv_bookauthor);
			holder.tv_publish = (TextView) convertView.findViewById(R.id.tv_publish);
			holder.tv_booknumber = (TextView) convertView.findViewById(R.id.tv_booknumber);
			holder.imageLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll_fourimagebutton);
			holder.ib_borrowed = (ImageView) convertView.findViewById(R.id.ib_borrowed);
			holder.ib_return = (ImageView) convertView.findViewById(R.id.ib_return);
			holder.ib_order = (ImageView) convertView.findViewById(R.id.ib_order);
			holder.ib_favorate = (ImageView) convertView.findViewById(R.id.ib_favorate);
			convertView.setTag(holder);
		} else {
			holder = (Holder)convertView.getTag();
		}
		BookInfo book = (BookInfo) getItem(position);
		String imageUrl = URL.WebSite2 + book.getBookImageUrl();
		
		holder.iv_book.setImageUrl(imageUrl);
		holder.tv_booktitle.setText(book.getBookName());
		holder.tv_bookauthor.setText(book.getWrite());
		holder.tv_publish.setText(book.getBookPublish());
		holder.tv_booknumber.setText(book.getBookNumber());
		
		if(position == currentPosition){
			holder.imageLinearLayout.setVisibility(View.VISIBLE);
			holder.ib_borrowed.setClickable(true);
			holder.ib_return.setClickable(true);
			holder.ib_order.setClickable(true);
			holder.ib_favorate.setClickable(true);
			//借书按钮设置监听事件
			holder.ib_borrowed.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentPosition = -1;
				}
			});
			//归还设置监听事件
			holder.ib_return.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentPosition = -1;
				}
			});
			//预约设置监听事件
			holder.ib_order.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentPosition = -1;
				}
			});
			//收藏设置监听事件
			holder.ib_favorate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentPosition = -1;
				}
			});
		} else {
			holder.imageLinearLayout.setVisibility(View.GONE);
			holder.ib_borrowed.setClickable(false);
			holder.ib_return.setClickable(false);
			holder.ib_order.setClickable(false);
			holder.ib_favorate.setClickable(false);
		}
		
		
		return convertView;
	}
	
	
}

package com.atom.android.booklist.adapts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.atom.android.booklist.R;
import com.atom.android.booklist.activity.DetailActivity;
import com.atom.android.booklist.beans.BookInfo;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.config.URL;
import com.atom.android.booklist.contants.GCConstant;
import com.atom.android.booklist.fragments.DetailFragment;
import com.atom.android.booklist.interfaces.OnTaskListener;
import com.atom.android.booklist.utils.NetUtils;

import android.content.Context;
import android.content.Intent;
import android.text.style.LineHeightSpan.WithDensity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BookWithoutPicsAdapter extends AdapterBase implements OnTaskListener{
	private Context mContext;
	private List<BookSerachRe> mList = new ArrayList<BookSerachRe>();
	private BookWithoutPicsAdapter mBookWithoutPicsAdapter = this;
	private static final String TAG = "BookwithPicAdapter";
	private int currentPosition = -1;
	
	public BookWithoutPicsAdapter(Context context, List<BookSerachRe> list) {
		super(context, list);
		mContext = context;
		mList = list;
	}
	
	public void setCurrentPosition(int currentPosition){
		this.currentPosition = currentPosition;
	}
	
	private class Holder{
		public TextView tv_item_position;
		public TextView tv_booktitle;
		public ImageView ib_bookstates;
		public LinearLayout imageLinearLayout;
		public ImageView ib_borrowed;
		public ImageView ib_return;
		public ImageView ib_order;
		public ImageView ib_favorate;
		
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
			holder.imageLinearLayout = (LinearLayout) convertView.findViewById(R.id.ll_fourimagebutton);
			holder.ib_borrowed = (ImageView) convertView.findViewById(R.id.ib_borrowed);
			holder.ib_return = (ImageView) convertView.findViewById(R.id.ib_return);
			holder.ib_order = (ImageView) convertView.findViewById(R.id.ib_order);
			holder.ib_favorate = (ImageView) convertView.findViewById(R.id.ib_favorate);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		final BookSerachRe bookSerachRe = (BookSerachRe) getItem(position);

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
		if(position == currentPosition){
			holder.imageLinearLayout.setVisibility(View.VISIBLE);
			switch (bookSerachRe.getFlag()) {
			case 0:
				holder.ib_borrowed.setBackgroundResource(R.drawable.borrowoff);
				holder.ib_borrowed.setEnabled(false);
				holder.ib_order.setBackgroundResource(R.drawable.bookoff);
				holder.ib_order.setEnabled(false);
				holder.ib_return.setClickable(true);
				holder.ib_favorate.setClickable(true);
				break;
			case 1:
				holder.ib_return.setBackgroundResource(R.drawable.returnoff);
				holder.ib_return.setEnabled(false);
				holder.ib_borrowed.setClickable(true);
				holder.ib_order.setClickable(true);
				holder.ib_favorate.setClickable(true);
				break;
			case 2:
				holder.ib_borrowed.setBackgroundResource(R.drawable.borrowoff);
				holder.ib_order.setBackgroundResource(R.drawable.bookoff);
				holder.ib_return.setBackgroundResource(R.drawable.returnoff);
				holder.ib_borrowed.setEnabled(false);
				holder.ib_return.setEnabled(false);
				holder.ib_order.setEnabled(false);
				holder.ib_favorate.setClickable(true);
			case 3:
				holder.ib_return.setBackgroundResource(R.drawable.returnoff);
				holder.ib_return.setEnabled(false);
				holder.ib_borrowed.setClickable(true);
				holder.ib_order.setClickable(true);
				holder.ib_favorate.setClickable(true);
			} 			
//				holder.ib_borrowed.setClickable(true);
//				holder.ib_return.setClickable(true);
//				holder.ib_order.setClickable(true);
//				holder.ib_favorate.setClickable(true);
			//借书按钮设置监听事件
			holder.ib_borrowed.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentPosition = -1;
					NetUtils nu = new NetUtils();
					nu.setmOnTaskListener(mBookWithoutPicsAdapter);
					Map<String, String> map = new HashMap<String, String>();
					map.put("userId", "" + bookSerachRe.getUserId());
					map.put("bookId", "" + bookSerachRe.getBookId());
					try {
						nu.post(map, GCConstant.ACTION_BORROW, URL.WebSite2 + "borrowBook");
						Log.d(TAG, "网络请求发送-----------");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			//归还设置监听事件
			holder.ib_return.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentPosition = -1;
					NetUtils nu = new NetUtils();
					nu.setmOnTaskListener(mBookWithoutPicsAdapter);
					Map<String, String> map = new HashMap<String, String>();
					map.put("userId", "" + bookSerachRe.getUserId());
					map.put("bookId", "" + bookSerachRe.getBookId());
					try {
						nu.post(map, GCConstant.ACTION_RETURN, URL.WebSite2 + "returnBook");
						Log.d(TAG, "还书请求发出-------");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			//预约设置监听事件
			holder.ib_order.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentPosition = -1;
					currentPosition = -1;
					NetUtils nu = new NetUtils();
					nu.setmOnTaskListener(mBookWithoutPicsAdapter);
					Map<String, String> map = new HashMap<String, String>();
					map.put("userId", "" + bookSerachRe.getUserId());
					map.put("bookId", "" + bookSerachRe.getBookId());
					try {
						nu.post(map, GCConstant.ACTION_ORDER, URL.WebSite2 + "orderBook");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			//收藏设置监听事件
			holder.ib_favorate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					currentPosition = -1;
					Intent intent = new Intent(mContext, DetailActivity.class);
					intent.putExtra(DetailFragment.EXTRA_BOOKINFODATAIL, (Serializable)bookSerachRe);
					mContext.startActivity(intent);	
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

	@Override
	public void onLoading(int taskflag, long total, long current) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSuccess(Object obj, int taskflag) {
		// TODO Auto-generated method stub
		String json = obj.toString();
		switch (taskflag) {
		case GCConstant.ACTION_BORROW:			
			Log.d(TAG, "成功借书-------");
			deal1(json);
			break;
		case GCConstant.ACTION_RETURN:
			deal1(json);			
			break;
		case GCConstant.ACTION_ORDER:
			Log.d(TAG, "预约图书----");
			deal1(json);
			break;		
		default:
			break;
		}
		
	}

	private void deal1(String json) {
		// TODO Auto-generated method stub
		try {
			JSONObject jo1 = new JSONObject(json);
			int resultId = jo1.getInt("resultId");
			if(resultId == 0){
				String msg = jo1.getString("obj");
				Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
			} else {
				String msg = jo1.getString("obj");
				Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	@Override
	public void onFailure(Throwable throwable, String s, Object obj) {
		// TODO Auto-generated method stub
		
	}

}

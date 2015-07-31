package com.atom.android.booklist.fragments;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atom.android.booklist.R;
import com.atom.android.booklist.activity.DetailActivity;
import com.atom.android.booklist.beans.BookInfoResult;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.config.URL;
import com.atom.android.booklist.contants.GCConstant;
import com.atom.android.booklist.interfaces.OnTaskListener;
import com.atom.android.booklist.utils.NetUtils;
import com.loopj.android.image.SmartImageView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailFragment extends Fragment implements OnClickListener,OnTaskListener{
	private View view;
	//顶部标题栏右边的后腿
	private ImageView titlebar_iv_left;
	//顶部标题栏的标题
	private TextView  titlebar_tv;
	//顶部标题栏右边的搜索
	private ImageView titlebar_iv_right;
	private TextView tv_showstates;
	private SmartImageView  iv_book;
	private TextView tv_booktitle;
	private TextView tv_bookauthor;
	private TextView tv_publish;
	private TextView tv_booknumber;
	//以往借阅时间
	private TextView tv_borrowedhistory;
	//当前借阅时间
	private TextView tv_nowdate;
	//当前在架
	private TextView tv_inrest;
	//文献图书类型
	private TextView tv_booktype;
	//语种
	private TextView tv_language;
	//isbn
	private TextView tv_bookisbn;
	//价格
	private TextView tv_bookprice;
	//内容描述
	private TextView tv_pricebook;
	//内容目录
	private TextView tv_bookcontents;
	private ImageView ib_borrowed;
	private ImageView ib_return;
	private ImageView ib_order;
	private ImageView ib_favorate;
	private ImageView ib_cancelfavorate;
	private ListView list_view;
	private LinearLayout ll_borrowRecordhistory;
	private DetailFragment mDetailFragment = this;
	private BookSerachRe bookSerachRe;
	private BookInfoResult bookInfoResult;
	private List<String> dateList;
	private static final String TAG = "DetailFragment";
	public static final String EXTRA_BOOKINFODATAIL =
			"com.atom.android.booklibrary.bookinfo";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bookSerachRe = new BookSerachRe();
		bookSerachRe = (BookSerachRe) getArguments().getSerializable(EXTRA_BOOKINFODATAIL);
		Log.d(TAG, bookSerachRe.getFlag()+"+图书的状态");
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.frag_bookinfos, container, false);
		initHttp();
		return view;
	}
	private void initHttp() {
		// TODO Auto-generated method stub
		NetUtils nu = new NetUtils();
		nu.setmOnTaskListener(mDetailFragment);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", "" + bookSerachRe.getUserId());
		map.put("bookId", "" + bookSerachRe.getBookId());
		try {
			nu.post(map, GCConstant.DETAIL_BOOK, URL.WebSite2 + "detail");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_showstates = (TextView) view.findViewById(R.id.tv_showstates);
		iv_book = (SmartImageView) view.findViewById(R.id.iv_bookpicture);
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		titlebar_iv_left = (ImageView) view.findViewById(R.id.titlebar_iv_left);
		tv_booktitle = (TextView) view.findViewById(R.id.tv_booktitle);
		tv_bookauthor = (TextView) view.findViewById(R.id.tv_bookauthor);
		tv_publish = (TextView) view.findViewById(R.id.tv_publish);
		tv_booknumber = (TextView) view.findViewById(R.id.tv_booknumber);
		//tv_borrowedhistory = (TextView) view.findViewById(R.id.tv_borrowedhistory);
		tv_nowdate = (TextView) view.findViewById(R.id.tv_nowdate);
		tv_inrest = (TextView) view.findViewById(R.id.tv_inrest);
		tv_booktype = (TextView) view.findViewById(R.id.tv_booktype);
		tv_language = (TextView) view.findViewById(R.id.tv_language);
		tv_bookisbn = (TextView) view.findViewById(R.id.tv_bookisbn);
		tv_bookprice = (TextView) view.findViewById(R.id.tv_bookprice);
		tv_pricebook = (TextView) view.findViewById(R.id.tv_pricebook);
		tv_bookcontents = (TextView) view.findViewById(R.id.tv_bookcontents);
		ib_borrowed = (ImageView) view.findViewById(R.id.ib_borrowed);
		ib_return = (ImageView) view.findViewById(R.id.ib_return);
		ib_order = (ImageView) view.findViewById(R.id.ib_order);
		ib_favorate = (ImageView) view.findViewById(R.id.ib_favorite2); 
		ib_cancelfavorate = (ImageView) view.findViewById(R.id.ib_cancelfavorate);
		list_view = (ListView) view.findViewById(R.id.list_view);
		ll_borrowRecordhistory = (LinearLayout) view.findViewById(R.id.ll_borrowRecordhistory);
		if(bookSerachRe.getIfCollection()!=0){
			ib_favorate.setVisibility(View.GONE);
			ib_cancelfavorate.setVisibility(View.VISIBLE);			
		}
		if(dateList!=null){
			ll_borrowRecordhistory.setVisibility(View.VISIBLE);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_expandable_list_item_1, dateList);
			list_view.setAdapter(adapter);
		}

		ib_borrowed.setOnClickListener(this);
		ib_return.setOnClickListener(this);
		ib_order.setOnClickListener(this);
		ib_favorate.setOnClickListener(this);
		ib_cancelfavorate.setOnClickListener(this);
		titlebar_iv_left.setOnClickListener(this);
		titlebar_iv_right.setVisibility(View.GONE);
		String imageUrl = URL.WebSite2 + bookInfoResult.getBookImageUrl();
		iv_book.setImageUrl(imageUrl);
		tv_booktitle.setText(bookInfoResult.getBookName());
		tv_bookauthor.setText(bookInfoResult.getWrite());
		tv_publish.setText(bookInfoResult.getBookPublish());
		tv_booknumber.setText(bookInfoResult.getBookNumber());
		tv_pricebook.setText(bookInfoResult.getBookDescribe());
	}
	
	private void setData() {
		// TODO Auto-generated method stub
		switch (bookSerachRe.getFlag()) {
		case 0:
			Log.d(TAG, bookSerachRe.getFlag() + "标志是0吗");
			ib_borrowed.setBackgroundResource(R.drawable.borrowoff);
			ib_borrowed.setEnabled(false);
			ib_order.setBackgroundResource(R.drawable.bookoff);
			ib_order.setEnabled(false);
			ib_return.setClickable(true);
			ib_favorate.setClickable(true);
			String returnDate = bookSerachRe.getReturnDate();
			tv_showstates.setVisibility(View.VISIBLE);
			tv_showstates.setBackgroundResource(R.drawable.shape_red_corner);
			tv_showstates.setText(returnDate+"前归还");
			break;
		case 1:
			ib_return.setBackgroundResource(R.drawable.returnoff);
			ib_return.setEnabled(false);
			ib_borrowed.setClickable(true);
			ib_order.setClickable(true);
			ib_favorate.setClickable(true);
			int returnCount = bookSerachRe.getReturnCount();
			tv_showstates.setVisibility(View.VISIBLE);
			tv_showstates.setBackgroundResource(R.drawable.shape_green_corner);
			tv_showstates.setText("借过"+returnCount+"次");
			break;
		case 2:
			ib_borrowed.setBackgroundResource(R.drawable.borrowoff);
			ib_order.setBackgroundResource(R.drawable.bookoff);
			ib_return.setBackgroundResource(R.drawable.returnoff);
			ib_borrowed.setEnabled(false);
			ib_return.setEnabled(false);
			ib_order.setEnabled(false);
			ib_favorate.setClickable(true);
			tv_showstates.setVisibility(View.VISIBLE);
			tv_showstates.setBackgroundResource(R.drawable.shape_yellow_corner);
			tv_showstates.setText("已预约");
			break;
		case 3:
			ib_return.setBackgroundResource(R.drawable.returnoff);
			ib_return.setEnabled(false);
			ib_borrowed.setClickable(true);
			ib_order.setClickable(true);
			ib_favorate.setClickable(true);
			break;
			
		} 	
	}
	//activity传输数据给Fragment,不需要让Fragment直接关联activity
			public static DetailFragment newInstance(BookSerachRe bookSerachRe){
				Bundle args = new Bundle();
				args.putSerializable(EXTRA_BOOKINFODATAIL, (Serializable) bookSerachRe);		
				DetailFragment fragment = new DetailFragment();
				fragment.setArguments(args);
				return fragment;		
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
		case GCConstant.DETAIL_BOOK:
		Log.d(TAG, "查看图书详情");
			deal2(json);
			Log.d(TAG, "到我这了-----");
			initView();
			setData();
			break;
		case GCConstant.FAVORITE_BOOK:
			deal3(json);	
			break;
		case GCConstant.CANCEL_FAVORITE_BOOK:
			deal4(json);
		default:
			break;
		}
	}
	private void deal4(String json) {
		// TODO Auto-generated method stub
		JSONObject jo1;
		try {
			Log.d(TAG, "正在进行收藏操作----");
			jo1 = new JSONObject(json);
			int resultId = jo1.getInt("resultId");
			if(resultId == 0){
				String msg = jo1.getString("obj");
				Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();	
					ib_cancelfavorate.setVisibility(View.GONE);
					ib_favorate.setVisibility(View.VISIBLE);					
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deal3(String json) {
		// TODO Auto-generated method stub
		JSONObject jo1;
		try {
			Log.d(TAG, "正在进行收藏操作----");
			jo1 = new JSONObject(json);
			int resultId = jo1.getInt("resultId");
			if(resultId == 0){
				String msg = jo1.getString("obj");
				Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();	
					ib_favorate.setVisibility(View.GONE);
					ib_cancelfavorate.setVisibility(View.VISIBLE);					
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void deal2(String json) {
		// TODO Auto-generated method stub
		
		try {
			JSONObject jo1 = new JSONObject(json);
			int resultId = jo1.getInt("resultId");
			if(resultId == 0){
				bookInfoResult = new BookInfoResult();
				JSONObject jo2 = jo1.getJSONObject("obj");
				JSONObject bookDatailJson = jo2.getJSONObject("bookDetail");
				JSONObject bookjson = bookDatailJson.getJSONObject("book");
				bookInfoResult = new BookInfoResult();
				int bookCount = bookDatailJson.getInt("bookCount");
				bookInfoResult.setBookId(bookjson.getInt("bookId"));
				bookInfoResult.setBookName(bookjson.getString("bookName"));
				bookInfoResult.setWrite(bookjson.getString("writer"));
				bookInfoResult.setBookType(bookjson.getString("bookType"));
				bookInfoResult.setBookNumber(bookjson.getString("bookNumber"));
				bookInfoResult.setBookPublish(bookjson.getString("bookPublish"));
				bookInfoResult.setBookPublish(bookjson.getString("bookPublishDate"));
				bookInfoResult.setBookPrice(bookjson.getInt("bookPrice"));
				bookInfoResult.setBookLanguage(bookjson.getString("bookLanguage"));
				bookInfoResult.setBookDescribe(bookjson.getString("bookDescribe"));
				bookInfoResult.setBookImageUrl(bookjson.getString("bookImage"));
				bookInfoResult.setIsbn(bookjson.getString("isbn"));
				bookInfoResult.setMBookContent(bookjson.getString("bookContent"));
				JSONArray duringArray = bookDatailJson.getJSONArray("durings");
				Log.d(TAG, bookCount + "读过的书架----------");
				dateList = new ArrayList<String>();
				Log.d(TAG, duringArray + "哈哈");
				if(duringArray != null){
					for(int i =0; i < duringArray.length(); i++){
						JSONObject jo5 = duringArray.getJSONObject(i);
						String borrowDate = jo5.getString("begin");
						String returnDate = jo5.getString("end");
						String date = borrowDate +"至" +returnDate;
						Log.d(TAG, date + "+借阅历史的时间");
						dateList.add(date);
					}
				} 		
				
				//bookInfoResult.setBookFlag(bookSerachRe.getFlag());
				//bookInfoResult.setIfCollection();
				Log.d(TAG, bookjson.getString("bookName") + "图书的名字----");
				Log.d(TAG, bookInfoResult.getBookName() + "这本书图书的名字----------");
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
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.titlebar_iv_left:
			getActivity().finish();
			break;
		case R.id.ib_favorite2:
			Log.d(TAG, "点击了收藏");
			NetUtils nu = new NetUtils();
			nu.setmOnTaskListener(mDetailFragment);
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", "" + bookSerachRe.getUserId());
			map.put("bookId", "" + bookSerachRe.getBookId());
			try {
				nu.post(map, GCConstant.FAVORITE_BOOK, URL.WebSite2 + "favorite");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case R.id.ib_cancelfavorate:
			Log.d(TAG, "点击了取消收藏");
			nu = new NetUtils();
			nu.setmOnTaskListener(mDetailFragment);
			map = new HashMap<String, String>();
			map.put("userId", "" + bookSerachRe.getUserId());
			map.put("bookId", "" + bookSerachRe.getBookId());
			try {
				nu.post(map, GCConstant.CANCEL_FAVORITE_BOOK, URL.WebSite2 + "cancelfavorite");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;

		default:
			break;
		}
	}
}

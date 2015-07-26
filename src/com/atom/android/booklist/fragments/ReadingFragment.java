package com.atom.android.booklist.fragments;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.atom.android.booklist.R;
import com.atom.android.booklist.activity.MainActivity;
import com.atom.android.booklist.activity.SearchActivity;
import com.atom.android.booklist.adapts.BookAdapter;
import com.atom.android.booklist.adapts.BookWithoutPicsAdapter;
import com.atom.android.booklist.adapts.BookwithPicAdapter;
import com.atom.android.booklist.beans.BookInfo;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.beans.UserInfo;

import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ReadingFragment extends Fragment implements OnItemClickListener,OnClickListener{
	private View view;
	private List<BookSerachRe> booksInReading;
	//查询图案结果的适配器
	private BookwithPicAdapter bookwithPicAdapter;
	//查询结果为文字结果
	private BookWithoutPicsAdapter booknoPicAdapter;
	//顶部标题栏右边的后腿
	private ImageView titlebar_iv_left;
	//顶部标题栏的标题
	private TextView  titlebar_tv;
	//顶部标题栏右边的搜索
	private ImageView titlebar_iv_right;
	//按图片显示
	private ImageButton listWithPics_IB;
	//按文字显示
	private ImageButton listWithoutPics_IB;
	private ListView lv_borrowedresult;
	private static final String TAG = "ReadingFragment";
	public static final String EXTRA_BorrowList = 
			"com.atom.android.booklist";
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		booksInReading = (List<BookSerachRe>) getArguments().getSerializable(EXTRA_BorrowList);
		for(BookSerachRe book : booksInReading){
			Log.d(TAG, book.getBookName()+"----------");
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.frag_borrowed, container, false);
		initView();
		setData();
		return view;
	}
	
	private void setData() {
		// TODO Auto-generated method stub
		if(bookwithPicAdapter == null){
			bookwithPicAdapter = new BookwithPicAdapter(getActivity(),booksInReading);
			lv_borrowedresult.setAdapter(bookwithPicAdapter);
		} else {
			bookwithPicAdapter.notifyDataSetChanged();
		}	
	}
	private void initView() {
		// TODO Auto-generated method stub
		lv_borrowedresult = (ListView) view.findViewById(R.id.lv_borrowedresult);
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		titlebar_iv_left = (ImageView) view.findViewById(R.id.titlebar_iv_left);
		listWithPics_IB = (ImageButton) view.findViewById(R.id.listWithPics_IB);
		listWithoutPics_IB = (ImageButton) view.findViewById(R.id.listWithoutPics_IB);
		lv_borrowedresult.setOnItemClickListener(this);
		listWithPics_IB.setOnClickListener(this);
		listWithoutPics_IB.setOnClickListener(this);
		titlebar_tv.setText(R.string.borrowed_book);
		titlebar_iv_right.setOnClickListener(this);
		titlebar_iv_left.setOnClickListener(this);
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.listWithPics_IB:
			listWithPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_with_pics));
			listWithoutPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_without_pics_off));
			lv_borrowedresult.setAdapter(bookwithPicAdapter);
			bookwithPicAdapter.notifyDataSetChanged();
			break;
		case R.id.listWithoutPics_IB:
			listWithoutPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_without_pics));
			listWithPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_with_pics_off));
			booknoPicAdapter = new BookWithoutPicsAdapter(getActivity(),booksInReading);
			lv_borrowedresult.setAdapter(booknoPicAdapter);
	
			break;
		case R.id.titlebar_iv_right:
			intent = new Intent(getActivity(),SearchActivity.class);
			startActivity(intent);
			break;
		case R.id.titlebar_iv_left:
			getActivity().finish();
			break;
		default:
			
			break;
		}		
	}		
	//activity传输数据给Fragment,不需要让Fragment直接关联activity
		public static ReadingFragment newInstance(List<BookSerachRe> booksInReading){
			Bundle args = new Bundle();
			args.putSerializable(EXTRA_BorrowList, (Serializable) booksInReading);		
			ReadingFragment fragment = new ReadingFragment();
			fragment.setArguments(args);
			return fragment;		
		}
}

package com.atom.android.booklist.fragments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.atom.android.booklist.R;
import com.atom.android.booklist.R.drawable;
import com.atom.android.booklist.activity.SearchActivity;
import com.atom.android.booklist.adapts.BookAdapter;
import com.atom.android.booklist.adapts.BookwithPicAdapter;
import com.atom.android.booklist.adapts.BookWithoutPicsAdapter;
import com.atom.android.booklist.beans.BookInfo;
import com.atom.android.booklist.beans.BookSerachRe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResultFragmnet extends Fragment implements OnItemClickListener, 
						OnItemLongClickListener, OnClickListener{
	private View view;
	//查询结果列表
	private ListView lv_searchresult;
	//查询图案结果的适配器
	private BookAdapter bookAdapter;
	private BookwithPicAdapter bookwithPicAdapter;
	//查询结果为文字结果
	private BookWithoutPicsAdapter booknoPicAdapter;
	//当前item位置
	private int currentPosition = -1;
	//顶部标题栏的标题
	private TextView  titlebar_tv;
	//顶部标题栏右边的搜索
	private ImageView titlebar_iv_right;
	//按图片显示
	private ImageButton listWithPics_IB;
	//按文字显示
	private ImageButton listWithoutPics_IB;
	//查询书的结果
	private List<BookSerachRe> bookSerachResList;
	private static final String TAG = "SearchResultFragmnet";
	public static final String EXTRA_BOOK_SERCHRESULT =
			"com.atom.android.android.booklist.book_searchresult";
					
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.frag_searchresult, container, false);		
		initView();
		setData();
		initListener();
		return view;
	}
	//初始化视图
	private void initView() {
		// TODO Auto-generated method stub
		lv_searchresult = (ListView) view.findViewById(R.id.lv_searchresult);
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		listWithPics_IB = (ImageButton) view.findViewById(R.id.listWithPics_IB);
		listWithoutPics_IB = (ImageButton) view.findViewById(R.id.listWithoutPics_IB);
		lv_searchresult.setOnItemClickListener(this);
		lv_searchresult.setOnItemLongClickListener(this);
		listWithPics_IB.setOnClickListener(this);
		listWithoutPics_IB.setOnClickListener(this);
		titlebar_tv.setText(R.string.serch_result);
		titlebar_iv_right.setOnClickListener(this);
	}
	//设置数据
	@SuppressWarnings("unchecked")
	private void setData() {
		// TODO Auto-generated method stub
//		bookResults = new ArrayList<Book>();
//		Book book = new Book();
//		for(int i = 0; i <= 5; i++){
//			book.setBookName("Android");
//			bookResults.add(book);
//		}
		bookSerachResList = new ArrayList<BookSerachRe>();
		bookSerachResList =(List<BookSerachRe>) getArguments().getSerializable(EXTRA_BOOK_SERCHRESULT);
		if(bookwithPicAdapter == null){
			bookwithPicAdapter = new BookwithPicAdapter(getActivity(),bookSerachResList);
			lv_searchresult.setAdapter(bookwithPicAdapter);
		} else {
			bookwithPicAdapter.notifyDataSetChanged();
		}		
	}
	
	//界面运行时更新listview
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		updateNoteBookList();
		super.onResume();
	}

	private void updateNoteBookList() {
		if (bookwithPicAdapter != null) {
			setData();
			bookwithPicAdapter.notifyDataSetChanged(); //通过此方法用于同志adapter的更新，即调用getView（）
		}
		if (booknoPicAdapter != null) {
			setData();
			booknoPicAdapter.notifyDataSetChanged(); //通过此方法用于同志adapter的更新，即调用getView（）
		}
	}
	
	//初始化监听器
	private void initListener() {
		// TODO Auto-generated method stub
		
	}
	//设置listview长按监听
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		// 设置震动周期
		vibrator.vibrate(40);
		currentPosition = position;
		//通过此方法用于同志adapter的更新，即调用getView（）
		bookwithPicAdapter.setCurrentPosition(currentPosition);
		bookwithPicAdapter.notifyDataSetChanged();
		return true;
	}
	//设置listview短按监听
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		bookwithPicAdapter.setCurrentPosition(-1);
		bookwithPicAdapter.notifyDataSetChanged();
	}
	//布局切换按钮监听
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.listWithPics_IB:
			listWithPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_with_pics));
			listWithoutPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_without_pics_off));
			lv_searchresult.setAdapter(bookwithPicAdapter);
			bookwithPicAdapter.notifyDataSetChanged();
			break;
		case R.id.listWithoutPics_IB:
			listWithoutPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_without_pics));
			listWithPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_with_pics_off));
			booknoPicAdapter = new BookWithoutPicsAdapter(getActivity(),bookSerachResList);
			lv_searchresult.setAdapter(booknoPicAdapter);
	
			break;
		case R.id.titlebar_iv_right:
			
			break;
		default:
			Intent intent = new Intent(getActivity(),SearchActivity.class);
			startActivity(intent);
			break;
		}		
	}
	
	public static SearchResultFragmnet newInstance(List<BookSerachRe> bookSearchReList){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_BOOK_SERCHRESULT, (Serializable)bookSearchReList);
		
		SearchResultFragmnet fragmnet = new SearchResultFragmnet();
		fragmnet.setArguments(args);
		return fragmnet;
	}
	

}

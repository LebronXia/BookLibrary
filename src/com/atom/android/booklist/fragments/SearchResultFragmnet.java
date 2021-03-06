package com.atom.android.booklist.fragments;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.atom.android.booklist.R;
import com.atom.android.booklist.R.drawable;
import com.atom.android.booklist.activity.SearchActivity;
import com.atom.android.booklist.adapts.BookAdapter;
import com.atom.android.booklist.adapts.BookwithPicAdapter;
import com.atom.android.booklist.adapts.BookWithoutPicsAdapter;
import com.atom.android.booklist.beans.BookInfo;
import com.atom.android.booklist.beans.BookSerachRe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.CapturedViewProperty;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
	//查询书的结果
	private List<BookSerachRe> bookSerachResList;
	private RadioGroup mRadioGroup;
	private RadioButton orderByName_RB,orderByPublish_RB;
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String TAG = "SearchResultFragmnet";
	public static final String EXTRA_BOOK_SERCHRESULT =
			"com.atom.android.android.booklist.book_searchresult";
					
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		bookSerachResList =(List<BookSerachRe>) getArguments().getSerializable(EXTRA_BOOK_SERCHRESULT);
	}
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
		titlebar_iv_left = (ImageView) view.findViewById(R.id.titlebar_iv_left);
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		listWithPics_IB = (ImageButton) view.findViewById(R.id.listWithPics_IB);
		listWithoutPics_IB = (ImageButton) view.findViewById(R.id.listWithoutPics_IB);
		mRadioGroup = (RadioGroup) view.findViewById(R.id.orderButtons_RG);
		orderByName_RB = (RadioButton) view.findViewById(R.id.orderByName_RB);
		orderByPublish_RB = (RadioButton) view.findViewById(R.id.orderByPublish_RB);
		lv_searchresult.setOnItemClickListener(this);
		lv_searchresult.setOnItemLongClickListener(this);
		listWithPics_IB.setOnClickListener(this);
		listWithoutPics_IB.setOnClickListener(this);
		titlebar_tv.setText(R.string.serch_result);
		titlebar_iv_left.setOnClickListener(this);
		titlebar_iv_right.setVisibility(View.GONE);
	}
	//设置数据
	@SuppressWarnings("unchecked")
	private void setData() {
		// TODO Auto-generated method stub		
		if(bookwithPicAdapter == null){
			bookwithPicAdapter = new BookwithPicAdapter(getActivity(),bookSerachResList);
			lv_searchresult.setAdapter(bookwithPicAdapter);
		} else {
			bookwithPicAdapter.notifyDataSetChanged();
		}	
		if(booknoPicAdapter == null){
			booknoPicAdapter = new BookWithoutPicsAdapter(getActivity(),bookSerachResList);
			lv_searchresult.setAdapter(bookwithPicAdapter);
		} else {
			booknoPicAdapter.notifyDataSetChanged();
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
		//给RidaoFroup设置监听事件
				mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						if(checkedId == orderByName_RB.getId()){
							//按照图书名字排序
							orderByName_RB.setBackgroundResource(R.color.background_green);
							orderByPublish_RB.setBackgroundResource(R.color.item_white);
							Collections.sort(bookSerachResList, new Comparator<BookSerachRe>() {

								@Override
								public int compare(BookSerachRe lhs,
										BookSerachRe rhs) {
									// TODO Auto-generated method stub
									return lhs.getBookName().compareTo(rhs.getBookName());
								}
							});
							bookwithPicAdapter.notifyDataSetChanged();
							booknoPicAdapter.notifyDataSetChanged();
							
						} else if (checkedId == orderByPublish_RB.getId()){
						//按照出版日排序
							orderByPublish_RB.setBackgroundResource(R.color.background_green);
							orderByName_RB.setBackgroundResource(R.color.item_white);
							Collections.sort(bookSerachResList, new Comparator<BookSerachRe>() {

								@Override
								public int compare(BookSerachRe lhs,
										BookSerachRe rhs) {
									// TODO Auto-generated method stub
									Date time1,time2;
									try {
										time1 = df.parse(lhs.getBookPublishDate());
										time2 = df.parse(rhs.getBookPublishDate());
										if(time1.before(time2))
											return 1;
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									return -1;
								}
							});
							bookwithPicAdapter.notifyDataSetChanged();
							booknoPicAdapter.notifyDataSetChanged();
						} 
					}
				});
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
		if(booknoPicAdapter != null){
			booknoPicAdapter.setCurrentPosition(currentPosition);
			booknoPicAdapter.notifyDataSetChanged();
		}		
		return true;
	}
	//设置listview短按监听
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		bookwithPicAdapter.setCurrentPosition(-1);
		bookwithPicAdapter.notifyDataSetChanged();
		if (booknoPicAdapter != null) {
			booknoPicAdapter.setCurrentPosition(-1);
			booknoPicAdapter.notifyDataSetChanged();
		}		
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
		case R.id.titlebar_iv_left:
			getActivity().finish();
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

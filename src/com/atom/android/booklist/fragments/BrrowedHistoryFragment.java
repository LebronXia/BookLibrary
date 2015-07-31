package com.atom.android.booklist.fragments;

import java.io.Serializable;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.atom.android.booklist.R;
import com.atom.android.booklist.R.string;
import com.atom.android.booklist.activity.BrrowedHistoryActivity2;
import com.atom.android.booklist.activity.SearchActivity;
import com.atom.android.booklist.adapts.BookWithoutPicsAdapter;
import com.atom.android.booklist.adapts.BookwithPicAdapter;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.config.URL;
import com.atom.android.booklist.contants.GCConstant;
import com.atom.android.booklist.interfaces.OnTaskListener;
import com.atom.android.booklist.utils.NetUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
public class BrrowedHistoryFragment extends Fragment implements OnClickListener,
	OnItemLongClickListener, OnItemClickListener{
	View view;
	//查询历史TextView
	private TextView tv_searchhistory;
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
	//历史借阅图书
	private List<BookSerachRe> historyBooks;
	//当前item位置
	private int currentPosition = -1;
	private ListView lv_borrowHistory;
	private RadioGroup mRadioGroup;
	private RadioButton orderByName_RB,orderByPublish_RB;
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final String TAG = "BrrowedHistoryFragment";
	public static final String EXTRA_HISTORYBOOKS =
			"com.atom.android.booklist.historybooks";

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		historyBooks = (List<BookSerachRe>) getArguments().getSerializable(EXTRA_HISTORYBOOKS);
		for(BookSerachRe book : historyBooks){
			Log.d(TAG, book.getBookName()+"----------");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.frag_borreturnhistory, container,false);
		initView();
		setData();
		initListener();
		return view;
	}
	
	private void setData() {
		// TODO Auto-generated method stub
		if(bookwithPicAdapter == null){
			bookwithPicAdapter = new BookwithPicAdapter(getActivity(),historyBooks);
			lv_borrowHistory.setAdapter(bookwithPicAdapter);
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
		// TODO Auto-generated method stub
				if(bookwithPicAdapter == null){
					bookwithPicAdapter = new BookwithPicAdapter(getActivity(),historyBooks);
					lv_borrowHistory.setAdapter(bookwithPicAdapter);
				} else {
					bookwithPicAdapter.notifyDataSetChanged();
				}
				if(booknoPicAdapter == null){
					booknoPicAdapter = new BookWithoutPicsAdapter(getActivity(),historyBooks);
					lv_borrowHistory.setAdapter(bookwithPicAdapter);
				} else {
					booknoPicAdapter.notifyDataSetChanged();
				}
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		tv_searchhistory = (TextView) view.findViewById(R.id.tv_searchhistory);
		lv_borrowHistory = (ListView) view.findViewById(R.id.lv_borrowhistory);
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		titlebar_iv_left = (ImageView) view.findViewById(R.id.titlebar_iv_left);
		listWithPics_IB = (ImageButton) view.findViewById(R.id.listWithPics_IB);
		listWithoutPics_IB = (ImageButton) view.findViewById(R.id.listWithoutPics_IB);
		mRadioGroup = (RadioGroup) view.findViewById(R.id.orderButtons_RG);
		orderByName_RB = (RadioButton) view.findViewById(R.id.orderByName_RB);
		orderByPublish_RB = (RadioButton) view.findViewById(R.id.orderByPublish_RB);
		orderByPublish_RB.setText(R.string.broowed_date);
		lv_borrowHistory.setOnItemClickListener(this);
		lv_borrowHistory.setOnItemLongClickListener(this);
		listWithPics_IB.setOnClickListener(this);
		listWithoutPics_IB.setOnClickListener(this);
		lv_borrowHistory.setOnItemClickListener(this);
		lv_borrowHistory.setOnItemLongClickListener(this);
		titlebar_tv.setText(R.string.borrow_history);
		titlebar_iv_right.setOnClickListener(this);
		titlebar_iv_left.setOnClickListener(this);
		tv_searchhistory.setOnClickListener(this);
		tv_searchhistory.setText(Html.fromHtml("<u>"+"查看历史"+"</u>"));
	}
	//初始化监听器
			private void initListener() {
				// TODO Auto-generated method stub
				//给RidaoFroup设置监听事件
						mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							@SuppressLint("ResourceAsColor") @Override
							public void onCheckedChanged(RadioGroup group, int checkedId) {
								// TODO Auto-generated method stub
								if(checkedId == orderByName_RB.getId()){
									//按照书名排序
									orderByName_RB.setBackgroundResource(R.color.background_green);
									orderByPublish_RB.setBackgroundResource(R.color.item_white);
									Collections.sort(historyBooks, new Comparator<BookSerachRe>() {

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
									//按照借出日排序
									orderByPublish_RB.setBackgroundResource(R.color.background_green);
									orderByName_RB.setBackgroundResource(R.color.item_white);
									Collections.sort(historyBooks, new Comparator<BookSerachRe>() {

										@Override
										public int compare(BookSerachRe lhs,
												BookSerachRe rhs) {
											// TODO Auto-generated method stub
											Date time1,time2;
											try {
												time1 = df.parse(lhs.getReturnDate());
												time2 = df.parse(rhs.getReturnDate());
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

	//activity传输数据给Fragment,不需要让Fragment直接关联activity
	public static BrrowedHistoryFragment newInstance(List<BookSerachRe> historyBooks){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_HISTORYBOOKS, (Serializable)historyBooks);		
		BrrowedHistoryFragment fragment = new BrrowedHistoryFragment();
		fragment.setArguments(args);
		return fragment;		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.listWithPics_IB:
			listWithPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_with_pics));
			listWithoutPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_without_pics_off));
			lv_borrowHistory.setAdapter(bookwithPicAdapter);
			bookwithPicAdapter.notifyDataSetChanged();
			break;
		case R.id.listWithoutPics_IB:
			listWithoutPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_without_pics));
			listWithPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_with_pics_off));
			booknoPicAdapter = new BookWithoutPicsAdapter(getActivity(),historyBooks);
			lv_borrowHistory.setAdapter(booknoPicAdapter);
	
			break;
		case R.id.tv_searchhistory:
			intent = new Intent(getActivity(),BrrowedHistoryActivity2.class);
			startActivity(intent);
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

}

package com.atom.android.booklist.fragments;

import java.io.Serializable;
import java.util.List;

import com.atom.android.booklist.R;
import com.atom.android.booklist.activity.SearchActivity;
import com.atom.android.booklist.adapts.BookWithoutPicsAdapter;
import com.atom.android.booklist.adapts.BookwithPicAdapter;
import com.atom.android.booklist.beans.BookSerachRe;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class OrderRecordFragment extends Fragment implements OnClickListener{
	private View view;
	private List<BookSerachRe> orderRecordlist;
	//查询图案结果的适配器
	private BookwithPicAdapter bookwithPicAdapter;
	//查询结果为文字结果
	private BookWithoutPicsAdapter booknoPicAdapter;
	//顶部标题栏的标题
	private TextView  titlebar_tv;
	//顶部标题栏右边的搜索
	private ImageView titlebar_iv_right;
	//按图片显示
	private ImageButton listWithPics_IB;
	//按文字显示
	private ImageButton listWithoutPics_IB;
	private ListView lv_orderRecord;
	private static final String TAG = "OrderRecordFragment";
	public static final String EXTRA_ORDERRECORD = 
			"com.atom.android.booklist.orderrecordlist";
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		orderRecordlist = (List<BookSerachRe>) getArguments().getSerializable(EXTRA_ORDERRECORD);
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fra_orderrecord, container,false);
		initView();
		setData();
		return view;
	}
	private void setData() {
		// TODO Auto-generated method stub
		if(bookwithPicAdapter == null){
			bookwithPicAdapter = new BookwithPicAdapter(getActivity(),orderRecordlist);
			lv_orderRecord.setAdapter(bookwithPicAdapter);
		} else {
			bookwithPicAdapter.notifyDataSetChanged();
		}	
	}
	private void initView() {
		// TODO Auto-generated method stub
		lv_orderRecord = (ListView) view.findViewById(R.id.lv_orderrecord);
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		listWithPics_IB = (ImageButton) view.findViewById(R.id.listWithPics_IB);
		listWithoutPics_IB = (ImageButton) view.findViewById(R.id.listWithoutPics_IB);
		listWithPics_IB.setOnClickListener(this);
		listWithoutPics_IB.setOnClickListener(this);
		titlebar_tv.setText(R.string.Order_history);
		titlebar_iv_right.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.listWithPics_IB:
			listWithPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_with_pics));
			listWithoutPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_without_pics_off));
			lv_orderRecord.setAdapter(bookwithPicAdapter);
			bookwithPicAdapter.notifyDataSetChanged();
			break;
		case R.id.listWithoutPics_IB:
			listWithoutPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_without_pics));
			listWithPics_IB.setImageDrawable(getResources().getDrawable(R.drawable.list_with_pics_off));
			booknoPicAdapter = new BookWithoutPicsAdapter(getActivity(),orderRecordlist);
			lv_orderRecord.setAdapter(booknoPicAdapter);
			break;
		case R.id.titlebar_iv_right:
			
			break;
		default:
			Intent intent = new Intent(getActivity(),SearchActivity.class);
			startActivity(intent);
			break;
		}		
	}
	
	//activity传输数据给Fragment,不需要让Fragment直接关联activity
	public static OrderRecordFragment newInstance(List<BookSerachRe> orderRecordlist){
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_ORDERRECORD, (Serializable) orderRecordlist);		
		OrderRecordFragment fragment = new OrderRecordFragment();
		fragment.setArguments(args);
		return fragment;		
	}

}

package com.atom.android.booklist.fragments;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class BrrowedHistoryFragment extends Fragment implements OnClickListener{
	View view;
	//��ѯ��ʷTextView
	private TextView tv_searchhistory;
	//��ѯͼ�������������
	private BookwithPicAdapter bookwithPicAdapter;
	//��ѯ���Ϊ���ֽ��
	private BookWithoutPicsAdapter booknoPicAdapter;
	//�����������ұߵĺ���
	private ImageView titlebar_iv_left;
	//�����������ı���
	private TextView  titlebar_tv;
	//�����������ұߵ�����
	private ImageView titlebar_iv_right;
	//��ͼƬ��ʾ
	private ImageButton listWithPics_IB;
	//��������ʾ
	private ImageButton listWithoutPics_IB;
	//��ʷ����ͼ��
	private List<BookSerachRe> historyBooks;
	private ListView lv_borrowHistory;
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
	
	private void initView() {
		// TODO Auto-generated method stub
		tv_searchhistory = (TextView) view.findViewById(R.id.tv_searchhistory);
		lv_borrowHistory = (ListView) view.findViewById(R.id.lv_borrowhistory);
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		titlebar_iv_left = (ImageView) view.findViewById(R.id.titlebar_iv_left);
		listWithPics_IB = (ImageButton) view.findViewById(R.id.listWithPics_IB);
		listWithoutPics_IB = (ImageButton) view.findViewById(R.id.listWithoutPics_IB);
		listWithPics_IB.setOnClickListener(this);
		listWithoutPics_IB.setOnClickListener(this);
		titlebar_tv.setText(R.string.borrow_history);
		titlebar_iv_right.setOnClickListener(this);
		titlebar_iv_left.setOnClickListener(this);
		tv_searchhistory.setOnClickListener(this);
		tv_searchhistory.setText(Html.fromHtml("<u>"+"�鿴��ʷ"+"</u>"));
	}

	//activity�������ݸ�Fragment,����Ҫ��Fragmentֱ�ӹ���activity
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

}
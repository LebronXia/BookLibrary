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
	//��ѯ����б�
	private ListView lv_searchresult;
	//��ѯͼ�������������
	private BookAdapter bookAdapter;
	private BookwithPicAdapter bookwithPicAdapter;
	//��ѯ���Ϊ���ֽ��
	private BookWithoutPicsAdapter booknoPicAdapter;
	//��ǰitemλ��
	private int currentPosition = -1;
	//�����������ı���
	private TextView  titlebar_tv;
	//�����������ұߵ�����
	private ImageView titlebar_iv_right;
	//��ͼƬ��ʾ
	private ImageButton listWithPics_IB;
	//��������ʾ
	private ImageButton listWithoutPics_IB;
	//��ѯ��Ľ��
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
	//��ʼ����ͼ
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
	//��������
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
	
	//��������ʱ����listview
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		updateNoteBookList();
		super.onResume();
	}

	private void updateNoteBookList() {
		if (bookwithPicAdapter != null) {
			setData();
			bookwithPicAdapter.notifyDataSetChanged(); //ͨ���˷�������ͬ־adapter�ĸ��£�������getView����
		}
		if (booknoPicAdapter != null) {
			setData();
			booknoPicAdapter.notifyDataSetChanged(); //ͨ���˷�������ͬ־adapter�ĸ��£�������getView����
		}
	}
	
	//��ʼ��������
	private void initListener() {
		// TODO Auto-generated method stub
		
	}
	//����listview��������
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		// TODO Auto-generated method stub
		Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		// ����������
		vibrator.vibrate(40);
		currentPosition = position;
		//ͨ���˷�������ͬ־adapter�ĸ��£�������getView����
		bookwithPicAdapter.setCurrentPosition(currentPosition);
		bookwithPicAdapter.notifyDataSetChanged();
		return true;
	}
	//����listview�̰�����
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		bookwithPicAdapter.setCurrentPosition(-1);
		bookwithPicAdapter.notifyDataSetChanged();
	}
	//�����л���ť����
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

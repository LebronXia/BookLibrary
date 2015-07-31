package com.atom.android.booklist.fragments;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import com.atom.android.booklist.R;
import com.atom.android.booklist.activity.SearchActivity;
import com.atom.android.booklist.adapts.BookWithoutPicsAdapter;
import com.atom.android.booklist.adapts.BookwithPicAdapter;
import com.atom.android.booklist.beans.BookSerachRe;
import android.view.View.OnClickListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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

public class ReadingFragment extends Fragment implements OnItemClickListener,
	OnItemLongClickListener,OnClickListener{
	private View view;
	private List<BookSerachRe> booksInReading;
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
	private RadioGroup mRadioGroup;
	private RadioButton orderByName_RB,orderByPublish_RB;
	private ListView lv_borrowedresult;
	//��ǰitemλ��
	private int currentPosition = -1;
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		initListener();
		return view;
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
	
	private void setData() {
		// TODO Auto-generated method stub
		if(bookwithPicAdapter == null){
			bookwithPicAdapter = new BookwithPicAdapter(getActivity(),booksInReading);
			lv_borrowedresult.setAdapter(bookwithPicAdapter);
		} else {
			bookwithPicAdapter.notifyDataSetChanged();
		}
		if(booknoPicAdapter == null){
			booknoPicAdapter = new BookWithoutPicsAdapter(getActivity(),booksInReading);
			lv_borrowedresult.setAdapter(bookwithPicAdapter);
		} else {
			booknoPicAdapter.notifyDataSetChanged();
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
		mRadioGroup = (RadioGroup) view.findViewById(R.id.orderButtons_RG);
		orderByName_RB = (RadioButton) view.findViewById(R.id.orderByName_RB);
		orderByPublish_RB = (RadioButton) view.findViewById(R.id.orderByPublish_RB);
		orderByPublish_RB.setText(R.string.broowed_date);
		lv_borrowedresult.setOnItemClickListener(this);
		lv_borrowedresult.setOnItemLongClickListener(this);
		listWithPics_IB.setOnClickListener(this);
		listWithoutPics_IB.setOnClickListener(this);
		titlebar_tv.setText(R.string.borrowed_book);
		titlebar_iv_right.setOnClickListener(this);
		titlebar_iv_left.setOnClickListener(this);
	}
	
	//��ʼ��������
		private void initListener() {
			// TODO Auto-generated method stub
			//��RidaoFroup���ü����¼�
					mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@SuppressLint("ResourceAsColor") @Override
						public void onCheckedChanged(RadioGroup group, int checkedId) {
							// TODO Auto-generated method stub
							if(checkedId == orderByName_RB.getId()){
								//������������
								orderByName_RB.setBackgroundResource(R.color.background_green);
								orderByPublish_RB.setBackgroundResource(R.color.item_white);
								Collections.sort(booksInReading, new Comparator<BookSerachRe>() {

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
								//���ս��������
								orderByPublish_RB.setBackgroundResource(R.color.background_green);
								orderByName_RB.setBackgroundResource(R.color.item_white);
								Collections.sort(booksInReading, new Comparator<BookSerachRe>() {

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
			if(booknoPicAdapter != null){
				booknoPicAdapter.setCurrentPosition(currentPosition);
				booknoPicAdapter.notifyDataSetChanged();
			}		
			return true;
		}
		//����listview�̰�����
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
	//activity�������ݸ�Fragment,����Ҫ��Fragmentֱ�ӹ���activity
		public static ReadingFragment newInstance(List<BookSerachRe> booksInReading){
			Bundle args = new Bundle();
			args.putSerializable(EXTRA_BorrowList, (Serializable) booksInReading);		
			ReadingFragment fragment = new ReadingFragment();
			fragment.setArguments(args);
			return fragment;		
		}
	
}

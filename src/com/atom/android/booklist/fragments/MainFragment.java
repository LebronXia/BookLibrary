package com.atom.android.booklist.fragments;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atom.android.booklist.R;
import com.atom.android.booklist.activity.BrrowedHistoryActivity;
import com.atom.android.booklist.activity.OrderRecordActivity;
import com.atom.android.booklist.activity.ReadingActivity;
import com.atom.android.booklist.activity.SearchActivity;
import com.atom.android.booklist.adapts.BookAdapter;
import com.atom.android.booklist.adapts.BookwithPicAdapter;
import com.atom.android.booklist.beans.BookInfo;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.beans.UserInfo;
import com.atom.android.booklist.config.URL;
import com.atom.android.booklist.contants.GCConstant;
import com.atom.android.booklist.database.DBManager;
import com.atom.android.booklist.interfaces.OnTaskListener;
import com.atom.android.booklist.utils.DateUtils;
import com.atom.android.booklist.utils.NetUtils;
import com.atom.android.booklist.view.TitleBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;



@SuppressLint("SimpleDateFormat") public class MainFragment extends Fragment implements OnTaskListener{
	
	private static final String TAG = "MainFragment";
	private View view;
	private ListView lv;
	private EditText etInput;
	private MainFragment m;
	private Context mContext;
	private DBManager dbManager;
	//���ڵ��������
	private int outdayCount = 0;
	//��ʷ���ĵ�����
	private int bookhistoryCount = 0;
	//��ǰ���ĵ�����
	private int currentBorrowing = 0;
	//һ������Ҫ�黹����
	private List<BookSerachRe> booksReturnInOneW = new ArrayList<BookSerachRe>();
	//��ǰ���ĵ���
	private List<BookSerachRe> booksInReading = new ArrayList<BookSerachRe>();
	//��ʷ����ͼ��
	private List<BookSerachRe> historyBooks = new ArrayList<BookSerachRe>();
	//ԤԼ��¼��ͼ��
	private List<BookSerachRe> OrderBooks = new ArrayList<BookSerachRe>();
	//�û�ID
	private int userId; 
	private UserInfo user;
	//�ĸ��ײ�������
	private TitleBar mTitleBar; 
	//�ײ���ǰ���ı�����
	private TitleBar mBorowedBookTitle;
	//ԤԼ��¼������
	private TitleBar mOrderRecordTitle;
	//�������ļ�¼������
	private TitleBar mBorrowedHistoryTitle;
	//�ҵ��ղر�����
	private TitleBar mCollectionTitle;
	//�û��˺�
	private TextView textViewAccount;
	//�����������
	private TextView textViewBookamount;
	//��������ڼ�¼
	private DateUtils dateUtils;
	private TextView textViewOverdue;
	private TextView tv_brrowed_book;
	private Button search_btn_back;
	public MainFragment(){
		m = this;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dbManager = new DBManager(getActivity());
		user = (UserInfo) getArguments().getSerializable(LoginFragment.EXTRA_USER);
		userId = user.getUserId();		
		inithttp();
			
	}
    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.frag_home, container, false);
		View headerView = inflater.inflate(R.layout.include_second, null);
		lv = (ListView) view.findViewById(R.id.lv_home);
		lv.addHeaderView(headerView);		
		initTitleBar();		//��ʼ���ײ�������
		initView();	
		//��ѯͼ�������������
		BookwithPicAdapter bookwithPicAdapter = new BookwithPicAdapter(getActivity(), booksReturnInOneW);
		lv.setAdapter(bookwithPicAdapter);		
		return view;		
	}
	private void initView() {
		// TODO Auto-generated method stub
		etInput = (EditText) view.findViewById(R.id.search_et_input);
		textViewAccount = (TextView) view.findViewById(R.id.textViewAccount);
		textViewBookamount = (TextView) view.findViewById(R.id.textViewBookamount);
		textViewOverdue = (TextView) view.findViewById(R.id.textViewOverdue);		
		tv_brrowed_book = (TextView) view.findViewById(R.id.tv_brrowed_book);
		search_btn_back = (Button) view.findViewById(R.id.search_btn_back);
		search_btn_back.setVisibility(View.GONE);
		textViewAccount.setText(user.getName());
		etInput.clearFocus();
		etInput.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),SearchActivity.class);
				intent.putExtra(LoginFragment.EXTRA_USER, user);
				startActivity(intent);
			}
		});
		 //���������
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		
		
	}
	private void initTitleBar() {
		// TODO Auto-generated method stub
		//��ǰ���ı�����
		mBorowedBookTitle = new TitleBar(getActivity());
		mBorowedBookTitle.setLogo(R.drawable.down);
		mBorowedBookTitle.setTitle(R.string.borrowed_book);
		mBorowedBookTitle.setAllTitle("��Android���Ȩ��ָ�ϡ�");		
		lv.addFooterView(mBorowedBookTitle);
		//ԤԼ��¼������
		mOrderRecordTitle = new TitleBar(getActivity());
		mOrderRecordTitle.setLogo(R.drawable.order);
		mOrderRecordTitle.setTitle(R.string.Order_history);
		mOrderRecordTitle.setBookNum(5);
		mOrderRecordTitle.setAllTitle("��Android���Ȩ��ָ�ϡ�");
		lv.addFooterView(mOrderRecordTitle);
		//�������ļ�¼������
		mBorrowedHistoryTitle = new TitleBar(getActivity());
		mBorrowedHistoryTitle.setLogo(R.drawable.borrowhistory);
		mBorrowedHistoryTitle.setTitle(R.string.borrow_history);
		mBorrowedHistoryTitle.setAllTitle("��Android���Ȩ��ָ�ϡ�");
		lv.addFooterView(mBorrowedHistoryTitle);
		
		//�ҵ��ղ�
		mCollectionTitle = new TitleBar(getActivity());
		mCollectionTitle.setLogo(R.drawable.order);
		mCollectionTitle.setTitle(R.string.my_favorite);
		mCollectionTitle.setAllTitle("��Android���Ȩ��ָ�ϡ�");
		lv.addFooterView(mCollectionTitle);
	}

	private void inithttp(){
	//��������
		NetUtils nu = new NetUtils();
		nu.setmOnTaskListener(m);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", ""+userId);
		try {
			nu.post(map,GCConstant.SHOW_BORROWEDBOOKS, URL.WebSite2 + "showBorrowedBooks");
			
			Log.d(TAG, "����������-----------");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
	}
	
	private void initlistenr() {
		// TODO Auto-generated method stub
		mBorowedBookTitle.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ReadingActivity.class);
				intent.putExtra(ReadingFragment.EXTRA_BorrowList, (Serializable)booksInReading);
				startActivity(intent);
			}
		});
		
		mOrderRecordTitle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), OrderRecordActivity.class);
				intent.putExtra(OrderRecordFragment.EXTRA_ORDERRECORD, (Serializable)OrderBooks);
				startActivity(intent);
				
			}
		});
		mBorrowedHistoryTitle.setOnClickListener(new OnClickListener() {					
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(), BrrowedHistoryActivity.class);
				intent.putExtra(BrrowedHistoryFragment.EXTRA_HISTORYBOOKS, (Serializable)historyBooks);
				startActivity(intent);
			}
		});
	}
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		dbManager.addUserInfo(user);
	}
	//activity�������ݸ�Fragment,����Ҫ��Fragmentֱ�ӹ���activity
	public static MainFragment newInstance(UserInfo user){
		Bundle args = new Bundle();
		args.putSerializable(LoginFragment.EXTRA_USER, user);		
		MainFragment fragment = new MainFragment();
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
		try {
			switch (taskflag) {
			case GCConstant.SHOW_BORROWEDBOOKS:
				deal(json);
				textViewBookamount.setText("" + bookhistoryCount);
				Log.d(TAG,"����ɹ�����---222222222222222-----");
				textViewOverdue.setText("" + outdayCount + "��");
				tv_brrowed_book.setText("һ������黹(" + booksReturnInOneW.size() + ")");
				mBorowedBookTitle.setBookNum(currentBorrowing);
				initlistenr();		

				break;
				
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

	private void deal(String json) {
		// TODO Auto-generated method stub
		try {
			JSONObject jo1 = new JSONObject(json);
			int resultId = jo1.getInt("resultId");
			if(resultId == 0){
				JSONObject jo2 = jo1.getJSONObject("obj"); 
				//�������ڼ�¼
				outdayCount = jo2.getInt("outdayCount");
				//������ǰ����������
				currentBorrowing = jo2.getInt("currentBorrowing");
				//������ʷ���������
				bookhistoryCount = jo2.getInt("bookhistoryCount");
				//�����������ļ�¼
				JSONArray jsonArray1 = jo2.getJSONArray("historyBooks");
				Log.d(TAG, jsonArray1+ "�������ļ�¼------------");
				for(int i = 0; i < jsonArray1.length(); i++){
					JSONObject jo3 = jsonArray1.getJSONObject(i);
					JSONObject jo6 = jo3.getJSONObject("book");
					BookSerachRe bookSerachRe = new BookSerachRe();
					bookSerachRe.setBookId(jo6.getInt("bookId"));
					Log.d(TAG, jo6.getInt("bookId") + "�������ļ�¼------------");
					bookSerachRe.setBookName(jo6.getString("bookName"));
					Log.d(TAG, jo6.getString("bookName")+"��ѯ�������ļ�¼-----------");					
					bookSerachRe.setWrite(jo6.getString("writer"));
					bookSerachRe.setBookPublish(jo6.getString("bookPublish"));
					bookSerachRe.setBookNumber(jo6.getString("bookNumber"));
					bookSerachRe.setBookImageUrl(jo6.getString("bookImage"));
					bookSerachRe.setFlag(jo3.getInt("flag"));
					bookSerachRe.setReturnDate(jo3.getString("returnDate"));
					bookSerachRe.setReturnCount(jo3.getInt("returnCount"));
					historyBooks.add(bookSerachRe);
				}
				//����һ������黹�����¼
				JSONArray jsonArray2 = jo2.getJSONArray("booksReturnInOneW");
				Log.d(TAG, jsonArray2+ "һ������黹�����¼------------");
				for(int i = 0; i < jsonArray2.length(); i++){
					Log.d(TAG, "һ������黹�����¼");
					JSONObject jo4 = jsonArray2.getJSONObject(i);
					JSONObject jo7 = jo4.getJSONObject("book");
					BookSerachRe bookSerachRe = new BookSerachRe();
					bookSerachRe.setBookId(jo7.getInt("bookId"));
					bookSerachRe.setBookName(jo7.getString("bookName"));
					bookSerachRe.setWrite(jo7.getString("writer"));
					bookSerachRe.setBookPublish(jo7.getString("bookPublish"));
					bookSerachRe.setBookNumber(jo7.getString("bookNumber"));
					bookSerachRe.setBookImageUrl(jo7.getString("bookImage"));
					bookSerachRe.setFlag(jo4.getInt("flag"));
					bookSerachRe.setReturnDate(jo4.getString("returnDate"));
					Log.d(TAG,jo4.getString("returnDate")+ "returnDate����ʱ��");
					bookSerachRe.setReturnCount(jo4.getInt("returnCount"));
					booksReturnInOneW.add(bookSerachRe);
				}
				//������ǰ����
				JSONArray jsonArray3 = jo2.getJSONArray("booksInReading");
				for(int i = 0; i <jsonArray3.length(); i++){
					JSONObject jo5 = jsonArray3.getJSONObject(i);
					JSONObject jo8 = jo5.getJSONObject("book");
					BookSerachRe bookSerachRe = new BookSerachRe();
					bookSerachRe.setBookId(jo8.getInt("bookId"));
					bookSerachRe.setBookName(jo8.getString("bookName"));
					bookSerachRe.setWrite(jo8.getString("writer"));
					bookSerachRe.setBookPublish(jo8.getString("bookPublish"));
					bookSerachRe.setBookNumber(jo8.getString("bookNumber"));
					bookSerachRe.setBookImageUrl(jo8.getString("bookImage"));
					bookSerachRe.setFlag(jo5.getInt("flag"));
					Log.d(TAG, bookSerachRe.getFlag() + "������ǰ���ı�־");
					bookSerachRe.setReturnDate(jo5.getString("returnDate"));
					bookSerachRe.setReturnCount(jo5.getInt("returnCount"));
					booksInReading.add(bookSerachRe);
				}		
				//ԤԼ��¼
				JSONArray jsonArray4 = jo2.getJSONArray("orderBookRecords");
				for(int i = 0; i < jsonArray4.length(); i++){
					Log.d(TAG, "������ԤԼ��----");
					JSONObject orderJsons = jsonArray4.getJSONObject(i);
					JSONObject orderBook = orderJsons.getJSONObject("book");
					BookSerachRe bookSerachRe = new BookSerachRe();
					bookSerachRe.setBookId(orderBook.getInt("bookId"));
					bookSerachRe.setBookName(orderBook.getString("bookName"));
					Log.d(TAG, "ԤԼ��¼===="+orderBook.getString("bookName"));
					bookSerachRe.setWrite(orderBook.getString("writer"));
					bookSerachRe.setBookPublish(orderBook.getString("bookPublish"));
					bookSerachRe.setBookNumber(orderBook.getString("bookNumber"));
					bookSerachRe.setBookImageUrl(orderBook.getString("bookImage"));
					bookSerachRe.setFlag(orderJsons.getInt("flag"));
					Log.d(TAG, bookSerachRe.getFlag() + "������ǰ���ı�־");
					bookSerachRe.setReturnDate(orderJsons.getString("returnDate"));
					bookSerachRe.setReturnCount(orderJsons.getInt("returnCount"));
					OrderBooks.add(bookSerachRe);
				}
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

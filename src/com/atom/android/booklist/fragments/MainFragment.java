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
	//超期的书的数量
	private int outdayCount = 0;
	//历史借阅的数量
	private int bookhistoryCount = 0;
	//当前借阅的数量
	private int currentBorrowing = 0;
	//一周内需要归还的书
	private List<BookSerachRe> booksReturnInOneW = new ArrayList<BookSerachRe>();
	//当前借阅的书
	private List<BookSerachRe> booksInReading = new ArrayList<BookSerachRe>();
	//历史借阅图书
	private List<BookSerachRe> historyBooks = new ArrayList<BookSerachRe>();
	//预约记录的图书
	private List<BookSerachRe> OrderBooks = new ArrayList<BookSerachRe>();
	//用户ID
	private int userId; 
	private UserInfo user;
	//四个底部标题栏
	private TitleBar mTitleBar; 
	//底部当前借阅标题栏
	private TitleBar mBorowedBookTitle;
	//预约记录标题栏
	private TitleBar mOrderRecordTitle;
	//以往借阅记录标题栏
	private TitleBar mBorrowedHistoryTitle;
	//我的收藏标题栏
	private TitleBar mCollectionTitle;
	//用户账号
	private TextView textViewAccount;
	//读的书的数量
	private TextView textViewBookamount;
	//读书的逾期记录
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
		initTitleBar();		//初始化底部标题栏
		initView();	
		//查询图案结果的适配器
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
		 //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
		
		
	}
	private void initTitleBar() {
		// TODO Auto-generated method stub
		//当前借阅标题栏
		mBorowedBookTitle = new TitleBar(getActivity());
		mBorowedBookTitle.setLogo(R.drawable.down);
		mBorowedBookTitle.setTitle(R.string.borrowed_book);
		mBorowedBookTitle.setAllTitle("《Android编程权威指南》");		
		lv.addFooterView(mBorowedBookTitle);
		//预约记录标题栏
		mOrderRecordTitle = new TitleBar(getActivity());
		mOrderRecordTitle.setLogo(R.drawable.order);
		mOrderRecordTitle.setTitle(R.string.Order_history);
		mOrderRecordTitle.setBookNum(5);
		mOrderRecordTitle.setAllTitle("《Android编程权威指南》");
		lv.addFooterView(mOrderRecordTitle);
		//以往借阅记录标题栏
		mBorrowedHistoryTitle = new TitleBar(getActivity());
		mBorrowedHistoryTitle.setLogo(R.drawable.borrowhistory);
		mBorrowedHistoryTitle.setTitle(R.string.borrow_history);
		mBorrowedHistoryTitle.setAllTitle("《Android编程权威指南》");
		lv.addFooterView(mBorrowedHistoryTitle);
		
		//我的收藏
		mCollectionTitle = new TitleBar(getActivity());
		mCollectionTitle.setLogo(R.drawable.order);
		mCollectionTitle.setTitle(R.string.my_favorite);
		mCollectionTitle.setAllTitle("《Android编程权威指南》");
		lv.addFooterView(mCollectionTitle);
	}

	private void inithttp(){
	//网络请求
		NetUtils nu = new NetUtils();
		nu.setmOnTaskListener(m);
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", ""+userId);
		try {
			nu.post(map,GCConstant.SHOW_BORROWEDBOOKS, URL.WebSite2 + "showBorrowedBooks");
			
			Log.d(TAG, "网络请求发送-----------");
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
	//activity传输数据给Fragment,不需要让Fragment直接关联activity
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
				Log.d(TAG,"请求成功进行---222222222222222-----");
				textViewOverdue.setText("" + outdayCount + "次");
				tv_brrowed_book.setText("一周内须归还(" + booksReturnInOneW.size() + ")");
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
				//解析逾期记录
				outdayCount = jo2.getInt("outdayCount");
				//解析当前借的书的数量
				currentBorrowing = jo2.getInt("currentBorrowing");
				//解析历史读书的数量
				bookhistoryCount = jo2.getInt("bookhistoryCount");
				//解析以往借阅记录
				JSONArray jsonArray1 = jo2.getJSONArray("historyBooks");
				Log.d(TAG, jsonArray1+ "以往借阅记录------------");
				for(int i = 0; i < jsonArray1.length(); i++){
					JSONObject jo3 = jsonArray1.getJSONObject(i);
					JSONObject jo6 = jo3.getJSONObject("book");
					BookSerachRe bookSerachRe = new BookSerachRe();
					bookSerachRe.setBookId(jo6.getInt("bookId"));
					Log.d(TAG, jo6.getInt("bookId") + "以往借阅记录------------");
					bookSerachRe.setBookName(jo6.getString("bookName"));
					Log.d(TAG, jo6.getString("bookName")+"查询以往借阅记录-----------");					
					bookSerachRe.setWrite(jo6.getString("writer"));
					bookSerachRe.setBookPublish(jo6.getString("bookPublish"));
					bookSerachRe.setBookNumber(jo6.getString("bookNumber"));
					bookSerachRe.setBookImageUrl(jo6.getString("bookImage"));
					bookSerachRe.setFlag(jo3.getInt("flag"));
					bookSerachRe.setReturnDate(jo3.getString("returnDate"));
					bookSerachRe.setReturnCount(jo3.getInt("returnCount"));
					historyBooks.add(bookSerachRe);
				}
				//解析一周内须归还的书记录
				JSONArray jsonArray2 = jo2.getJSONArray("booksReturnInOneW");
				Log.d(TAG, jsonArray2+ "一周内须归还的书记录------------");
				for(int i = 0; i < jsonArray2.length(); i++){
					Log.d(TAG, "一周内须归还的书记录");
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
					Log.d(TAG,jo4.getString("returnDate")+ "returnDate借阅时间");
					bookSerachRe.setReturnCount(jo4.getInt("returnCount"));
					booksReturnInOneW.add(bookSerachRe);
				}
				//解析当前借阅
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
					Log.d(TAG, bookSerachRe.getFlag() + "解析当前借阅标志");
					bookSerachRe.setReturnDate(jo5.getString("returnDate"));
					bookSerachRe.setReturnCount(jo5.getInt("returnCount"));
					booksInReading.add(bookSerachRe);
				}		
				//预约记录
				JSONArray jsonArray4 = jo2.getJSONArray("orderBookRecords");
				for(int i = 0; i < jsonArray4.length(); i++){
					Log.d(TAG, "我来到预约了----");
					JSONObject orderJsons = jsonArray4.getJSONObject(i);
					JSONObject orderBook = orderJsons.getJSONObject("book");
					BookSerachRe bookSerachRe = new BookSerachRe();
					bookSerachRe.setBookId(orderBook.getInt("bookId"));
					bookSerachRe.setBookName(orderBook.getString("bookName"));
					Log.d(TAG, "预约记录===="+orderBook.getString("bookName"));
					bookSerachRe.setWrite(orderBook.getString("writer"));
					bookSerachRe.setBookPublish(orderBook.getString("bookPublish"));
					bookSerachRe.setBookNumber(orderBook.getString("bookNumber"));
					bookSerachRe.setBookImageUrl(orderBook.getString("bookImage"));
					bookSerachRe.setFlag(orderJsons.getInt("flag"));
					Log.d(TAG, bookSerachRe.getFlag() + "解析当前借阅标志");
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

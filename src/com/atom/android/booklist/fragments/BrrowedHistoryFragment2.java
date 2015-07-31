package com.atom.android.booklist.fragments;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.atom.android.booklist.R;
import com.atom.android.booklist.R.string;
import com.atom.android.booklist.activity.BrrowedHistoryActivity;
import com.atom.android.booklist.activity.SearchActivity;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.beans.UserInfo;
import com.atom.android.booklist.config.URL;
import com.atom.android.booklist.contants.GCConstant;
import com.atom.android.booklist.database.DBManager;
import com.atom.android.booklist.interfaces.OnTaskListener;
import com.atom.android.booklist.utils.NetUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.AlteredCharSequence;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class BrrowedHistoryFragment2 extends Fragment implements OnTaskListener,OnClickListener{
	private View view;
	private DatePicker datePicker;//日期选择控件
	private AlertDialog alert;//对话框
	private RadioGroup mRadioGroup;
	private RadioButton mRadio1,mRadio2,mRadio3;
	private TextView tv_startdate,tv_enddate;
	private Button bt_searchbutton;
	//顶部标题栏右边的后腿
	private ImageView titlebar_iv_left;
	//顶部标题栏的标题
	private TextView  titlebar_tv;
	//顶部标题栏右边的搜索
	private ImageView titlebar_iv_right;
	private String mStartTime,mEndTime;
	//历史借阅图书
	private List<BookSerachRe> historyBooks = new ArrayList<BookSerachRe>();
	private DBManager dbManager;
	private UserInfo user;
	private BrrowedHistoryFragment2 fragment;
	private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	private static final String TAG = "BrrowedHistoryFragment2";
	public BrrowedHistoryFragment2(){
		fragment = this;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dbManager = new DBManager(getActivity());
		user = dbManager.queryUser();
		
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.frag_borreturnhistory2, container, false);		
		initView();	
		initData();
		return view;
	}

	private void initData() {
		// TODO Auto-generated method stub
		Date endTime = new Date();
		mEndTime = String.format("%tF", endTime);
		Calendar c = Calendar.getInstance();
		c.setTime(endTime);
		c.add(Calendar.MONTH, -6);
		Date startTime = c.getTime();
		mStartTime = String.format("%tF", startTime);
		Log.d(TAG, mEndTime + "&" + mStartTime +"点击了半年-------");
		tv_startdate.setText(mStartTime);
		tv_enddate.setText(mEndTime);
	}
	private void initView() {
		// TODO Auto-generated method stub
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		titlebar_iv_left = (ImageView) view.findViewById(R.id.titlebar_iv_left);
		mRadioGroup = (RadioGroup) view.findViewById(R.id.rg_historyrecord);
		mRadio1 = (RadioButton) view.findViewById(R.id.rb_halfyearhistory);
		mRadio2 = (RadioButton) view.findViewById(R.id.rb_allyearhistory);
		mRadio3 = (RadioButton) view.findViewById(R.id.rb_userdefined);
		tv_startdate = (TextView) view.findViewById(R.id.tv_startdate);
		tv_enddate = (TextView) view.findViewById(R.id.tv_enddate);	
		bt_searchbutton = (Button) view.findViewById(R.id.bt_searchbutton);
		titlebar_tv.setText(R.string.borrow_history);
		titlebar_iv_right.setOnClickListener(this);
		titlebar_iv_left.setOnClickListener(this);
		bt_searchbutton.setOnClickListener(this);
		//给RidaoFroup设置监听事件
		mRadioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				if(checkedId == mRadio1.getId()){
					initData();
				} else if (checkedId == mRadio2.getId()){
					Date endTime = new Date();
					mEndTime = String.format("%tF", endTime);
					Calendar c = Calendar.getInstance();
					c.setTime(endTime);
					c.add(Calendar.YEAR, -1);
					Date startTime = c.getTime();
					mStartTime = String.format("%tF", startTime);
					Log.d(TAG, mEndTime + "&" + mStartTime +"点击了全年查找-------");
					tv_startdate.setText(mStartTime);
					tv_enddate.setText(mEndTime);
				} else if (checkedId == mRadio3.getId()){
					Log.d(TAG, "点击了自定义按钮-----");
					initlistener();					
					Log.d(TAG, mStartTime + mEndTime + "点击按钮的时间---------");
				}
			}
		});
	}	
	
	private void initHttp() {
		// TODO Auto-generated method stub
		NetUtils nu = new NetUtils();
		nu.setmOnTaskListener(fragment);
		Map<String, String> map = new HashMap<String, String>();
		mStartTime = tv_startdate.getText().toString().trim();
		mEndTime = tv_enddate.getText().toString().trim();	
		map.put("userId", user.getUserId()+"");
		map.put("begin", mStartTime);
		map.put("end", mEndTime);
		try {
			nu.post(map, GCConstant.BRROW_RETURN_HISTORY, URL.WebSite2 + "borrowRecord");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private void initlistener(){		
		tv_startdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "点击时间控价"+"---------");
				//创建日期对话框
				creatalert(tv_startdate);
				alert.show();
				
			}
		});
		tv_enddate.setOnClickListener(new OnClickListener() {
						
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//创建日期对话框
					creatalert(tv_enddate);
					alert.show();
				}
			});
	}
	//创建日期对话框
	public void creatalert(final TextView mTextView){
		Builder builder = new AlertDialog.Builder(getActivity());
		View v = getActivity().getLayoutInflater()
				.inflate(R.layout.dialog_date, null);
		String date = mTextView.getText().toString().trim();
		Date sDate;
		try {
			sDate = df.parse(date);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(sDate);
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			datePicker = (DatePicker) v.findViewById(R.id.dialog_date_datePicker);
			datePicker.init(year, month, day, null);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		builder.setTitle("请选择日期");
		builder.setView(v);
		builder.setNegativeButton("取消", null);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String dialogDate = String.valueOf(datePicker.getYear()) + "-"
						+ String.valueOf(datePicker.getMonth() + 1) + "-"
						+ String.valueOf(datePicker.getDayOfMonth());
				mTextView.setText(dialogDate);
				//mTextView.setText(Html.fromHtml("<u>"+dialogDate+"</u>"));
			}
		});
		alert = builder.create();
	}

	@Override
	public void onLoading(int taskflag, long total, long current) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(Object obj, int taskflag) {
		// TODO Auto-generated method stub
		switch (taskflag) {
		case GCConstant.BRROW_RETURN_HISTORY:
			String json = obj.toString();
			deal1(json);
			Log.d(TAG, "网络传输成功--------");
			Intent intent = new Intent(getActivity(), BrrowedHistoryActivity.class);
			intent.putExtra(BrrowedHistoryFragment.EXTRA_HISTORYBOOKS, (Serializable)historyBooks);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	private void deal1(String json) {
		// TODO Auto-generated method stub
		try {
			JSONObject jo1 = new JSONObject(json);
			int resultId = jo1.getInt("resultId");
			if(resultId == 0){
				JSONObject jo2 = jo1.getJSONObject("obj");
				JSONArray jsonArray1 = jo2.getJSONArray("historys");
				for(int i = 0; i < jsonArray1.length(); i++){
					JSONObject jo3 = jsonArray1.getJSONObject(i);
					JSONObject jo4 = jo3.getJSONObject("book");
					BookSerachRe bookSerachRe = new BookSerachRe();
					bookSerachRe.setBookId(jo4.getInt("bookId"));
					Log.d(TAG, jo4.getInt("bookId") + "以往借阅记录------------");
					bookSerachRe.setBookName(jo4.getString("bookName"));									
					bookSerachRe.setWrite(jo4.getString("writer"));
					bookSerachRe.setBookPublish(jo4.getString("bookPublish"));
					bookSerachRe.setBookNumber(jo4.getString("bookNumber"));
					bookSerachRe.setBookImageUrl(jo4.getString("bookImage"));
					bookSerachRe.setFlag(1);
					bookSerachRe.setBorrowDate(jo3.getString("borrowDate"));
					bookSerachRe.setReturnDate(jo3.getString("retrnDate"));
					bookSerachRe.setReturnCount(jo3.getInt("brrowCount"));
					Log.d(TAG, jo3.getString("brrowCount")+"查询以往借阅记录-----------");	
					historyBooks.add(bookSerachRe);
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
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bt_searchbutton:
			Log.d(TAG, "你好------------");
			//查询以往借阅记录
			initHttp();
			break;
		case R.id.titlebar_iv_right:
			Intent intent = new Intent(getActivity(),SearchActivity.class);
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

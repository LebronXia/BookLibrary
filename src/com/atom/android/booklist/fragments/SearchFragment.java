package com.atom.android.booklist.fragments;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.atom.android.booklist.R;
import com.atom.android.booklist.activity.SearchResultActivity;
import com.atom.android.booklist.adapts.AutoCompleAdapter;
import com.atom.android.booklist.adapts.SearchHistoryAdapter;
import com.atom.android.booklist.beans.BookInfo;
import com.atom.android.booklist.beans.BookSerachRe;
import com.atom.android.booklist.beans.SearchHistory;
import com.atom.android.booklist.beans.UserInfo;
import com.atom.android.booklist.config.URL;
import com.atom.android.booklist.contants.GCConstant;
import com.atom.android.booklist.database.DBManager;
import com.atom.android.booklist.interfaces.OnTaskListener;
import com.atom.android.booklist.utils.FileSavaLoad;
import com.atom.android.booklist.utils.NetUtils;
import com.atom.android.booklist.view.SearchView;
import com.atom.android.booklist.view.SearchView.SearchViewListener;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class SearchFragment extends Fragment implements SearchViewListener
						,OnTaskListener{
	private SearchView searchView;
	//搜索历史列表
	private ListView lvsearchHistory;
	//搜索历史列表adapter
	private SearchHistoryAdapter shadapter;
	//搜索补全列表adapter
	private AutoCompleAdapter autoComoleAdapter;
	//搜索历史数据
	private List<String> searchHustoryData;
	//自动补全数据
	private List<String> autoCompleteData;
	//搜索过程数据
	private String antoword;
	private View view;
	//清除历史搜索layout
	private View headerView;
	//输入
	private EditText et_input;
	private RelativeLayout rl_clearrelativeLayout;
	//清理历史搜索按钮
	private Button clearhistory;
	//搜索结果的list
	private List<BookSerachRe> bookSerachReslist;
	private UserInfo user;
	private SearchFragment searchFragment;
	private DBManager dbManager;
	private static final String TAG = "SearchFragment";
    private static final String FILENAME = "searchhistory.txt";
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onCreate(savedInstanceState);
    	searchFragment = this;
    	dbManager = new DBManager(getActivity());
    	user = dbManager.queryUser();
    	Log.d(TAG, user.getName()+"用户的名字");
    	//user = (UserInfo) getActivity().getIntent().getSerializableExtra(LoginFragment.EXTRA_USER);   	
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.frag_search,container, false);
		headerView = inflater.inflate(R.layout.clearbutton_layout, null);
		initView();
		initData();
		return view;
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		lvsearchHistory = (ListView) view.findViewById(R.id.main_lv_search_results);
		searchView = (SearchView) view.findViewById(R.id.searchView_layout);
		et_input = (EditText) view.findViewById(R.id.search_et_input);
		rl_clearrelativeLayout = (RelativeLayout) view.findViewById(R.id.rl_clearrelativeLayout);
		clearhistory = (Button) headerView.findViewById(R.id.bt_clearhistory);
		lvsearchHistory.addHeaderView(headerView);
		searchView.setListView(lvsearchHistory);
		//设置监听
        searchView.setSearchViewListener(this);       
        searchView.setAutoCompleteAdapter(autoComoleAdapter);
        //清除历史数据
        clearhistory.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d(TAG, "点击了删除历史");
				File file = getActivity().getFilesDir();
				File dFile = new File(file + "/searchhistory.txt");
				 if (dFile.exists()) {
					 dFile.delete();
					 
             }
				 shadapter.notifyDataSetChanged();
				 lvsearchHistory.setVisibility(View.GONE);
			}
		});
        lvsearchHistory.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String text = lvsearchHistory.getAdapter().getItem(position).toString();
				et_input.setText(text);
				et_input.setSelection(text.length());
                //hint list view gone and result list view show
				lvsearchHistory.setVisibility(View.GONE);
			}
		});    
        
	}
	
	private void initData() {
		// TODO Auto-generated method stub
		getAutoCompleteData(null);
		List<String> shList = new ArrayList<String>();
		FileSavaLoad mFileSavaLoad = new FileSavaLoad(getActivity(), FILENAME);
		try {
			shList = mFileSavaLoad.load();
		} catch (Exception e) {
			shList = new ArrayList<String>();
			Log.d(TAG, "Error loading");
		}
		if(shList.isEmpty()){
			//rl_clearrelativeLayout.setVisibility(View.GONE);
		} else if (shadapter == null){
			shadapter = new SearchHistoryAdapter(getActivity(),shList);
			lvsearchHistory.setAdapter(shadapter);
			} else {
				shadapter.notifyDataSetChanged();
			}
				
	}

	//获取自动补全数据和adapter
	private void getAutoCompleteData(String text) {
		// TODO Auto-generated method stub
		if(autoCompleteData == null){
			autoCompleteData = new ArrayList<String>();
		} else {
			autoCompleteData.clear();
			autoCompleteData.add(text);
		}
		
		if(autoComoleAdapter == null){
			autoComoleAdapter = new AutoCompleAdapter(getActivity(), autoCompleteData);
		} else {
			autoComoleAdapter.setList(autoCompleteData);
			autoComoleAdapter.notifyDataSetChanged();
		} 			
			searchView.setAutoCompleteAdapter(autoComoleAdapter);	
	}
	/**
     * 当搜索框 文本改变时 触发的回调 ,更新自动补全数据
     * @param text
     */
    @Override
    public void onRefreshAutoComplete(String text) {
        //更新数据
        getAutoCompleteData(text);
    }

    /**
     * 点击搜索键时edit text触发的回调
     * @param text
     */
    @Override
    public void onSearch(String text) {
    	NetUtils nu = new NetUtils();
    	nu.setmOnTaskListener(searchFragment);
    	Map<String, String> map = new HashMap<String, String>();
    	map.put("userId", user.getUserId()+"");
    	map.put("searchContent", text);
    	try {
			nu.post(map, GCConstant.SEARCH_BOOKS, URL.WebSite2  +"search");
			Log.d(TAG, "查询书的请求发出----");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
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
			case GCConstant.SEARCH_BOOKS:
				Log.d(TAG, "成功接收请求");
				deal1(json);	
				if(bookSerachReslist.isEmpty()){
					Toast.makeText(getActivity(), "搜索不到结果", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "完成搜索", Toast.LENGTH_SHORT).show();
				}	
				break;
			default:
				break;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void deal1(String json) {
		// TODO Auto-generated method stub
		Log.d(TAG, "进入deal方法体内----------");
		try {
			JSONObject jo1 = new JSONObject(json);
			int resultId = jo1.getInt("resultId");			
			if(resultId == 0){				
				//键为obj
				JSONObject jo2 = jo1.getJSONObject("obj");
				//键为list
				JSONArray jsonArray1 = jo2.getJSONArray("list");
				bookSerachReslist = new ArrayList<BookSerachRe>();
				for(int i = 0; i < jsonArray1.length(); i++){
					JSONObject jo3 = jsonArray1.getJSONObject(i);
					JSONObject jo4 = jo3.getJSONObject("book");
					BookSerachRe bookSerachRe = new BookSerachRe();
					String returnDate = jo3.getString("returnDate");
					int returnCount = jo3.getInt("returnCount");
					int flag = jo3.getInt("flag");
					bookSerachRe.setUserId(jo3.getInt("userId"));
					bookSerachRe.setIfCollection(jo3.getInt("ifCollect"));
					bookSerachRe.setBookId(jo4.getInt("bookId"));
					bookSerachRe.setBookName(jo4.getString("bookName"));
					Log.d(TAG, jo4.getString("bookName") +"+"+ flag + "----0000------");
					bookSerachRe.setReturnCount(returnCount);
					bookSerachRe.setFlag(flag);
					bookSerachRe.setReturnDate(returnDate);
					bookSerachRe.setWrite(jo4.getString("writer"));
					bookSerachRe.setBookType(jo4.getString("bookType"));
					bookSerachRe.setBookNumber(jo4.getString("bookNumber"));
					bookSerachRe.setBookPublish(jo4.getString("bookPublish"));
					bookSerachRe.setBookPrice(jo4.getInt("bookPrice"));
					bookSerachRe.setBookImageUrl(jo4.getString("bookImage"));
					bookSerachRe.setBookPublishDate(jo4.getString("bookPublishDate"));
					Log.d(TAG, jo4.getString("bookPublishDate")+"出版日");
					bookSerachReslist.add(bookSerachRe);
					Log.d(TAG, "解析完毕-------");					
				}	
				Intent intent = new Intent(getActivity(), SearchResultActivity.class);
				intent.putExtra(SearchResultFragmnet.EXTRA_BOOK_SERCHRESULT,(Serializable)bookSerachReslist);
				startActivity(intent);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.d(TAG, "success出错了");
		}
		
	}

	@Override
	public void onFailure(Throwable throwable, String s, Object obj) {
		// TODO Auto-generated method stub
		
	}       
}

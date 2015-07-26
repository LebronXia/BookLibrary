package com.atom.android.booklist.view;

import java.util.ArrayList;
import java.util.List;

import com.atom.android.booklist.R;
import com.atom.android.booklist.adapts.AutoCompleAdapter;
import com.atom.android.booklist.utils.FileSavaLoad;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

public class SearchView extends LinearLayout implements View.OnClickListener{

	public SearchView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
        LayoutInflater.from(context).inflate(R.layout.include_search, this);
        initViews();
	}

	public SearchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mContext = context;
        LayoutInflater.from(context).inflate(R.layout.include_search, this);
        initViews();
	}
	public SearchView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mContext = context;
        LayoutInflater.from(context).inflate(R.layout.include_search, this);
        initViews();
	}
	/**
     * �����
     */
    private EditText etInput;

    /**
     * ɾ����
     */
    private ImageView ivDelete;

    /**
     * ���ذ�ť
     */
    private Button btnBack;

    /**
     * �����Ķ���
     */
    private Context mContext;

    /**
     * �����б�
     */
    private ListView lvTips;
    /**
     * �Զ���ȫadapter ֻ��ʾ����
     */
    private AutoCompleAdapter mAutoCompleteAdapter;
    /**
     * �����ص��ӿ�
     */
    private SearchViewListener mListener;
    /**
     * ���������ص��ӿ�
     *
     * @param listener ������
     */
    private ListView lvsearchHistory;
    private static final String TAG = "SearchView";
    private static final String FILENAME = "searchhistory.txt";
    public void setSearchViewListener(SearchViewListener listener) {
        mListener = listener;
    }
    
	private void initViews() {
		// TODO Auto-generated method stub
		etInput = (EditText) findViewById(R.id.search_et_input);
        ivDelete = (ImageView) findViewById(R.id.search_iv_delete);
        btnBack = (Button) findViewById(R.id.search_btn_back);
        lvTips = (ListView) findViewById(R.id.search_lv_tips);

        lvTips.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //����edit��ֵ
                String text = lvTips.getAdapter().getItem(i).toString();
                etInput.setText(text);
                etInput.setSelection(text.length());
                //����������¼               
                FileSavaLoad mFileSavaLoad = new FileSavaLoad(mContext, FILENAME);
                List<String> shList = new ArrayList<String>();
                try {
        			shList = mFileSavaLoad.load();
        		} catch (Exception e) {
        			shList = new ArrayList<String>();
        			Log.d(TAG, "Error loading");
        		}
                //�ж��Ƿ�ֵ��list�д���
                if(!shList.contains(text)){
                	try {
                    	mFileSavaLoad.save(text);
                    	Log.d(TAG, "searchhistory save to files");
    				} catch (Exception e) {
    					// TODO: handle exception
    					Log.e(TAG, "Error saving ");
    				}
                }                
                //����lvTips
                notifyStartSearching(text);
                lvTips.setVisibility(View.GONE);
                
            }

        });

        ivDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        etInput.setOnClickListener(this);
        lvTips.setAdapter(this.mAutoCompleteAdapter);        
        etInput.addTextChangedListener(new EditChangedListener());
        
	}

	/**
     * ֪ͨ������ ������������
     * @param text
     */
    private void notifyStartSearching(String text){
        if (mListener != null) {
            mListener.onSearch(etInput.getText().toString());
        }
    }
    /**
     * �����Զ���ȫadapter
     */
    public void setAutoCompleteAdapter(AutoCompleAdapter adapter) {
        this.mAutoCompleteAdapter = adapter;
        lvTips.setAdapter(this.mAutoCompleteAdapter); 
    }
    
    public void setListView(ListView listView){
    	this.lvsearchHistory = listView;
    }
   
    private class EditChangedListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (!"".equals(charSequence.toString())) {
            	lvsearchHistory.setVisibility(GONE);
                ivDelete.setVisibility(VISIBLE);
                lvTips.setVisibility(VISIBLE);
                
                if (mAutoCompleteAdapter != null && lvTips.getAdapter() != mAutoCompleteAdapter) {
                    lvTips.setAdapter(mAutoCompleteAdapter);
                }
                //����autoComplete����
                if (mListener != null) {
                    mListener.onRefreshAutoComplete(charSequence + "");
                }
            } else {
                ivDelete.setVisibility(GONE);
                lvTips.setVisibility(GONE);
                lvsearchHistory.setVisibility(VISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.search_et_input:
               // lvTips.setVisibility(VISIBLE);
                
                break;
            case R.id.search_iv_delete:
                etInput.setText("");
                ivDelete.setVisibility(GONE);
                lvsearchHistory.setVisibility(VISIBLE);
                break;
            case R.id.search_btn_back:
                ((Activity)mContext).finish();
                break;
        }
    }

    /**
     * search view�ص�����
     */
    public interface SearchViewListener {

        /**
         * �����Զ���ȫ����
         *
         * @param text ���벹ȫ����ı�
         */
        void onRefreshAutoComplete(String text);

        /**
         * ��ʼ����
         *
         * @param text �����������ı�
         */
        void onSearch(String text);

    }



}

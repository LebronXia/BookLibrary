package com.atom.android.booklist.fragments;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.atom.android.booklist.R;
import com.atom.android.booklist.beans.UserInfo;
import com.atom.android.booklist.config.URL;
import com.atom.android.booklist.contants.GCConstant;
import com.atom.android.booklist.database.DBManager;
import com.atom.android.booklist.interfaces.OnTaskListener;
import com.atom.android.booklist.utils.NetUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UpdatePasswordFragment extends Fragment implements OnTaskListener{
	private View view;
	private TextView tv_account;
	private EditText et_inputpassword;
	private EditText et_Secondpassword;
	private Button bt_sure;
	//顶部标题栏右边的后腿
	private ImageView titlebar_iv_left;
	//顶部标题栏的标题
	private TextView  titlebar_tv;
	//顶部标题栏右边的搜索
	private ImageView titlebar_iv_right;
	private UserInfo user;
	private DBManager dbManager;
	private UpdatePasswordFragment mUpdatePasswordFragment = this;
	private static final String TAG = "UpdatePasswordFragment";
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
		view = inflater.inflate(R.layout.frag_updatepw, container, false);
		initView();
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_account = (TextView) view.findViewById(R.id.tv_account);
		et_inputpassword = (EditText) view.findViewById(R.id.et_inputpassword);
		et_Secondpassword = (EditText) view.findViewById(R.id.et_Secondpassword);
		titlebar_iv_left = (ImageView) view.findViewById(R.id.titlebar_iv_left);
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		bt_sure = (Button) view.findViewById(R.id.bt_sure);
		titlebar_tv.setText("修改密码");
		titlebar_iv_right.setVisibility(View.GONE);
		tv_account.setText(user.getName());
		titlebar_iv_left.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getActivity().finish();
			}
		});
		bt_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String passWord = et_inputpassword.getText().toString().trim();
				String secondWord = et_Secondpassword.getText().toString().trim();
				NetUtils nu = new NetUtils();
				nu.setmOnTaskListener(mUpdatePasswordFragment);
				Map<String, String> map = new HashMap<String, String>();
				map.put("name", user.getName());
				map.put("newPsw", passWord);
				map.put("secondPsw", secondWord);
				try {
					nu.post(map, GCConstant.UPDATE_PASSWORD, URL.WebSite2  +"changePsw");
					Log.d(TAG, "启动----");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	@Override
	public void onLoading(int taskflag, long total, long current) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSuccess(Object obj, int taskflag) {
		// TODO Auto-generated method stub
		Log.d(TAG, "成功传输----");
		String json = obj.toString();
		switch (taskflag) {
		case GCConstant.UPDATE_PASSWORD:
			try {
				JSONObject jo1 = new JSONObject(json);
				int resultId = jo1.getInt("resultId");
				if(resultId == 0){
					String msg = jo1.getString("obj");
					Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
				} else {
					String msg = jo1.getString("obj");
					Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
	@Override
	public void onFailure(Throwable throwable, String s, Object obj) {
		// TODO Auto-generated method stub
		
	}
}

package com.atom.android.booklist.fragments;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.atom.android.booklist.R;
import com.atom.android.booklist.activity.UpdatePasswordActivity;
import com.atom.android.booklist.beans.UserInfo;
import com.atom.android.booklist.config.URL;
import com.atom.android.booklist.contants.GCConstant;
import com.atom.android.booklist.database.DBManager;
import com.atom.android.booklist.interfaces.OnTaskListener;
import com.atom.android.booklist.utils.NetUtils;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InstallFragment extends BaseFragment implements OnClickListener,OnTaskListener{
	private View view;
	private TextView tv_userCount;
	private TextView tv_userName;
	private TextView tv_telephone;
	private TextView tv_updatepw;
	private LinearLayout ll_logout;
	private InstallFragment mInstallFragment = this;
	//顶部标题栏右边的后腿
	private ImageView titlebar_iv_left;
	//顶部标题栏的标题
	private TextView  titlebar_tv;
	//顶部标题栏右边的搜索
	private ImageView titlebar_iv_right;
	private UserInfo user;
	private DBManager dbManager;
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
		view = inflater.inflate(R.layout.frag_install, container, false);
		initHttp();		
		titlebar_iv_left = (ImageView) view.findViewById(R.id.titlebar_iv_left);
		titlebar_tv = (TextView) view.findViewById(R.id.titlebar_tv);
		titlebar_iv_right = (ImageView) view.findViewById(R.id.titlebar_iv_right);
		titlebar_iv_right.setVisibility(View.GONE);
		titlebar_tv.setText("设置");
		titlebar_iv_left.setOnClickListener(this);
		return view;
	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_userCount = (TextView) view.findViewById(R.id.tv_userCount);
		tv_userName = (TextView) view.findViewById(R.id.tv_userName);
		tv_telephone = (TextView) view.findViewById(R.id.tv_telephone);
		ll_logout = (LinearLayout) view.findViewById(R.id.ll_logout);	
		tv_updatepw  = (TextView) view.findViewById(R.id.tv_updatepw);		
		tv_userCount.setText(user.getAccoount());
		tv_userName.setText(user.getName());
		tv_telephone.setText(user.getTelephone());
		tv_updatepw.setOnClickListener(this);
	}
	
	private void initHttp() {
		// TODO Auto-generated method stub
		NetUtils nu = new NetUtils();
		nu.setmOnTaskListener(mInstallFragment);
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", user.getName());
		try {
			nu.post(map, GCConstant.FIND_USERINFO, URL.WebSite2 + "find");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_updatepw:
			openActivity(UpdatePasswordActivity.class);
			break;
		case R.id.titlebar_iv_left:
			getActivity().finish();
			break;
		default:
			break;
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
		switch (taskflag) {
		case GCConstant.FIND_USERINFO:
			try {
				JSONObject jo1 = new JSONObject(json);
				int resultId = jo1.getInt("resultId");
				if(resultId == 0){
					user = new UserInfo();
					JSONObject jo2 = jo1.getJSONObject("obj");
					user.setAccoount(jo2.getString("account"));
					user.setName(jo2.getString("name"));
					user.setTelephone(jo2.getString("telephone"));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
		initView();
	}
	@Override
	public void onFailure(Throwable throwable, String s, Object obj) {
		// TODO Auto-generated method stub
		
	}


}

package com.atom.android.booklist.fragments;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.atom.android.booklist.R;
import com.atom.android.booklist.activity.MainActivity;
import com.atom.android.booklist.beans.UserInfo;
import com.atom.android.booklist.config.URL;
import com.atom.android.booklist.contants.GCConstant;
import com.atom.android.booklist.interfaces.OnTaskListener;
import com.atom.android.booklist.utils.NetUtils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginFragment extends Fragment implements OnTaskListener{
	public static final String EXTRA_USER =
			"com.atom.android.booklist.user";
	private final static String TAG = "LoginFragment";
	private AutoCompleteTextView cardNUmAuto;
	private EditText etPassword;
	private Button btLogin;
	private CheckBox savePassword;
	private SharedPreferences sp;
	private int userId;
	private LoginFragment m;
	private Context mContext;
	private String account;
	private String password;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "你好-------------");
		
	}
	public LoginFragment(Context pContext) {
		mContext = pContext;
		m = this;
    }
	

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.frag_login, container,false);
		initView(view);
		initOnclick();
		return view;
	}

	private void initView(View view) {
		// TODO Auto-generated method stub
		sp = getActivity().getSharedPreferences("passwordFile",
				0);
		cardNUmAuto = (AutoCompleteTextView) view.findViewById(R.id.account);
		etPassword = (EditText) view.findViewById(R.id.password);
		savePassword = (CheckBox) view.findViewById(R.id.rember_pw);
		savePassword.setChecked(true);//默认记住密码
		cardNUmAuto.setThreshold(1);//输入一个字母就开始提示
		etPassword.setInputType(InputType.TYPE_CLASS_TEXT
				| InputType.TYPE_TEXT_VARIATION_PASSWORD);
			//隐藏密码为InputType.TYPE_TEXT_VARIATION_PASSWORD 0X81
		btLogin = (Button) view.findViewById(R.id.login);
	}
	


	
	private void initOnclick() {
		// TODO Auto-generated method stub
		//设置输入账号监听
		cardNUmAuto.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String[] allUserName = new String[sp.getAll().size()];
				allUserName = sp.getAll().keySet().toArray(new String[0]);
				// sp.getAll()返回一张hash map
                // keySet()得到的是a set of the keys.
                // hash map是由key-value组成的
				
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						getActivity(), 
						android.R.layout.simple_dropdown_item_1line,
						allUserName);
				cardNUmAuto.setAdapter(adapter);//设置适配器
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				etPassword.setText(sp.getString(cardNUmAuto.getText()
						.toString(), ""));//自动输入密码
			}
		});
		btLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				account = cardNUmAuto.getText().toString();
			    password = etPassword.getText().toString();
				NetUtils nu = new NetUtils();
				nu.setmOnTaskListener(m);
				Map<String, String> map = new HashMap<String, String>();
				map.put("account", account);
				map.put("password", password);
				try {
					 nu.post(map, GCConstant.LOGIN_ID, URL.WebSite2 + "login");
					 Log.d(TAG, "success 发送----------");
				} catch (Exception e) {
					// TODO: handle exception
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
		
		String json = obj.toString();
		try {
			switch(taskflag){
			case GCConstant.LOGIN_ID:
				deal(json);
				break;}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	

	@Override
	public void onFailure(Throwable throwable, String s, Object obj) {
		// TODO Auto-generated method stub
		Log.d(TAG, "失败--------");
	}
	/**
	 *·解析返回来的json文件
	 * @param json
	 * @throws JSONException
	 */
	private void deal(String json) throws JSONException {
		// TODO Auto-generated method stub
		
		JSONObject jo = new JSONObject(json);
		int resultId = jo.getInt("resultId");
		JSONObject jo1 = jo.getJSONObject("obj");
		if(resultId == 0){
			Log.d(TAG, "成功跳转-----------");
			//当保存密码核对后，保存数据到本地文件
			if(savePassword.isChecked()){
				sp.edit().putString(account, password).commit();
			}
			String msg = jo1.getString("message");
			userId = jo1.getInt("result");
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
			//向MainActivity传送userid；
			Intent i = new Intent(getActivity(),MainActivity.class);
			UserInfo user = new UserInfo();
			user.setUserId(userId);
			user.setName(account);
			user.setPassword(password);
			i.putExtra(LoginFragment.EXTRA_USER, user);
			
			startActivity(i);
		} else {
			String msg = jo1.getString("message");
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
		}						
	}
}

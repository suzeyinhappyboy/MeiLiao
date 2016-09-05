package com.qq.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qq.R;
import com.qq.bean.ChatMsgEntity;
import com.qq.bean.ChatMsgViewAdapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ChatActivity extends Activity implements OnClickListener{

	private Button mBtnSend;
	private Button mBtnBack;
	private TextView mTextView;
	private EditText mEditTextContent;
	private ListView mListView;
	private String friendName;
	private String userName = "帅哥";
	private String send_content;
	private ChatMsgViewAdapter mAdapter;
	private List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_chat);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		Intent intent = getIntent();
		friendName = intent.getStringExtra("friendName");
		initView();
		initData();
	}
	
	public void initView() {
		mTextView = (TextView) findViewById(R.id.chat_name);
		mTextView.setText(friendName);
		mListView = (ListView) findViewById(R.id.listview);
		mBtnSend = (Button) findViewById(R.id.chat_send);
		mBtnSend.setOnClickListener(this);
		mBtnBack = (Button) findViewById(R.id.chat_back);
		mBtnBack.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.chat_editmessage);
	}
	
	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.chat_send:
			sendMsg();
			break;
		case R.id.chat_back:
			finish();
			break;	
		default:
			break;
		}
	}
	
	private void sendMsg() {
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0) {
			send_content = contString;
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setName(userName);
			entity.setMsgType(false);
			entity.setText(contString);
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm:ss");
			String sendTime = sdf.format(date);
			entity.setSendDate(sendTime);
			
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() -1);
			
		}
		
	}
	
	private String[] msgArray = new String[]{"你好帅啊！我好喜欢你啊"};
	
	public void initData() {
		ChatMsgEntity entity = new ChatMsgEntity();
		entity.setName(friendName);
		entity.setMsgType(true);
		entity.setText(msgArray[0]);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd kk:mm:ss");
		String sendTime = sdf.format(date);
		entity.setSendDate(sendTime);
		mDataArrays.add(entity);
		mAdapter = new ChatMsgViewAdapter(this,mDataArrays); 
		mListView.setAdapter(mAdapter);
	}
}

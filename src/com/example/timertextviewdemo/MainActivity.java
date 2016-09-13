package com.example.timertextviewdemo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;
/**
 * http://blog.csdn.net/flyxman
 * @author flyxman
 */

public class MainActivity extends Activity {
	private TimerTextView mTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy/MM/dd HH:mm:ss");
		
		//获得当前时间
		Date now = new Date();
		//目标时间
		Date target = null;
		try {
			
			target = dateFormat.parse("2016/07/29 23:37:00");
		} catch (ParseException e) {
			e.printStackTrace();
			
		}
		//获得时间差
		long diff = target.getTime() - now.getTime();
		mTextView = (TimerTextView) findViewById(R.id.timerTextView);
		//设置时间
		mTextView.setTimes(diff);
		
		/**
		 * 开始倒计时  开始倒计时
		 */
		if(!mTextView.isRun()){
			mTextView.start();
		}
	}
}

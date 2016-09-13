package com.example.timertextviewdemo;


import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TimerTextView extends ViewGroup implements Runnable {
	// 时间变量
	private int day, hour, minute, second;
	// 当前计时器是否运行
	private boolean isRun = false;
	
	//继承ViewGroup后添加的一些变量
	Context mContext;
	private int mTargetWidth;
	private int mBackground;                // TextView背景资源
	private int mTextSize = 14;             // 字体大小, 默认为20sp
	private int mVerticalPadding = 2;       // TextView上下内边距, 默认为2dp
	private int mHorizontalPadding = 2;     // TextView左右内边距, 默认为2dp
	private int mViewSpace = 0;
	private TextView lefTextView;//左边textView，代表时
	private TextView middleTextView;//中间textView，代表分
	private TextView rightTextView;//右边textView，代表秒
	private TextView leftMaoTV;//左边冒号
	private TextView rightMaoTV;//右边冒号

	public TimerTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		if (getChildCount() > 0) {
            throw new RuntimeException("IncreaseReduceTextView不允许有子元素.");
        }
		this.mContext = context;
		// 读取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TimerTextView);
        mBackground = ta.getResourceId(R.styleable.TimerTextView_textBackground, /*R.drawable.ic_launcher*/0);
        mTextSize = ta.getDimensionPixelSize(R.styleable.TimerTextView_textSize,
                DensityUtil.dip2px(context, mTextSize));
        mVerticalPadding = ta.getDimensionPixelSize(R.styleable
                .TimerTextView_verticalPadding, DensityUtil.dip2px(context,
                mVerticalPadding));
        mHorizontalPadding = ta.getDimensionPixelSize(R.styleable
                .TimerTextView_horizontalPadding, DensityUtil.dip2px(context,
                mHorizontalPadding));
        mViewSpace = ta.getDimensionPixelSize(R.styleable.TimerTextView_viewSpace,
                mViewSpace);
        ta.recycle();//记得回收
      //该画笔的设置，只是为了测量在mTextSize前提下，0000的宽度，并将获取到的宽度给设置到onMeasure中去。
        Paint paint = new Paint();
        paint.setTextSize(mTextSize);
        mTargetWidth = (int) paint.measureText("0000");//获取到一个宽度，且是在字体大小为mTextSize的前提下的宽度
        initializeView();
	}

	public TimerTextView(Context context, AttributeSet attrs) {
//		super(context, attrs);
		this(context, attrs, 0);
	}

	public TimerTextView(Context context) {
//		super(context);
		this(context, null);
	}
	
	

	/**
	 * 将倒计时时间毫秒数转换为自身变量
	 * 
	 * @param time
	 *            时间间隔毫秒数  时间间隔毫秒数
	 */
	public void setTimes(long time) {
		//将毫秒数转化为时间
				this.second = (int) (time / 1000) % 60;
				this.minute = (int) (time / (60 * 1000) % 60);
				this.hour = (int) (time / (60 * 60 * 1000) % 24);
				this.day = (int) (time / (24 * 60 * 60 * 1000));
	}

	/**
	 * 显示当前时间
	 * 
	 * @return
	 */
	public String showTime() {
		StringBuilder time = new StringBuilder();
		time.append(day);
		time.append("天");
		time.append(hour);
		time.append("小时");
		time.append(minute);
		time.append("分钟");
		time.append(second);
		time.append("秒");
		System.out.println("--showTime:001");
		
		lefTextView.setText(hour);
		leftMaoTV.setText(":");
		middleTextView.setText(minute);
		rightMaoTV.setText(":");
		rightTextView.setText(second);
		return time.toString();
	}

	/**
	 * 实现倒计时
	 */
	private void countdown() {
		if (second == 0) {
			if (minute == 0) {
				if (hour == 0) {
					if (day == 0) {
						//当时间归零时停止倒计时
						isRun = false;
						return;
					} else {
						day--;
					}
					hour = 23;
				} else {
					hour--;
				}
				minute = 59;
			} else {
				minute--;
			}
			second = 60;
		}

		second--;
	}

	public boolean isRun() {
		return isRun;
	}

	/**
	 * 开始计时
	 */
	public void start() {
		isRun = true;
		run();
	}

	/**
	 * 结束计时
	 */
	public void stop() {
		isRun = false;
	}

	/**
	 * 实现计时循环
	 */
	@Override
	public void run() {
		if (isRun) {
			// Log.d(TAG, "Run");
			countdown();
			System.out.println("--diff:xunhuan");
			//下面一句是设置时间的最终方式
//			this.setText(showTime());
			//通过自定义ViewGroup方式来设置时间的显示
			showTime();
			postDelayed(this, 1000);
			
		} else {
			removeCallbacks(this);
		}
	}
	
	// 初始化视图
    private void initializeView() {
    	// 11 : 22 : 33
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        System.out.println("--showTime:002");
        lefTextView = new TextView(mContext);
        lefTextView.setLayoutParams(params);
        lefTextView.setBackgroundResource(mBackground);
//        lefTextView.setBackgroundColor(Color.parseColor("#000000"));
//        lefTextView.setTextColor(Color.parseColor("#ffffff"));
//        lefTextView.setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding,mVerticalPadding);
        lefTextView.setGravity(Gravity.CENTER);
        addView(lefTextView);
        
        leftMaoTV = new TextView(mContext);
        leftMaoTV.setLayoutParams(params);
        leftMaoTV.setBackgroundResource(mBackground);
//        leftMaoTV.setBackgroundColor(Color.parseColor("#ffffff"));
//        leftMaoTV.setTextColor(Color.parseColor("#ffffff"));
//        leftMaoTV.setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding,mVerticalPadding);
        leftMaoTV.setGravity(Gravity.CENTER);
        addView(leftMaoTV);
        
        middleTextView = new TextView(mContext);
        middleTextView.setLayoutParams(params);
        middleTextView.setBackgroundResource(mBackground);
//        middleTextView.setBackgroundColor(Color.parseColor("#000000"));
//        middleTextView.setTextColor(Color.parseColor("#ffffff"));
//        middleTextView.setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding,mVerticalPadding);
        middleTextView.setGravity(Gravity.CENTER);
        addView(middleTextView);
        
        rightMaoTV = new TextView(mContext);
        rightMaoTV.setLayoutParams(params);
        rightMaoTV.setBackgroundResource(mBackground);
//        rightMaoTV.setBackgroundColor(Color.parseColor("#ffffff"));
//        rightMaoTV.setTextColor(Color.parseColor("#ffffff"));
//        rightMaoTV.setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding,mVerticalPadding);
        rightMaoTV.setGravity(Gravity.CENTER);
        addView(rightMaoTV);
        
        rightTextView = new TextView(mContext);
        rightTextView.setLayoutParams(params);
        rightTextView.setBackgroundResource(mBackground);
//        rightTextView.setBackgroundColor(Color.parseColor("#000000"));
//        rightTextView.setTextColor(Color.parseColor("#ffffff"));
//        rightTextView.setPadding(mHorizontalPadding, mVerticalPadding, mHorizontalPadding,mVerticalPadding);
        rightTextView.setGravity(Gravity.CENTER);
        addView(rightTextView);

//        numberChanged();
    }

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		measureChildren(widthMeasureSpec, heightMeasureSpec);
		
		int widthSum ;   // 总宽度, 最终结果为自定义组件的宽度
		int childCount = getChildCount();
		View firstChild = getChildAt(0);
		int childWidth = firstChild.getMeasuredWidth();
		int childHeight = firstChild.getMeasuredHeight();
		widthSum = childWidth + mTargetWidth;
		firstChild.measure(MeasureSpec.makeMeasureSpec(childWidth + mTargetWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.AT_MOST));
		
		for(int x=1;x<childCount;x++){
			View secondView = getChildAt(x);
			int secondWidth = secondView.getMeasuredWidth();
			int secondHeight = secondView.getMeasuredHeight();
			secondView.measure(MeasureSpec.makeMeasureSpec(secondWidth, MeasureSpec.AT_MOST), MeasureSpec.makeMeasureSpec(secondHeight, MeasureSpec.AT_MOST));
			widthSum += (secondWidth +mTargetWidth);
		}
		setMeasuredDimension(widthSum + mViewSpace * 4, childHeight);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		int childCount = getChildCount();
		int left = 0;
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            childView.layout(left, 0, left + childWidth, childHeight);
            left += childWidth;
            if (i != childCount - 1) {
                left += mViewSpace;
            }
        }
	}
}

package com.itheima.quickindex.ui;

import com.itheima.quickindex.MainActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 快速索引
 * @author poplar
 *
 */
public class QuickIndexBar extends View {
	
	private static final String[] LETTERS = new String[] { 
		"A", "B", "C", "D",
		"E", "F", "G", "H", 
		"I", "J", "K", "L", 
		"M", "N", "O", "P", 
		"Q", "R", "S", "T", 
		"U", "V", "W", "X", 
		"Y", "Z" };

	private Paint paint;

	private int mHeight;

	private float cellHeight; // 单元格高度

	private int cellWidth; // 单元格宽度

	/**
	 * java new
	 * @param context
	 */
	public QuickIndexBar(Context context) {
		this(context, null);
	}

	/**
	 * xml 创建
	 * @param context
	 * @param attrs
	 */
	public QuickIndexBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * xml 创建 , 并且配置style
	 * @param context
	 * @param attrs
	 * @param defStyle
	 */
	public QuickIndexBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		paint = new Paint();
		paint.setFlags(Paint.ANTI_ALIAS_FLAG); // 设置抗锯齿
		paint.setColor(Color.WHITE);// 设置画笔为白色
		paint.setTypeface(Typeface.DEFAULT_BOLD);// 字体加粗
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// 绘制字母A-Z
		
		for (int i = 0; i < LETTERS.length; i++) {
			String letter = LETTERS[i];
			// 计算每个字母的x, y值
			float x = cellWidth * 0.5f - paint.measureText(letter) * 0.5f;
			
			// 获取指定字符串指定区域的宽高信息
			Rect bounds = new Rect();
			paint.getTextBounds(letter, 0, letter.length(), bounds);
			int textHeight = bounds.height();
			
			// 求出y坐标值
			float y = cellHeight * 0.5f + textHeight * 0.5f + i * cellHeight;
			
			// 绘制字母到画板
			canvas.drawText(letter, x, y, paint);
			
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		
		mHeight = getMeasuredHeight();
		cellHeight = mHeight * 1.0f / LETTERS.length;
		
		cellWidth = getMeasuredWidth();
	}

	int currentIndex = -1; // 被选中的字母索引
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int index = -1;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 当前y / 单元格高度 = 索引
			index = (int) (event.getY() / cellHeight);
			
			// 当前索引和刚刚的索引不同
			if(index != currentIndex){
				// 索引在正确范围内
				if(index >= 0 && index < LETTERS.length){ // [0, 25]
//					System.out.println("index: " + index + ":" + LETTERS[index]);
					if(onLetterUpdateListener != null){
						onLetterUpdateListener.onLetterUpdate(LETTERS[index]);
					}
					currentIndex = index; //更新索引
				}
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			index = (int) (event.getY() / cellHeight);
			
			// 当前索引和刚刚的索引不同
			if(index != currentIndex){
				// 索引在正确范围内
				if(index >= 0 && index < LETTERS.length){ // [0, 25]
//					System.out.println("index: " + index + ":" + LETTERS[index]);
					if(onLetterUpdateListener != null){
						onLetterUpdateListener.onLetterUpdate(LETTERS[index]);
					}
					currentIndex = index; //更新索引
				}
			}
			
			break;
		case MotionEvent.ACTION_UP:
			currentIndex = -1;//恢复初始值
			break;

		default:
			break;
		}
		
		return true; // 消费事件
	}
	
	OnLetterUpdateListener onLetterUpdateListener;
	public void setOnLetterUpdateListener(
			OnLetterUpdateListener onLetterUpdateListener) {
		this.onLetterUpdateListener = onLetterUpdateListener;
	}

	// 声明接口
	public interface OnLetterUpdateListener{
		// 字母更新了
		void onLetterUpdate(String letter);
	}
	
}

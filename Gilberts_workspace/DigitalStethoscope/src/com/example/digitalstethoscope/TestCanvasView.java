package com.example.digitalstethoscope;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class TestCanvasView extends View {
	private Paint picture;
	private int[] colors;
	private int[] rectCoord;
	
	private Bitmap cache;
	
	public TestCanvasView(Context con) {
		super(con);
		
		initialize();
	}
	
	public TestCanvasView(Context con, AttributeSet attr) {
		super(con, attr);
		
		initialize();
	}
	
	public TestCanvasView(Context con, AttributeSet attr, int defStyleAttr) {
		super(con, attr, defStyleAttr);
		
		initialize();
	}
	
	private void initialize() {
		picture = new Paint();
	}
	
	public void setPictureData(int screenWidth, int screenHeight, int[] rectCoord, int[] colors) {
		this.rectCoord = rectCoord;
		this.colors = colors;
		
		cache = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ARGB_8888);
	}
	
	public void forceDraw() {
		Canvas canvas = new Canvas(cache);
		
		for(int i = 0; i < colors.length
				&& i*4 + 3 < rectCoord.length; i++) {
			picture.setColor(colors[i]);
			picture.setStyle(Paint.Style.FILL);
			canvas.drawRect(rectCoord[i*4], rectCoord[i*4 + 1], rectCoord[i*4 + 2], rectCoord[i*4 + 3], picture);
		}
		
		postInvalidate();
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(cache != null) {
			canvas.drawBitmap(cache, 0, 0, picture);
		}
	}
}

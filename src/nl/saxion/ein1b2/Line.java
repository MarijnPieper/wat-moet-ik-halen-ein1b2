package nl.saxion.ein1b2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class Line extends View {
	
	public Line(Context context, AttributeSet attrs) {
		super(context, attrs);		
	}
	
	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		Paint paint =new Paint();
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(5);
//		LinearGradient gradient = new LinearGradient(0, 25, 0, 50, Color.rgb(255, 100, 100), Color.rgb(255, 0, 0), Shader.TileMode.MIRROR);
//		paint.setShader(gradient);
		paint.setAntiAlias(true);
		canvas.drawLine(0,0,1000,0, paint);
		
		
	}

}

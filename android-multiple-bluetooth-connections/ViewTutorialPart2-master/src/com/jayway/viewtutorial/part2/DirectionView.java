package com.jayway.viewtutorial.part2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.view.*;
import android.util.AttributeSet;
import android.util.Log;
   
public class DirectionView extends View {

  // Paints used to draw the Compass
  private Paint markerPaint;
  private Paint textPaint;
  private Paint circlePaint;
  private Paint ReadingPaint;
  
  // Cardinal point Strings
  private String northString;
  private String eastString;
  private String southString;
  private String westString;
  
  // Height of text
  private int textHeight;
  private float bearing;
  private float airflowSpeed;
  
  
  /** Get or set the bearing displayed by the compass **/  
  public void setBearing(float _bearing,float _speed) {
	  this.bearing = _bearing;
	  this.airflowSpeed=_speed;
	  invalidate();
   
  }
  public float getBearing() {
    return bearing;
  }

	
  /** Constructors **/
  public DirectionView(Context context) {
    super(context);
    initCompassView();
  }   

  public DirectionView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initCompassView();
  }

  public DirectionView(Context context, AttributeSet attrs, int defaultStyle) {
    super(context, attrs, defaultStyle);
    initCompassView();
  }

  /** Initialize the Class variables **/
  protected void initCompassView() {
    setFocusable(true);

    // Get a reference to the external resources
    Resources r = this.getResources();
    northString = r.getString(R.string.cardinal_north);
    eastString = r.getString(R.string.cardinal_east);
    southString = r.getString(R.string.cardinal_south);
    westString = r.getString(R.string.cardinal_west);

    // Create the paints
    circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    circlePaint.setColor(r.getColor(R.color.background_color));
    circlePaint.setStrokeWidth(4);
    circlePaint.setStyle(Paint.Style.FILL_AND_STROKE);

    markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    markerPaint.setColor(r.getColor(R.color.marker_color));
    markerPaint.setStrokeWidth(10);
    
    textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    textPaint.setColor(r.getColor(R.color.text_color));
    textPaint.setStrokeWidth(10);
    
    ReadingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    ReadingPaint.setColor(r.getColor(R.color.text_color));
    ReadingPaint.setStrokeWidth(10);
    ReadingPaint.setTextSize(28);
    
    textHeight = (int)textPaint.measureText("yY");
  }
  
  @Override    
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 
    // The compass is a circle that fills as much space as possible.
    // Set the measured dimensions by figuring out the shortest boundary,
    // height or width.
    int measuredWidth = measure(widthMeasureSpec);
    int measuredHeight = measure(heightMeasureSpec);
        
    int d = Math.min(measuredWidth, measuredHeight+30);
        
    setMeasuredDimension(measuredWidth, d);    
  }
      
  private int measure(int measureSpec) {
    int result = 0; 

    // Decode the measurement specifications.
    int specMode = MeasureSpec.getMode(measureSpec);
    int specSize = MeasureSpec.getSize(measureSpec); 

    if (specMode == MeasureSpec.UNSPECIFIED) {
      // Return a default size of 200 if no bounds are specified. 
      result = 200;
    } else {
      // As you want to fill the available space
      // always return the full available bounds.
      result = specSize;
    } 
    return result;
  }

  @Override 
  protected void onDraw(Canvas canvas) {
    int px = getMeasuredWidth() / 2;
    int py = getMeasuredHeight() / 2;

    int radius = Math.min(px, py)-30;
    
    // Draw the background
    canvas.drawCircle(px, py, radius, circlePaint);
    
    // Rotate our perspective so that the 'top' is facing the current bearing.
    canvas.save();
   // canvas.rotate(-bearing, px, py); 
    int textWidth = (int)textPaint.measureText("W");
    int cardinalX = px-textWidth/2;
    int cardinalY = py-radius+textHeight;
        
    // Draw the marker every 15 degrees and a text every 45.
    for (int i = 0; i < 24; i++) {
      // Draw a marker.
      canvas.drawLine(px, py-radius, px, py-radius+10, markerPaint);
      
      canvas.save();
      canvas.translate(0, textHeight);

      // Draw the cardinal points
      if (i % 6 == 0) {
        String dirString = "";
        switch (i) {
          case(0)  : {
                       dirString = northString;
                      // int arrowY = 2*textHeight;
                       //canvas.drawLine(px, arrowY, px-5, 3*textHeight, markerPaint);
                       //canvas.drawLine(px, arrowY, px+5, 3*textHeight, markerPaint);
                       break;
                     }
          case(6)  : dirString = eastString; break;
          case(12) : dirString = southString; break;
          case(18) : dirString = westString; break;
        }
        
        canvas.drawText(dirString, cardinalX, cardinalY, textPaint);
      } 

      else if (i % 3 == 0) {
        // Draw the text every alternate 45deg
        String angle = String.valueOf(i*15);
        float angleTextWidth = textPaint.measureText(angle);

        int angleTextX = (int)(px-angleTextWidth/2);
        int angleTextY = py-radius+textHeight;
        canvas.drawText(angle, angleTextX, angleTextY, textPaint);
      }
      canvas.restore();
      canvas.rotate(15, px, py);
    }
    
     canvas.rotate(-bearing, px, py);
     int arrowY = 6*textHeight;
    canvas.drawLine(px, arrowY, px-5, 3*textHeight, markerPaint);
    canvas.drawLine(px, arrowY, px+5, 3*textHeight, markerPaint);

    canvas.restore();
    ReadingPaint.setTextSize(75);
    canvas.drawText( Float.toString((float) airflowSpeed), px-95 , py+40, ReadingPaint);
    ReadingPaint.setTextSize(45);
    canvas.drawText( "m/s" , px+15 , py+40, ReadingPaint);
    
  }
}
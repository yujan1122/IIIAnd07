package tw.org.iii.iiiand07;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.LinkedList;

//user能互動, 功能要能運作

public class MyView extends View { //建構式
    //private LinkedList<Point> line; //變數資料結構
    private LinkedList<LinkedList<Point>> lines, recycler; //線集合


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.BLUE);

        //line = new LinkedList<>();
        lines = new LinkedList<>();
        recycler = new LinkedList<>();

        /*
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("brad", "onClick");//初始化,寫在建構式
            }
        });
        */
    }

    //版面配置的所有東西,都繼承view, 皆可以被摸
    //實驗一下return與onClick之間關係,設計方式與使用者習慣
    @Override
    public boolean onTouchEvent(MotionEvent event) { //回傳boolean; event系統傳遞給的
        float ex =  event.getX(), ey = event.getY();
        Point point = new Point(ex, ey);
        //如何說明是一條新線? down觸發
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            LinkedList<Point> line = new LinkedList<Point>() ; //擁有一條新線, 記得括號
            lines.add(line);
        }

        lines.getLast().add(point);//把最後一點作為線段結束
        //line.add(point);
        // Java => repaint
        // On開頭是被觸發
        invalidate(); //android方法

        /*
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            Log.v("brad", "actionDown");
        }else if(event.getAction()== MotionEvent.ACTION_UP){
            Log.v("brad", "actionUP");
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            Log.v("brad", "actionMove");
        }
         */


        //蒐集有順序性的座標點, 蒐集幾點不知道, 陣列不適合==>List(有順序性可重複) or Set(東西進去保證不重複)
        //java array內容具順序性, 但限制 1.型別固定  2.宣告長度後就固定; 非缺點,要用到對的地方
        //骰子(陣列1-6用陣列)
        //一點(一座標點),一直線, Obj O導向去看; 物件方法都一樣,主要差異是屬性

        //Log.v("brad", ex + "x" + ey);
        //Log.v("brad", "onTouch");
        //return super.onTouchEvent(event);//--影響執行onClick;
        return true; //是否還有觸發其他?未知; 回傳true,讓程式單純化
    }

    //override & on開頭, canvas畫布物件,不用呼叫自己會去呈現
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(10);

        for (LinkedList<Point> line : lines){
            //for (Point point : line){ //尋訪每一點
            for (int i=1; i<line.size(); i++){ //i=0沒有在畫,畫一條線
                Point p0 = line.get(i-1);
                Point p1 = line.get(i);
                canvas.drawLine(p0.x,p0.y,p1.x,p1.y, paint);
            }
        }



        //canvas.drawLine(0,0,200,200, paint);
    }

    // app 類別寫在類別當中,內部類別好處? 不用訂定規格可直接呼叫
    //汽車類別,產生汽車物件實體, 公用類別彼此呼叫, 有定義規格才能方便呼叫

    public void clear(){ //對外提供
        lines.clear();
        invalidate();//畫面重新繪製
    }

    public void undo(){
        if(lines.size() > 0){
            //lines.removeLast();//移掉最後一條線
            recycler.add(lines.removeLast()) ;
            invalidate();//版面在繪製一下
        }
    }

    public void redo(){
        if(recycler.size() > 0){
            lines.add(recycler.removeLast());
            invalidate();
        }
    }

    //砍掉的東西需要回收桶

    private class Point{
        float x, y;
        Point(float x, float y){
            this.x = x; this.y = y;
        }
    }
}

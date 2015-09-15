package com.sloop.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 虚拟摇杆                 <br/>
 * Sloop 创建于 2015/9/12. <br/>
 * <ul type="disc">
 * <li><a href="http://www.sloop.icoc.cc"    target="_blank">作者网站</a>      <br/></li>
 * <li><a href="http://weibo.com/5459430586" target="_blank">作者微博</a>      <br/></li>
 * <li><a href="https://github.com/GcsSloop" target="_blank">作者GitHub</a>   <br/></li>
 * </ul>
 */
public class Rocker extends SurfaceView implements Runnable, SurfaceHolder.Callback {

    private Context context;
    private SurfaceHolder mHolder;
    private boolean isStop = false;
    private Thread mThread;
    private Paint mPaint;

    private Point mRockerPosition;                  //摇杆位置
    private Point mCtrlPoint = new Point(80, 80);   //摇杆起始位置
    private int mRudderRadius = 30;                 //摇杆半径
    private int mWheelRadius = 80;                  //摇杆活动范围半径

    Bitmap rocker_bg, rocker_ctrl;


    private RudderListener listener = null; //事件回调接口
    public static final int ACTION_RUDDER = 1, ACTION_ATTACK = 2; // 1：摇杆事件 2：按钮事件（未实现）


    public Rocker(Context context, AttributeSet as) {
        super(context, as);
        this.context = context;
        //数值修正
        mWheelRadius = DensityUtil.dip2px((ContextThemeWrapper) context, mWheelRadius);
        mRudderRadius = DensityUtil.dip2px((ContextThemeWrapper) context, mRudderRadius);


        this.setKeepScreenOn(true);
        mHolder = getHolder();
        mHolder.addCallback(this);
        mThread = new Thread(this);
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setAntiAlias(true);                  //抗锯齿
        mRockerPosition = new Point(mCtrlPoint);    //设置起始位置
        setFocusable(true);                         //可获得焦点（键盘）
        setFocusableInTouchMode(true);              //可获得焦点（触摸）
        setZOrderOnTop(true);                       //保持在界面最上层
        mHolder.setFormat(PixelFormat.TRANSPARENT); //设置背景透明

/*        rocker_bg = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_bg1);
        rocker_bg = Bitmap.createScaledBitmap(rocker_bg, mWheelRadius * 2, mWheelRadius * 2, true);
        rocker_ctrl = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_ctrl);
        rocker_ctrl = Bitmap.createScaledBitmap(rocker_ctrl, mRudderRadius * 2, mRudderRadius * 2, true);
        Log.e("ASD", "mWheelRadius:" + mWheelRadius + "  width" + rocker_bg.getWidth());*/
    }

    /**
     * 设置摇杆背景图片
     *
     * @param bitmap
     */
    public void setRockerBg(Bitmap bitmap) {
        rocker_bg = Bitmap.createScaledBitmap(bitmap, mWheelRadius * 2, mWheelRadius * 2, true);
    }

    /**
     * 设置摇杆图片
     *
     * @param bitmap
     */
    public void setRockerCtrl(Bitmap bitmap) {
        rocker_ctrl = Bitmap.createScaledBitmap(bitmap, mRudderRadius * 2, mRudderRadius * 2, true);
    }

    /**
     * 设置摇杆活动半径
     *
     * @param radius
     */
    public void setmWheelRadius(int radius) {
        mWheelRadius = DensityUtil.dip2px((ContextThemeWrapper) context, radius);
    }

    /**
     * 设置摇杆半径
     *
     * @param radius
     */
    public void setmRudderRadius(int radius) {
        mRudderRadius = DensityUtil.dip2px((ContextThemeWrapper) context, radius);
    }

    @Override
    public void run() {
        Canvas canvas = null;

        while (!isStop) {
            try {

                canvas = mHolder.lockCanvas();
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//清除屏幕
                if (rocker_bg != null) {
                    canvas.drawBitmap(rocker_bg, mCtrlPoint.x - mWheelRadius, mCtrlPoint.y - mWheelRadius, mPaint);//这里的60px是最外围的图片的半径
                } else {
                    mPaint.setColor(Color.CYAN);
                    canvas.drawCircle(mCtrlPoint.x, mCtrlPoint.y, mWheelRadius, mPaint);//绘制范围
                }

                if (rocker_ctrl != null) {
                    canvas.drawBitmap(rocker_ctrl, mRockerPosition.x - mRudderRadius, mRockerPosition.y - mRudderRadius, mPaint);//这里的20px是最里面的图片的半径
                } else {
                    mPaint.setColor(Color.RED);
                    canvas.drawCircle(mRockerPosition.x, mRockerPosition.y, mRudderRadius, mPaint);//绘制摇杆
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (canvas != null) {
                    mHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //确定中心点
        int width = this.getWidth();
        int height = this.getHeight();
        mCtrlPoint = new Point(width / 2, height / 2);
        mRockerPosition = new Point(mCtrlPoint);
        mThread.start();
        Log.e("WH", width + ":" + height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isStop = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int len = MathUtils.getLength(mCtrlPoint.x, mCtrlPoint.y, event.getX(), event.getY());
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //如果屏幕接触点不在摇杆挥动范围内,则不处理
            //  Log.e("Rocker", "len:"+len);
            if (len > mWheelRadius) {
                return true;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (listener != null) {
                listener.onSteeringWheelChanged(ACTION_RUDDER, 0);
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (len <= mWheelRadius) {
                //如果手指在摇杆活动范围内，则摇杆处于手指触摸位置
                mRockerPosition.set((int) event.getX(), (int) event.getY());

            } else {
                //设置摇杆位置，使其处于手指触摸方向的 摇杆活动范围边缘
                mRockerPosition = MathUtils.getBorderPoint(mCtrlPoint,
                        new Point((int) event.getX(), (int) event.getY()), mWheelRadius);
            }
            if (listener != null) {
                float radian = MathUtils.getRadian(mCtrlPoint, new Point((int) event.getX(), (int) event.getY()));
                listener.onSteeringWheelChanged(ACTION_RUDDER, Rocker.this.getAngleCouvert(radian));
            }
        }
        //如果手指离开屏幕，则摇杆返回初始位置
        if (event.getAction() == MotionEvent.ACTION_UP) {
            mRockerPosition = new Point(mCtrlPoint);
        }
        return true;
    }

    //获取摇杆偏移角度 0-360°
    private int getAngleCouvert(float radian) {
        int tmp = (int) Math.round(radian / Math.PI * 180);
        if (tmp < 0) {
            return -tmp;
        } else {
            return 180 + (180 - tmp);
        }
    }

    //回调接口
    public interface RudderListener {
        void onSteeringWheelChanged(int action, int angle);
    }

    //设置回调接口
    public void setRudderListener(RudderListener rockerListener) {
        listener = rockerListener;
    }


}

/**
 * 数学工具类
 */
class MathUtils {
    //获取两点间直线距离
    public static int getLength(float x1, float y1, float x2, float y2) {
        return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    /**
     * 获取线段上某个点的坐标，长度为a.x - cutRadius
     *
     * @param a         点A
     * @param b         点B
     * @param cutRadius 截断距离
     * @return 截断点
     */
    public static Point getBorderPoint(Point a, Point b, int cutRadius) {
        float radian = getRadian(a, b);
        return new Point(a.x + (int) (cutRadius * Math.cos(radian)), a.y + (int) (cutRadius * Math.sin(radian)));
    }

    //获取水平线夹角弧度
    public static float getRadian(Point a, Point b) {
        float lenA = b.x - a.x;
        float lenB = b.y - a.y;
        float lenC = (float) Math.sqrt(lenA * lenA + lenB * lenB);
        float ang = (float) Math.acos(lenA / lenC);
        ang = ang * (b.y < a.y ? -1 : 1);
        return ang;
    }
}

/**
 * 转换分辨率
 *
 * @author sloop
 * @ClassName: DensityUtil
 * @date 2015年3月19日 下午2:13:45
 */

class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(ContextThemeWrapper context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(ContextThemeWrapper context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}

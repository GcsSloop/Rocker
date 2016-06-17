# Rocker

一个安卓虚拟摇杆程序，可作为游戏控制器或者小车遥控器。

### 作者微博: [@GcsSloop](http://weibo.com/GcsSloop)

# 摘要
由于需要制作一个控制小车移动的应用，使用按键控制不太舒服，故制作了一个虚拟摇杆。
之前的摇杆程序只是满足了基本功能，还有很多不方便以及不合理的地方，本次对其进行了重构，使其结构更佳合理，也完善了之前不足的部分。

**目前已完成内容:**

* 程序结构更佳合理
* 视图大小自适应
* 支持界面预览
* 支持在界面文件中直接配置参数
* 增加定时发送数据功能

## 效果预览

### 默认参数

``` xml
<com.gcssloop.widget.RockerView
	android:id="@+id/rocker"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"/>
```
![](http://ww1.sinaimg.cn/large/005Xtdi2jw1f4y0d7x353j30dw0mot99.jpg)
 
摇杆的图片和背景图片可以随意指定，并且在运行过程中也可以更换（ps：图片内容需要为圆形且背景透明），指定图片后效果如下：

 <img src="https://github.com/GcsSloop/Rocker/blob/master/art/Rocker1.jpeg" width = "320" height = "560" alt="rocker3" align=center />  <img src="https://github.com/GcsSloop/Rocker/blob/master/art/Rocker2.jpeg" width = "320" height = "560" alt="rocker3" align=center />
 
## <a href="http://pan.baidu.com/share/link?shareid=1802923607&uk=3009583694&fid=188394437825762" target="_blank">点击这里可以观看效果视频</a>

 
# 使用示例：
## 1.在布局文件中添加摇杆
 

     <com.sloop.widget.Rocker
         android:id="@+id/rudder"
         android:layout_width="match_parent"
         android:layout_height="match_parent" />

##  2.找到该组件，指定摇杆图片和背景图片（可省略）
 
    Rocker rocker = (Rocker) findViewById(R.id.rudder);
		Bitmap rocker_bg = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_bg1);
		Bitmap rocker_ctrl = BitmapFactory.decodeResource(getResources(), R.drawable.rocker_ctrl);
		rocker.setRockerBg(rocker_bg);
		rocker.setRockerCtrl(rocker_ctrl);
		
## 3.设置监听器获得摇杆状态

      rocker.setRudderListener(new Rocker.RudderListener() {
  			@Override
  			public void onSteeringWheelChanged(int action, int angle) {
  				if (action == Rocker.ACTION_RUDDER) {
  					//TODO:事件实现
  					Log.e("夹角", "angle"+angle);
  				}
  			}
		});
		
# 组件设置参数的方法：

    /** 设置摇杆背景图  */
    public void setRockerBg(Bitmap bitmap) {
        rocker_bg = Bitmap.createScaledBitmap(bitmap, mWheelRadius * 2, mWheelRadius * 2, true);
    }

    /** 设置摇杆图片 */
    public void setRockerCtrl(Bitmap bitmap) {
        rocker_ctrl = Bitmap.createScaledBitmap(bitmap, mRudderRadius * 2, mRudderRadius * 2, true);
    }

    /** 设置摇杆活动半径  */
    public void setmWheelRadius(int radius) {
        mWheelRadius = DensityUtil.dip2px((ContextThemeWrapper) context, radius);
    }

    /** 设置摇杆半径 */
    public void setmRudderRadius(int radius) {
        mRudderRadius = DensityUtil.dip2px((ContextThemeWrapper) context, radius);
    }

#### ps：摇杆位置默认处于surfaceView的中心，故这里没有提供设置摇杆位置的方法。

### 参考的文章： [android 虚拟摇杆图片版](http://blog.csdn.net/jwzhangjie/article/details/8839744)


## About Me

### 作者微博: [@GcsSloop](http://weibo.com/GcsSloop)

<a href="https://github.com/GcsSloop/SloopBlog/blob/master/FINDME.md" target="_blank"> <img src="http://ww4.sinaimg.cn/large/005Xtdi2gw1f1qn89ihu3j315o0dwwjc.jpg" width=300 height=100 /> </a>



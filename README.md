# Rocker

一个安卓虚拟摇杆程序，可作为游戏控制器或者小车遥控器。

### 作者微博: [@GcsSloop](http://weibo.com/GcsSloop)

## 摘要
由于需要制作一个控制小车移动的应用，使用按键控制不太舒服，故制作了一个虚拟摇杆。
之前的摇杆程序只是满足了基本功能，还有很多不方便以及不合理的地方，本次对其进行了重构，使其结构更佳合理，也完善了之前不足的部分。

**目前已完成内容:**

* 程序结构更佳合理
* 视图大小自适应
* 支持界面预览
* 支持在界面文件中直接配置参数
* 增加定时发送状态数据

## 使用示例

### 默认参数

``` xml
<com.gcssloop.widget.RockerView
	android:id="@+id/rocker"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"/>
```
![](http://ww1.sinaimg.cn/large/005Xtdi2jw1f4y0d7x353j30dw0mot99.jpg)
 
### 自定义参数
可自定义参数包括:

参数类型          | 中文     | 数据类型         | 默认值 |简介
------------------|----------|------------------|--------|-----------
area_radius       | 区域半径 | dimension        | 75dp   | 摇杆可活动区域的半径
rocker_radius     | 摇杆半径 | dimension        | 25dp   | 摇杆本身半径
area_background   | 区域背景 | color, reference | CYAN   | 区域背景
rocker_background | 摇杆背景 | color, reference | RED    | 摇杆背景
refresh_cycle     | 刷新周期 | integer          | 30ms   | 界面刷新周期，默认30毫秒，也意味着每30ms就会发送一次状态数据

> 
**视图大小会根据 区域半径以及摇杆半径值自适应，一般使用 wrap_content 即可。**

``` xml
<com.gcssloop.widget.RockerView
	android:id="@+id/rocker"
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	rocker:area_background="@drawable/area_bg"
	rocker:area_radius="80dp"
	rocker:rocker_background="@drawable/rocker_bg"
	rocker:rocker_radius="30dp"/>
```

![](http://ww3.sinaimg.cn/large/005Xtdi2jw1f4y1bofuu5j30dw0mota1.jpg)



### 参考的文章： [android 虚拟摇杆图片版](http://blog.csdn.net/jwzhangjie/article/details/8839744)


## About Me

### 作者微博: [@GcsSloop](http://weibo.com/GcsSloop)

<a href="https://github.com/GcsSloop/SloopBlog/blob/master/FINDME.md" target="_blank"> <img src="http://ww4.sinaimg.cn/large/005Xtdi2gw1f1qn89ihu3j315o0dwwjc.jpg" width=300 height=100 /> </a>



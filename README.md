# <img src="http://ww3.sinaimg.cn/large/005Xtdi2gw1f4y9q9eql0j3074074jrm.jpg" width=32 /> Rocker

[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
![](https://img.shields.io/badge/Support-11%2B-green.svg)
[![](https://jitpack.io/v/GcsSloop/Rocker.svg)](https://jitpack.io/#GcsSloop/Rocker)

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
<img src="http://ww1.sinaimg.cn/large/005Xtdi2jw1f4y0d7x353j30dw0mot99.jpg" width=300 />
 
### 自定义参数
可自定义参数包括:

参数类型          | 中文     | 数据类型         | 默认值 |简介
------------------|----------|------------------|--------|-----------
area_radius       | 区域半径 | dimension        | 75dp   | 摇杆可活动区域的半径
rocker_radius     | 摇杆半径 | dimension        | 25dp   | 摇杆本身半径
area_background   | 区域背景 | color, reference | CYAN   | 区域背景
rocker_background | 摇杆背景 | color, reference | RED    | 摇杆背景
refresh_cycle     | 刷新周期 | integer          | 30ms   | 界面刷新周期，默认30毫秒，每30ms就会刷新一次界面
callback_cycle    | 回调周期 | integer          | 100ms  | 数据回调周期，默认100毫秒，每100ms就会发送一次界面状态回调

> 
**视图大小会根据 区域半径以及摇杆半径值自适应，一般使用 wrap_content 即可。**

**请在 layout 文件的根节点添加命名空间: `xmlns:rocker="http://schemas.android.com/apk/res-auto"`**

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

<img src="http://ww3.sinaimg.cn/large/005Xtdi2jw1f4y1bofuu5j30dw0mota1.jpg" width=300 />

### 监听回调

监听回调可监听到两个事件.

事件类型     | 中文     | 简介
-------------|----------|----------------------------------------------------------------
EVENT_ACTION | 操作事件 | 用户点击和滑动时会返回当前角度，当用户手指离开屏幕，返回 -1
EVENT_CLOCK  | 时钟事件 | 定时返回当前角度，如果没有任何操作，则返回 -1，默认每30ms返回一次

``` java
RockerView rocker = (RockerView) findViewById(R.id.rocker);
if (null != rocker){
    rocker.setListener(new RockerView.RockerListener() {
        @Override
        public void callback(int eventType, int currentAngle) {
            switch (eventType) {
                case RockerView.EVENT_ACTION:
                    // 触摸事件回调
                    Log.e("EVENT_ACTION-------->", "angle="+currentAngle);
                    break;
                case RockerView.EVENT_CLOCK:
                    // 定时回调
                    Log.e("EVENT_CLOCK", "angle="+currentAngle);
                    break;
            }
        }
    });
}
```

**PS：EVENT_CLOCK是定时返回当前状态的，可以用 'callback_cycle' 来控制数据回调周期。**

## 如何添加


**Step 1. 添加JitPack仓库**

在当前项目等根目录下的 `build.gradle` 文件中添加如下内容:

``` gradle
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

**Step 2. 添加项目依赖**

``` gradle
	dependencies {
	        compile 'com.github.GcsSloop:Rocker:v1.0.3'
	}
```

## About Me

### 作者微博: [@GcsSloop](http://weibo.com/GcsSloop)

<a href="https://github.com/GcsSloop/SloopBlog/blob/master/FINDME.md" target="_blank"> <img src="http://ww4.sinaimg.cn/large/005Xtdi2gw1f1qn89ihu3j315o0dwwjc.jpg" width=300 height=100 /> </a>

## License

```
Copyright (c) 2016 GcsSloop

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```


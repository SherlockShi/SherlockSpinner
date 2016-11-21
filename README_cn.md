#SherlockSpinner  
[ ![Download](https://api.bintray.com/packages/sherlockshi/android-widgets/SherlockSpinner/images/download.svg) ](https://bintray.com/sherlockshi/android-widgets/SherlockSpinner/_latestVersion)

一个可以像系统Spinner一样简单易用的SherlockSpinner，并且如果你想在异步加载数据后，再显示出更新后的数据，你可以使用它的点击事件来处理。

# 文档
[English](./README.md)  
中文

# 截图
![SherlockSpinner](http://7xlpfl.com1.z0.glb.clouddn.com/sherlockshi/2016-11-20-demo3.gif)

# 依赖
使用Gradle依赖:
```groovy
dependencies {
    ...
    compile 'com.sherlockshi.widget:sherlockspinner:1.0.1'
}
```

或使用Maven依赖:
```groovy
<dependency>
  <groupId>com.sherlockshi.widget</groupId>
  <artifactId>sherlockspinner</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

# 用法
##### 1. 像使用系统Spinner一样，在XML文件中使用:
```xml
<com.sherlockshi.widget.SherlockSpinner
    android:id="@+id/sherlock_spinner"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:lineColor="#00FF00"
    android:gravity="center"
    android:hint="Please Select..."/>
```

SherlockSpinner有以下属性:  
`lineColor`: 设置底部横线的颜色

> 由于SherlockSpinner继承自`EditText`, 所以你可以使用EditText的其它属性，例如`gravity`...

##### 2. 还是像使用系统Spinner一样，在代码中设置`Adapter`和`ItemClickListener`:
```java
mSherlockSpinner = (SherlockSpinner) findViewById(R.id.sherlock_spinner);
ArrayAdapter<String> mAdapterLanguages = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLanguages);
mSherlockSpinner.setAdapter(mAdapterLanguages);
mSherlockSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showMessage("Select " + mLanguages[position]);
    }
});
```

##### 3. (可选) 如果你想在异步加载数据后，再显示出更新后的数据，你可以使用它的点击事件来处理
`记住：`在获取数据后，你必须手动调用`sherlockSpinner.show()`方法来显示SherlockSpinner的下拉选项

```java
mSherlockSpinner.setOnClickListener(new SherlockSpinner.OnClickListener() {
    @Override
    public void onClick(View v) {
        getDataFromNet();
    }
});

public void getDataFromNet() {
    // after delay 2s, modify the source data, to simulate net request
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mLanguages[4] = "Javaaaaaaaaaaa";
            mAdapterLanguages.notifyDataSetChanged();

            // then you must manually call sherlockSpinner.show()
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSherlockSpinner.show();
                }
            });
        }
    }).start();
}
```

##### 4. (可选) 更多属性
- 由于SherlockSpinner继承自EditText，所以你可以使用EditText的其它属性，例如`gravity`、`textSize`、`textColor`...
- SherlockSpinner还有一个属性，可以设置下拉框的显示位置，即锚点设置：

```java
mSherlockSpinner.setAnchorView(findViewById(R.id.llyt_anchor));
```

效果如图中3和4的区别，在第3部分中，下拉框停靠在Spinner上；而第4部分中，下拉框停靠在Spinner所在的整行布局上，宽度更大。

# ProGuard
```groovy
-keep class com.sherlockshi.widget.** { *; }
```

# License
```
Copyright 2016 SherlockShi

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
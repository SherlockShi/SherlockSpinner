#SherlockSpinner  
[ ![Download](https://api.bintray.com/packages/sherlockshi/android-widgets/SherlockSpinner/images/download.svg) ](https://bintray.com/sherlockshi/android-widgets/SherlockSpinner/_latestVersion)

A spinner which you can use just like system spinner, furthermore, you can refresh data by using the click listener asynchronously.

# Document
English  
[中文](./README_cn.md)

# Screenshot
![SherlockSpinner](http://7xlpfl.com1.z0.glb.clouddn.com/sherlockshi/2016-11-20-demo3.gif)

# Dependency
By Gradle:
```groovy
dependencies {
    ...
    compile 'com.sherlockshi.widget:sherlockspinner:1.0.1'
}
```

or by Maven:
```groovy
<dependency>
  <groupId>com.sherlockshi.widget</groupId>
  <artifactId>sherlockspinner</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```


# Usage
##### 1. use in xml files, just as system Spinner:
```xml
<com.sherlockshi.widget.SherlockSpinner
    android:id="@+id/sherlock_spinner"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:lineColor="#00FF00"
    android:gravity="center"
    android:hint="Please Select..."/>
```

SherlockSpinner has one additional attributes:  
`lineColor`: set the bottom line color  
> As SherlockSpinner extends EditText, you can use other Attributes of EditText, such as `gravity`...

##### 2. then use it just like system Spinner, set `Adapter` and `ItemClickListener`:
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

##### 3. (Optional) If you want to refresh data asynchronously, you can use the click listener:
`Remember:`after you get data, you must manually call `sherlockSpinner.show()` to show dropdown item

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
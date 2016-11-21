package com.sherlockshi.widget;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.sherlockshi.widget.sherlockspinner.R;

public class MainActivity extends AppCompatActivity {

    private static final int USE_LIST_POPUP_WINDOW = 1;
    private static final int USE_SHERLOCK_SPINNER = 2;

    private String[] mLanguages = {"C", "C++", "C#", "OC", "Java", "Python"};

    // 1. System Spinner
    private Spinner spnLanguages;

    // 2. CustomSpinner = EditText + ListPopupWindow
    private EditText etLanguages;
    private ListPopupWindow lpwLanguages;
    private ArrayAdapter<String> mAdapterLanguages;

    // 3. use SherlockSpinner just like System Spinner
    private SherlockSpinner mSherlockSpinnerBase;

    // 4. SherlockSpinner Pro(In XML)
    private SherlockSpinner mSherlockSpinnerProInXML;

    // 5. SherlockSpinner Pro(In Code)
    private SherlockSpinner mSherlockSpinnerProInCode;
    ArrayAdapter<String> mAdapterSherlockSpinnerProInCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. System Spinner
        initSystemSpinner();

        // 2. CustomSpinner = EditText + ListPopupWindow
        initEditTextPlusListPopupWindow();

        // 3. use SherlockSpinner just like System Spinner
        initSherlockSpinner();

        // 4. SherlockSpinner Pro(In XML)
        initSherlockSpinnerProInXML();

        // 5. SherlockSpinner Pro(In Code)
        initSherlockSpinnerProInCode();
    }

    // 5. SherlockSpinner Pro(In Code)
    private void initSherlockSpinnerProInCode() {
        mSherlockSpinnerProInCode = (SherlockSpinner) findViewById(R.id.sherlock_spinner_pro_in_code);
        mAdapterSherlockSpinnerProInCode = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLanguages);
        mSherlockSpinnerProInCode.setAdapter(mAdapterSherlockSpinnerProInCode);
        mSherlockSpinnerProInCode.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMessage("Select " + mLanguages[position]);
            }
        });

        // SherlockSpinner Pro
        // 1) comtom setting
        mSherlockSpinnerProInCode.setGravity(Gravity.RIGHT);
//        mSherlockSpinnerProInCode.setLineColorResource(R.color.colorAccent);
        mSherlockSpinnerProInCode.setLineColor(0xFF0000);
        mSherlockSpinnerProInCode.setAnchorView(findViewById(R.id.llyt_anchor));

        // 2) Click Listener(Async)
        mSherlockSpinnerProInCode.setOnClickListener(new SherlockSpinner.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromNet(USE_SHERLOCK_SPINNER);
            }
        });
    }

    // 4. SherlockSpinner Pro(In XML)
    private void initSherlockSpinnerProInXML() {
        mSherlockSpinnerProInXML = (SherlockSpinner) findViewById(R.id.sherlock_spinner_pro_in_xml);
        ArrayAdapter<String> mAdapterSherlockSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLanguages);
        mSherlockSpinnerProInXML.setAdapter(mAdapterSherlockSpinner);
        mSherlockSpinnerProInXML.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMessage("Select " + mLanguages[position]);
            }
        });
    }

    // 3. use SherlockSpinner just like System Spinner
    private void initSherlockSpinner() {
        mSherlockSpinnerBase = (SherlockSpinner) findViewById(R.id.sherlock_spinner_base);
        ArrayAdapter<String> mAdapterSherlockSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLanguages);
        mSherlockSpinnerBase.setAdapter(mAdapterSherlockSpinner);
        mSherlockSpinnerBase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMessage("Select " + mLanguages[position]);
            }
        });
    }

    // 2. CustomSpinner = EditText + ListPopupWindow
    private void initEditTextPlusListPopupWindow() {
        etLanguages = (EditText) findViewById(R.id.et_languages);
        etLanguages.setKeyListener(null);   // 设置EditText不可编辑，等同于在xml中设置editable="false"
        lpwLanguages = new ListPopupWindow(this);
        mAdapterLanguages = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLanguages);
        lpwLanguages.setAdapter(mAdapterLanguages);
        lpwLanguages.setAnchorView(etLanguages);    //设置ListPopupWindow的锚点，即关联PopupWindow的显示位置
        lpwLanguages.setModal(true);    // 是否为模态，当设置为true时，会处理返回按键的事件

        lpwLanguages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showMessage("Select " + mLanguages[position]);

                etLanguages.setText(mLanguages[position]);
                lpwLanguages.dismiss();
            }
        });

        // 如果使用onClick事件，会出现第一次点击只获取焦点，第二次点击才出现下拉
        etLanguages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // Do what you want
                    getDataFromNet(USE_LIST_POPUP_WINDOW);
                }
                return false;
            }
        });
    }

    // 1. System Spinner
    private void initSystemSpinner() {
        // System Spinner
        spnLanguages = (Spinner) findViewById(R.id.spn_languages);
        ArrayAdapter<String> mAdapterSystemSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mLanguages);
        spnLanguages.setAdapter(mAdapterSystemSpinner);
        spnLanguages.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                showMessage("Select " + mLanguages[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                showMessage("Select Nothing.");
            }
        });

        spnLanguages.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        showMessage("Touch Spinner");
                        break;
                }
                return false;
            }
        });
    }

    private void showMessage(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    public void getDataFromNet(final int which) {
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

        // 延时2秒后，修改源数据，用来模拟网络请求
        // after delay 2s, modify the source data, to simulate net request
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // then you must manually call sherlockSpinner.show()
                if (USE_LIST_POPUP_WINDOW == which) {
                    mLanguages[1] = "C+++++++++++++";
                    mAdapterLanguages.notifyDataSetChanged();
                } else if (USE_SHERLOCK_SPINNER == which) {
                    mLanguages[4] = "Javaaaaaaaaaaa";
                    mAdapterSherlockSpinnerProInCode.notifyDataSetChanged();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        findViewById(R.id.progress_bar).setVisibility(View.GONE);

                        if (USE_LIST_POPUP_WINDOW == which) {
                            lpwLanguages.show();
                        } else if (USE_SHERLOCK_SPINNER == which) {
                            mSherlockSpinnerProInCode.show();
                        }
                    }
                });
            }
        }).start();
    }
}

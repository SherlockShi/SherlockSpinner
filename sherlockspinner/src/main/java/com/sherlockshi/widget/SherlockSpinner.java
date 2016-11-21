package com.sherlockshi.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.ListPopupWindow;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;


/**
 * Author: SherlockShi
 * Date:   2016-11-19 20:34
 * Description:
 */
public class SherlockSpinner extends AppCompatEditText {

    private static final int DEF_LINE_COLOR = 0XFF3F51B5;

    private int mLineColor = DEF_LINE_COLOR;

    private Context mContext;

    private ListPopupWindow mListPopupWindow;
    private ListAdapter mListAdapter;
    private View mAnchorView;
    private int mDropdownIcon = -1;

    private OnClickListener mOnClickListener;
    private AdapterView.OnItemClickListener mItemClickListener;

    /**
     * Interface definition for a callback to be invoked when a view is clicked.
     */
    public interface OnClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v The view that was clicked.
         */
        void onClick(View v);
    }

    public SherlockSpinner(Context context) {
        this(context, null);
        this.mContext = context;
        init();
    }

    public SherlockSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SherlockSpinner);
        try {
            mLineColor = a.getInteger(R.styleable.SherlockSpinner_lineColor, DEF_LINE_COLOR);
        } finally {
            a.recycle();
        }

        init();
    }

    private void init() {
        // set EditText not editable
        this.setKeyListener(null);

        // set distance between Text and Image
        this.setCompoundDrawablePadding(10);

        setLineColor(mLineColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                initSpinner();

                if (mOnClickListener == null) {
                    show();
                } else {
                    mOnClickListener.onClick(this);
                }
                break;
        }

        return super.onTouchEvent(event);
    }

    private void initSpinner() {
        mListPopupWindow = new ListPopupWindow(mContext);
        mListPopupWindow.setAdapter(mListAdapter);

        // If anchor view is not set, then set current Spinner(EditText) as anchor view
        // 如果未设置锚点，则将锚点设置在当前Spinner(EditText)上
        if (mAnchorView == null) {
            mListPopupWindow.setAnchorView(this);
        } else {
            mListPopupWindow.setAnchorView(mAnchorView);
        }

        mListPopupWindow.setModal(true);

        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SherlockSpinner.super.setText(mListAdapter.getItem(position).toString());
                mItemClickListener.onItemClick(parent, view, position, id);
                mListPopupWindow.dismiss();
            }
        });
    }

    /**
     * Show the popup list.
     */
    public void show() {
        if (mListPopupWindow != null) {
            mListPopupWindow.show();
        }
    }

    /**
     * set the bottom line color
     * 设置底部横线颜色
     * @param resId
     */
    public void setLineColorResource(@ColorRes int resId) {
        ViewCompat.setBackgroundTintList(this, getResources().getColorStateList(resId));
    }

    /**
     * set the bottom line color
     * 设置底部横线颜色
     * @param color
     */
    public void setLineColor(@ColorInt int color) {
        ViewCompat.setBackgroundTintList(this, new ColorStateList(new int[][]{new int[0]}, new int[]{color | 0xFF000000}));
    }

    /**
     * Sets a listener to receive events when a list item is clicked.
     *
     * @param itemClickListener Listener to register
     *
     * @see ListView#setOnItemClickListener(android.widget.AdapterView.OnItemClickListener)
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    /**
     * Register a callback to be invoked when this view is clicked. If this view is not
     * clickable, it becomes clickable.
     *
     * @param l The callback that will run
     */
    public void setOnClickListener(OnClickListener l) {
        if (!isClickable()) {
            setClickable(true);
        }
        this.mOnClickListener = l;
    }

    /**
     * 设置ListPopupWindow的锚点，即关联PopupWindow的显示位置在这个锚点上
     *
     * Sets the popup's anchor view. This popup will always be positioned relative to the anchor
     * view when shown.
     *
     * @param anchor The view to use as an anchor.
     */
    public void setAnchorView(View anchor) {
        this.mAnchorView = anchor;
    }

    /**
     * Sets the adapter that provides the data and the views to represent the data
     * in this popup window.
     *
     * @param adapter The adapter to use to create this window's content.
     */
    public void setAdapter(ListAdapter adapter) {
        if (adapter != null) {
            this.mListAdapter = adapter;
        }
    }

    /**
     * set dropdown icon
     * 设置下拉按钮
     * @param resId
     */
    public void setDropdownIcon(@DrawableRes int resId) {
        this.mDropdownIcon = resId;
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(Drawable left, Drawable top, Drawable right, Drawable bottom) {
        Drawable dropdownIcon = null;
        if (mDropdownIcon != -1) {
            dropdownIcon = ContextCompat.getDrawable(getContext(), R.drawable.icon_dropdown);
        } else {
            dropdownIcon = ContextCompat.getDrawable(getContext(), mDropdownIcon);
        }

        if (dropdownIcon != null) {
            right = dropdownIcon;
//            right.mutate().setAlpha(66);
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }
}

package com.example.stn.stn.views;

/**
 * Created by ASUS on 2018/4/4.
 */

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.stn.stn.R;
import com.example.stn.stn.utils.SystemUtils;

/**
 * 自定义的可下拉刷新或上拉加载的ListView
 */
public class RefreshableListView extends ListView implements AbsListView.OnScrollListener {
    private static final int STATE_NORMAL = 0x000; // 正常状态（没有显示头部布局）
    private static final int STATE_PULLING = 0x001; // 正在下拉或上拉，但没有达到刷新或加载的要求的状态
    private static final int STATE_PREPARED = 0x002; // 达到刷新或加载的要求，松开手指就可以刷新或加载的状态
    private static final int STATE_REFRESHING = 0x003; // 正在刷新或加载的状态

    private View headerView; // 顶部布局
    private ImageView arrow; // 顶部布局中的箭头
    private ProgressBar headerProgress; // 顶部布局中的进度条
    private TextView headerTip; // 顶部布局中的提示信息
    private int headerHeight; // 头部布局的高度
    private boolean isRefreshEnabled; // 是否允许下拉刷新
    private boolean isRefreshable; // 是否可以下拉刷新

    private ProgressBar footerProgress; // 底部布局中的进度条
    private TextView footerTip; // 底部布局中的提示信息
    private int footerHeight; // 底部布局的高度
    private boolean isLoadEnabled; // 是否允许上拉加载
    private boolean isLoadable; // 是否可以上拉加载

    private int firstItemIndex; // 第一个可见Item的下标
    private int visibleItemCount; // 页面中可见的Item的个数
    private int totalItemCount; // ListView中加载的Item的总个数

    private int firstItemTopPadding; // 第一个Item的top值
    private int startY; // 记录手指按下时的Y坐标位置
    private int offsetY; // 记录手指拖动过程中Y坐标的偏移量
    private int rotateTime; // 旋转次数，用于控制箭头只旋转一次
    private boolean isScrollIdle; // 滑动动作是否是停止的

    public OnRefreshListener onRefreshListener;
    private View footerView;

    public RefreshableListView(Context context) {
        super(context);
        initView(context);
    }

    public RefreshableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RefreshableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 初始化界面，添加顶部布局文件到ListView中
     */
    private void initView(final Context context) {
        // 初始化头部布局及布局中的控件
        headerView = LayoutInflater.from(context).inflate(R.layout.sideworks_rlv_header, this, false);
        arrow = (ImageView) headerView.findViewById(R.id.rlv_header_iv_arrow);
        headerProgress = (ProgressBar) headerView.findViewById(R.id.rlv_header_progress_progressbar);
        headerTip = (TextView) headerView.findViewById(R.id.rlv_header_tv_tip);
        // 初始化底部布局及布局中的控件
        footerView = LayoutInflater.from(context).inflate(R.layout.sideworks_rlv_footer, this, false);
        footerProgress = (ProgressBar) footerView.findViewById(R.id.rlv_footer_progress_progressbar);
        footerTip = (TextView) footerView.findViewById(R.id.rlv_footer_tv_tip);
        // 此时视图刚刚开始初始化，如果直接获取测量值会返回0，因此需要将这个操作post到初始化之后进行
        this.post(new Runnable() {
            @Override
            public void run() {
               // headerHeight = headerView.getMeasuredHeight();
                // 布局文件中，头部布局100dp，底部布局60dp，因此偷个懒，用*0.6的方法得到底部布局的高度
                headerHeight = SystemUtils.dip2px(context, 100);
                int i = SystemUtils.dip2px(context,100);
                footerHeight = i;
                setViewPadding(-headerHeight, -footerHeight);
            }
        });
        this.addHeaderView(headerView);
        this.addFooterView(footerView);
        this.setOnScrollListener(this);
    }

    /**
     * 设置RefreshableListView的上下边距（用于隐藏头部和底部布局）
     */
    private void setViewPadding(int topPadding, int bottomPadding) {
        this.setPadding(headerView.getPaddingLeft(), topPadding, headerView.getPaddingRight(), bottomPadding);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.firstItemIndex = firstVisibleItem;
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
        View firstView = this.getChildAt(firstVisibleItem);
        if (firstView != null) {
            this.firstItemTopPadding = firstView.getTop();
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        isScrollIdle = scrollState == OnScrollListener.SCROLL_STATE_IDLE;
    }



    /**
     * 监听手指操作的事件（按下、滑动、抬起）
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            // 手指按下时，判断是否可以下拉刷新或上拉加载
            case MotionEvent.ACTION_DOWN:
                isRefreshable = false;
                if (isRefreshEnabled && firstItemIndex == 0 && firstItemTopPadding == -headerHeight) {
                    isRefreshable = true;
                } else if (isLoadEnabled && isScrollIdle && firstItemIndex + visibleItemCount == totalItemCount) {
                    isLoadable = true;
                }
                startY = (int) ev.getY();
                offsetY = 0;
                break;
            // 手指移动时，判断是否在下拉刷新或上拉加载，如果是，则动态改变头部布局或底部布局的状态
            case MotionEvent.ACTION_MOVE:
                offsetY = (int) ev.getY() - startY;
                if (isRefreshEnabled && isRefreshable && offsetY > 0) {
                    setViewPadding(-headerHeight + offsetY, -footerHeight);
                    if (offsetY >= headerHeight) {
                        setCurrentState(STATE_PREPARED);
                    } else {
                        setCurrentState(STATE_PULLING);
                    }
                } else if (isLoadEnabled && isLoadable && offsetY < 0) {


                    setViewPadding(-headerHeight, -footerHeight - offsetY);
                    if (offsetY <= -footerHeight) {
                        setCurrentState(STATE_PREPARED);
                    } else {
                        setCurrentState(STATE_PULLING);
                    }
                }
                break;
            // 手指抬起时，判断是否下拉或上拉到可以刷新或加载的程度，如果达到程度，则进行刷新或加载
            case MotionEvent.ACTION_UP:
                if (isRefreshEnabled && isRefreshable && offsetY > 0) {
                    if (offsetY <= headerHeight) {
                        setViewPadding(-headerHeight, -footerHeight);
                        setCurrentState(STATE_NORMAL);
                    } else {
                        setViewPadding(0, -footerHeight);
                        setCurrentState(STATE_REFRESHING);
                        onRefreshListener.onRefreshing(); // 调用接口的回调方法
                    }
                } else if (isLoadEnabled && isLoadable && offsetY < 0) {
                    if (offsetY >= -footerHeight) {
                        setViewPadding(-headerHeight, -footerHeight);
                        setCurrentState(STATE_NORMAL);
                    } else {
                        setViewPadding(-headerHeight, 0);
                        setCurrentState(STATE_REFRESHING);
                        onRefreshListener.onLoading(); // 调用接口的回调方法
                    }
                }
                isRefreshable = false;
                isLoadable = false;
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 根据当前的状态进行相应的处理
     */
    private void setCurrentState(int state) {
        switch (state) {
            // 普通状态：头部布局和尾部布局都隐藏，头部布局中显示箭头不显示进度条，底部布局中不显示进度条
            case STATE_NORMAL:
                headerProgress.setVisibility(View.GONE);
                arrow.setVisibility(View.VISIBLE);
                footerProgress.setVisibility(View.GONE);
                break;
            // 正在下拉后上拉，但没有达到刷新或加载的要求的状态：
            // 如果是下拉，则将头部布局中的箭头指向调整为指下，同时改变文本；
            // 如果是上拉，则改变文本
            case STATE_PULLING:
                if (isRefreshEnabled && isRefreshable) {
                    if (rotateTime == 1) {
                        ObjectAnimator toUpAnim = ObjectAnimator.ofFloat(arrow, "rotation", 180f, 0f);
                        toUpAnim.setDuration(200);
                        toUpAnim.start();
                        rotateTime--;
                    }
                    headerTip.setText("下拉可以刷新");
                } else if (isLoadEnabled && isLoadable) {
                    footerTip.setText("上拉加载更多");
                }
                break;
            // 下拉或上拉达到刷新或加载的条件，但还没有松手的状态：
            // 如果是下拉，则将头部布局中的箭头指向调整为指上，同时改变文本；
            // 如果是上拉，则改变文本
            case STATE_PREPARED:
                if (isRefreshEnabled && isRefreshable) {
                    if (rotateTime == 0) {
                        ObjectAnimator toUpAnim = ObjectAnimator.ofFloat(arrow, "rotation", 0f, 180f);
                        toUpAnim.setDuration(200);
                        toUpAnim.start();
                        rotateTime++;
                    }
                    headerTip.setText("松开手指刷新");
                } else if (isLoadEnabled && isLoadable) {
                    footerTip.setText("松开手指加载");
                }
                break;
            // 正在刷新或加载的状态：
            // 如果是下拉，则隐藏头部布局中的箭头，显示头部布局中的进度条，改变文本；
            // 如果是上拉，则显示底部布局中的进度条，改变文本
            case STATE_REFRESHING:
                if (isRefreshEnabled && isRefreshable) {
                    arrow.setVisibility(View.GONE);
                    headerProgress.setVisibility(View.VISIBLE);
                    if (rotateTime == 1) {
                        ObjectAnimator toUpAnim = ObjectAnimator.ofFloat(arrow, "rotation", 180f, 0f);
                        toUpAnim.setDuration(200);
                        toUpAnim.start();
                        rotateTime--;
                    }
                    headerTip.setText("正在刷新......");
                } else if (isLoadEnabled && isLoadable) {
                    footerProgress.setVisibility(View.VISIBLE);
                    footerTip.setText("正在加载......");
                }
                break;
        }
    }

    /**
     * 刷新结束后必须调用这个方法来重置一些参数
     */
    public void onRefreshComplete() {
        setViewPadding(-headerHeight, -footerHeight);
        setCurrentState(STATE_NORMAL);
        isRefreshable = false;
    }

    /**
     * 设置是否允许下拉刷新和上拉加载
     */
    public void setEnables(boolean isRefreshEnabled, boolean isLoadEnabled) {
        this.isRefreshEnabled = isRefreshEnabled;
        this.isLoadEnabled = isLoadEnabled;
    }

    /**
     * 监听下拉刷新的接口
     */
    public interface OnRefreshListener {
        void onRefreshing(); // 在下拉刷新的时候回调的方法

        void onLoading(); // 在上拉加载的时候回调的方法
    }

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}

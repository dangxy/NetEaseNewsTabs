package com.example.dangxueyi.neteasenewstabs;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.dangxueyi.neteasenewstabs.adapter.ChoosedAdapter;
import com.example.dangxueyi.neteasenewstabs.adapter.UnChoosedAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView choosedRecycler;
    private RecyclerView unChoosedRecycler;

    private ArrayList<String> choosedList;
    private ArrayList<String> unChoosedList;
    private Context mcontext;
    private ChoosedAdapter choosedAdapter;
    private UnChoosedAdapter unChoosedAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private RelativeLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        mcontext = this;


        choosedAdapter = new ChoosedAdapter(mcontext, choosedList);
        choosedRecycler.setAdapter(choosedAdapter);
        choosedRecycler.setLayoutManager(new GridLayoutManager(mcontext, 4));


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(choosedAdapter);

        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(choosedRecycler);
        unChoosedAdapter = new UnChoosedAdapter(mcontext, unChoosedList);
        unChoosedRecycler.setAdapter(unChoosedAdapter);
        unChoosedRecycler.setLayoutManager(new GridLayoutManager(mcontext, 4));
        unChoosedAdapter.setonClickMoveItemListener(new UnChoosedAdapter.OnClickMoveItemListener() {
            @Override
            public void moveItemListener(final View view, final int position) {

                final PathMeasure mPathMeasure;
                final float[] mCurrentPosition = new float[2];
                int parentLoc[] = new int[2];
                linearLayout.getLocationInWindow(parentLoc);

                int startLoc[] = new int[2];
                view.getLocationInWindow(startLoc);

                final View startView = view;
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(view.getWidth(), view.getHeight());
                unChoosedRecycler.removeView(view);
                linearLayout.addView(startView, params);


                final View endView;
                float toX, toY;
                int endLoc[] = new int[2];
                //进行判断
                int i = choosedList.size();


                if (i == 0) {
                    toX = view.getWidth();
                    toY = view.getHeight();
                } else if (i % 4 == 0) {
                    endView = choosedRecycler.getChildAt(i - 4);
                    endView.getLocationInWindow(endLoc);
                    toX = endLoc[0] - parentLoc[0];
                    toY = endLoc[1] + view.getHeight() - parentLoc[1];
                } else {
                    endView = choosedRecycler.getChildAt(i - 1);
                    endView.getLocationInWindow(endLoc);
                    toX = endLoc[0] + view.getWidth() - parentLoc[0];
                    toY = endLoc[1] - parentLoc[1];
                }


                float startX = startLoc[0] - parentLoc[0];
                float startY = startLoc[1] - parentLoc[1];


                Path path = new Path();
                path.moveTo(startX, startY);
                path.lineTo(toX, toY);
                mPathMeasure = new PathMeasure(path, false);


                //属性动画实现
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
                valueAnimator.setDuration(500);
                // 匀速插值器
                valueAnimator.setInterpolator(new LinearInterpolator());
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (Float) animation.getAnimatedValue();
                        // 获取当前点坐标封装到mCurrentPosition
                        mPathMeasure.getPosTan(value, mCurrentPosition, null);
                        startView.setTranslationX(mCurrentPosition[0]);
                        startView.setTranslationY(mCurrentPosition[1]);
                    }
                });
                valueAnimator.start();


                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        //默认recyclerviewe的动画
                        unChoosedRecycler.setItemAnimator(new DefaultItemAnimator());
                        choosedRecycler.setItemAnimator(new DefaultItemAnimator());
                        choosedList.add(choosedList.size(), unChoosedList.get(position));
                        unChoosedList.remove(position);
                        //先更新数据
                        unChoosedAdapter.notifyDataSetChanged();
                        choosedAdapter.notifyDataSetChanged();
                        //再更新动画
                        unChoosedAdapter.notifyItemRemoved(position);
                        choosedAdapter.notifyItemInserted(choosedList.size());
                        linearLayout.removeView(startView);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });

            }
        });
    }

    private void initView() {
        linearLayout = (RelativeLayout) findViewById(R.id.tbs_ll);
        choosedRecycler = (RecyclerView) findViewById(R.id.rv_choosed_recycler_view);
        unChoosedRecycler = (RecyclerView) findViewById(R.id.rv_unchoosed_recycler_view);


    }

    private void initData() {

        choosedList = new ArrayList<>();
        unChoosedList = new ArrayList<>();
        choosedList.add("头条");
        choosedList.add("科技");
        choosedList.add("热点");
        choosedList.add("政务");
        choosedList.add("移动互联");
        choosedList.add("军事");
        choosedList.add("历史");
        choosedList.add("社会");
        choosedList.add("财经");
        choosedList.add("娱乐");


        unChoosedList.add("体育");
        unChoosedList.add("时尚");
        unChoosedList.add("房产");
        unChoosedList.add("论坛");
        unChoosedList.add("博客");
        unChoosedList.add("健康");
        unChoosedList.add("轻松一刻");
        unChoosedList.add("直播");
        unChoosedList.add("段子");
        unChoosedList.add("彩票");
        unChoosedList.add("直播");
        unChoosedList.add("段子");
        unChoosedList.add("彩票");
        unChoosedList.add("直播");
        unChoosedList.add("段子");
        unChoosedList.add("彩票");

    }
}

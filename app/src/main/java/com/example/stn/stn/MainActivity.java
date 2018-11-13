package com.example.stn.stn;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends BaseActivity {

    //splash显示时间
    private final int SPLASH_DISPLAY_LENGHT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        LinearLayout llLayout = new LinearLayout(this);
        super.setContentView(llLayout);

        ImageView imageView = new ImageView(this);
        ViewGroup.LayoutParams ivparam = new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(ivparam);
        imageView.setImageResource(R.mipmap.ic_splash);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        llLayout.addView(imageView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(SPLASH_DISPLAY_LENGHT);
        llLayout.startAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new AnimationImpl());



    }
    private class AnimationImpl implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            skip(); // 动画结束后跳转到别的页面
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
    private void skip() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        MainActivity.this.startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }





}

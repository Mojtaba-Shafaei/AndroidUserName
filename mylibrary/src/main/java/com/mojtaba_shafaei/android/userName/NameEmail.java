package com.mojtaba_shafaei.android.userName;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class NameEmail extends RelativeLayout {
    private final String TAG = "NameEmail";
    private ViewGroup root;
    private TextView name, email;
    private ImageView arrowDownUp;

    private OnClickListener onClickListener;
    public final static int LTR = 0;
    public final static int RTL = 1;

    private int rotation = 0;

    @IntDef({LTR, RTL})
    @Retention(RetentionPolicy.SOURCE)
    @Documented
    public @interface Direction {
    }

    public NameEmail(Context context) {
        super(context);
        init(context, null);
    }

    public NameEmail(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NameEmail(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(21)
    public NameEmail(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    void init(Context context, AttributeSet attrs) {
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.NameEmail,
                0, 0);

        inflate(context, R.layout.ltr, this);
        root = findViewById(R.id.nameEmail_root);
        name = findViewById(R.id.textView_name);
        email = findViewById(R.id.textView_email);
        arrowDownUp = findViewById(R.id.btnExpandUserInfo);


        //<editor-fold desc="Read Attributes">
        try {

            final String nameString = a.getString(R.styleable.NameEmail_name);
            name.setText(nameString);

            final String emailString = a.getString(R.styleable.NameEmail_email);
            email.setText(emailString);

            @ColorInt final int nameTextColor = a.getColor(R.styleable.NameEmail_nameTextColor, Color.WHITE);
            name.setTextColor(nameTextColor);

            @ColorInt final int emailTextColor = a.getColor(R.styleable.NameEmail_emailTextColor, getResources().getColor(android.R.color.secondary_text_dark));
            email.setTextColor(emailTextColor);

            @ColorRes final int background = a.getResourceId(R.styleable.NameEmail_android_background, R.color._transparent);
            root.setBackgroundResource(background);

            @DrawableRes final Integer arrow = a.getResourceId(R.styleable.NameEmail_arrow, R.drawable.ic_arrow_dark);
            arrowDownUp.setImageResource(arrow);

        } catch (Exception e) {
            Log.e(TAG, "readAttrs: " + e.getMessage());
        } finally {
            a.recycle();
        }
        //</editor-fold>

        try {
            root.setEnabled(true);
            root.setClickable(true);

            root.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.setId(getId());
                    if (onClickListener != null) {
                        onClickListener.onClick(view);
                    }
                    toggleArrowImage();
                }
            });
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Exception2", e);
        }

    }


    public void setName(@StringRes int text) {
        name.setText(text);
    }

    public void setName(String text) {
        name.setText(text);
    }

    public void setNameTypeface(Typeface nameTypeface) {
        name.setTypeface(nameTypeface);
    }


    public void setEmail(@StringRes int text) {
        email.setText(text);
    }

    public void setEmail(String text) {
        email.setText(text);
    }

    public void setEmailTypeface(Typeface nameTypeface) {
        email.setTypeface(nameTypeface);
    }


    public void toggleArrowImage() {
        rotation += 180;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(arrowDownUp.getRotation(), rotation);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(final ValueAnimator valueAnimator) {
                arrowDownUp.setRotation((Float) valueAnimator.getAnimatedValue());
            }
        });
        valueAnimator.setDuration(300);
        valueAnimator.start();
    }


    public void setOnClickListener(final OnClickListener l) {
        this.onClickListener = l;
    }

    @Override
    public void setClickable(boolean clickable) {
        root.setClickable(clickable);
        arrowDownUp.setVisibility(!clickable ? GONE : VISIBLE);
    }


}

package com.imsle.cqceteasayschool.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.activity.ScoreQueryActivity;
import com.imsle.cqceteasayschool.activity.TimetableActivity;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述:
 */
public class FuncFragment extends ImmersionFragment {

    @BindView(R.id.func_card_timeTable)
    CardView func_card_timeTable;
    @BindView(R.id.func_card_calendar)
    CardView func_card_calendar;
    @BindView(R.id.func_card_exam)
    CardView func_card_exam;
    @BindView(R.id.func_card_note)
    CardView func_card_note;
    @BindView(R.id.func_card_score)
    CardView func_card_score;
    @BindView(R.id.func_card_way)
    CardView func_card_way;

    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_func,null);
        ButterKnife.bind(this,view);
        return view;
    }


    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this)
                .statusBarColorTransformEnable(true)
                .autoStatusBarDarkModeEnable(true)
                .init();
    }

    @OnClick(R.id.func_card_timeTable)
    public void timeTableOnClick(){
        Intent intent = new Intent(getContext(), TimetableActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_still);
    }
    @OnClick(R.id.func_card_calendar)
    public void calendarOnClick(){
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord("当前功能并未开通!"+"\n"+"敬请期待!")
                .create();
        tipDialog.show();
        tipDialog.setCanceledOnTouchOutside(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        },2000);
    }
    @OnClick(R.id.func_card_score)
    public void scoreOnClick(){
        Intent intent = new Intent(getContext(), ScoreQueryActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_still);
    }
    @OnClick(R.id.func_card_note)
    public void noteOnClick(){
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord("当前功能并未开通!"+"\n"+"敬请期待!")
                .create();
        tipDialog.show();
        tipDialog.setCanceledOnTouchOutside(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        },2000);
    }
    @OnClick(R.id.func_card_exam)
    public void examOnClick(){
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord("当前功能并未开通!"+"\n"+"敬请期待!")
                .create();
        tipDialog.show();
        tipDialog.setCanceledOnTouchOutside(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        },2000);
    }
    @OnClick(R.id.func_card_way)
    public void wayOnClick(){
        QMUITipDialog tipDialog = new QMUITipDialog.Builder(getContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord("当前功能并未开通!"+"\n"+"敬请期待!")
                .create();
        tipDialog.show();
        tipDialog.setCanceledOnTouchOutside(true);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        },2000);
    }

}

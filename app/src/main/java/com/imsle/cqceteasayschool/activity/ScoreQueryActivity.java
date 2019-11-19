package com.imsle.cqceteasayschool.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.core.TableConfig;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.format.bg.ICellBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.model.ScoreTable;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScoreQueryActivity extends QMUIActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.score_changeTerm)
    LinearLayout score_changeTerm;
    @BindView(R.id.score_now)
    TextView score_now;
    @BindView(R.id.score_doing)
    TextView score_doing;
    @BindView(R.id.score_table)
    SmartTable<ScoreTable> score_table;
    @BindView(R.id.score_bottomBox)
    QMUILinearLayout score_bottomBox;
    @BindView(R.id.score_headerBox)
    QMUILinearLayout score_headerBox;

    private int mScreenWidth;
    private float mShadowAlpha = 0.4f;
    private int mShadowElevationDp = 7;
    private int mRadius;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_query);
        ButterKnife.bind(this);
        initTopBar();
        loadShadow();
        initTable();
    }
    /**
     * 函数名: loadShadow
     * 函数说明: 加载阴影
     * 创建时间: 2019/11/13 4:14
     * @param:
     * @return: void
     */
    private void loadShadow(){
        mRadius = QMUIDisplayHelper.dp2px(this, 15);
        score_headerBox.setRadiusAndShadow(mRadius, QMUIDisplayHelper.dp2px(this, mShadowElevationDp), mShadowAlpha);
        score_bottomBox.setRadiusAndShadow(mRadius,QMUIDisplayHelper.dp2px(this, mShadowElevationDp), mShadowAlpha);
    }
    /***
     * 函数名: initTopBar
     * 函数说明: 初始化topbar和沉浸式状态栏
     * 创建时间: 2019/11/19 9:39
     * @param:
     * @return: void
     */
    public void initTopBar(){

        mTopBar.addLeftImageButton(R.mipmap.left,R.id.topbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
                QMUIStatusBarHelper.translucent(getWindow(),getResources().getColor(R.color.myFragmentTopBackColor));
            }
        });
        mTopBar.setTitle("成绩查询");
        //改为浅色
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        mTopBar.setBackgroundColor(getResources().getColor(R.color.homeToolBarColor));
    }

    /***
     * 函数名: initTable
     * 函数说明: 初始化表格
     * 创建时间: 2019/11/19 9:39
     * @param:
     * @return: void
     */
    private void initTable(){
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;

        List<ScoreTable> scoreTables = new ArrayList<>();
        for (int i = 0 ; i < 15 ; i++){
            ScoreTable scoreTable = new ScoreTable();
            scoreTable.setName("PHP程序设计")
                    .setCredit("12")
                    .setAchievement("1")
                    .setScore("61")
                    .setType("正常考试")
                    .setLesson("专业必修");
            scoreTables.add(scoreTable);
        }
        for (int i = 0 ; i < 15 ; i++){
            ScoreTable scoreTable = new ScoreTable();
            scoreTable.setName("Java程序设计")
                    .setCredit("12")
                    .setAchievement("1")
                    .setScore("59")
                    .setType("正常考试")
                    .setLesson("专业必修");
            scoreTables.add(scoreTable);
        }
        score_table.setData(scoreTables);

        TableConfig tableConfig = score_table.getConfig();
        tableConfig.setShowXSequence(false);//关闭行号
        tableConfig.setShowYSequence(false);//关闭列号
        tableConfig.setMinTableWidth(0);
        tableConfig.setHorizontalPadding(15);
        tableConfig.setColumnTitleHorizontalPadding(15);
        tableConfig.setShowTableTitle(false);//关闭标题
        tableConfig.setContentCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, CellInfo cellInfo, Paint paint) {

            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                if (cellInfo.col == 5){
                    String score = cellInfo.value;
                    if (Integer.parseInt(score) >= 60){
                        return getResources().getColor(R.color.scorePassTextColor);
                    }else{
                        return getResources().getColor(R.color.scoreNoPassTextColor);
                    }
                }
                return 0;
            }
        });

    }

    QMUITipDialog tipDialog;
    @OnClick(R.id.score_changeTerm)
    public void changeTermOnClick(){
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord("当前更改学期功能并未开通!"+"\n"+"敬请期待!")
                .create();
        tipDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        },2000);
    }



}

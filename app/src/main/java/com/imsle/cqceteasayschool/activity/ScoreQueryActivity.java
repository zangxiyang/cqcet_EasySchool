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
import com.imsle.cqceteasayschool.App;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.model.KCachievement;
import com.imsle.cqceteasayschool.model.ScoreTable;
import com.imsle.cqceteasayschool.model.StuCredit;
import com.imsle.cqceteasayschool.service.InfoClient;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    QMUITipDialog tipDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_query);
        ButterKnife.bind(this);
        initTopBar();
        loadShadow();
        initScore();
        initTable();
    }

    /**
     * 函数名: loadShadow
     * 函数说明: 加载阴影
     * 创建时间: 2019/11/13 4:14
     *
     * @param:
     * @return: void
     */
    private void loadShadow() {
        mRadius = QMUIDisplayHelper.dp2px(this, 15);
        score_headerBox.setRadiusAndShadow(mRadius, QMUIDisplayHelper.dp2px(this, mShadowElevationDp), mShadowAlpha);
        score_bottomBox.setRadiusAndShadow(mRadius, QMUIDisplayHelper.dp2px(this, mShadowElevationDp), mShadowAlpha);
    }

    /***
     * 函数名: initTopBar
     * 函数说明: 初始化topbar和沉浸式状态栏
     * 创建时间: 2019/11/19 9:39
     * @param:
     * @return: void
     */
    public void initTopBar() {

        mTopBar.addLeftImageButton(R.mipmap.left, R.id.topbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
                QMUIStatusBarHelper.translucent(getWindow(), getResources().getColor(R.color.myFragmentTopBackColor));
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
    private void initTable() {
        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenWidth = outMetrics.widthPixels;



        List<ScoreTable> scoreTables = new ArrayList<>();
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord("正在加载")
                .create();
        tipDialog.setCanceledOnTouchOutside(false);
        tipDialog.show();
        InfoClient infoClient = InfoClient.getInstance();
        ArrayList<KCachievement> kCachievements = infoClient.getStuCachievement(App.jwxtCookie);
        infoClient.setKCachievementOnFinishEvent(new InfoClient.InfoEvent() {
            @Override
            public void OnFinishEvent() {
                scoreTables.clear();
                for (KCachievement i : kCachievements) {
                    ScoreTable scoreTable = new ScoreTable();
                    scoreTable.setName(i.getCourseName());
                    scoreTable.setLesson(i.getCourseNature());
                    scoreTable.setType(i.getExaminationNature());
                    scoreTable.setCredit(String.valueOf(i.getCredit()));
                    scoreTable.setAchievement(String.valueOf(i.getAchievementPoint()));
                    scoreTable.setScore(i.getAchievenment());
                    scoreTables.add(scoreTable);
                }
                score_table.setData(scoreTables);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tipDialog.dismiss();
                    }
                });

            }
        });

        TableConfig tableConfig = score_table.getConfig();
        tableConfig.setShowXSequence(false);//关闭行号
        tableConfig.setShowYSequence(false);//关闭列号
        tableConfig.setMinTableWidth(mScreenWidth);
        tableConfig.setHorizontalPadding(15);
        tableConfig.setColumnTitleHorizontalPadding(5);
        tableConfig.setShowTableTitle(false);//关闭标题
        tableConfig.setContentCellBackgroundFormat(new ICellBackgroundFormat<CellInfo>() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, CellInfo cellInfo, Paint paint) {

            }

            @Override
            public int getTextColor(CellInfo cellInfo) {
                if (cellInfo.col == 5) {
                    String score = cellInfo.value;
                    if (isNumeric(score)) {
                        if (Float.parseFloat(score) >= 60.00f) {
                            return getResources().getColor(R.color.scorePassTextColor);
                        } else {
                            return getResources().getColor(R.color.scoreNoPassTextColor);
                        }
                    }else {
                        return getResources().getColor(R.color.scorePassTextColor);
                    }
                }

                return 0;
            }
        });

    }


    @OnClick(R.id.score_changeTerm)
    public void changeTermOnClick() {
        tipDialog = new QMUITipDialog.Builder(this)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_INFO)
                .setTipWord("当前更改学期功能并未开通!" + "\n" + "敬请期待!")
                .create();
        tipDialog.setCanceledOnTouchOutside(true);
        tipDialog.show();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                tipDialog.dismiss();
            }
        }, 2000);
    }


    public static boolean isNumeric(String str) {
        // 该正则表达式可以匹配所有的数字 包括负数
        Pattern pattern = Pattern.compile("-?[0-9]+\\.?[0-9]*");
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        Matcher isNum = pattern.matcher(bigStr); // matcher是全匹配
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
    /***
     * 函数名: initScore
     * 函数说明: 初始化学分
     * 创建时间: 2019/11/20 2:55
     * @param:
     * @return: void
     */
    public void initScore(){
        StuCredit stuCredit = new StuCredit();
        InfoClient infoClient = InfoClient.getInstance();
        stuCredit = infoClient.getStuCredit(App.jwxtCookie);
        StuCredit finalStuCredit = stuCredit;
        infoClient.setScoreEvent(new InfoClient.InfoEvent() {
            @Override
            public void OnFinishEvent() {
                String now = String.valueOf(finalStuCredit.getFinishedCredit());
                String doing = String.valueOf(finalStuCredit.getBeingCredit());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        score_now.setText(now);
                        score_doing.setText(doing);
                        //tipDialog.dismiss();
                    }
                });
            }
        });

    }

}

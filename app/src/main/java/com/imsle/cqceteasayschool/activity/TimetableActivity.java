package com.imsle.cqceteasayschool.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.imsle.cqceteasayschool.App;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.base.SubjectRepertory;
import com.imsle.cqceteasayschool.model.KBDetail;
import com.imsle.cqceteasayschool.model.MySubject;
import com.imsle.cqceteasayschool.service.InfoClient;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.zhuangfei.timetable.TimetableView;
import com.zhuangfei.timetable.listener.ISchedule;
import com.zhuangfei.timetable.listener.IWeekView;
import com.zhuangfei.timetable.listener.OnItemBuildAdapter;
import com.zhuangfei.timetable.model.Schedule;
import com.zhuangfei.timetable.view.WeekView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimetableActivity extends QMUIActivity {
    public static final String AD_URL = "";
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.id_weekview)
    WeekView weekView;
    @BindView(R.id.id_timetableView)
    TimetableView timetableView;

    ArrayList<KBDetail> kbDetails = new ArrayList<>();

    List<MySubject> mySubjects = new ArrayList<>();
    //记录切换的周次，不一定是当前周
    int target = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        ButterKnife.bind(this);
        //mySubjects.clear();

        kbDetails.clear();
        mySubjects.clear();

        initTopBar();
        initTimetableView();
        requestData();
    }

    public void initTopBar() {

        mTopBar.addLeftImageButton(R.mipmap.left, R.id.topbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
                QMUIStatusBarHelper.translucent(getWindow(), getResources().getColor(R.color.myFragmentTopBackColor));
            }
        });
        mTopBar.setTitle("课程表");
        //改为浅色
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        mTopBar.setBackgroundColor(getResources().getColor(R.color.homeToolBarColor));
    }

    /**
     * 2秒后刷新界面，模拟网络请求
     */
    private void requestData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0x121);
            }
        }).start();


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x121) {
                QMUITipDialog dialog = new QMUITipDialog.Builder(TimetableActivity.this)
                        .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                        .setTipWord("正在加载")
                        .create();
                dialog.show();

                InfoClient infoClient = InfoClient.getInstance();
                kbDetails = infoClient.getKB(App.jwxtCookie);
                infoClient.setKbDetailOnFinishEvent(new InfoClient.InfoEvent() {
                    @Override
                    public void OnFinishEvent() {

                        for (KBDetail i : kbDetails) {
                            String name, teacher, room;
                            int start, step, weekday;
                            ArrayList<Integer> weekList, sectionList;
                            name = i.getCourseName();
                            teacher = i.getTeacherName();
                            room = i.getClassRoom();
                            start = i.getSections().get(0);
                            step = i.getSections().size();
                            weekday = i.getWeekDay();
                            weekList = i.getWeekDetail();
                            System.out.println(i.getCourseName() + "\n\t" + i.getSection() + "\n\t" + i.getWeekDetail());
                            MySubject mySubject = new MySubject();
                            mySubject.setName(name)
                                    .setTeacher(teacher)
                                    .setRoom(room)
                                    .setStart(start)
                                    .setStep(step)
                                    .setWeekList(weekList)
                                    .setDay(weekday)
                                    .setColorRandom(-1);
                            System.out.println(mySubject);

                        /*SubjectRepertory subjectRepertory = new SubjectRepertory();
                        List<MySubject> list = subjectRepertory.loadDefaultSubjects();
                        for (MySubject i1 : list){
                            System.out.println(i1);
                        }*/
                            mySubjects.add(mySubject);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    weekView.source(mySubjects).showView();
                                    timetableView.source(mySubjects).hideFlaglayout().showView();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                        }
                                    },0);
                                }
                            });
                        }


                    }
                });
            }


            //增加广告
            /*MySubject adSubject=new MySubject();
            adSubject.setName("【广告】");
            adSubject.setStart(1);
            adSubject.setStep(2);
            adSubject.setDay(7);
            List<Integer> list= new ArrayList<>();
            for(int i=1;i<=20;i++) list.add(i);
            adSubject.setWeekList(list);
            adSubject.setUrl(AD_URL);
            mySubjects.add(adSubject);*/


        }
    };

    /**
     * 初始化课程控件
     */
    private void initTimetableView() {

        //设置周次选择属性
        weekView.curWeek(1)
                .callback(new IWeekView.OnWeekItemClickedListener() {
                    @Override
                    public void onWeekClicked(int week) {
                        int cur = timetableView.curWeek();
                        //更新切换后的日期，从当前周cur->切换的周week
                        timetableView.onDateBuildListener()
                                .onUpdateDate(cur, week);
                        timetableView.changeWeekOnly(week);
                    }
                })
                .callback(new IWeekView.OnWeekLeftClickedListener() {
                    @Override
                    public void onWeekLeftClicked() {
                        onWeekLeftLayoutClicked();
                    }
                })
                .isShow(true)
                .showView();

        timetableView.curWeek(1)
                .curTerm("大三下学期")
                .callback(new ISchedule.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, List<Schedule> scheduleList) {
                        display(scheduleList);
                    }
                })
                .callback(new ISchedule.OnItemLongClickListener() {
                    @Override
                    public void onLongClick(View v, int day, int start) {
                        Toast.makeText(TimetableActivity.this,
                                "长按:周" + day + ",第" + start + "节",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .callback(new ISchedule.OnWeekChangedListener() {
                    @Override
                    public void onWeekChanged(int curWeek) {
                        mTopBar.setTitle("第" + curWeek + "周");
                    }
                })
                .callback(new OnItemBuildAdapter() {
                    @Override
                    public void onItemUpdate(FrameLayout layout, TextView textView, TextView countTextView, Schedule schedule, GradientDrawable gd) {
                        super.onItemUpdate(layout, textView, countTextView, schedule, gd);
                        if (schedule.getName().equals("【广告】")) {
                            layout.removeAllViews();
                            ImageView imageView = new ImageView(TimetableActivity.this);
                            imageView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
                            layout.addView(imageView);
                            String url = (String) schedule.getExtras().get(MySubject.EXTRAS_AD_URL);

                            Glide.with(TimetableActivity.this)
                                    .load(url)
                                    .into(imageView);

                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(TimetableActivity.this, "进入广告网页链接", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                })
                .showView();
    }


    /**
     * 周次选择布局的左侧被点击时回调<br/>
     * 对话框修改当前周次
     */
    protected void onWeekLeftLayoutClicked() {
        final String items[] = new String[20];
        int itemCount = weekView.itemCount();
        for (int i = 0; i < itemCount; i++) {
            items[i] = "第" + (i + 1) + "周";
        }
        target = -1;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置当前周");
        builder.setSingleChoiceItems(items, timetableView.curWeek() - 1,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        target = i;
                    }
                });
        builder.setPositiveButton("设置为当前周", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (target != -1) {
                    weekView.curWeek(target + 1).updateView();
                    timetableView.changeWeekForce(target + 1);
                }
            }
        });
        builder.setNegativeButton("取消", null);
        builder.create().show();
    }

    /**
     * 显示内容
     *
     * @param beans
     */
    protected void display(List<Schedule> beans) {
        String str = "";
        for (Schedule bean : beans) {
            str += bean.getName() + "," + bean.getWeekList().toString() + "," + bean.getStart() + "," + bean.getStep() + "\n";
        }
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


}

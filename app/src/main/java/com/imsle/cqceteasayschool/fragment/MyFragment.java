package com.imsle.cqceteasayschool.fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.gyf.immersionbar.components.ImmersionOwner;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.activity.AboutActivity;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.qmuiteam.qmui.layout.QMUILinearLayout;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.grouplist.QMUICommonListItemView;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by Seale on 2019/11/11.
 * Author:臧锡洋
 * 功能描述:
 */
public class MyFragment extends ImmersionFragment{
    @BindView(R.id.my_userName)
    TextView my_UserName;
    @BindView(R.id.my_infoBox)
    QMUILinearLayout my_infoBox;
    @BindView(R.id.my_bottom_groupListView)
    QMUIGroupListView my_bottom_groupListView;
    @BindView(R.id.my_bottomContainer)
    QMUILinearLayout my_bottomContainer;
    @BindView(R.id.my_last_groupListView)
    QMUIGroupListView my_last_groupListView;
    @BindView(R.id.my_lastContainer)
    QMUILinearLayout my_lastContainer;
    @BindView(R.id.fillView)
    View fillView;

    private float mShadowAlpha = 0.4f;
    private int mShadowElevationDp = 7;
    private int mRadius;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  LayoutInflater.from(getContext()).inflate(R.layout.fragment_my,null);
        mRadius = QMUIDisplayHelper.dp2px(getContext(), 15);
        ButterKnife.bind(this,view);
        loadShadow();
        initGroupListView();
        return view;
    }

    /**
     * 函数名: loadShadow
     * 函数说明: 加载阴影
     * 创建时间: 2019/11/13 4:14
     * @param:
     * @return: void
     */
    private void loadShadow(){
        my_infoBox.setRadiusAndShadow(mRadius,QMUIDisplayHelper.dp2px(getActivity(), mShadowElevationDp), mShadowAlpha);
        my_bottomContainer.setRadiusAndShadow(mRadius,QMUIDisplayHelper.dp2px(getActivity(), mShadowElevationDp), mShadowAlpha);
        my_lastContainer.setRadiusAndShadow(mRadius,QMUIDisplayHelper.dp2px(getActivity(), mShadowElevationDp), mShadowAlpha);
    }

    /***
     * 函数名: initGroupListView
     * 函数说明: 加载我的界面底部菜单
     * 创建时间: 2019/11/13 4:14
     * @param:
     * @return: void
     */
    private void initGroupListView(){

        my_bottom_groupListView.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NONE);
        my_last_groupListView.setSeparatorStyle(QMUIGroupListView.SEPARATOR_STYLE_NONE);
        /**************************************/
        QMUICommonListItemView returnsQuestionItem = my_bottom_groupListView.createItemView("问题反馈");
        returnsQuestionItem.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.task_red));
        returnsQuestionItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        ImageView right_img = new ImageView(getContext());
        right_img.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.right));
        returnsQuestionItem.addAccessoryCustomView(right_img);

        /***********************************/
        QMUICommonListItemView QqunItem = my_bottom_groupListView.createItemView("官方QQ群");
        QqunItem.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.qq_normal));
        QqunItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        ImageView right_img1 = new ImageView(getContext());
        right_img1.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.right));
        QqunItem.addAccessoryCustomView(right_img1);

        /***********************************/
        QMUICommonListItemView aboutItem = my_bottom_groupListView.createItemView("关于");
        aboutItem.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.about));
        aboutItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        ImageView right_img2 = new ImageView(getContext());
        right_img2.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.right));
        aboutItem.addAccessoryCustomView(right_img2);

        /***********************************/
        QMUICommonListItemView websiteItem = my_bottom_groupListView.createItemView("官方网站");
        websiteItem.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.website));
        websiteItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        ImageView right_img3 = new ImageView(getContext());
        right_img3.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.right));
        websiteItem.addAccessoryCustomView(right_img3);


        /***********************************/
        QMUICommonListItemView adviceItem = my_bottom_groupListView.createItemView("未来开发建议");
        adviceItem.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.returns));
        adviceItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_CUSTOM);
        ImageView right_img4 = new ImageView(getContext());
        right_img4.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.right));
        adviceItem.addAccessoryCustomView(right_img4);

        /***********************************/
        QMUICommonListItemView logoutItem = my_bottom_groupListView.createItemView("退出登录");
        logoutItem.setImageDrawable(ContextCompat.getDrawable(getContext(), R.mipmap.logout));
        logoutItem.setAccessoryType(QMUICommonListItemView.ACCESSORY_TYPE_NONE);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v instanceof QMUICommonListItemView) {
                    CharSequence text = ((QMUICommonListItemView) v).getText();
                    Toast.makeText(getActivity(), text + " is Clicked", Toast.LENGTH_SHORT).show();
                }
            }
        };

        QMUIGroupListView.newSection(getContext())
                .addItemView(returnsQuestionItem,onClickListener)
                .addItemView(QqunItem, onClickListener)
                .addItemView(aboutItem, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), AboutActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_still);
                    }
                })
                .addItemView(websiteItem, onClickListener)
                .addTo(my_bottom_groupListView);

        QMUIGroupListView.newSection(getContext())
                .addItemView(adviceItem,onClickListener)
                .addItemView(logoutItem,onClickListener)
                .addTo(my_last_groupListView);


    }


    @Override
    public void initImmersionBar() {

        ImmersionBar.with(this)
                .statusBarView(fillView)
                .statusBarColor(R.color.myFragmentTopBackColor)
                .autoStatusBarDarkModeEnable(true)
                .init();
    }
}

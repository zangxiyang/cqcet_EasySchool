package com.imsle.cqceteasayschool.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.SimpleImmersionOwner;
import com.imsle.cqceteasayschool.R;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.grouplist.QMUIGroupListView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutActivity extends QMUIActivity {

    @BindView(R.id.topbar)
    QMUITopBar mTopBar;
    @BindView(R.id.version)
    TextView mVersionTextView;
    @BindView(R.id.about_list)
    QMUIGroupListView mAboutGroupListView;
    @BindView(R.id.copyright)
    TextView mCopyrightTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = LayoutInflater.from(this).inflate(R.layout.activity_about, null);
        ButterKnife.bind(this, root);
        initTopBar();
        setContentView(root);
        initList();
    }

    public void initTopBar(){

        mTopBar.addLeftImageButton(R.mipmap.left,R.id.topbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_still, R.anim.slide_out_right);
                QMUIStatusBarHelper.translucent(getWindow(),getResources().getColor(R.color.myFragmentTopBackColor));
            }
    });
        mTopBar.setTitle(R.string.about_title);
        //改为浅色
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        mTopBar.setBackgroundColor(getResources().getColor(R.color.homeToolBarColor));


    }

    public void initList(){
        QMUIGroupListView.newSection(this)
                .addItemView(mAboutGroupListView.createItemView(getResources().getString(R.string.about_item_homepage)), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"访问官网被点击",Toast.LENGTH_LONG).show();
                        Bundle bundle = new Bundle();
                        bundle.putString("url","https://www.imsle.com");
                        Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent,bundle);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_still);
                    }
                })
                .addItemView(mAboutGroupListView.createItemView(getResources().getString(R.string.about_item_github)), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"访问Github被点击",Toast.LENGTH_LONG).show();
                        String url = "https://github.com/zangxiyang/cqcet_EasySchool";
                        Bundle bundle = new Bundle();
                        bundle.putString("url",url);
                        Intent intent = new Intent(getApplicationContext(),WebViewActivity.class);
                        intent.putExtras(bundle);
                        startActivity(intent,bundle);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_still);
                    }
                })
                .addTo(mAboutGroupListView);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
        String currentYear = dateFormat.format(new java.util.Date());
        mCopyrightTextView.setText(String.format(getResources().getString(R.string.about_copyright), currentYear));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        QMUIStatusBarHelper.setStatusBarDarkMode(this);
    }
    
}

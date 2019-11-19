package com.imsle.cqceteasayschool.activity;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.imsle.cqceteasayschool.App;
import com.imsle.cqceteasayschool.R;
import com.imsle.cqceteasayschool.model.UserDetail;
import com.imsle.cqceteasayschool.model.UserLogin;
import com.imsle.cqceteasayschool.utils.ZhxyUtil;
import com.qmuiteam.qmui.arch.QMUIActivity;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends QMUIActivity {

    @BindView(R.id.login_button)
    Button login_button;
    @BindView(R.id.login_username)
    EditText login_username;
    @BindView(R.id.login_password)
    EditText login_password;
    @BindView(R.id.topbar)
    QMUITopBar mTopBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initTopBar();

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
        mTopBar.setTitle("登录");
        QMUIStatusBarHelper.setStatusBarLightMode(this);
    }

    /***
     * 函数名: login
     * 函数说明: 进行登录逻辑的处理
     * 创建时间: 2019/11/18 22:23
     * @param:
     * @return: void
     */
    @OnClick(R.id.login_button)
    public void login(){
        String username , pwd ;
        username = login_username.getText().toString();
        pwd = login_password.getText().toString();
        if (!username.isEmpty() && !pwd.isEmpty()){
            //当前的帐号密码不为空
            new Thread(new Runnable() {
                @Override
                public void run() {
                    UserLogin userLogin = new UserLogin();
                    userLogin.setUsername(username);
                    userLogin.setPassword(pwd);
                    ZhxyUtil zhxyUtil = new ZhxyUtil(userLogin);
                    App.zhxyCookie = zhxyUtil.getZhxyCookie();
                    App.basicCookie = zhxyUtil.getBasicCookie();
                    System.out.println(App.zhxyCookie);
                    UserDetail userDetail = zhxyUtil.getUserDetail();
                    System.out.println(userDetail.getData().getName());
                }
            }).start();


        }
        if (username.isEmpty() || pwd.isEmpty()){
            //当前帐号或密码为空
            new QMUIDialog.MessageDialogBuilder(this)
                    .setTitle("出错了!")
                    .setMessage("帐号或密码不能为空")
                    .addAction("了解", new QMUIDialogAction.ActionListener() {
                        @Override
                        public void onClick(QMUIDialog dialog, int index) {
                            login_username.setText("");
                            login_password.setText("");
                            dialog.dismiss();
                        }
                    })
                    .create(com.qmuiteam.qmui.R.style.QMUI_Dialog).show();
        }
    }
}

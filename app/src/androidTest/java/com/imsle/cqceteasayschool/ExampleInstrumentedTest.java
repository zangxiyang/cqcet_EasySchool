package com.imsle.cqceteasayschool;

import android.content.Context;

import com.imsle.cqceteasayschool.model.UserLogin;
import com.imsle.cqceteasayschool.utils.ZhxyUtil;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.imsle.cqceteasayschool", appContext.getPackageName());
    }

    @Test
    public void testOK(){
        UserLogin userLogin = new UserLogin();
        userLogin.setUsername("2017180543");
        userLogin.setPassword("1235689az");
        ZhxyUtil zhxyUtil = new ZhxyUtil(userLogin);

        //String cookie = zhxyUtil.getZhxyCookie();
       // String basicCookie = zhxyUtil.getBasicCookie();


        System.out.println(zhxyUtil.getUserDetail());
    }
}

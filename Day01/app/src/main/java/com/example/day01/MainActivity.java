package com.example.day01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.day01.bean.LoginBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//邹鸿运  1810B
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 账号
     */
    private EditText mAccount;
    /**
     * 密码
     */
    private EditText mPassword;
    /**
     * 登录
     */
    private Button mBtnRegister;
    /**
     * 注册
     */
    private Button mBtnZc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        mAccount = (EditText) findViewById(R.id.account);
        mPassword = (EditText) findViewById(R.id.password);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnRegister.setOnClickListener(this);
        mBtnZc = (Button) findViewById(R.id.btn_zc);
        mBtnZc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_register:
                String zh = mAccount.getText().toString();
                String mm = mPassword.getText().toString();
                if (!TextUtils.isEmpty(zh)&&!TextUtils.isEmpty(mm)) {
                    OkHttpClient okHttpClient = new OkHttpClient.Builder()
                            .build();
                    FormBody formBody = new FormBody.Builder()
                            .add("username", zh)
                            .add("password",mm )
                            .build();
                    Request request = new Request.Builder()
                            .post(formBody)
                            //username=zhangsan&password=123456
                            .url("http://yun918.cn/study/public/login")
                            .build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, final IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"账号或密码输入错误", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String string = response.body().string();
                            Gson gson = new Gson();
                            final LoginBean loginBean = gson.fromJson(string, LoginBean.class);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    List<LoginBean.DataBean> data = loginBean.getData();
                                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this,DengluActivity.class));

                                }
                            });

                        }
                    });
                }else {
                    Toast.makeText(this, "完善账号和密码", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.btn_zc:
                startActivity(new Intent(MainActivity.this,Main2Activity.class));
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.POSTING,sticky = true)
    public void show(String s){
        String[] split = s.split(",");
        mAccount.setText(split[0]);
        mPassword.setText(split[1]);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

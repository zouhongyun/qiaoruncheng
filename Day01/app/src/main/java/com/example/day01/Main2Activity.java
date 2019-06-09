package com.example.day01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.day01.bean.AuthBean;
import com.example.day01.bean.LoginBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 用户名
     */
    private EditText mEtName;
    /**
     * 密码
     */
    private EditText mEtPassword;
    /**
     * 确认密码
     */
    private EditText mEtOkPassword;
    /**
     * 手机号
     */
    private EditText mEtPhone;
    /**
     * 验证码
     */
    private EditText mEtYzm;
    /**
     * 注册
     */
    private Button mBtnZhuche;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        inityzm();
        initView();

    }

    private void inityzm() {
        OkHttpClient build = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url("http://yun918.cn/study/public/verify")
                .build();
        build.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Main2Activity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson = new Gson();
                final AuthBean authBean = gson.fromJson(string, AuthBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (authBean.getCode()==200) {
                            String data = authBean.getData(); //这个是验证码把他设置进你的输入框
                            mEtYzm.setText(data);
                        }
                    }
                });

            }
        });
    }

    private void initView() {
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mEtOkPassword = (EditText) findViewById(R.id.et_ok_password);
        mEtPhone = (EditText) findViewById(R.id.et_phone);
        mEtYzm = (EditText) findViewById(R.id.et_yzm);
        mBtnZhuche = (Button) findViewById(R.id.btn_zhuche);
        mBtnZhuche.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn_zhuche:
                final String name = mEtName.getText().toString(); //这个是用户名
                final String one = mEtPassword.getText().toString();//这个是密码
                String two = mEtOkPassword.getText().toString();
                String phone = mEtPhone.getText().toString();//这个是手机
                String yzm = mEtYzm.getText().toString();//这个是验证码

                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(one)&&!TextUtils.isEmpty(two)&&!TextUtils.isEmpty(phone)&&!TextUtils.isEmpty(yzm)) {
                    if (one.equals(two)) {
                        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                .build();

                        String s="username="+name+"&password="+one+"&phone="+phone+"&verify="+yzm;
                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=utf-8"), s);

                        // http://yun918.cn/study/public/register?
                        Request request = new Request.Builder()
                                .url("http://yun918.cn/study/public/register")
                                .post(requestBody)
                                .build();
                        okHttpClient.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Main2Activity.this, "注册失败", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String string = response.body().string();
                                Gson gson = new Gson();
                                final AuthBean loginBean = gson.fromJson(string, AuthBean.class);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (loginBean.getCode()==200) {
                                            Toast.makeText(Main2Activity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                            EventBus.getDefault().postSticky(name+","+one);
                                            finish();
                                        }
                                    }
                                });

                            }
                        });


                    }else {
                        Toast.makeText(Main2Activity.this, "两次输入密码请一致", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "都不为空", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}

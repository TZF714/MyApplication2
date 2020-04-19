package com.example.tzf.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelper mDBOpenHelper;
    private LinearLayout body;
    private TextView tv_back;
    private Button btn_register;
    private EditText id,pwd_1,pwd_again;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        mDBOpenHelper=new DBOpenHelper(this);
    }
    private void initView() {
        id = (EditText) findViewById(R.id.et_register_username);
        pwd_1 = (EditText) findViewById(R.id.et_register_password1);
        pwd_again = (EditText) findViewById(R.id.et_register_password2);
        btn_register = (Button) findViewById(R.id.bt_register);
        tv_back = (TextView) findViewById(R.id.tv_back);
        body = (LinearLayout) findViewById(R.id.register_body);
        tv_back.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                Intent intent1=new Intent(this,MainActivity.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.bt_register:
                String username=id.getText().toString().trim();
                String password=pwd_1.getText().toString().trim();
                if (!TextUtils.isEmpty(username)&&!TextUtils.isEmpty(password)){
                        mDBOpenHelper.add(username,password);
                        Intent intent2=new Intent(this,ViewActivity.class);
                        startActivity(intent2);
                        finish();
                        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                    }

        else {
                    Toast.makeText(this,"未完善信息",Toast.LENGTH_SHORT).show();
                }
        }
    }
}

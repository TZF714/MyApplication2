package com.example.tzf.myapplication2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DBOpenHelper mDBOpenHelper;
    private TextView register;
    private EditText idEdit;
    private EditText passwordEdit;
    private Button login;
    public CheckBox rememberCode;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mDBOpenHelper = new DBOpenHelper(this);
    }
    public void initView() {
        register = (TextView) findViewById(R.id.tv_register);
        idEdit = (EditText) findViewById(R.id.et_username);
        passwordEdit = (EditText) findViewById(R.id.et_code);
        CheckBox checkBoxshow = (CheckBox) findViewById(R.id.check_box1);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        boolean isRemember=pref.getBoolean("check_box2",false);
        checkBoxshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {//实现密码显示
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
        login = (Button) findViewById(R.id.button_login);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        rememberCode = (CheckBox) findViewById(R.id.check_box2);
        ActionBar actionbar = getSupportActionBar();//隐藏标题栏
        if (actionbar != null) {
            actionbar.hide();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_register:
                startActivity(new Intent(this, RegisterActivity.class));
                finish();
                break;
            case R.id.button_login:
                String name=idEdit.getText().toString().trim();
                String password1=passwordEdit.getText().toString().trim();
                editor=pref.edit();
                if (rememberCode.isChecked()){
                    editor.putBoolean("remember_password",true);
                    editor.putString("name",name);
                    editor.putString("password",password1);
                }else {
                    editor.clear();
                }
                if (!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password1)){
                    ArrayList<User>data=mDBOpenHelper.getAllData();
                    boolean match=false;
                    for (int i=0;i<data.size();i++){
                        User user=data.get(i);
                        if (name.equals(user.getName())&&password1.equals(user.getPassword())){
                            match=true;
                            break;
                        }else {
                            match=false;
                        }
                    }
                    if (match){
                        Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(this,ViewActivity.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Toast.makeText(this,"用户名或密码不正确，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this,"请输入用户名或密码",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

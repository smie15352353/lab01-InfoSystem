package com.xiaoshq.infosystem;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.input.InputManager;
import android.media.Image;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.R.attr.fingerprintAuthDrawable;
import static android.R.attr.id;
import static com.xiaoshq.infosystem.R.id.radioGroup;
import static com.xiaoshq.infosystem.R.id.stuNum;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDialog();
        initSnackbar();
        checkLogin();
        checkRegister();
    }

    public void initDialog() {
        //initiate dialog
        String[] Items={this.getString(R.string.camera),this.getString(R.string.album)};
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(R.string.upload)
            .setItems(Items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                   if(i == 0) toastShow(R.string.dialogToast1);
                   else if(i == 1) toastShow(R.string.dialogToast2);
                }
            })
            .setNegativeButton(R.string.dialogCancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    toastShow(R.string.dialogToast3);
                }
            });
        // show
        ImageView img = (ImageView) findViewById(R.id.imgSYSU);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }

    public void toastShow(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
    public void toastShow(int id) { Toast.makeText(this, id, Toast.LENGTH_SHORT).show(); }

    public void initSnackbar() {
        final RadioButton stuBTN = (RadioButton) findViewById(R.id.studentBtn);
        final RadioButton teaBTN = (RadioButton) findViewById(R.id.teacherBTN);
        stuBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stuBTN.isChecked()) {
                    snackbarShow(R.string.radioSnackbar1);
                }
            }
        });
        teaBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (teaBTN.isChecked()) {
                    snackbarShow(R.string.radioSnackbar2);
                }
            }
        });
    }

    public void snackbarShow(String str) {
        Snackbar.make(findViewById(R.id.radioGroup), str, Snackbar.LENGTH_SHORT)
                .setAction(R.string.snackbarYes, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toastShow(R.string.snackbarToast);
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .setDuration(5000)
                .show();
    }
    public void snackbarShow(int id) {
        Snackbar.make(findViewById(R.id.radioGroup), id, Snackbar.LENGTH_SHORT)
                .setAction(R.string.snackbarYes, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        toastShow(R.string.snackbarToast);
                    }
                })
                .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                .setDuration(5000)
                .show();
    }

    public void checkLogin() {
        final TextInputLayout stuNum = (TextInputLayout) findViewById(R.id.stuNum) ;
        final TextInputLayout password = (TextInputLayout) findViewById(R.id.password);
        stuNum.setHint(id2string(R.string.stuNum));
        password.setHint(id2string(R.string.password));

        final Button loginBTN = (Button) findViewById(R.id.login);
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stuNumStr = stuNum.getEditText().getText().toString();
                String passwordStr = password.getEditText().getText().toString();
                if(stuNumStr.isEmpty()) {
                    stuNum.setError(id2string(R.string.stuNumEmpty));
                    stuNum.setErrorEnabled(true);
                }else if(passwordStr.isEmpty()) {
                    password.setError(id2string(R.string.passwordEmpty));
                    password.setErrorEnabled(true);
                }else if(!validateStuNum(stuNumStr) || !validatePassword(passwordStr)) {
                    snackbarShow(R.string.inputError);
                }else {
                    snackbarShow(R.string.loginSuccess);
                    stuNum.setErrorEnabled(false);
                    password.setErrorEnabled(false);
                }
            }
        });
    }

    public void checkRegister() {
        final Button registerBTN = (Button) findViewById(R.id.register);
        registerBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton stuBTN = (RadioButton) findViewById(R.id.studentBtn);
                RadioButton teaBTN = (RadioButton) findViewById(R.id.teacherBTN);
                String grpText1 = stuBTN.getText().toString();
                String grpText2 = teaBTN.getText().toString();
                if (stuBTN.isChecked()) {
                    snackbarShow(id2string(R.string.studentBtn) + id2string(R.string.registerFail));
                }
                else if (teaBTN.isChecked()) {
                    toastShow(id2string(R.string.teacherBtn) + id2string(R.string.registerFail));
                }
            }
        });
    }

    public String id2string(int id) {
        String str;
        str = this.getString(id);
        return str;
    }

    public boolean validateStuNum(String stuNum) {
        if (stuNum.equals("123456")) return true;
        else return false;
    }

    public boolean validatePassword(String password) {
        if (password.equals("6666")) return true;
        else return false;
    }
}

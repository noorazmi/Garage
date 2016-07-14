package com.arsalan.garage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.arsalan.garage.R;
import com.arsalan.garage.models.StatusMessage;
import com.arsalan.garage.uicomponents.CustomButton;
import com.arsalan.garage.uicomponents.CustomEditText;
import com.arsalan.garage.uicomponents.CustomProgressDialog;
import com.arsalan.garage.uicomponents.CustomTextViewEnglish;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.Logger;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.volleytask.VolleyHttpListener;
import com.arsalan.garage.volleytask.VolleyHttpResponse;
import com.arsalan.garage.volleytask.VolleyHttpTask;

import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener{
    CustomEditText editTextEmail;
    CustomEditText editTextPassword;
    CustomEditText editTextConfirmPassword;
    TextView textViewLoginNow;
    CustomTextViewEnglish textViewClose;
    CustomButton btnRegister;
    TextInputLayout inputLayoutEmail;
    TextInputLayout inputLayoutPassword;
    TextInputLayout inputLayoutConfirmPassword;
    CustomTextViewEnglish passwordError;
    ImageView showPassword;
    ImageView showConfirmPassword;
    private CustomProgressDialog progressDialog;
    private int passwordInputType;
    private String calledFrom = "";
    private StatusMessage mStatusMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
    }

    private void init() {

        editTextEmail = (CustomEditText) findViewById(R.id.edittext_email);
        editTextPassword = (CustomEditText) findViewById(R.id.edittext_password);
        editTextConfirmPassword = (CustomEditText) findViewById(R.id.edittext_confirm_password);
        textViewLoginNow = (CustomTextViewEnglish) findViewById(R.id.edittext_login_now);
        textViewClose = (CustomTextViewEnglish) findViewById(R.id.textview_close);
        btnRegister = (CustomButton) findViewById(R.id.button_register);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        inputLayoutConfirmPassword = (TextInputLayout) findViewById(R.id.input_layout_confirm_password);
        showPassword = (ImageView) findViewById(R.id.imageview_show_assword);
        showConfirmPassword = (ImageView) findViewById(R.id.imageview_show_confirm_password);



        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            calledFrom = bundle.getString(AppConstants.CALLED_FROM);
        }

        progressDialog = new CustomProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);

        editTextEmail.setOnFocusChangeListener(this);
        editTextPassword.setOnFocusChangeListener(this);

        showPassword.setOnClickListener(this);
        showConfirmPassword.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        textViewLoginNow.setOnClickListener(this);
        textViewClose.setOnClickListener(this);
        passwordInputType = editTextPassword.getInputType();
    }

    private JSONObject createRegisterUerJson() {
        JSONObject registerJSON = new JSONObject();
        try {
            registerJSON.put(AppConstants.EMAIL, editTextEmail.getText().toString());
            registerJSON.put(AppConstants.PASSWORD, editTextPassword.getText().toString());
            registerJSON.put(AppConstants.CONFIRM_PASSWORD, editTextConfirmPassword.getText().toString());
        } catch (JSONException e) {
            Logger.printLog(e.getMessage());
        }
        return registerJSON;
    }


    private void skipActivity() {
        if(calledFrom.equals(AppConstants.CALLED_BY_POST_ADD)){
            finish();
            return;
        }
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            switch (view.getId()) {
                case R.id.edittext_email:
                    verifyEmailAddress();
                    break;
                case R.id.edittext_password:
                    verifyPassword();
                    break;
                default:
                    return;
            }
        }
    }

    private boolean verifyEmailAddress() {
        String email = editTextEmail.getText().toString();
        if (TextUtils.isEmpty(email.trim())) {
            inputLayoutEmail.setError(getString(R.string.blank_email));
            return false;
        }else if (!Utils.isValidEmail(email.trim())) {
            inputLayoutEmail.setError(getString(R.string.invalid_email));
            return false;
        } else {
            inputLayoutEmail.setError("");
            return true;
        }
    }

    private boolean verifyPassword() {
        String pass = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(pass.trim())) {
            inputLayoutPassword.setError(getString(R.string.error_blank_password));
            return false;
        } else if (pass.trim().length() < 6) {
            inputLayoutPassword.setError(getString(R.string.password_length));
            return false;
        } else if (!pass.trim().matches("[A-Za-z0-9]*")) {
            inputLayoutPassword.setError(getString(R.string.wrong_password));
            return false;
        } else {
            inputLayoutPassword.setError("");
            return true;
        }
    }

    private boolean verifyConfirmPassword() {
        String confirmPassword = editTextConfirmPassword.getText().toString();
        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(confirmPassword.trim())){
            inputLayoutConfirmPassword.setError(getString(R.string.error_blank_mobile));
            return false;
        } else if(!password.equals(confirmPassword)) {
            inputLayoutConfirmPassword.setError(getString(R.string.password_does_not_match));
            return false;
        } else {
            return true;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        checkInternetConnection();
    }

    private boolean checkInternetConnection() {
        if (!Utils.isNetworkAvailable()) {
            Utils.showSnackBar(this, getString(R.string.internet_error_msg));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageview_show_assword:
                togglePasswordVisiblity(editTextPassword);
                break;
            case R.id.imageview_show_confirm_password:
                togglePasswordVisiblity(editTextConfirmPassword);
                break;
            case R.id.button_register:
                registerUser();
                break;
            case R.id.edittext_login_now:
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.textview_close:
                skipActivity();
                break;
            default:return;
        }
    }

    private void registerUser() {
        if (Utils.isNetworkAvailable()) {
            if (verifyEmailAddress()  && verifyPassword() && verifyConfirmPassword()) {
                JSONObject registerJSON = createRegisterUerJson();
                progressDialog.show();
                VolleyHttpTask volleyHttpTask = new VolleyHttpTask(Urls.REGISTER_USER, Request.Method.POST, new VolleyHttpListener() {
                    @Override
                    public void onResult(VolleyHttpResponse volleyHttpResponse) {
                        if (volleyHttpResponse.getResponseModel() instanceof StatusMessage) {
                            mStatusMessage = (StatusMessage) volleyHttpResponse.getResponseModel();
                        }
                    }
                    @Override
                    public void onError(VolleyError error) {
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        if (!Utils.isNetworkAvailable()) {
                            Utils.showSnackBar(RegisterActivity.this, getString(R.string.internet_error_msg));
                            return;
                        }
                        Utils.showSnackBar(RegisterActivity.this, getString(R.string.error_something_went_wrong));
                    }
                });
                volleyHttpTask.setJsonPayload(registerJSON);
                volleyHttpTask.setResponseModelClassFullyQualifiedName(StatusMessage.class.getName());
                volleyHttpTask.start();
            }
        } else {
            Utils.showSnackBar(this, getString(R.string.internet_error_msg));
            return;
        }
    }

    private void togglePasswordVisiblity(EditText editTextPassword) {
        if (editTextPassword.getInputType() == passwordInputType) {
            editTextPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            editTextPassword.setInputType(passwordInputType);
        }
        editTextPassword.setSelection(editTextPassword.getText().length());

    }

}

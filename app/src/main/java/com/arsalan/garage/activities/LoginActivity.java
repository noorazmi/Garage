package com.arsalan.garage.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.arsalan.garage.R;
import com.arsalan.garage.models.StatusMessage;
import com.arsalan.garage.models.UserInfo;
import com.arsalan.garage.uicomponents.CustomButton;
import com.arsalan.garage.uicomponents.CustomEditText;
import com.arsalan.garage.uicomponents.CustomProgressDialog;
import com.arsalan.garage.uicomponents.CustomTextViewEnglish;
import com.arsalan.garage.utils.AppConstants;
import com.arsalan.garage.utils.PrefUtility;
import com.arsalan.garage.utils.Urls;
import com.arsalan.garage.utils.Utils;
import com.arsalan.garage.volleytask.VolleyHttpListener;
import com.arsalan.garage.volleytask.VolleyHttpResponse;
import com.arsalan.garage.volleytask.VolleyHttpTask;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseActivity implements View.OnClickListener, View.OnFocusChangeListener {
    EditText editEmail;
    EditText editPassword;
    Button buttonLogin;
    TextView signUpNow;
    TextView textViewSkip;
    CustomTextViewEnglish buttonForgotPassword;
    TextInputLayout inputLayoutEmail;
    TextInputLayout inputLayoutPassword;
    ImageView showPassword;
    private CustomProgressDialog progressDialog;
    private Dialog forgotPasswordDialog;
    private String calledFrom = "";
    private int passwordInputType;
    private UserInfo mUserInfo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }

    private void init() {
        editPassword = (EditText) findViewById(R.id.edittext_password);
        editEmail = (EditText) findViewById(R.id.edittext_email);
        buttonLogin = (Button) findViewById(R.id.button_sign_in);
        signUpNow = (TextView) findViewById(R.id.textview_signup);
        textViewSkip = (TextView) findViewById(R.id.textview_close);
        buttonForgotPassword = (CustomTextViewEnglish) findViewById(R.id.forgotPassword);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        showPassword = (ImageView) findViewById(R.id.imageview_show_assword);

        progressDialog = new CustomProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            calledFrom = bundle.getString(AppConstants.CALLED_FROM);
        }

        forgotPasswordDialog = new Dialog(this);
        passwordInputType = editPassword.getInputType();
        editEmail.setOnFocusChangeListener(this);
        textViewSkip.setOnClickListener(this);
        signUpNow.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
        showPassword.setOnClickListener(this);
        buttonForgotPassword.setOnClickListener(this);
        editEmail.setOnFocusChangeListener(this);
        editPassword.setOnFocusChangeListener(this);
    }

    private void startHomeActivity() {
        if (calledFrom.equals("post")) {
            finish();
            return;
        }
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    /**
     * Send login details to server
     */

    private void emailLogin() {
        String email = editEmail.getText().toString();
        String pass = editPassword.getText().toString();
        try {
            JSONObject loginJson = new JSONObject();
            loginJson.put("email", email.trim());
            loginJson.put("password", pass.trim());
            loginJson.put("confirm_password", pass.trim());
            progressDialog.show();
            VolleyHttpTask volleyHttpTask = new VolleyHttpTask(Urls.LOGIN, Request.Method.POST, new VolleyHttpListener() {
                @Override
                public void onResult(VolleyHttpResponse volleyHttpResponse) {
                    if (volleyHttpResponse.getResponseModel() instanceof UserInfo) {
                        mUserInfo = (UserInfo) volleyHttpResponse.getResponseModel();
                        loginSuccess(mUserInfo);
                    }
                }

                @Override
                public void onError(VolleyError error) {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    if (!Utils.isNetworkAvailable(LoginActivity.this)) {
                        //TtnUtil.displaySnackBar(getString(R.string.internet_error_msg), snackbarlocation, LoginActivity.this);
                        return;
                    }
                    //TtnUtil.displaySnackBar(getString(R.string.invalid_user_password), snackbarlocation, LoginActivity.this);
                }
            });
            volleyHttpTask.setJsonPayload(loginJson);
            volleyHttpTask.setResponseModelClassFullyQualifiedName(UserInfo.class.getName());
            volleyHttpTask.start();
        } catch (JSONException e) {
            Log.e("Exception", Log.getStackTraceString(e));
        }
    }

    private boolean validatePassword() {
        String pass = editPassword.getText().toString();
        if (TextUtils.isEmpty(pass.trim())) {
            inputLayoutPassword.setError(getString(R.string.error_blank_password));
            editPassword.clearFocus();
            inputLayoutPassword.setErrorEnabled(true);
            return false;
        } else if (!TextUtils.isEmpty(pass) && pass.trim().length() <= 5) {
            inputLayoutPassword.setError(getString(R.string.password_length));
            editPassword.clearFocus();
            inputLayoutPassword.setErrorEnabled(true);
            return false;
        }
        return true;
    }

    private boolean validateEmail() {
        String email = editEmail.getText().toString();
        if (TextUtils.isEmpty(email.trim())) {
            inputLayoutEmail.setError(getString(R.string.blank_email));
            inputLayoutEmail.setErrorEnabled(true);
            return false;
        } else if (!Utils.isValidEmail(email.trim())) {
            inputLayoutEmail.setError(getString(R.string.invalid_email));
            inputLayoutEmail.setErrorEnabled(true);
            return false;
        }
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_sign_in:
                login();
                break;
            case R.id.textview_close:
                startHomeActivity();
                break;
            case R.id.textview_signup:
                signUp();
                break;
            case R.id.imageview_show_assword:
                togglePasswordVisiblity();
                break;
            case R.id.forgotPassword:
                createForgotPasswordDialog();
                break;
            default:
                return;
        }
    }

    private void login() {
        if (checkInternetConnection()) {
            if (validateEmail() && validatePassword()) {
                emailLogin();
            }
        } else {
            //TtnUtil.launchActivity(this, RegisterActivity.class);
            finish();
        }
    }

    private void togglePasswordVisiblity() {
        if (editPassword.getInputType() == passwordInputType) {
            editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            editPassword.setInputType(passwordInputType);
        }
        if (!TextUtils.isEmpty(editPassword.getText().toString())) {
            editPassword.setSelection(editPassword.getText().toString().length());
        }
    }

    private void signUp() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }


    private void createForgotPasswordDialog() {
        // Create custom dialog object
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_forgot_password, null, false);
        final TextInputLayout inputLayoutEmailForgotPassword = (TextInputLayout) view.findViewById(R.id.input_layout_email_forgot_password);
        forgotPasswordDialog.setContentView(view);
        forgotPasswordDialog.setCanceledOnTouchOutside(true);
        forgotPasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        CustomButton resetPassword = (CustomButton) forgotPasswordDialog.findViewById(R.id.resetPassword);
        final TextInputLayout emailError = (TextInputLayout) forgotPasswordDialog.findViewById(R.id.input_layout_email_forgot_password);
        final CustomEditText emailForgotPassword = (CustomEditText) forgotPasswordDialog.findViewById(R.id.edittext_email_forgot_password);
        //forgotPasswordResult = (SwanTextView) forgotPasswordDialog.findViewById(R.id.forgotPasswordResult);
        ImageButton cancelDialog = (ImageButton) forgotPasswordDialog.findViewById(R.id.cancel_dialog);
        forgotPasswordDialog.show();
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(emailForgotPassword.getText().toString())) {
                    inputLayoutEmailForgotPassword.setError(getString(R.string.blank_email));
                } else if (!Utils.isValidEmail(emailForgotPassword.getText().toString())) {
                    inputLayoutEmailForgotPassword.setError(getString(R.string.invalid_email));
                } else {
                    JSONObject forgotPasswordJSON = new JSONObject();
                    try {
                        forgotPasswordJSON.put(AppConstants.EMAIL, emailForgotPassword.getText().toString());
                    } catch (JSONException e) {
                        Log.e("Exception:", Log.getStackTraceString(e));
                    }
                    progressDialog.show();
                    VolleyHttpTask volleyHttpTask = new VolleyHttpTask(Urls.RESET_PASSWORD, Request.Method.POST, new VolleyHttpListener() {
                        @Override
                        public void onResult(VolleyHttpResponse volleyHttpResponse) {
                            StatusMessage statusMessage = (StatusMessage) volleyHttpResponse.getResponseModel();
                            progressDialog.dismiss();
                            if (statusMessage.getStatus().equals(AppConstants.SUCCESS)) {
                                Utils.showSnackBar(LoginActivity.this, getString(R.string.msg_password_sent));
                                forgotPasswordDialog.dismiss();
                            } else {
                                Utils.showSnackBar(LoginActivity.this, statusMessage.getMessage());
                                inputLayoutEmailForgotPassword.setError(statusMessage.getMessage());
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            if (!Utils.isNetworkAvailable()) {
                                Utils.showSnackBar(LoginActivity.this, getString(R.string.internet_error_msg));
                                return;
                            }
                            Utils.showSnackBar(LoginActivity.this, getString(R.string.error_something_went_wrong));
                        }
                    });
                    volleyHttpTask.setJsonPayload(forgotPasswordJSON);
                    volleyHttpTask.setResponseModelClassFullyQualifiedName(StatusMessage.class.getName());
                    volleyHttpTask.start();

                }
            }
        });
        cancelDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPasswordDialog.dismiss();
            }
        });
    }


    private boolean checkInternetConnection() {
        if (!Utils.isNetworkAvailable()) {
            Utils.showSnackBar(this, getString(R.string.internet_error_msg));
            return false;
        }
        return true;
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        switch (view.getId()) {
            case R.id.edittext_email:
                if (hasFocus) {
                    inputLayoutEmail.setError("");
                    inputLayoutEmail.setErrorEnabled(true);
                } else {
                    validateEmail();
                }
                break;
            case R.id.edittext_password:
                if (hasFocus) {
                    inputLayoutPassword.setError("");
                    inputLayoutPassword.setErrorEnabled(true);
                    buttonForgotPassword.setVisibility(View.VISIBLE);
                    showPassword.setVisibility(View.VISIBLE);
                } else {
                    validatePassword();
                }
                break;
            default:
                return;
        }
    }

    private void loginSuccess(UserInfo mUserInfo) {
        progressDialog.dismiss();
        if (mUserInfo.getStatus().equals(AppConstants.SUCCESS)) {
            PrefUtility.saveUsrInfo(mUserInfo);
            if (calledFrom.equals(AppConstants.CALLED_BY_POST_ADD)) {
                finish();
            } else {
                //TtnUtil.launchActivity(this, HomeActivity.class);
                finish();
            }
        } else {
            Utils.showSnackBar(LoginActivity.this, mUserInfo.getMessage());

        }
    }
}

package com.vanity.mobilevanity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vanity.mobilevanity.data.NetworkResult;
import com.vanity.mobilevanity.data.User;
import com.vanity.mobilevanity.manager.NetworkManager;
import com.vanity.mobilevanity.manager.NetworkRequest;
import com.vanity.mobilevanity.manager.PropertyManager;
import com.vanity.mobilevanity.request.FacebookLoginRequest;
import com.vanity.mobilevanity.request.LoginRequest;
import com.vanity.mobilevanity.request.MyInfoRequest;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SplashActivity extends AppCompatActivity {

    LoginManager loginManager;
    CallbackManager callbackManager;

    @BindView(R.id.login_button)
    LoginButton loginButton;

    private final static int RC_SIGN_UP = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        loginButton.setVisibility(View.GONE);

        loginManager = LoginManager.getInstance();
        callbackManager = CallbackManager.Factory.create();

        MyInfoRequest request = new MyInfoRequest(this);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                if (result.getCode() == 1) {
                    moveMainActivity();
                }
                else if (result.getCode() == -1) {
                    if (isFacebookLogin())
                        processFacebookLogin();
                    else resetFacebookAndMoveLoginActivity();
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                resetFacebookAndMoveLoginActivity();
            }
        });

        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                processAfterFacebookLogin();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RC_SIGN_UP)
                moveMainActivity();
            else callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void processAfterFacebookLogin() {
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
            String token = accessToken.getToken();
            String regid = PropertyManager.getInstance().getRegistrationId();
            FacebookLoginRequest request = new FacebookLoginRequest(this, token, regid);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                    if (result.getCode() == 1) {
                        String facebookId = accessToken.getUserId();
                        PropertyManager.getInstance().setFacebookId(facebookId);
                        moveMainActivity();
                    } else if (result.getCode() == 2) {
                        User user = result.getResult();
                        Intent intent = new Intent(SplashActivity.this, FacebookSignUpActivity.class);
                        intent.putExtra(FacebookSignUpActivity.TAG_FACEBOOK_ID, user.getFacebookId());
                        intent.putExtra(FacebookSignUpActivity.TAG_EMAIL, user.getEmail());
                        startActivityForResult(intent, RC_SIGN_UP);
                    } else Toast.makeText(SplashActivity.this, result.getError(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                    Toast.makeText(SplashActivity.this, "sign up fail", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void moveMainActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean isFacebookLogin() {
        if (!TextUtils.isEmpty(PropertyManager.getInstance().getFacebookId()))
            return true;

        return false;
    }

    private void processFacebookLogin() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (!accessToken.getUserId().equals(PropertyManager.getInstance().getFacebookId())) {
            resetFacebookAndMoveLoginActivity();
            return;
        }

        if (accessToken != null) {
            String token = accessToken.getToken();
            String regId = PropertyManager.getInstance().getRegistrationId();
            FacebookLoginRequest request = new FacebookLoginRequest(this, token, regId);
            NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                @Override
                public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                    if (result.getCode() == 1)
                        moveMainActivity();
                    else if (result.getCode() == -1)
                        resetFacebookAndMoveLoginActivity();
                    else if (result.getCode() == 2) {
                        //Toast.makeText(SplashActivity.this, "가입되지 않은 회원입니다.", Toast.LENGTH_SHORT).show();
                        resetFacebookAndMoveLoginActivity();
                    }
                }

                @Override
                public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                    resetFacebookAndMoveLoginActivity();
                }
            });
        } else {
            facebookLogin();
        }
    }

    private void facebookLogin() {
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult result) {
                AccessToken accessToken = AccessToken.getCurrentAccessToken();

                if (!accessToken.getUserId().equals(PropertyManager.getInstance().getFacebookId())) {
                    resetFacebookAndMoveLoginActivity();
                    return;
                }

                FacebookLoginRequest request = new FacebookLoginRequest(SplashActivity.this, accessToken.getToken(), PropertyManager.getInstance().getRegistrationId());

                NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
                    @Override
                    public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                        if (result.getCode() == 1)
                            moveMainActivity();
                        else resetFacebookAndMoveLoginActivity();
                    }

                    @Override
                    public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                        resetFacebookAndMoveLoginActivity();
                    }
                });
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        loginManager.logInWithReadPermissions(this, null);
    }

    private void resetFacebookAndMoveLoginActivity() {
        loginManager.logOut();
        PropertyManager.getInstance().setFacebookId("");
        loginButton.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_fb_login)
    public void onLoginClick(View view) {
        String email = "admin@mobilevanity.com";
        String password = "pwd";
        String regId = "1234";

        LoginRequest request = new LoginRequest(this, email, password, regId);
        NetworkManager.getInstance().getNetworkData(request, new NetworkManager.OnResultListener<NetworkResult<User>>() {
            @Override
            public void onSuccess(NetworkRequest<NetworkResult<User>> request, NetworkResult<User> result) {
                if (result.getCode() == 1) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFail(NetworkRequest<NetworkResult<User>> request, int errorCode, String errorMessage, Throwable e) {
                Toast.makeText(SplashActivity.this, errorCode + " : " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkManager.getInstance().cancelAll(this);
    }
}

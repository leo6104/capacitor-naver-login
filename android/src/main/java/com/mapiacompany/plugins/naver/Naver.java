package com.mapiacompany.plugins.naver;

import android.content.Context;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;

@NativePlugin
public class Naver extends Plugin {
    private OAuthLogin mOAuthLoginModule;

    public void load() {
        super.load();

        // Initialize Naver OAuth Module
        this.mOAuthLoginModule = OAuthLogin.getInstance();
        this.mOAuthLoginModule.init(
                this.getActivity(),
                (String) getConfigValue("OAUTH_CLIENT_ID"),
                (String) getConfigValue("OAUTH_CLIENT_SECRET"),
                (String) getConfigValue("OAUTH_CLIENT_NAME")
        );
    }

    @PluginMethod
    public void logout(PluginCall call) {
        this.mOAuthLoginModule.logout(this.getActivity());
        call.success();
    }

    @PluginMethod
    public void logoutAndDeleteToken(PluginCall call) {
        Context context = this.getContext();
        boolean isSuccessDeleteToken = this.mOAuthLoginModule.logoutAndDeleteToken(context);
        try {
            if (isSuccessDeleteToken) {
                call.success();
            } else {
                call.error(this.mOAuthLoginModule.getLastErrorDesc(context));
            }
        } catch (Exception e) {
            call.error(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void refreshAccessToken(PluginCall call) {
        Context context = this.getContext();
        String accessToken = this.mOAuthLoginModule.refreshAccessToken(context);
        JSObject result = new JSObject();
        result.put("accessToken", accessToken);
        call.success(result);
    }

    @PluginMethod
    public void getState(PluginCall call) {
        OAuthLoginState state = this.mOAuthLoginModule.getState(this.getActivity());
        JSObject result = new JSObject();
        result.put("state", state.name());
        call.success(result);
    }

    @PluginMethod
    public void login(final PluginCall call) {
        this.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Naver.this.mOAuthLoginModule.startOauthLoginActivity(
                        Naver.this.getActivity(),
                        new LoginHandler(Naver.this.getActivity(), call)
                );
            }
        });
    }

    /**
     * OAuth Login 실패 성공에 따른 행동을 핸들링하는 클래스
     */
    private class LoginHandler extends OAuthLoginHandler {
        private PluginCall mCall;
        private Context mContext;

        LoginHandler(Context context, PluginCall call) {
            this.mContext = context;
            this.mCall = call;
        }

        @Override
        public void run(boolean isSuccess) {
            JSObject resultObject = new JSObject();
            try {
                if (isSuccess) {
                    String accessToken = mOAuthLoginModule.getAccessToken(this.mContext);
                    String refreshToken = mOAuthLoginModule.getRefreshToken(this.mContext);
                    long expiresAt = mOAuthLoginModule.getExpiresAt(this.mContext);
                    String tokenType = mOAuthLoginModule.getTokenType(mContext);

                    // Result JSON 생성
                    resultObject.put("accessToken", accessToken);
                    resultObject.put("refreshToken", refreshToken);
                    resultObject.put("expiresAt", expiresAt);
                    resultObject.put("tokenType", tokenType);

                    // Result Callback
                    this.mCall.success(resultObject);
                } else {
                    String errorCode = mOAuthLoginModule.getLastErrorCode(this.mContext).getCode();
                    String errorDescription = mOAuthLoginModule.getLastErrorDesc(this.mContext);

                    // Result Callback
                    this.mCall.error(errorDescription);
                }
            } catch (Exception e) {
                this.mCall.error(e.getMessage());
            }
        }
    }
}

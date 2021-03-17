package com.mapiacompany.plugins.naver;

import android.content.Context;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginConfig;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;

@CapacitorPlugin(name = "Naver")
public class Naver extends Plugin {
    private OAuthLogin mOAuthLoginModule;

    public void load() {
        super.load();

        // Initialize Naver OAuth Module
        this.mOAuthLoginModule = OAuthLogin.getInstance();
        PluginConfig config = getConfig();
        this.mOAuthLoginModule.init(
                getActivity(),
                (String) config.getString("OAUTH_CLIENT_ID"),
                (String) config.getString("OAUTH_CLIENT_SECRET"),
                (String) config.getString("OAUTH_CLIENT_NAME")
        );
    }

    @PluginMethod
    public void logout(PluginCall call) {
        this.mOAuthLoginModule.logout(getActivity());
        call.resolve();
    }

    @PluginMethod
    public void logoutAndDeleteToken(PluginCall call) {
        Context context = getContext();
        boolean isSuccessDeleteToken = this.mOAuthLoginModule.logoutAndDeleteToken(context);
        try {
            if (isSuccessDeleteToken) {
                call.resolve();
            } else {
                call.reject(this.mOAuthLoginModule.getLastErrorDesc(context));
            }
        } catch (Exception e) {
            call.reject(e.getMessage(), e);
        }
    }

    @PluginMethod
    public void refreshAccessToken(PluginCall call) {
        Context context = getContext();
        String accessToken = this.mOAuthLoginModule.refreshAccessToken(context);
        JSObject result = new JSObject();
        result.put("accessToken", accessToken);
        call.resolve(result);
    }

    @PluginMethod
    public void getState(PluginCall call) {
        OAuthLoginState state = this.mOAuthLoginModule.getState(this.getActivity());
        JSObject result = new JSObject();
        result.put("state", state.name());
        call.resolve(result);
    }

    @PluginMethod
    public void login(final PluginCall call) {
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            Naver.this.mOAuthLoginModule.startOauthLoginActivity(
              Naver.this.getActivity(),
              new LoginHandler(call)
            );
          }
        });
    }

    /**
     * OAuth Login 실패 성공에 따른 행동을 핸들링하는 클래스
     */
    private class LoginHandler extends OAuthLoginHandler {
        PluginCall call;
        LoginHandler(PluginCall call) {
          this.call = call;
        }

        @Override
        public void run(boolean isSuccess) {
            Context mContext = getContext();
            JSObject resultObject = new JSObject();
            try {
                if (isSuccess) {
                    String accessToken = mOAuthLoginModule.getAccessToken(mContext);
                    String refreshToken = mOAuthLoginModule.getRefreshToken(mContext);
                    long expiresAt = mOAuthLoginModule.getExpiresAt(mContext);
                    String tokenType = mOAuthLoginModule.getTokenType(mContext);

                    // Result JSON 생성
                    resultObject.put("accessToken", accessToken);
                    resultObject.put("refreshToken", refreshToken);
                    resultObject.put("expiresAt", expiresAt);
                    resultObject.put("tokenType", tokenType);

                    // Result Callback
                    call.resolve(resultObject);
                } else {
                    String errorCode = mOAuthLoginModule.getLastErrorCode(mContext).getCode();
                    String errorDescription = mOAuthLoginModule.getLastErrorDesc(mContext);

                    // Result Callback
                    call.reject(errorDescription);
                }
            } catch (Exception e) {
                call.reject(e.getMessage());
            }
        }
    }
}

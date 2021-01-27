import Foundation
import Capacitor
import NaverThirdPartyLogin

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(Naver)
public class Naver: CAPPlugin {
    private let instance = NaverThirdPartyLoginConnection.getSharedInstance()
    var signInCall: CAPPluginCall?

    public override func load() {
        // 네이버 앱으로 인증하는 방식을 활성화
        if let isNaverAppOauthEnable = getConfigValue("isNaverAppOauthEnable") as? Bool {
            instance?.isNaverAppOauthEnable = isNaverAppOauthEnable
        }

        // SafariViewController에서 인증하는 방식을 활성화
        if let isInAppOauthEnable = getConfigValue("isInAppOauthEnable") as? Bool {
            instance?.isInAppOauthEnable = isInAppOauthEnable
        }

        // 인증 화면을 iPhone의 세로 모드에서만 사용하기
        if (getConfigValue("isOnlyPortraitSupportedInIphone") as? Bool) != nil {
            instance?.isOnlyPortraitSupportedInIphone()
        }

        // 네이버 아이디로 로그인하기 설정
        // 애플리케이션을 등록할 때 입력한 URL Scheme
        if let serviceUrlScheme = getConfigValue("SERVICE_URL_SCHEME") as? String {
            instance?.serviceUrlScheme = serviceUrlScheme;
        }
        // 애플리케이션 등록 후 발급받은 클라이언트 아이디
        if let consumerKey = getConfigValue("OAUTH_CLIENT_ID") as? String {
            instance?.consumerKey = consumerKey;
        }
        // 애플리케이션 등록 후 발급받은 클라이언트 시크릿
        if let consumerSecret = getConfigValue("OAUTH_CLIENT_SECRET") as? String {
            instance?.consumerSecret = consumerSecret;
        }

        // 애플리케이션 이름
        if let oAuthClientName = getConfigValue("OAUTH_CLIENT_NAME") as? String {
            instance?.appName = oAuthClientName;
        }

        instance?.delegate = self;
    }

    @objc func login(_ call: CAPPluginCall) {
        signInCall = call;
        DispatchQueue.main.async {
            self.instance?.requestThirdPartyLogin();
        }
    }
}

extension Naver: NaverThirdPartyLoginConnectionDelegate {
    // 로그인에 성공한 경우 호출
    public func oauth20ConnectionDidFinishRequestACTokenWithAuthCode() {
        var userData: [String: Any] = [
            "accessToken": instance?.accessToken,
            "refreshToken": instance?.refreshToken,
            "expiresAt": instance?.accessTokenExpireDate,
            "tokenType": instance?.tokenType
        ];
        signInCall?.resolve(userData);
    }

    // referesh token
    public func oauth20ConnectionDidFinishRequestACTokenWithRefreshToken() {
        <#code#>
    }

    // 로그아웃
    public func oauth20ConnectionDidFinishDeleteToken() {
        <#code#>
    }

    // 모든 error
    public func oauth20Connection(_ oauthConnection: NaverThirdPartyLoginConnection!, didFailWithError error: Error!) {
        <#code#>
        signInCall?.reject(error.localizedDescription);
    }
}

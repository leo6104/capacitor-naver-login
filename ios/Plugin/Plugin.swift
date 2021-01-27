import Foundation
import Capacitor
import NaverThirdPartyLogin

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(Naver)
public class Naver: CAPPlugin {
    private let naverLogin = NaverThirdPartyLoginConnection.getSharedInstance()

    public override func load() {
        // 네이버 앱으로 인증하는 방식을 활성화
        if let isNaverAppOauthEnable = getConfigValue("isNaverAppOauthEnable") as? Bool {
            naverLogin?.isNaverAppOauthEnable = isNaverAppOauthEnable
        }

        // SafariViewController에서 인증하는 방식을 활성화
        if let isInAppOauthEnable = getConfigValue("isInAppOauthEnable") as? Bool {
            naverLogin?.isInAppOauthEnable = isInAppOauthEnable
        }

        // 인증 화면을 iPhone의 세로 모드에서만 사용하기
        if (getConfigValue("isOnlyPortraitSupportedInIphone") as? Bool) != nil {
            naverLogin?.isOnlyPortraitSupportedInIphone()
        }

        // 네이버 아이디로 로그인하기 설정
        // 애플리케이션을 등록할 때 입력한 URL Scheme
        if let serviceUrlScheme = getConfigValue("SERVICE_URL_SCHEME") as? String {
            naverLogin?.serviceUrlScheme = serviceUrlScheme;
        }
        // 애플리케이션 등록 후 발급받은 클라이언트 아이디
        if let consumerKey = getConfigValue("OAUTH_CLIENT_ID") as? String {
            naverLogin?.consumerKey = consumerKey;
        }
        // 애플리케이션 등록 후 발급받은 클라이언트 시크릿
        if let consumerSecret = getConfigValue("OAUTH_CLIENT_SECRET") as? String {
            naverLogin?.consumerSecret = consumerSecret;
        }

        // 애플리케이션 이름
        if let oAuthClientName = getConfigValue("OAUTH_CLIENT_NAME") as? String {
            naverLogin?.appName = oAuthClientName;
        }
    }

    @objc func login(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""

        DispatchQueue.main.async {
            call.resolve([
                "value": value
            ])
        }
    }
}

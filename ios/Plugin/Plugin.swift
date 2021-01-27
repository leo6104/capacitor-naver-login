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

    @objc func login(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""

        DispatchQueue.main.async {
            call.resolve([
                "value": value
            ])
        }
    }
}

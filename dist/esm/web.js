var __awaiter = (this && this.__awaiter) || function (thisArg, _arguments, P, generator) {
    function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
    return new (P || (P = Promise))(function (resolve, reject) {
        function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
        function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
        function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
        step((generator = generator.apply(thisArg, _arguments || [])).next());
    });
};
import { registerPlugin } from '@capacitor/core';
export class NaverWeb {
    getState() {
        return __awaiter(this, void 0, void 0, function* () {
            return { state: 'Not Implemeneted Yet' };
        });
    }
    login() {
        return __awaiter(this, void 0, void 0, function* () {
            return {
                accessToken: 'Not Implemented Yet',
                refreshToken: 'Not Implemented Yet',
                expiresAt: 'Not Implemented Yet',
                tokenType: 'Barear'
            };
        });
    }
    logout() {
        return __awaiter(this, void 0, void 0, function* () {
            throw new Error('Not Implemented Yet');
        });
    }
    logoutAndDeleteToken() {
        return __awaiter(this, void 0, void 0, function* () {
            throw new Error('Not Implemented Yet');
        });
    }
    refreshAccessToken() {
        return __awaiter(this, void 0, void 0, function* () {
            return { accessToken: 'Not Implemented Yet' };
        });
    }
}
const Naver = /*#__PURE__*/ registerPlugin("Naver", {
    web: new NaverWeb()
});
export { Naver };
//# sourceMappingURL=web.js.map
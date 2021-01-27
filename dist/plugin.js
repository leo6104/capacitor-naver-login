var capacitorPlugin = (function (exports, core) {
    'use strict';

    var __awaiter = (undefined && undefined.__awaiter) || function (thisArg, _arguments, P, generator) {
        function adopt(value) { return value instanceof P ? value : new P(function (resolve) { resolve(value); }); }
        return new (P || (P = Promise))(function (resolve, reject) {
            function fulfilled(value) { try { step(generator.next(value)); } catch (e) { reject(e); } }
            function rejected(value) { try { step(generator["throw"](value)); } catch (e) { reject(e); } }
            function step(result) { result.done ? resolve(result.value) : adopt(result.value).then(fulfilled, rejected); }
            step((generator = generator.apply(thisArg, _arguments || [])).next());
        });
    };
    class NaverWeb extends core.WebPlugin {
        constructor() {
            super({
                name: 'Naver',
                platforms: ['web'],
            });
        }
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
    const Naver = new NaverWeb();
    core.registerPlugin('Naver', { web: Naver });

    exports.Naver = Naver;
    exports.NaverWeb = NaverWeb;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

}({}, capacitorExports));
//# sourceMappingURL=plugin.js.map

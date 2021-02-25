import { WebPlugin } from '@capacitor/core';
import { NaverPlugin } from './definitions';
export declare class NaverWeb extends WebPlugin implements NaverPlugin {
    constructor();
    getState(): Promise<{
        state: string;
    }>;
    login(): Promise<{
        accessToken: string;
        refreshToken: string;
        expiresAt: string;
        tokenType: string;
    }>;
    logout(): Promise<void>;
    logoutAndDeleteToken(): Promise<void>;
    refreshAccessToken(): Promise<{
        accessToken: string;
    }>;
}
declare const Naver: NaverPlugin;
export { Naver };

declare module '@capacitor/core' {
  interface PluginRegistry {
    Naver: NaverPlugin;
  }
}

export interface NaverPlugin {
  logoutAndDeleteToken(): Promise<void>;

  refreshAccessToken(): Promise<{ accessToken: string }>;

  getState(): Promise<{ state: string }>;

  login(): Promise<{ accessToken: string, refreshToken: string, expires: string, tokenType: string }>;

  logout(): void;
}

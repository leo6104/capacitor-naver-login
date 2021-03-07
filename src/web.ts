import { registerPlugin } from '@capacitor/core';
import { NaverPlugin } from './definitions';

export class NaverWeb implements NaverPlugin {
  async getState() {
    return { state: 'Not Implemeneted Yet' };
  }

  async login() {
    return {
      accessToken: 'Not Implemented Yet',
      refreshToken: 'Not Implemented Yet',
      expiresAt: 'Not Implemented Yet',
      tokenType: 'Barear'
    };
  }

  async logout() {
    throw new Error('Not Implemented Yet');
  }

  async logoutAndDeleteToken() {
    throw new Error('Not Implemented Yet');
  }

  async refreshAccessToken() {
    return { accessToken: 'Not Implemented Yet' };
  }
}


const Naver = /*#__PURE__*/ registerPlugin<NaverPlugin>("Naver", {
  web: new NaverWeb()
});

export { Naver };

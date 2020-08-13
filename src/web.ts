import { registerWebPlugin, WebPlugin } from '@capacitor/core';
import { NaverPlugin } from './definitions';

declare const naver: any;

export class NaverWeb extends WebPlugin implements NaverPlugin {
  constructor() {
    super({
      name: 'Naver',
      platforms: ['web'],
    });
  }

  async getState() {
    return { state: 'Not Implemeneted Yet' };
  }

  async login() {
    return {
      accessToken: 'Not Implemented Yet',
      refreshToken: 'Not Implemented Yet',
      expires: 'Not Implemented Yet',
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

const Naver = new NaverWeb();

export { Naver };

registerWebPlugin(Naver);

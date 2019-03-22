const _sessionStorage = window.sessionStorage
const _locationStorage = window.locationStorage

/**
 * 本地存储
 * 登录之后，用于储存Token
 */
const MyStorage = {
  sessionStorage: _sessionStorage,
  locationStorage: _localStorage
};

export { MyStorage }
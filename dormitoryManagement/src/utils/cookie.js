export const Cookie = {
  /**
   * 设置Cookie
   * @param { String } cname key
   * @param { String | Number | Object } cvalue 要存储的值
   * @param { Number } exdays 设置有效时间
   */
  setCookie (cname, cvalue, exdays) {
    let d = new Date();
    d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
    let expires = 'expires=' + d.toUTCString();
    document.cookie = cname + '=' + cvalue + '; ' + expires;
  },
  /**
   * 从Cookie中取值
   * @param { String } cname key
   */
  getCookie (cname) {
    let name = cname + '=';
    let ca = document.cookie.split(';');
    for (let i = 0; i < ca.length; i++) {
      let c = ca[i];
      while (c.charAt(0) === ' ') c = c.substring(1);
      if (c.indexOf(name) !== -1) return c.substring(name.length, c.length);
    }
    return '';
  },
  /**
   * 清除Cookie
   * @param {*} name key
   */
  clearCookie (name) {
    this.setCookie(name, '', -1);
  }
};

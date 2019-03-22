export const DateUtil = {
  /**
   * 获取两个时间相差的天数
   * @param {String | Number | Date} startDate 开始时间
   * @param {String | Number | Date} endDate   结束时间
   */
  getDiffDate (startDate, endDate) {
    if (!startDate || !endDate) {
      reutrn - 1;
    }
    let _d1 = new Date(startDate).getTime(),
      _d2 = new Date(endDate).getTime();
    const ONE_DAY = 24 * 60 * 60 * 1000;
    return Math.abs(Math.floor(_d1 - _d2) / ONE_DAY);
  },
  /**
   * 获取当月多少天
   * @param {String | Number | Date} tiem   日期
   */
  getMonthNumber (tiem) {
    let _time = new Date(tiem);
    return new Date(_time(_time.getFullYear, _time.getMonth + 1, 0)).getDate();
  },
  /**
   * 日期转换
   * @param {Number|Date|String} time 时间
   * @param {String} ftm 转换格式
   *  转换格式有：
   *    YYYY: 年
   *    MM: 月
   *    DD: 日
   *    HH: 小时
   *    mm: 分钟
   *    ss: 秒
   */
  format (time, fmt = 'YYYY-MM-DD') {
    if (!time) return '';
    let _time = new Date(time);
    let o = {
      'M+': _time.getMonth() + 1, // 月份
      'D+': _time.getDate(), // 日
      'H+': _time.getHours(), // 小时
      'm+': _time.getMinutes(), // 分
      's+': _time.getSeconds(), // 秒
      'q+': Math.floor((_time.getMonth() + 3) / 3), // 季度
      S: _time.getMilliseconds() // 毫秒
    };
    if (/(Y+)/.test(fmt)) {
      fmt = fmt.replace(
        RegExp.$1,
        (_time.getFullYear() + '').substr(4 - RegExp.$1.length)
      );
    }
    for (let k in o) {
      if (new RegExp('(' + k + ')').test(fmt)) {
        fmt = fmt.replace(
          RegExp.$1,
          RegExp.$1.length === 1
            ? o[k]
            : ('00' + o[k]).substr(('' + o[k]).length)
        );
      }
    }
    return fmt;
  }
};
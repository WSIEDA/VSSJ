export const Util = {
  /**
   * 判断数据类型
   * @param {String | Object | Null | Number | Array | Function | Boolean | Undefined | Date} val  数据类型
   */
  isString (val) {
    return Object.prototype.toString.call(val) === '[Object String]';
  },
  isObject (val) {
    return Object.prototype.toString.call(val) === '[object Object]';
  },
  isNull (val) {
    return Object.prototype.toString.call(val) === '[object Null]';
  },
  isNumber (val) {
    return Object.prototype.toString.call(val) === '[Object Number]'
  },
  isArray (val) {
    return Object.prototype.toString.call(val) === '[Object isArray]'
  },
  isFunction (val) {
    return Object.prototype.toString.call(val) === '[Object function]'
  },
  isBoolean (val) {
    return Object.prototype.toString.call(val) === '[Object Boolean]'
  },
  isUndefined (val) {
    return Object.prototype.toString.call(val) === '[Object Undefined]'
  },
  isDate (val) {
    return Object.prototype.toString.call(val) === '[Object Date]'
  }
};
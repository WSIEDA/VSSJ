import { GENDER_TYPE, START_TYPE } from '../../config/dormitoryManagement/index'

/**
 * 性别的格式化
 * @param {*} value 
 */
export function formatGenderType (value) {
  let _result = GENDER_TYPE.find(type => {
    return type.value === Number(value);
  });
  return _result ? _result.label : '';
}

/**
 * 启用禁用的格式化
 * @param {*} value 
 */
export function formatStartType (value) {
  let _result = START_TYPE.find(type => {
    return type.value === Number(value);
  });
  return _result ? _result.label : '';
}
import {http} from '../utils/http';

/**
 * 获取token
 */
export function api_findToken () {
    return http.post('/dormitoryManagementSystem/doToken.action');
}
// @import
import out from "./Out";
import { proto_common } from "../msg/common.All";

// 协议数组
const g_oProtocolMap = new Map([
    ["protoBuf_comm", proto_common]
]);

/**
 * 消息识别器
 */
export default class MsgRecognizer {
    /**
     * 私有化类默认构造器
     */
    private constructor() {
    }

    /**
     * 添加协议
     * 
     * @param strName 协议名称
     * @param oProtocol 协议
     */
    static addProtocol(strName: string, oProtocol: any): void {
        if (null == strName ||
            null == oProtocol) {
            return;
        }
        g_oProtocolMap.set(strName, oProtocol);
    }

    /**
     * 根据名称移除协议
     * 
     * @param strName 协议名称
     */
    static removeProtocolByName(strName: string): void {
        if (null != strName) {
            g_oProtocolMap.delete(strName)
        }
    }

    /**
     * 根据消息编码获取消息类
     * 
     * @param nMsgCode 消息编码
     * @param out_oMsgClazzName ( 输出参数 ) 消息类名称
     * @return 消息类
     */
    static getMsgClazzByMsgCode(nMsgCode: number, out_oMsgClazzName: out<string>): any {
        if ("number" != typeof(nMsgCode) || 
            nMsgCode <= 0) {
            return null;
        }

        // 获取协议
        let oProtocol = __getProtocolByMsgCode(nMsgCode);

        if (null == oProtocol) {
            console.warn(`无法找到消息所属协议, msgCode = ${nMsgCode}`);
            return null;
        }

        // 获取消息类名称
        let strMsgClazzName = oProtocol.msgCodeDef[nMsgCode];

        if (null == strMsgClazzName) {
            return null;
        }

        strMsgClazzName = strMsgClazzName.substring(1);

        if (null != out_oMsgClazzName) {
            out_oMsgClazzName.put(strMsgClazzName);
        }

        return oProtocol[strMsgClazzName];
    }
}

///////////////////////////////////////////////////////////////////////

/**
 * 根据消息编码获取协议
 * 
 * @param nMsgCode 消息编号
 * @param 协议对象
 */
function __getProtocolByMsgCode(nMsgCode: number): any {
    if (nMsgCode <= 0) {
        return null;
    }

    for(let oCurrProtocol of g_oProtocolMap.values()) {
        if (null != oCurrProtocol["msgCodeDef"][nMsgCode]) {
            return oCurrProtocol;
        }
    }

    return null;
}

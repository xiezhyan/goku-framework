import DateUtil from "./DateUtil";

/**
 * 输出参数
 */
export default class Out<T> {
    /**
     * 值
     */
    private oVal!: T;

    /**
     * 类参数构造器
     * 
     * @param oVal 值
     */
    constructor() {
    }
    
    /**
     * 获取值
     */
    get(): T {
        return this.oVal;
    }

    /**
     * 设置值
     * 
     * @param oVal 值
     */
    put(oVal: T) {
        this.oVal = oVal;
    }
}
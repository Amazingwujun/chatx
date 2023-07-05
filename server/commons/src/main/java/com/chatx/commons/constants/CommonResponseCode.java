package com.chatx.commons.constants;

/**
 * 响应代码
 *
 * @author Jun
 * @since 1.0.0
 */
public interface CommonResponseCode {
    //@formatter:off

    /*--------------------------------------------
    |                 通用消息代码                 |
    ============================================*/

    /** 服务忙（实际是未知异常） */
    String SERVER_ERROR = "80000001";

    /** 非法的参数 */
    String ILLEGAL_ARGS = "80000002";

    /** http 请求方法错误 */
    String HTTP_METHOD_ERR = "80000003";

    /** 不支持的操作 */
    String UNSUPPORTED_OPERATION = "80000004";

    /** 数据更新失败 */
    String DATA_UPDATE_ERR = "80000005";

    /** 数据创建失败 */
    String DATA_CREATE_ERR = "80000006";

    /** 数据不存在 */
    String DATA_NOT_EXIST = "80000007";

    /** 断路器打开，微服务接口请求失败 */
    String CIRCUIT_BREAKER_OPEN = "80000008";

    /** 请求未认证 */
    String UNAUTHORIZED = "80000009";

    /** 令牌过期 */
    String TOKEN_EXPIRE = "80000010";

    /** 令牌错误 */
    String TOKEN_ERR = "80000011";

    /** 权限不足 */
    String ACCESS_FORBIDDEN = "80000012";

    /** 数据删除失败 */
    String DATA_DELETE_ERR = "80000013";

    /** 数据查询失败 */
    String DATA_QUERY_ERR = "80000014";

    /** 数据已经存在（数据重复） */
    String DATA_ALREADY_EXIST = "80000015";

    /** 执行失败 */
    String EXECUTION_ERR = "80000016";
}

package top.zopx.starter.sms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * top.zopx.starter.sms.entity.SmsResponse
 *
 * @author sanq.Yan
 * @date 2020/4/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsResponse {

    private String code;

    private String message;


}

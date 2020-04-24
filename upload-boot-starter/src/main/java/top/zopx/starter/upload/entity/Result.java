package top.zopx.starter.upload.entity;

import lombok.Builder;
import lombok.Data;

/**
 * top.zopx.starter.upload.entity.Result
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Data
@Builder
public class Result {

    private String uploadFileUrl;

    private String showFileUrl;
}

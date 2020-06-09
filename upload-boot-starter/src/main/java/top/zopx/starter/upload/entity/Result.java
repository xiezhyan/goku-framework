package top.zopx.starter.upload.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * top.zopx.starter.upload.entity.Result
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    private String uploadFileUrl;

    private String showFileUrl;

    private String uploadId;
}

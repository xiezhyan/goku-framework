package top.zopx.starter.upload.service;

import top.zopx.starter.upload.entity.Result;
import top.zopx.starter.upload.entity.UploadFile;

import java.util.List;

/**
 * top.zopx.starter.upload.service.FileManageService
 *
 * @author sanq.Yan
 * @date 2020/4/24
 */
public interface FileManageService {

    /**
     * 简单上传
     */
    Result uploadFile(UploadFile uploadFile);


    /**
     * 删除文件
     */
    List<String> deleteFile(String... keys);

    /**
     * 断点续传
     */
    Result resumeUploadFile(UploadFile uploadFile);


    /**
     * 分片上传
     */
    Result multipartUploadFile(UploadFile uploadFile);

}

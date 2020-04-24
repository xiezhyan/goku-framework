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

    Result uploadFile(UploadFile uploadFile);

    List<Result> uploadFile(List<UploadFile> uploadFiles);

    List<String> deleteFile(String... keys);

}

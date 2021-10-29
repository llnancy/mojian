package com.sunchaser.mojian.uploader.impl.kodo;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.sunchaser.mojian.uploader.AbstractUploader;
import com.sunchaser.mojian.uploader.exception.MoJianUploaderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static com.sunchaser.mojian.uploader.autoconfigure.UploaderProperties.QiNiu;

/**
 * @author sunchaser admin@lilu.org.cn
 * @since JDK8 2021/10/24
 */
@Slf4j
public class QiNiuCloudUploader extends AbstractUploader {

    private static final String BASE_LOG = "上传七牛云";

    private final UploadManager uploadManager;

    private final Auth auth;

    public QiNiuCloudUploader(UploadManager uploadManager, Auth auth) {
        this.uploadManager = uploadManager;
        this.auth = auth;
    }

    @Override
    protected String doUpload(MultipartFile multipartFile, String fileUri) throws Exception {
        try {
            QiNiu qiniu = this.uploaderProperties.getQiniu();
            FileInputStream fileInputStream = (FileInputStream) multipartFile.getInputStream();
            String token = auth.uploadToken(qiniu.getBucketName());
            Response put = uploadManager.put(fileInputStream, fileUri, token, null, null);
            if (!put.isOK()) {
                throw new MoJianUploaderException(BASE_LOG + "出错:" + put.toString());
            }
            DefaultPutRet defaultPutRet = new Gson().fromJson(put.bodyString(), DefaultPutRet.class);
            return qiniu.getDomain() + defaultPutRet.key;
        } catch (QiniuException qe) {
            String errorRes = BASE_LOG + "异常:" + qe.response.toString();
            log.error(errorRes);
            throw new MoJianUploaderException(errorRes);
        }
    }

}

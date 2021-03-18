package com.maydear.core.sample.web.controller;

import com.maydear.core.authorization.annotation.AllowAnonymous;
import com.maydear.core.framework.infrastructure.FileStorageInfrastructure;
import com.maydear.core.framework.io.FileSummary;
import com.maydear.core.framework.util.Assert;
import com.maydear.core.sample.web.model.Attachment;
import com.maydear.core.springboot.web.exception.FailedUploadException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 系统-附件控制器
 *
 * @author joshua.jiang
 * @version 1.0.0
 */
@Slf4j
@RestController
@RequestMapping(path = "/v1/common/attachment")
public class AttachmentController {

    /**
     * 附件配置信息
     */
    private final FileStorageInfrastructure fileStorageInfrastructure;

    public AttachmentController(FileStorageInfrastructure fileStorageInfrastructure) {
        this.fileStorageInfrastructure = fileStorageInfrastructure;
    }

    /**
     * 上传附件到临时目录
     *
     * @param label 文件标识
     * @param file  文件
     * @return 附件信息
     */
    @PostMapping(path = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @AllowAnonymous
    public Attachment upload(@RequestParam(required = false) String label, @RequestParam MultipartFile file) {
        Assert.isNotNull(file, "file不能为空");
        try {

            FileSummary fileSummary = FileSummary.builder()
                .contentType(file.getContentType())
                .createTime(LocalDateTime.now())
                .originalFilename(file.getOriginalFilename())
                .size(file.getSize())
                .tag(label)
                .build();
            fileStorageInfrastructure.writeTemp(fileSummary, file.getBytes());
            return Attachment.builder()
                .code(fileSummary.encodeBase64())
                .label(StringUtils.isBlank(label) ? "" : label)
                .build();
        } catch (IOException e) {
            throw new FailedUploadException();
        }
    }

    @GetMapping(path = "")
    @AllowAnonymous
    public Attachment parse(@RequestParam String fileCode) {
        Assert.isNotNull(fileCode, "fileCode不能为空");
        FileSummary fileSummary = FileSummary.parse(fileCode);
        fileStorageInfrastructure.persistence(fileSummary);
        return Attachment.builder()
            .code(fileSummary.encodeBase64())
            .label(StringUtils.isBlank(fileSummary.getTag()) ? "" : fileSummary.getTag())
            .build();
    }
}

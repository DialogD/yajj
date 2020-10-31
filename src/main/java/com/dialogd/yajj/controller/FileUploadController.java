package com.dialogd.yajj.controller;

import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.common.pojo.WangEditorResultBean;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: DJA
 * @Date: 2019/11/1 10:05
 */
@RestController
@RequestMapping("/file")
@Api(tags = "文件管理FastDFS")
@Slf4j
public class FileUploadController {

    @Autowired
    private FastFileStorageClient client;

    @Value("${image.server}")
    private String imageServer;

    @ApiOperation(value = "上传图片",notes = "上传图片")
    @RequestMapping(value = "/upload")
    public ResultBean upload(@RequestParam("file_upload") MultipartFile file){
        log.info("fileupload...start");
        //获取文件后缀
        String filename = file.getOriginalFilename();
        String endName = filename.substring(filename.lastIndexOf(".") + 1);
        StorePath storePath = null;
        try {
            storePath = client.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(), endName, null);
            StringBuilder builder = new StringBuilder(imageServer).append(storePath.getFullPath());
            log.info("fileupload...end,imageUrl={}",builder.toString());
            return new ResultBean(200,"fastDFSUpload",builder.toString());
        } catch (IOException e) {
            //TODO 结合日志框架写入日志信息
            log.error("文件上传失败={}",e);
            return new ResultBean(500,"服务器繁忙，请稍后再试...");
        }

    }

    @ApiOperation(value = "批量上传图片",notes = "批量上传图片")
    @PostMapping("batchUpload")
    public WangEditorResultBean uploads(@RequestParam("file_uploads") MultipartFile[] files){
        System.out.println("batchUploads....");
        String[] data = new String[files.length];  //字符数组的长度为文件的个数
        try {
            for(int i=0;i<files.length;i++){
                String filename = files[i].getOriginalFilename();
                String endName = filename.substring(filename.lastIndexOf(".") + 1);
                StorePath storePath = client.uploadImageAndCrtThumbImage(files[i].getInputStream(), files[i].getSize(), endName, null);
                StringBuilder builder = new StringBuilder(imageServer).append(storePath.getFullPath());
                data[i] = builder.toString();

            }
        }catch (IOException e) {
            //TODO 结合日志框架写入日志信息
            e.printStackTrace();
            return new WangEditorResultBean("1",null);
        }
        return new WangEditorResultBean("0",data);   //不能放在循环内，try--catch最好在循环外
    }

}

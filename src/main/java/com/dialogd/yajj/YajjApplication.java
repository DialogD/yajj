package com.dialogd.yajj;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@MapperScan("com.dialogd.yajj.mapper")
@Import(FdfsClientConfig.class)
public class YajjApplication {

    public static void main(String[] args) {
        SpringApplication.run(YajjApplication.class, args);
    }

}

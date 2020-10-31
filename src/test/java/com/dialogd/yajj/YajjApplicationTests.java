package com.dialogd.yajj;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.entity.User;
import com.dialogd.yajj.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class YajjApplicationTests {

    @Autowired
    private IUserService userService;

    @Test
    public void getConditionPage(){
        Page page = new Page();
        PageInfo list = userService.getConditionPage("zs", "", page);
        System.out.println(list.toString());
    }

}

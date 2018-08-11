package cn.xiaowenjie;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 *  图标和上传模块
 */
@Component
public class ChartModule {

    @PostConstruct
    public  void init(){
        System.out.println("-----------------------------------------------");
        System.out.println("--                                                                     --");
        System.out.println("--              Chart Module Loaded                 --");
        System.out.println("--                                                                     --");
        System.out.println("-----------------------------------------------");
    }
}

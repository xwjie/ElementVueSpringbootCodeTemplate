package cn.xiaowenjie;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class FavoriteModule {

    @PostConstruct
    public  void init(){
        System.out.println("-----------------------------------------------");
        System.out.println("--                                                                     --");
        System.out.println("--          Favorite Module Loaded                 --");
        System.out.println("--                                                                     --");
        System.out.println("-----------------------------------------------");
    }
}

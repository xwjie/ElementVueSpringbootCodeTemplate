package cn.xiaowenjie.jpa;

public class JPAThreadLocal {
    static ThreadLocal<Boolean>  backgroundThreadLocal =new ThreadLocal<Boolean>();

    public static void setBackground(boolean value) {
        backgroundThreadLocal.set(value);
    }

    public static boolean background(){
        return backgroundThreadLocal.get();
    }
}

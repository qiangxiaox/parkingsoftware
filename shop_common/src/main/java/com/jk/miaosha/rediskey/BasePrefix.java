package com.jk.miaosha.rediskey;

/**
 * @ClassName : BasePrefix
 * @Author : xiaoqiang
 * @Date : 2018/11/1 23:19:19
 * @Description :
 * @Version ： 1.0
 */
public abstract class BasePrefix implements KeyPrefix{

    private int expireSeconds;
    private String prefix;

    public BasePrefix(String prefix){ //expireSeconds默认为0时，表示永不过期
        this(0, prefix);
    }

    public BasePrefix(int expireSeconds,String prefix){
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        //获取调用该方法的类的简化名
        String className = this.getClass().getSimpleName();
        //在同一模块中不同的操作会有不同的key，为了区分，所以还需要为不同的操作设置前缀
        return className + ":" + prefix;
    }
}

package com.redis.redisson.controller;

import org.redisson.RedissonRedLock;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author liuboren
 * @Title:
 * @Description:
 * @date 2019/5/28 9:27
 */
@RestController
public class TestController {

    @Resource
    private RedissonClient redisson;


    @GetMapping("test_redisson")
    public String testRedisson() {
        RBucket<String> rBucket = redisson.getBucket("testBucket");
        rBucket.set("setBucket");
        return rBucket.get();
    }


    /*
     * 测试Redisson存储数据
     * bucket就相当于key,获取到bucket再获取值
     * 值是唯一的,后面的值会覆盖
     * */
    @GetMapping("insert_by_bucket")
    public void insertByBucket(String key) {
        RBucket<String> rBucket = redisson.getBucket(key);
        rBucket.set(key + ": 输入的测试值");
        rBucket.set(key + ": 输入的测试值2");
    }

    /*
     * 测试获取bucuket再获取bucket里面的值
     * */
    @GetMapping("get_by_bucket")
    public String getByBucket(String key) {
        RBucket<String> rBucket = redisson.getBucket(key);
        return rBucket.get();
    }


    /*
     * RMap就是Redis里面的map
     * */
    @GetMapping("test_rmap")
    public String testRmap(String key, String value) {
        RMap<String, String> rMap = redisson.getMap("testMap");
        rMap.put(key, value);
        RMap<String, String> getMap = redisson.getMap("testMap");
        return getMap.get(key);
    }


    /*
     * 测试redis锁.
     * 测试成功,在不释放锁的情况下,再次访问本方法,会阻塞
     * */
    @GetMapping("test_lock")
    public String testLock() {
        RLock lock = redisson.getLock("lock");
        lock.lock();
        RBucket<String> bucket = redisson.getBucket("lbr");
        lock.unlock();
        return bucket.get();
    }


    @GetMapping("get_lock")
     public boolean getLock(){
        RLock lock = redisson.getLock("lock");
        RedissonRedLock redissonRedLock = (RedissonRedLock) redisson.getRedLock(lock);
        lock.lock();
        lock.unlock();
        return lock.isLocked();
    }

}
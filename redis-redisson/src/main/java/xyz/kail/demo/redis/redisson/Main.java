package xyz.kail.demo.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class Main {

    public static void main(String[] args) {
        Config config=new Config();
        config.useSingleServer().setAddress("");

        final RedissonClient redisson = Redisson.create(config);


    }

}

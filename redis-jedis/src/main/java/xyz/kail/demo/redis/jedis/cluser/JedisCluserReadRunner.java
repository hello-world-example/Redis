package xyz.kail.demo.redis.jedis.cluser;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * www:SellCarProgress:56377946:{"auctionId":56377946,"auctionType":0,"createTime":1539882336251,"mobile":"15010611877","modifyTime":1539882336251,"nodeIndex":0,"registerTime":1539882336229,"steps":[{"auctionId":56377946,"createTime":1539882336251,"currentStatus":110,"eventTime":1539882336229,"modifyTime":1539882336251,"msg":"天天拍车客服将来电与您沟通车辆检测事宜，请注意接听10108885的来电。","nodeName":"预约检测","nodeNum":1,"progressStatus":0,"success":false,"title":"您已报名卖车"},{"auctionId":56377946,"createTime":1539882336251,"currentStatus":-1,"modifyTime":1539882336251,"nodeName":"车辆检测","nodeNum":2,"progressStatus":-1,"success":false,"title":""},{"auctionId":56377946,"createTime":1539882336251,"currentStatus":-1,"modifyTime":1539882336251,"nodeName":"车商竞价","nodeNum":3,"progressStatus":-1,"success":false,"title":""},{"auctionId":56377946,"createTime":1539882336251,"currentStatus":-1,"modifyTime":1539882336251,"nodeName":"门店交易","nodeNum":4,"progressStatus":-1,"success":false,"title":""},{"auctionId":56377946,"createTime":1539882336251,"currentStatus":-1,"modifyTime":1539882336251,"nodeName":"手续办理","nodeNum":5,"progressStatus":-1,"success":false,"title":""}],"vehicleModel":"未知车型","vehiclePictureUrl":"http://cdn11.ttpaicdn.com/news/pages/mobile-v3.5/dist/images/icon_common.png"}
 * www:SellCarProgress:56421174:{"auctionId":56421174,"auctionType":0,"createTime":1539890700282,"mobile":"18001265033","modifyTime":1539890700282,"nodeIndex":0,"registerTime":1539890700192,"steps":[{"auctionId":56421174,"createTime":1539890700282,"currentStatus":110,"eventTime":1539890700192,"modifyTime":1539890700282,"msg":"天天拍车客服将来电与您沟通车辆检测事宜，请注意接听10108885的来电。","nodeName":"预约检测","nodeNum":1,"progressStatus":0,"success":false,"title":"您已报名卖车"},{"auctionId":56421174,"createTime":1539890700282,"currentStatus":-1,"modifyTime":1539890700282,"nodeName":"车辆检测","nodeNum":2,"progressStatus":-1,"success":false,"title":""},{"auctionId":56421174,"createTime":1539890700282,"currentStatus":-1,"modifyTime":1539890700282,"nodeName":"车商竞价","nodeNum":3,"progressStatus":-1,"success":false,"title":""},{"auctionId":56421174,"createTime":1539890700282,"currentStatus":-1,"modifyTime":1539890700282,"nodeName":"门店交易","nodeNum":4,"progressStatus":-1,"success":false,"title":""},{"auctionId":56421174,"createTime":1539890700282,"currentStatus":-1,"modifyTime":1539890700282,"nodeName":"手续办理","nodeNum":5,"progressStatus":-1,"success":false,"title":""}],"vehicleModel":"未知车型","vehiclePictureUrl":"http://cdn11.ttpaicdn.com/news/pages/mobile-v3.5/dist/images/icon_common.png"}
 * www:SellCarProgress:56381088:{"auctionId":56381088,"auctionType":0,"createTime":1539883112277,"mobile":"18780022123","modifyTime":1539883112277,"nodeIndex":0,"registerTime":1539883112252,"steps":[{"auctionId":56381088,"createTime":1539883112277,"currentStatus":110,"eventTime":1539883112252,"modifyTime":1539883112277,"msg":"天天拍车客服将来电与您沟通车辆检测事宜，请注意接听10108885的来电。","nodeName":"预约检测","nodeNum":1,"progressStatus":0,"success":false,"title":"您已报名卖车"},{"auctionId":56381088,"createTime":1539883112277,"currentStatus":-1,"modifyTime":1539883112277,"nodeName":"车辆检测","nodeNum":2,"progressStatus":-1,"success":false,"title":""},{"auctionId":56381088,"createTime":1539883112277,"currentStatus":-1,"modifyTime":1539883112277,"nodeName":"车商竞价","nodeNum":3,"progressStatus":-1,"success":false,"title":""},{"auctionId":56381088,"createTime":1539883112277,"currentStatus":-1,"modifyTime":1539883112277,"nodeName":"门店交易","nodeNum":4,"progressStatus":-1,"success":false,"title":""},{"auctionId":56381088,"createTime":1539883112277,"currentStatus":-1,"modifyTime":1539883112277,"nodeName":"手续办理","nodeNum":5,"progressStatus":-1,"success":false,"title":""}],"vehicleModel":"未知车型","vehiclePictureUrl":"http://cdn11.ttpaicdn.com/news/pages/mobile-v3.5/dist/images/icon_common.png"}
 */
public class JedisCluserReadRunner implements Runnable {

    private JedisPool jedisPool;

    private String key;

    public JedisCluserReadRunner(JedisPool jedisPool, String key) {
        this.jedisPool = jedisPool;
        this.key = key;
    }

    @Override
    public void run() {
        for (;;) {
            Jedis jedis = null;
            try {
                jedis = jedisPool.getResource();

                jedis.get(key);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println(Thread.currentThread().getName() + "-----" + new Date());
            } finally {
                if (null != jedis) {
                    jedis.close();
                }
            }


            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("record:" + new Date());


        }
    }

}

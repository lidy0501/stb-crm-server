package cn.stb.stbcrmserver.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;



/**
 * 唯一 UUid 生成策略
 * 2020-06-20
 */
public class UUIDUtil {

    /**
     * 以纳秒级时间戳生成 20 位数字字符串，通过 对象锁 + 休眠 10毫秒 的方式，
     * 保证即使在多线程的环境下，每次获取的结果仍不重复
     *
     * @return
     */
    public static String getNumId() {

        synchronized (UUIDUtil.class) {
            //时间戳 20位字符串
            try {
                Thread.sleep(10);
                String s = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS").format(new Date());
                return s;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;

    }


    /**
     * 以下用 50000 个线程做并发测试依然有效
     *
     * @param args
     */
    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        int k = 0;
        System.out.println("线程开始：" + new Date());
        while (k < 50000) {
            new Thread(() -> {
                String id = getNumId();
                if (map.keySet().contains(id)) {
                    map.put(id, map.get(id) + 1);
                } else {
                    map.put(id, 1);
                }
            }).start();
            k++;
        }
        try {
            Thread.sleep(100000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("线程结束：" + new Date());
        for (String id : map.keySet()) {
            if (map.get(id) > 1) {
                System.out.println("重复id:" + id + ",重复次数:" + map.get(id));
            }
        }
        System.out.println("主线程结束：" + new Date());
    }
}

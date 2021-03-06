package com.iflytek.edu;
/**
 * Created by admin on 2019/4/26.
 */

import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.MetaException;

import java.util.List;

/**
 * @Author: xzran
 * @Date: 2019/4/26 14:47
 **/
public class HiveMetastoreTest2 {

    public static void main(String[] args){
        HiveConf hiveConf = new HiveConf();
//        hiveConf.set("hive.metastore.uris","thrift://192.168.1.100:9083");
//        hiveConf.set("hive.metastore.uris","thrift://192.168.1.102:9083");
        hiveConf.set("hive.metastore.uris","thrift://192.168.1.100:9083,thrift://192.168.1.102:9083");
        try {
            HiveMetaStoreClient client = new HiveMetaStoreClient(hiveConf);
            showAllDatabases(client);
        } catch (MetaException e) {
            e.printStackTrace();
        }
    }

    private static void showAllDatabases(HiveMetaStoreClient client){
        try {
            List<String> list = client.getAllDatabases();
            for(String item:list){
                System.out.println(item);
            }
        } catch (MetaException e) {
            e.printStackTrace();
        }
    }

}

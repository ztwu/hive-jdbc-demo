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
public class HiveMetastoreTest4 {

    public static void main(String[] args){
        try {
            System.setProperty("HADOOP_USER_NAME","mlguo");
            HiveConf hiveConf = new HiveConf();
            hiveConf.set("hive.metastore.uris","thrift://172.31.195.52:9083");
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

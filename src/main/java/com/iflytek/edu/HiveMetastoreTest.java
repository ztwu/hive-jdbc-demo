package com.iflytek.edu;
/**
 * Created by admin on 2019/4/26.
 */

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hive.conf.HiveConf;
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient;
import org.apache.hadoop.hive.metastore.api.MetaException;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;
import java.util.List;

/**
 * @Author: ztwu2
 * @Date: 2019/4/26 14:47
 **/
public class HiveMetastoreTest {

    public static void main(String[] args){

        /*Configuration configuration = new Configuration();
        configuration.set("hadoop.security.authentication" , "Kerberos" );
        System.setProperty("java.security.krb5.conf", "D:\\data\\idea\\java\\private\\github-self-workspace\\hive-jdbc-demo\\src\\main\\resources\\kerberos\\krb5.conf");
        UserGroupInformation.setConfiguration(configuration);
        try {
            UserGroupInformation.loginUserFromKeytab("hive/hadoop01.ztwu.com@ZTWU.COM",
                    "D:\\data\\idea\\java\\private\\github-self-workspace\\hive-jdbc-demo\\src\\main\\resources\\kerberos\\hive.keytab");
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        HiveConf hiveConf = new HiveConf();
        hiveConf.set("hive.metastore.uris","thrift://172.31.195.52:9083");
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

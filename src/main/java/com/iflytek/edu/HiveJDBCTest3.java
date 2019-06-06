package com.iflytek.edu;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2017/8/14
 * Time: 11:29
 * Description
 * 检查hive server2是否启动
 * jdbc连接hiveserver2,需要在服务器上启动hiveserver2 hive --service hiveserver2
 */

public class HiveJDBCTest3 {

    private static String driverName =
            "org.apache.hive.jdbc.HiveDriver";

    public static void main(String[] args)
            throws Exception {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        //登录Kerberos账号
        Configuration configuration = new Configuration();
        configuration.set("hadoop.security.authentication" , "Kerberos" );
        System.setProperty("java.security.krb5.conf", "D:\\data\\idea\\java\\private\\github-self-workspace\\hive-jdbc-demo\\src\\main\\resources\\kerberos\\krb5.conf");
        UserGroupInformation.setConfiguration(configuration);
        UserGroupInformation.loginUserFromKeytab("hive/hadoop01.ztwu.com@ZTWU.COM",
                "D:\\data\\idea\\java\\private\\github-self-workspace\\hive-jdbc-demo\\src\\main\\resources\\kerberos\\hive.keytab");

        Connection con = DriverManager.getConnection(
                "jdbc:hive2://192.168.223.100:10000/ztwu;principal=hive/hadoop01.ztwu.com@ZTWU.COM", "hive", "");

        Statement stmt = con.createStatement();

//        createTable(stmt);

//        loadData(stmt);

        showTable(stmt);

    }

    /**
     * 创建db数据库
     * @param stmt
     */
    private static void createTable(Statement stmt) throws Exception {
        String sql = "create table if not EXISTS edu_bg.test_hive_jdbc (" +
                "id int," +
                "name string" +
                ")" +
                "partitioned by (pdate string)" +
                "stored as parquet";
        System.out.println(sql);
        boolean result = stmt.execute(sql);
        System.out.println(result);
    }

    /**
     * load data数据
     * @param stmt
     */
    private static void loadData(Statement stmt) throws Exception {
        String conf = "set hive.exec.dynamic.partition.mode=nonstrict";
        System.out.println(conf);
        stmt.execute(conf);

        String sql = "insert overwrite table test_hive_jdbc partition(pdate)\n" +
                "select 1,\"ztwu1\",'2018-05-24'\n" +
                "union all\n" +
                "select 2,\"ztwu2\",'2018-05-24'\n" +
                "union all\n" +
                "select 3,\"ztwu3\",'2018-05-24'";
        System.out.println(sql);
        boolean result = stmt.execute(sql);
        System.out.println(result);
    }

    /**
     * 查询hive表
     * @param stmt
     */
    private static void showTable(Statement stmt) throws Exception{
        String tableName = "ztwu.test";
        // show tables
        String sql = "select * from " + tableName;
        System.out.println(sql);
        ResultSet res = stmt.executeQuery(sql);
        while (res.next()) {
            System.out.println(String.valueOf(res.getInt(1)) + "\t"
                    + res.getString(2));
        }
    }

}

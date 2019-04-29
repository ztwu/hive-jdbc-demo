package com.iflytek.edu;

import java.sql.*;

/**
 * Created with Intellij IDEA.
 * User: ztwu2
 * Date: 2017/8/14
 * Time: 11:29
 * Description
 * 检查hive server2是否启动
 * jdbc连接hiveserver2,需要在服务器上启动hiveserver2 hive --service hiveserver2
 */

public class HiveJDBCTest {

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

        Connection con = DriverManager.getConnection(
                "jdbc:hive2://****:20000/edu_bg", "***", "***");
        Statement stmt = con.createStatement();

        createTable(stmt);

//        loadData(stmt);

        showTable(stmt);

    }

    /**
     * 创建db数据库
     * @param stmt
     */
    private static void createTable(Statement stmt) throws Exception {
        String sql = "create table if not EXISTS test_hive_jdbc (" +
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
        String tableName = "dim_date";
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

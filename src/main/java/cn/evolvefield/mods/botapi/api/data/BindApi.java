package cn.evolvefield.mods.botapi.api.data;

import cn.evolvefield.mods.botapi.BotApi;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/3/18 21:24
 * Version: 1.0
 */
public class BindApi {
    //获取某玩家的绑定QQ
    public static String getBindQQ(String Player){
            try {
                String sql = "SELECT * FROM bot_player_data";
                PreparedStatement statement = BotApi.connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery(sql);
                //历遍所有结果
                while (rs.next()) {
                    if(rs.getString("Player").equalsIgnoreCase(Player)){
                        return rs.getString("QQ");
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
            return null;


    }

    //获取某QQ的绑定玩家
    public static String getBindPlayer(String QQ){
            try {
                String sql = "SELECT * FROM bot_player_data";
                PreparedStatement statement = BotApi.connection.prepareStatement(sql);
                ResultSet rs = statement.executeQuery(sql);
                //历遍所有结果
                while (rs.next()) {
                    if(rs.getString("QQ").equalsIgnoreCase(QQ)){
                        return rs.getString("Player");
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return null;
            }
            return null;



    }

    //设置某QQ的绑定玩家
    public static Boolean setBind(String QQ,String Player){
            try {
                String sql = "SELECT * FROM bot_player_data";
                PreparedStatement statement = BotApi.connection.prepareStatement(sql);
                //ResultSet类，用来存放获取的结果集合！！
                ResultSet rs = statement.executeQuery(sql);
                //历遍所有结果
                while (rs.next()) {
                    if(rs.getString("QQ").equalsIgnoreCase(QQ)){
                        //要执行的SQL语句
                        sql = "UPDATE bot_player_data SET Player='"+Player+"' WHERE QQ='"+QQ+"'";
                        statement.executeUpdate(sql);
                        return true;
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
            return false;



    }

    //删除绑定玩家
    public static Boolean delBind(String QQ){
            try {
                String sql = "SELECT * FROM bot_player_data";
                PreparedStatement statement = BotApi.connection.prepareStatement(sql);
                //ResultSet类，用来存放获取的结果集合！！
                ResultSet rs = statement.executeQuery(sql);

                //历遍所有结果
                while (rs.next()) {
                    if(rs.getString("QQ").equalsIgnoreCase(QQ)){
                        //要执行的SQL语句
                        sql = "DELETE FROM bot_player_data WHERE QQ='"+QQ+"'";
                        statement.executeUpdate(sql);
                        return true;
                    }
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
            return false;



    }

    //新增绑定玩家
    public static Boolean addBind(String QQ,String Player){
            try {
                //要执行的SQL语句
                String sql = "SELECT * FROM bot_player_data";
                PreparedStatement statement = BotApi.connection.prepareStatement(sql);
                //ResultSet类，用来存放获取的结果集合！！
                ResultSet rs = statement.executeQuery(sql);
                //历遍所有结果
                while (rs.next()) {
                    if(rs.getString("Player").equalsIgnoreCase(Player) || rs.getString("QQ").equalsIgnoreCase(QQ)){
                        return false;
                    }
                }
                //执行的sql语句，用？代替变量
                sql = "INSERT INTO bot_player_data(Player,QQ) values(?,?)";
                statement = BotApi.connection.prepareStatement(sql);

                //getInt //整数
                //getString //字符串，文本
                //getBoolean //布尔型，判断
                //getDouble //浮点型,小数
                //getDate //时间日期
                //getObject //泛型
                //绑定变量
                statement.setString(1,Player);
                statement.setString(2,QQ);
                //执行语句
                statement.executeUpdate();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
                return false;
            }
            return true;

    }
}

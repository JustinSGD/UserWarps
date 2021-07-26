package com.jubyte.userwarps.database;

import com.jubyte.userwarps.UserWarps;

import java.sql.*;

/**
 * @author Justin_SGD
 * @since 13.07.2021
 */

public class MySQL {

    private Connection connection;

    public void createConnection() {
        if (!isConnected()) {
            try {
                String mysqlHost = UserWarps.getPlugin().getConfig().getString("MySQL.Host");
                int mysqlPort = UserWarps.getPlugin().getConfig().getInt("MySQL.Port");
                String mysqlDatabase = UserWarps.getPlugin().getConfig().getString("MySQL.Database");
                String mysqlUser = UserWarps.getPlugin().getConfig().getString("MySQL.User");
                String mysqlPassword = UserWarps.getPlugin().getConfig().getString("MySQL.Password");
                connection = DriverManager.getConnection("jdbc:mysql://" + mysqlHost + ":" + mysqlPort + "/" + mysqlDatabase + "?autoReconnect=true", mysqlUser, mysqlPassword);
                UserWarps.getPlugin().getLogger().info("The MySQL connection has been established.");
            } catch (SQLException e) {
                UserWarps.getPlugin().getLogger().warning("The MySQL connection could not be established.");
            }
        } else {
            UserWarps.getPlugin().getLogger().warning("A MySQL connection already exists");
        }
    }

    public ResultSet getResult(String sql) {
        if (isConnected()) {
            try {
                return connection.createStatement().executeQuery(sql);
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }

        return null;
    }

    public void closeConnection() {
        if (isConnected()) {
            try {
                connection.close();
                UserWarps.getPlugin().getLogger().info("The MySQL connection was terminated.");
            } catch (SQLException var1) {
                var1.printStackTrace();
            }
        } else {
            UserWarps.getPlugin().getLogger().info("There is no open connection.");
        }

    }

    public boolean isConnected() {
        return connection != null;
    }

    public void update(String query)	{
        try	{
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
        }
        catch (SQLException e) {
            createConnection();
            System.err.println(e);
        }
    }

    public void update(String query, PreparedStatementConsumer preparedStatementConsumer) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatementConsumer.accept(preparedStatement);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet query(String qry)	{
        ResultSet result = null;
        try {
            Statement statement = connection.createStatement();
            result = statement.executeQuery(qry);
        }
        catch (SQLException e) {
            createConnection();
            System.err.println(e);
        }
        return result;
    }

    public <R> R query(String query, PreparedStatementConsumer preparedStatementConsumer, ResultSetFunction<R> resultSetFunction)	{
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatementConsumer.accept(preparedStatement);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSetFunction.apply(resultSet);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    interface PreparedStatementConsumer {

        void accept(PreparedStatement preparedStatement) throws SQLException;
    }

    interface ResultSetFunction<R> {

        R apply(ResultSet resultSet) throws SQLException;
    }

    public Connection getConnection() {
        return connection;
    }
}
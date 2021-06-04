package org.example.connection;


import java.sql.*;

public class ConnectionClass {

    private Connection connection;
    private String username="root";
    private String password="09197696799_*MjM";

    public Connection getConnection() {

        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gnhs_system_db?useTimezone=true&serverTimezone=UTC", "root", "09197696799_*MjM  ");

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public ConnectionClass() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/gnhs_system_db?useTimezone=true&serverTimezone=UTC";
        String username = "root";
        String password = "09197696799_*MjM";

        connection = DriverManager.getConnection(url, username, password);
    }

    public ConnectionClass(String username, String password) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/final_project?useTimezone=true&serverTimezone=UTC";
        this.username = username;
        this.password = password;

        connection = DriverManager.getConnection(url, this.username, this.password);
    }

    public ResultSet select(String query) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }

    public int insert(String query){

        int affectedRows=0;

        try{
            PreparedStatement prep = connection.prepareStatement(query);
            affectedRows=prep.executeUpdate(query);


        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            e.printStackTrace();
        }

        return affectedRows;
    }

    public int delete(String query) throws SQLException {

        int affectedRows=0;

        PreparedStatement statement = connection.prepareStatement(query);
        affectedRows=statement.executeUpdate(query);

        return affectedRows;
    }

    public int update(String query) throws SQLException {

        int affectedRows=0;

        PreparedStatement statement = connection.prepareStatement(query);
        affectedRows=statement.executeUpdate(query);

        return affectedRows;
    }

    public void close() throws SQLException {
        connection.close();
    }

    public static void main(String[] args){
        int count=0;
        ResultSet resultSet;
        try {
            ConnectionClass sample=new ConnectionClass();
            resultSet=sample.select("SELECT * FROM items");

            while(resultSet.next()){
                count++;
            }
            sample.close();
        }catch (Exception e){
            count=0;
            System.out.println(e.getMessage());
        }
        System.out.println(count);
    }
}

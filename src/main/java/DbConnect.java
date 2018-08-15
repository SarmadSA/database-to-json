import java.sql.*;

public class DbConnect {

    //Constants
    private final String driver = "com.mysql.jdbc.Driver";
    private final String databaseName = "techwizzle";
    private final String host = "localhost";
    private final String port = "3306";
    private final String url = "jdbc:mysql://" + host +  ":" + port + "/" + databaseName;
    private final String user = "root";
    private final String password = "";

    private Connection connection;
    private Statement statement;

    public DbConnect() {
        try {
            Class.forName(driver);
            this.connection = DriverManager.getConnection(url, user, password);
            this.statement = connection.createStatement();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public Statement getStatement() {
        return this.statement;
    }
}

package utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtility {

    private static Connection connection;
    private static Statement statement;
    public static ResultSet resultSet;

    public static void createConnection() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://195.35.59.18/u201212290_qaloantec", "u201212290_qaloanuser", "HPo?+7r$");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Test sınıflarının bağlantı nesnesini alabilmesi için eklendi.
    public static Connection getConnection() {
        return connection;
    }

    public static void executeQuery(String query) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Object> getColumnData(String query, String columnName) {
        List<Object> columnData = new ArrayList<>();
        executeQuery(query);
        try {
            while (resultSet.next()) {
                columnData.add(resultSet.getObject(columnName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columnData;
    }

    public static void closeConnection() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package swing;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class QueryExecutor {
    public static ResultSet executeQuery(String query) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }

    public static StringBuilder formatResultSet(ResultSet resultSet) throws SQLException {
        StringBuilder result = new StringBuilder();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            result.append(String.format("%-20s", metaData.getColumnName(i)));
        }
        result.append("\n");

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                result.append(String.format("%-20s", resultSet.getString(i)));
            }
            result.append("\n");
        }

        return result;
    }
}

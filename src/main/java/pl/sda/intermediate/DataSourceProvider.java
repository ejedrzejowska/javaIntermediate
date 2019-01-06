package pl.sda.intermediate;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceProvider {
    private static DataSource dataSource;
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/intermediate12" +
            "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER_NAME = "intermediate-app-user";
    private static final String PASSWORD = "sda";


    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            BasicDataSource basicDataSource = new BasicDataSource();
            basicDataSource.setUrl(URL);
            basicDataSource.setUsername(USER_NAME);
            basicDataSource.setPassword(PASSWORD);
            dataSource = basicDataSource;
        }
        return dataSource.getConnection();
    }

    public static void closeRequest(AutoCloseable... closeables){
        for (AutoCloseable closeable : closeables) {
            try {
                if(closeable != null){
                    closeable.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package jdbc;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.Setter;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

@Getter
@Setter
public class CustomDataSource implements DataSource {
    private static volatile CustomDataSource instance;
    private final String driver;
    private final String url;
    private final String name;
    private final String password;

    private CustomDataSource(String driver, String url, String password, String name) throws Exception {
        this.password = password;
        this.driver = driver;
        this.name = name;
        this.url = url;
        try{
            Class.forName(driver);
        }catch (Exception e){
            throw new Exception("Failed to load JDBC");
        }
    }

    public static CustomDataSource getInstance() throws Exception {
        if (instance == null) {
            synchronized (CustomDataSource.class) {
                if(instance == null){
                    Properties properties = new Properties();
                    try {
                        properties.load(CustomDataSource.class.getClassLoader().getResourceAsStream("app.properties"));
                        String driver = properties.getProperty("postgres.driver");
                        String url = properties.getProperty("postgres.url");
                        String password = properties.getProperty("postgres.password");
                        String name = properties.getProperty("postgres.name");
                        instance = new CustomDataSource(driver, url, password, name);
                        Class.forName(properties.getProperty("postgres.driver"));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return null;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        System.out.println("NOT SUPPORTED");
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        System.out.println("NOT SUPPORTED");
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        throw new SQLException("NOT SUPPORTED");
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        throw new SQLException("NOT SUPPORTED");
    }
}

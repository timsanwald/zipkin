package zipkin2.autoconfigure.storage.mongodb;

import org.springframework.util.StringUtils;

import javax.sql.DataSource;

public class ZipkinMongodbStorageProperties {

  private static final long serialVersionUID = 0L;

  private String jdbcUrl;
  private String host = "localhost";
  private int port = 3306;
  private String username;
  private String password;
  private String db = "zipkin";
  private int maxActive = 10;
  private boolean useSsl;

  public String getJdbcUrl() {
    return jdbcUrl;
  }

  public void setJdbcUrl(String jdbcUrl) {
    this.jdbcUrl = jdbcUrl;
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = "".equals(username) ? null : username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = "".equals(password) ? null : password;
  }

  public String getDb() {
    return db;
  }

  public void setDb(String db) {
    this.db = db;
  }

  public int getMaxActive() {
    return maxActive;
  }

  public void setMaxActive(int maxActive) {
    this.maxActive = maxActive;
  }

  public boolean isUseSsl() {
    return useSsl;
  }

  public void setUseSsl(boolean useSsl) {
    this.useSsl = useSsl;
  }

  public DataSource toDataSource() {
    HikariDataSource result = new HikariDataSource();
    result.setDriverClassName("org.mariadb.jdbc.Driver");
    result.setJdbcUrl(determineJdbcUrl());
    result.setMaximumPoolSize(getMaxActive());
    result.setUsername(getUsername());
    result.setPassword(getPassword());
    return result;
  }

  private String determineJdbcUrl() {
    if (StringUtils.hasText(getJdbcUrl())) {
      return getJdbcUrl();
    }

    StringBuilder url = new StringBuilder();
    url.append("jdbc:mysql://");
    url.append(getHost()).append(":").append(getPort());
    url.append("/").append(getDb());
    url.append("?autoReconnect=true");
    url.append("&useSSL=").append(isUseSsl());
    url.append("&useUnicode=yes&characterEncoding=UTF-8");
    return url.toString();
  }

}

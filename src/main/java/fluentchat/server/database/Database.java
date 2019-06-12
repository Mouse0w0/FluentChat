package fluentchat.server.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;

public class Database {

    private ConnectionSource userAuthConn;
    private ConnectionSource userConn;

    private Dao<UserAuthentication, String> userAuthDao;
    private Dao<User, Integer> userDao;

    public void init() throws SQLException {
        userAuthConn = new JdbcConnectionSource("jdbc:sqlite:user_auth.db");
        userConn = new JdbcConnectionSource("jdbc:sqlite:user.db");

        userAuthDao = DaoManager.createDao(userAuthConn, UserAuthentication.class);
        TableUtils.createTableIfNotExists(userAuthConn, UserAuthentication.class);

        userDao = DaoManager.createDao(userConn, User.class);
        TableUtils.createTableIfNotExists(userConn, User.class);
    }

    public void close() {
        close(userAuthConn);
        close(userConn);
    }

    public Dao<UserAuthentication, String> getUserAuthDao() {
        return userAuthDao;
    }

    public Dao<User, Integer> getUserDao() {
        return userDao;
    }

    private void close(ConnectionSource connectionSource) {
        try {
            connectionSource.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:test.database");

        Dao<User, Integer> userDataDao = DaoManager.createDao(connectionSource, User.class);

        TableUtils.createTableIfNotExists(connectionSource, User.class);

        User user = new User(0, "Hello");
        userDataDao.create(user);

        User user1 = userDataDao.queryForId(0);
        System.out.println(user1.id + " " + user1.username);

        connectionSource.close();
    }
}

package fluentchat.server.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.IOException;
import java.sql.SQLException;

public class Database {

    public static void main(String[] args) throws SQLException, IOException {
        ConnectionSource connectionSource = new JdbcConnectionSource("jdbc:sqlite:test.database");

        Dao<User, Integer> userDataDao = DaoManager.createDao(connectionSource, User.class);

        TableUtils.createTableIfNotExists(connectionSource, User.class);

        User user = new User(0, "Hello");
        userDataDao.create(user);

        User user1 = userDataDao.queryForId(0);
        System.out.println(user1.getId() + " " + user1.getName());

        connectionSource.close();
    }
}

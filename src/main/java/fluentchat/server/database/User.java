package fluentchat.server.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class User {

    @DatabaseField(generatedId = true)
    public int id;

    @DatabaseField(canBeNull = false, unique = true)
    public String username;

    public User() {
    }

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }
}

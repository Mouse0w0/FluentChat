package fluentchat.server.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_authentications")
public class UserAuthentication {
    @DatabaseField(canBeNull = false, foreign = true)
    private User user;

    @DatabaseField(canBeNull = false)
    private String password;
}

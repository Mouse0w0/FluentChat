package fluentchat.server.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_authentications")
public class UserAuthentication {
    @DatabaseField(id = true)
    public String username;

    @DatabaseField(canBeNull = false)
    public String password;

}

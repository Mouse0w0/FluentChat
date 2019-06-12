package fluentchat.server.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_profiles")
public class UserProfile {

    @DatabaseField(canBeNull = false, foreign = true)
    public User user;

    @DatabaseField
    public String nickname;

    @DatabaseField
    public String face;

    @DatabaseField
    public String bio;

    @DatabaseField
    public String lastLoginTime;
}

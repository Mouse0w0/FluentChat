package fluentchat.server.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_profiles")
public class UserProfile {

    @DatabaseField(canBeNull = false, foreign = true)
    private User user;

    @DatabaseField
    private String nickname;

    @DatabaseField
    private String face;

    @DatabaseField
    private String bio;

    @DatabaseField
    private String lastLoginTime;
}

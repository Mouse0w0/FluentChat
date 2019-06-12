package fluentchat.server.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_datas")
public class UserData {

    @DatabaseField(foreign = true)
    public User user;

    @DatabaseField
    public long registerTime;

    @DatabaseField
    public String friends;

    @DatabaseField
    public String groups;
}

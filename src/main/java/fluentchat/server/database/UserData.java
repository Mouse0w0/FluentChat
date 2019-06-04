package fluentchat.server.database;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_datas")
public class UserData {

    private User user;

    private String registerTime;

    private String friends;

    private String groups;
}

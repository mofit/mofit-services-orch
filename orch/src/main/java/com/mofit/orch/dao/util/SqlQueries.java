package com.mofit.orch.dao.util;

public final class SqlQueries {

    // Database
    private static final String DATABASE_NAME = "Mofit";

    // Database tables
    private static final String USER_TABLE = "user";
    private static final String USER_PERMISSIONS_TABLE = "userpermission";
    private static final String ACCESS_MODULES_TABLE = "accessmodule";

    // User columns (common user columns)
    private static final String USER_ID = "userId";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String ACCESS_MODULE_ID = "moduleId";
    private static final String ACCESS_MODULE_NAME = "name";
    private static final String MODULE_PERMISSION = "permission";
    private static final String USER_PERMISSION_ID = "userPermissionId";

    private static final String CLIENT_TABLE = "client";
    private static final String TRAINER_TABLE = "trainer";

    private static final String TRAINER_ID = "trainerId";
    private static final String CLIENT_ID = "clientId";

    // User queries
    public static final String GET_USER_BY_EMAIL = "SELECT u."
        + USER_PASSWORD + ", u."
        + USER_EMAIL + ", u."
        + USER_ID + ", c."
        + CLIENT_ID + ", t."
        + TRAINER_ID + ", p."
        + ACCESS_MODULE_ID + ", m."
        + ACCESS_MODULE_NAME + ", p."
        + USER_PERMISSION_ID + ", p."
        + MODULE_PERMISSION + " FROM " + USER_TABLE + " AS u"
        + " LEFT JOIN " + CLIENT_TABLE + " AS c ON u." + USER_ID + "=c." + USER_ID
        + " LEFT JOIN " + TRAINER_TABLE + " AS t ON u." + USER_ID + "=t." + USER_ID
        + " LEFT JOIN " + USER_PERMISSIONS_TABLE + " AS p ON u." + USER_ID + "=p." + USER_ID
        + " LEFT JOIN " + ACCESS_MODULES_TABLE + " AS m ON m." + ACCESS_MODULE_ID + "=p." + ACCESS_MODULE_ID
        + " WHERE u." + USER_EMAIL + "=:email";

}

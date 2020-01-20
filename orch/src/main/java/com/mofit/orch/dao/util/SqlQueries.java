package com.mofit.orch.dao.util;

public final class SqlQueries {

    // Database
    private static final String DATABASE_NAME = "Mofit";

    // Database tables
    private static final String USER_TABLE = "user";
    private static final String USER_PERMISSIONS_TABLE = "user_permissions";
    private static final String ACCESS_MODULES_TABLE = "accessmodule";


    // User columns (common user columns)
    private static final String USER_ID = "user_id";
    private static final String USER_EMAIL = "email";
    private static final String USER_PASSWORD = "password";
    private static final String USER_TYPE = "user_type";
    private static final String ACCESS_MODULE_ID = "module_id";
    private static final String ACCESS_MODULE_NAME = "name";
    private static final String MODULE_PERMISSION = "permission";


    // User queries
    public static final String GET_USER_BY_EMAIL = "SELECT u."
        + USER_PASSWORD + ", u."
        + USER_ID + ", u."
        + USER_EMAIL + ", u."
        + USER_TYPE + ", p."
        + ACCESS_MODULE_ID + ", m."
        + ACCESS_MODULE_NAME + ", p."
        + MODULE_PERMISSION + " FROM " + USER_TABLE + " AS u"
        + " LEFT JOIN " + USER_PERMISSIONS_TABLE + " AS p ON u." + USER_ID + "=p." + USER_ID
        + " LEFT JOIN " + ACCESS_MODULES_TABLE + " AS m ON m." + ACCESS_MODULE_ID + "=p." + ACCESS_MODULE_ID
        + " WHERE u." + USER_EMAIL + "=:email";

}

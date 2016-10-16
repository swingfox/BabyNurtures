package com.example.willthespoderman.babynurtures.app;
import com.parse.ParseInstallation;

/**
 * Created by WILL THE SPODERMAN on 8/19/2015.
 */
public class AppConfig {
    // Server user login url

    //XAMP SERVER

    public static String ip = "http://192.168.15.4:80/";
    public static String URL_LOGIN = ip+"android_login_api/";
    public static String URL_REGISTER = ip+"android_login_api/";
    public static String URL_TODO = ip+"android_login_api/";
    public static String URL_BABY = ip+"android_login_api/search.php";
    public static String URL_UPDATEPROFILE = ip+"android_login_api/updateprofile.php";
    public static String URL_NOTIFY = ip+"android_login_api/notification.php";
    public static String URL_SENDNOTIFICATION = ip+"android_login_api/sendNotificationToParent.php";
    public static String URL_SEENNOTIFICATION = ip+"android_login_api/updateMyNotification.php";
    public static String URL_FINISH_TODO = ip+"android_login_api/finishtodo.php";
    public static String URL_DELETE_TODO = ip+"android_login_api/deleteTodo.php";
    public static String URL_EDIT_TODO = ip+"android_login_api/editTodo.php";


    // BRAKET SERVERy
    //public static String URL_LOGIN = "http://braket.site90.com/login.php";
    //public static String URL_REGISTER = "http://braket.site90.com/register.php";
    //public static String URL_TODO = "http://braket.site90.com/todo.php";4

    public static void subscribeWithEmail(String email, String tag) {

        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put("email", email);
        installation.put("tag", tag);
        installation.saveInBackground();
    }


}


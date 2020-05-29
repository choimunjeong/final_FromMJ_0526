package DB;

/**
 * http://blog.naver.com/PostView.nhn?blogId=nife0719&logNo=221035148567&parentCategoryNo=&categoryNo=26&viewDate=&isShowPopularPosts=false&from=postView
 */

import android.provider.BaseColumns;

public final class Second_MainDB {

    //데이터베이스 테이블 구성
    public static final class CreateDB implements BaseColumns {
        public static final String USERID = "userid";
        public static final String _TABLENAME0 = "usertable";
        public static final String _CREATE0 = "create table if not exists "+_TABLENAME0+"("
                +_ID+" integer primary key autoincrement, "
                +USERID+"  text  not null ); ";
    }
}
package umengchatdemo.utils;

import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;

/**
 * Created by yanru.zhang on 16/6/30.
 * Email:yanru.zhang@renren-inc.com
 */
public class UMUtils {
    private static UMUtils instance;

    private YWIMKit mIMKit;

    public static UMUtils getInstance(){
        if(instance == null){
            instance = new UMUtils();
        }
        return instance;
    }

    public YWIMKit getmIMKit() {
        return mIMKit;
    }

    public void initMIMKit(String userId,String appKey) {
        mIMKit = YWAPI.getIMKitInstance(userId.toString(), appKey);
    }

}

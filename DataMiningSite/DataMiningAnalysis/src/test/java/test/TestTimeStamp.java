package test;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName TestTimeStamp
 * @Description TODO
 * @Author KANGJIAN
 * @Date 2019/4/1 13:54
 **/
public class TestTimeStamp {
    public static void main(String[] args) {
        String createTimestamp = "";
        String source ="";
        Long createTimestampn = null;
        if(StringUtils.isNotEmpty(createTimestamp)||!createTimestamp.equals("")){
            System.out.println(createTimestamp);
            createTimestampn = Long.parseLong(createTimestamp);
        }
        System.out.println(createTimestampn);
    }
}

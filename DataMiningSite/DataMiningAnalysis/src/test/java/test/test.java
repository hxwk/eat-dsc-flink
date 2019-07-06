package test;

import org.apache.commons.lang3.StringUtils;

/**
 * @ClassName test
 * @Description TODO
 * @Author KANGJIAN
 * @Date 2019/3/28 17:04
 **/
public class test {
    //static String txt ="\"5c9b075a2f5c3e7a70bb9cae\";\"车公庙\";\"人均￥45\";\"烧腊\";\"Mar 27, 2019 1:17:14 PM\";\"1553663834069\";\"8.6km\";\"22.54904\";\"113.94756\";\"3.6分\";\"-889497434\";\"深井村·烧鹅专门店(泰然店)\";;;;;;;;;";
    static String txt="\"12\";;;";
    public static void main(String[] args) {
        String[] split = txt.split(";",-1);
        //System.out.println(split.length);
        int i=0;
        String a1=split[0];
        String a2=StringUtils.isEmpty(split[1])?"0":split[1];
        for (String txt : split) {
            System.out.println(++i);
            System.out.println(txt);
            if(StringUtils.isEmpty(txt)){
                System.out.println("..");
            }
        }
    }
}

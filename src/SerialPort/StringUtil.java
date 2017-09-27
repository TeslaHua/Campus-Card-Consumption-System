package SerialPort;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * 指令字符串操作类
 *
 */
@SuppressWarnings("unused")
public class StringUtil {

    /**
     * 使用Sms 的 SendSms()方法发送短信时,调用此方法将其短信内容转换成十六进制
     * @param msg 短信内容
     * @return 转换后的十六进制短信
     */
    public static final String encodeHex(String msg) {
        byte[] bytes = null;
        try {
            bytes = msg.getBytes("GBK");   //获取汉字的GBK编码
        } catch (java.io.UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuffer buff = new StringBuffer(bytes.length * 4);
        String b = "";
        char a;
        int n = 0;
        int m = 0;
        for (int i = 0; i < bytes.length; i++) {
            try{b = Integer.toHexString(bytes[i]);}catch (Exception e) {}
            if (bytes[i] > 0) {
                buff.append("00");
                buff.append(b);
                n = n + 1;
            } else {
                a = msg.charAt((i - n) / 2 + n);
                m = a;
                try{b = Integer.toHexString(m);}catch (Exception e) {}
                buff.append(b.substring(0, 4));
                i = i + 1;  
            }
        }
        return buff.toString();  //aabbbbb(一个是四位)
    }
    //以下几个函数实现对电话号经行编码
    public static String getLength(String msg){
        int l=msg.length()/2;   //变成字节
        String s=Integer.toHexString(l);//变成16进制表示的字节数40--->21（00101000）
        if(s.length()==1){
            s="0"+s;
        }
        return s;
    }  
    //将接收方电话号码两两倒序
    public static String phonetoString(String phone){
        StringBuffer p=new StringBuffer(phone+"f");
        char t,t1;
        for(int i=0;i<p.length();i+=2){
            t=p.charAt(i);
            t1=p.charAt(i+1);
            p.setCharAt(i,t1);
            p.setCharAt(i+1,t);
        }
        return p.toString();
    }
    public static int total(String s){
        int num=Integer.parseInt(s,16);//基数是16  ,21---->40
        num=num+15;  //(0011000D9168+5105662671F2+000801)/2=15
        return num;
    }
}

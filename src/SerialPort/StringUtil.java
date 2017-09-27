package SerialPort;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Date;

/***
 * ָ���ַ���������
 *
 */
@SuppressWarnings("unused")
public class StringUtil {

    /**
     * ʹ��Sms �� SendSms()�������Ͷ���ʱ,���ô˷��������������ת����ʮ������
     * @param msg ��������
     * @return ת�����ʮ�����ƶ���
     */
    public static final String encodeHex(String msg) {
        byte[] bytes = null;
        try {
            bytes = msg.getBytes("GBK");   //��ȡ���ֵ�GBK����
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
        return buff.toString();  //aabbbbb(һ������λ)
    }
    //���¼�������ʵ�ֶԵ绰�ž��б���
    public static String getLength(String msg){
        int l=msg.length()/2;   //����ֽ�
        String s=Integer.toHexString(l);//���16���Ʊ�ʾ���ֽ���40--->21��00101000��
        if(s.length()==1){
            s="0"+s;
        }
        return s;
    }  
    //�����շ��绰������������
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
        int num=Integer.parseInt(s,16);//������16  ,21---->40
        num=num+15;  //(0011000D9168+5105662671F2+000801)/2=15
        return num;
    }
}

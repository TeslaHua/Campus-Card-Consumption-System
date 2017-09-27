package SerialPort;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import static SerialPort.StringUtil.getLength;
import static SerialPort.StringUtil.phonetoString;
import static SerialPort.StringUtil.total;

/***
 * 发送短信操作类
 */
@SuppressWarnings("unused")
public class Sms extends JFrame{
    private CommonSms commonsms;
    private static char symbol1 = 13;  //回车键
    private static String strReturn = "", atCommand = "";

    public boolean SendSms(Port myport) {
        if(!myport.isIsused())  //端口未打开
        {
            System.out.println("COM通讯端口未正常打开!");
            return false;
        }
//        setMessageMode(myport,0); //设置消息模式，0代表PDU格式
        // 空格
        char symbol2 = 34;
        // ctrl~z 发送指令
        char symbol3 = 26;
        try {
            atCommand = "AT+IPR=115200" + String.valueOf(symbol1);  //设置GSM模块的波特率
            strReturn = myport.sendAT(atCommand);
            System.out.println(strReturn);         //返回结果
            atCommand = "AT+CMGF=0" + String.valueOf(symbol1);    //设置短信方式为PDU方式
            strReturn = myport.sendAT(atCommand);
            System.out.println(strReturn);        //返回结果
            atCommand = "AT+CSCS=\"UCS2\"" + String.valueOf(symbol1); //设置PC字符集编码
            strReturn = myport.sendAT(atCommand);
            System.out.println(strReturn);        //返回结果
            atCommand = "AT+CSCA?" + String.valueOf(symbol1);    //获取短信中心号码， 烟台联通 +8613010161500 
            strReturn = myport.sendAT(atCommand);
            System.out.println(strReturn);       //返回结果
            String at = StringUtil.encodeHex(commonsms.getSmstext().trim());//将短信内容转化为十六进制
            String length=getLength(at);
            String phone=phonetoString(commonsms.getRecver());  //把接收方手机号转换为字符串
            int tol=total(length);
            if (strReturn.indexOf("OK", 0) != -1) {
                atCommand = "AT+CMGS="+tol+ String.valueOf(symbol1);//发送字节数
                strReturn = myport.sendAT(atCommand);
                System.out.println(strReturn);     //返回结果
                atCommand="0011000D9168"+phone+"000801"+ length + at+ String.valueOf(symbol3);  //将短信编码为PDU格式
                strReturn = myport.sendAT(atCommand);
                System.out.println(strReturn);
                if (strReturn.indexOf("OK") != -1
                        && strReturn.indexOf("+CMGS") != -1) {
                    System.out.println("短信发送成功...");
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("短信发送失败...");
            return false;
        }
        System.out.println("短信发送失败...");
        return false;
    }
    /**
     * 设置消息模式
     * @param op
     * 0-pdu 1-text(默认1 文本方式 )
     * @return
     */
/*    public boolean setMessageMode(Port myport,int op) {
        try {
            String atCommand = "AT+CMGF=" + String.valueOf(op)
                    + String.valueOf(symbol1);
            String  strReturn = myport.sendAT(atCommand);
            if (strReturn.indexOf("OK", 0) != -1) {
                System.out.println("*************文本方式设置成功************");
                return true;
            }
            return false;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }*/
    public CommonSms getCommonsms() {
        return commonsms;
    }

    public void setCommonsms(CommonSms commonsms) {
        this.commonsms = commonsms;
    }
    /**
     * 号码，内容，发送短信息
     * @param phone
     * @param countstring
     * @throws Exception
     */
    public static void sendmsn(String phone,String countstring){
        Sms sms = new Sms();
        // 发送测试
        CommonSms cs = new CommonSms();  //新建一个CommonSms类
        cs.setRecver(phone); //封装接收方电话号
        cs.setSmstext(countstring); //封装短信内容
        System.out.println(cs.getSmstext());
        sms.setCommonsms(cs);
        Port myort=new Port("COM6");  //这里的端口号，要看自己的电脑的设备管理器的端口号
        System.out.println(myort.isIsused()+"     "+myort.getCOMname());
        sms.SendSms(myort);  //开始发送短信
        myort.close();
    }
}

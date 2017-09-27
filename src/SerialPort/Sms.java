package SerialPort;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import static SerialPort.StringUtil.getLength;
import static SerialPort.StringUtil.phonetoString;
import static SerialPort.StringUtil.total;

/***
 * ���Ͷ��Ų�����
 */
@SuppressWarnings("unused")
public class Sms extends JFrame{
    private CommonSms commonsms;
    private static char symbol1 = 13;  //�س���
    private static String strReturn = "", atCommand = "";

    public boolean SendSms(Port myport) {
        if(!myport.isIsused())  //�˿�δ��
        {
            System.out.println("COMͨѶ�˿�δ������!");
            return false;
        }
//        setMessageMode(myport,0); //������Ϣģʽ��0����PDU��ʽ
        // �ո�
        char symbol2 = 34;
        // ctrl~z ����ָ��
        char symbol3 = 26;
        try {
            atCommand = "AT+IPR=115200" + String.valueOf(symbol1);  //����GSMģ��Ĳ�����
            strReturn = myport.sendAT(atCommand);
            System.out.println(strReturn);         //���ؽ��
            atCommand = "AT+CMGF=0" + String.valueOf(symbol1);    //���ö��ŷ�ʽΪPDU��ʽ
            strReturn = myport.sendAT(atCommand);
            System.out.println(strReturn);        //���ؽ��
            atCommand = "AT+CSCS=\"UCS2\"" + String.valueOf(symbol1); //����PC�ַ�������
            strReturn = myport.sendAT(atCommand);
            System.out.println(strReturn);        //���ؽ��
            atCommand = "AT+CSCA?" + String.valueOf(symbol1);    //��ȡ�������ĺ��룬 ��̨��ͨ +8613010161500 
            strReturn = myport.sendAT(atCommand);
            System.out.println(strReturn);       //���ؽ��
            String at = StringUtil.encodeHex(commonsms.getSmstext().trim());//����������ת��Ϊʮ������
            String length=getLength(at);
            String phone=phonetoString(commonsms.getRecver());  //�ѽ��շ��ֻ���ת��Ϊ�ַ���
            int tol=total(length);
            if (strReturn.indexOf("OK", 0) != -1) {
                atCommand = "AT+CMGS="+tol+ String.valueOf(symbol1);//�����ֽ���
                strReturn = myport.sendAT(atCommand);
                System.out.println(strReturn);     //���ؽ��
                atCommand="0011000D9168"+phone+"000801"+ length + at+ String.valueOf(symbol3);  //�����ű���ΪPDU��ʽ
                strReturn = myport.sendAT(atCommand);
                System.out.println(strReturn);
                if (strReturn.indexOf("OK") != -1
                        && strReturn.indexOf("+CMGS") != -1) {
                    System.out.println("���ŷ��ͳɹ�...");
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("���ŷ���ʧ��...");
            return false;
        }
        System.out.println("���ŷ���ʧ��...");
        return false;
    }
    /**
     * ������Ϣģʽ
     * @param op
     * 0-pdu 1-text(Ĭ��1 �ı���ʽ )
     * @return
     */
/*    public boolean setMessageMode(Port myport,int op) {
        try {
            String atCommand = "AT+CMGF=" + String.valueOf(op)
                    + String.valueOf(symbol1);
            String  strReturn = myport.sendAT(atCommand);
            if (strReturn.indexOf("OK", 0) != -1) {
                System.out.println("*************�ı���ʽ���óɹ�************");
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
     * ���룬���ݣ����Ͷ���Ϣ
     * @param phone
     * @param countstring
     * @throws Exception
     */
    public static void sendmsn(String phone,String countstring){
        Sms sms = new Sms();
        // ���Ͳ���
        CommonSms cs = new CommonSms();  //�½�һ��CommonSms��
        cs.setRecver(phone); //��װ���շ��绰��
        cs.setSmstext(countstring); //��װ��������
        System.out.println(cs.getSmstext());
        sms.setCommonsms(cs);
        Port myort=new Port("COM6");  //����Ķ˿ںţ�Ҫ���Լ��ĵ��Ե��豸�������Ķ˿ں�
        System.out.println(myort.isIsused()+"     "+myort.getCOMname());
        sms.SendSms(myort);  //��ʼ���Ͷ���
        myort.close();
    }
}

package SerialPort;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

/*
���ڵ�jar������Ϊ���ڵĵ��ò���jdk��Χ֮�У����Բ�ͬ�Ĳ���ϵͳʹ���������в�ͬ��
comm����rxtxcomm�����Ǵ��ڰ����������ǵİ�������һ�µģ�һ��ʹ�õĻ�û������
�������˽���ʹ��rxtxcomm����comm��ʵ��̫���� ��
* 
*/
/***
 * ���ڲ���ʵ����
 */
public class Port {
    private CommPortIdentifier portId;
    private SerialPort serialPort;
    private OutputStreamWriter out;  //���������
    private InputStreamReader in;    //����������
    private String COMname;          //�˿�����

    //�õ���������
    public String getCOMname() {   
        return COMname;
    }
    //���ô�������
    public void setCOMname(String mname) {
        COMname = mname;
    }
    //�õ�����ID
    public CommPortIdentifier getPortId() {
        return portId;
    }
    //���ô���ID
    public void setPortId(CommPortIdentifier portId) {
        this.portId = portId;
    }
    
    public SerialPort getSerialPort() {
        return serialPort;
    }
    public void setSerialPort(SerialPort serialPort) {
        this.serialPort = serialPort;
    }
    public OutputStreamWriter getOut() {
        return out;
    }
    public void setOut(OutputStreamWriter out) {
        this.out = out;
    }
    public InputStreamReader getIn() {
        return in;
    }
    public void setIn(InputStreamReader in) {
        this.in = in;
    }
    public boolean isused =true;

    public boolean isIsused() {
        return isused;
    }
    public void setIsused(boolean isused) {
        this.isused = isused;
    }
    /**
     * ��com��
     * @param portName
     * @return
     */
    public  Port(String portName) {
        try {
            portId = CommPortIdentifier.getPortIdentifier(portName);//ͨ���˿���ʶ��˿�
            if (portId == null) {
                System.out.println("port is null");
            }
            try {
                serialPort = (SerialPort) portId.open(portName,20000);//�򿪴��ڣ�����20000�Ĵ򿪳�ʱʱ��
            } catch (PortInUseException e) {
                System.gc();
                e.printStackTrace();
            }
            // �����ǵõ����ں�COM��ͨѶ�����롢�������
            try {
                in = new InputStreamReader(serialPort.getInputStream());
                out = new OutputStreamWriter(serialPort.getOutputStream());
            } catch (IOException e) {
                System.gc();
                System.out.println("IOException");
            }
            // �����ǳ�ʼ��COM�ڵĴ���������紫�����ʣ�115200�ȡ�
            try {
                serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                setCOMname(portId.getName()); //���ô��ڵ�����
                setIsused(true);//ȷ�����˴���
            } catch (UnsupportedCommOperationException e) {
                e.printStackTrace();
                System.gc();
            }
        } catch (NoSuchPortException e) {
            e.printStackTrace();  //�������д�ӡ�쳣��Ϣ�ڳ����г����λ�ü�ԭ��
            System.gc();          //�����������������
        }
    }
    /**
     * ���SIM�Ƿ����
     * @return
     */
/*    public  boolean chakanPort() {
        try {
            String  atCommand = "AT+ccid";
            String  strReturn = sendAT(atCommand);
            if (strReturn.indexOf("OK", 0) != -1) {  //����ֵ�к���OK
                return true;
            }
            return false;
        } catch (Exception ex) {
            System.gc();
            ex.printStackTrace();
            return false;
        }
    }*/
    /**
     * �ر�COM��
     * @return boolean
     */
    public void close() {
        try {
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        serialPort.close();
        System.gc();
        setIsused(false);
    }

    /**
     * �򴮿���д���ַ�������
     * @param s �ַ�������
     * @throws Exception �쳣
     */
    public  void writeln(String s) throws Exception {
        out.write(s);
        out.write('\r');
        out.flush();
    }

    /**
     * ��ȡCOM����ķ����ַ���
     * @return ����ַ���
     * @throws Exception
     */
    public  String read() throws Exception {
        int data, i;
        char character;
        String answer = "";
        for (i = 0; i < 100; i++) {
            while (in.ready()) {
                data = in.read();
                if (data != -1) {
                    character = (char) data;
                    answer = answer + character;
                    Thread.sleep(1);
                } else
                    break;
            }
            if (answer.indexOf("OK") != -1) {
                break;
            }
            Thread.sleep(100);
        }
        return answer;
    }

    /**
     * �򴮿ڷ���ATָ��
     * @param atcommand ָ������
     * @return ָ��ؽ��
     * @throws java.rmi.RemoteException
     */
    public String sendAT(String atcommand) throws java.rmi.RemoteException {
        String resultString = "";
        try {
            Thread.sleep(150);
            writeln(atcommand);
            Thread.sleep(100);
            resultString = read();
            Thread.sleep(200);
        } catch (Exception e) {
            System.gc();
            System.out.println("ERROR: send AT command failed; " + "Command: "
                    + atcommand + "; Answer: " + resultString + "  " + e);
        }
        return resultString;
    }
}

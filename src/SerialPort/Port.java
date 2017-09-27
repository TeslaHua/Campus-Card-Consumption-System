package SerialPort;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Random;

/*
串口的jar包。因为串口的调用不在jdk范围之中，所以不同的操作系统使用起来会有不同。
comm包和rxtxcomm包都是串口包，而且他们的包名基本一致的，一般使用的话没有区别，
不过个人建议使用rxtxcomm包，comm包实在太老了 。
* 
*/
/***
 * 串口操作实现类
 */
public class Port {
    private CommPortIdentifier portId;
    private SerialPort serialPort;
    private OutputStreamWriter out;  //串口输出流
    private InputStreamReader in;    //串口输入流
    private String COMname;          //端口名字

    //得到串口名字
    public String getCOMname() {   
        return COMname;
    }
    //设置串口名字
    public void setCOMname(String mname) {
        COMname = mname;
    }
    //得到串口ID
    public CommPortIdentifier getPortId() {
        return portId;
    }
    //设置串口ID
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
     * 打开com口
     * @param portName
     * @return
     */
    public  Port(String portName) {
        try {
            portId = CommPortIdentifier.getPortIdentifier(portName);//通过端口名识别端口
            if (portId == null) {
                System.out.println("port is null");
            }
            try {
                serialPort = (SerialPort) portId.open(portName,20000);//打开串口，给其20000的打开超时时间
            } catch (PortInUseException e) {
                System.gc();
                e.printStackTrace();
            }
            // 下面是得到用于和COM口通讯的输入、输出流。
            try {
                in = new InputStreamReader(serialPort.getInputStream());
                out = new OutputStreamWriter(serialPort.getOutputStream());
            } catch (IOException e) {
                System.gc();
                System.out.println("IOException");
            }
            // 下面是初始化COM口的传输参数，如传输速率：115200等。
            try {
                serialPort.setSerialPortParams(115200, SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                setCOMname(portId.getName()); //设置串口的名字
                setIsused(true);//确定打开了串口
            } catch (UnsupportedCommOperationException e) {
                e.printStackTrace();
                System.gc();
            }
        } catch (NoSuchPortException e) {
            e.printStackTrace();  //在命令行打印异常信息在程序中出错的位置及原因
            System.gc();          //虚拟机进行垃圾回收
        }
    }
    /**
     * 检查SIM是否存在
     * @return
     */
/*    public  boolean chakanPort() {
        try {
            String  atCommand = "AT+ccid";
            String  strReturn = sendAT(atCommand);
            if (strReturn.indexOf("OK", 0) != -1) {  //返回值中含有OK
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
     * 关闭COM口
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
     * 向串口中写入字符串命令
     * @param s 字符串命令
     * @throws Exception 异常
     */
    public  void writeln(String s) throws Exception {
        out.write(s);
        out.write('\r');
        out.flush();
    }

    /**
     * 读取COM命令的返回字符串
     * @return 结果字符串
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
     * 向串口发送AT指令
     * @param atcommand 指令内容
     * @return 指令返回结果
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

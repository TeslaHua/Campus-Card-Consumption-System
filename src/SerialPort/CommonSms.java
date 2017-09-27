//建一个Model类，叫CommonSms,代码如下:

package SerialPort;

public class CommonSms{
    /**短信内容*/
    private String smstext;//短信内容
    
    /**短信接收发*/
    private String recver;//短信接收方
    
    public String getSmstext() {
        return smstext;
    }
    public void setSmstext(String smstext) {
        this.smstext = smstext;
    }
    public String getRecver() {
        return recver;
    }
    public void setRecver(String recver) {
        this.recver = recver;
    }
}

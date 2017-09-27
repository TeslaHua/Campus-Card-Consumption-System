package dynamicreports;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.component.Components;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.PageType;
import net.sf.dynamicreports.report.exception.DRException;

import com.lowagie.text.pdf.BaseFont;
/**
 * 
 * @ClassName: Reports
 * @Description: ��ӡ��̬pdf����
 * @author: Administrator
 * @date: 2016��12��30�� ����8:01:09
 */
public class Reports{
	
	public static Connection getConn() throws ClassNotFoundException, SQLException{
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		return(Connection) DriverManager.getConnection("jdbc:sqlserver://localhost:1433; DatabaseName=CardManagementSystem", "Sa", "Wang89918157");
	}
	
	@SuppressWarnings("deprecation")
	public static void buildReport(Connection conn,String sqlString){
		JasperReportBuilder report=DynamicReports.report();//�����ձ���
		//���ñ����һϵ����ʽ ��stl�Ǵ������Զ������һ�鷽��
		StyleBuilder boldStl=DynamicReports.stl.style().bold();
		StyleBuilder boldCenteredStl=DynamicReports.stl.style(boldStl).setHorizontalAlignment(HorizontalAlignment.CENTER);;
		StyleBuilder titleStl=DynamicReports.stl.style(boldCenteredStl).setFontSize(16);
		StyleBuilder columnTitleStl=DynamicReports.stl.style(boldCenteredStl).setBorder(DynamicReports.stl.pen1Point())
				.setBackgroundColor(Color.LIGHT_GRAY);//�����������ı�����ɫΪ��ɫ
		
		StyleBuilder fontStyleBuilder = DynamicReports.stl.style().setPadding(2)
				.setPdfFontName("STSong-Light")
				.setPdfEncoding("UniGB-UCS2-H")
				.setPdfEmbedded(BaseFont.NOT_EMBEDDED);
		
		columnTitleStl.setPdfFontName("STSong-Light")
		.setPdfEncoding("UniGB-UCS2-H")
		.setPdfEmbedded(BaseFont.NOT_EMBEDDED);
		
		titleStl.setPdfFontName("STSong-Light")
		.setPdfEncoding("UniGB-UCS2-H")
		.setPdfEmbedded(BaseFont.NOT_EMBEDDED);
		
		report.setPageFormat(PageType.A5); //����ÿһҳ�ĸ�ʽ
		
		report.columns(Columns.column("��������", "OperateTime", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
        Columns.column("�û�����", "CustomerName", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
        Columns.column("��Ǯ", "Deductmoney", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
        Columns.column("��Ǯ", "Savemoney", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
        Columns.column("�û�ID", "CustomerID", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER),
        Columns.column("���", "CustomerMoney", DataTypes.stringType()).setHorizontalAlignment(HorizontalAlignment.CENTER))
        	  .setColumnStyle(fontStyleBuilder)   //��ѯ�����ݵ������ʽ
			  .setColumnTitleStyle(columnTitleStl) //���������ķ��
			  .setHighlightDetailEvenRows(true)  //ż���и�����ʾ
			  .title(Components.text("�ͻ����ѵ�").setStyle(titleStl))//����
			  .pageFooter(Components.pageXofY().setStyle(boldCenteredStl))//ҳ��
			  .setDataSource("SELECT * FROM ReportMessage WHERE CustomerID = '"+sqlString+"'", conn);//����Դ
		try {
			//��ʾ����
			report.show(false);  //�ر�Ԥ�����ں��˳�����
			try {
				FileOutputStream fileOutputStream = new FileOutputStream("D:/test.pdf");//����һ��pdf��ŵ����λ��
				report.toPdf(fileOutputStream);//��ӡ��pdf��ַ
				try {
					fileOutputStream.flush();  //��֤pdf������
					fileOutputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (DRException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class getLatAndLngByAddress {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try { // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw  
			  
			/* д��Txt�ļ� */  
            File writename = new File("E:\\�ҵı���\\����\\communityWH.txt"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�  
            //writename.createNewFile(); // �������ļ�  
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
            
            /* ����TXT�ļ� */  
            String pathname = "E:\\�ҵı���\\����\\shequ���人��.txt"; // ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��  
            File filename = new File(pathname); // Ҫ��ȡ����·����input��txt�ļ�  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename),"UTF-8"); // ����һ������������reader  
            BufferedReader br = new BufferedReader(reader); // ����һ�����������ļ�����ת�ɼ�����ܶ���������  
            String line =  "";
            line=br.readLine();  
              
            while (line != null) {  
            	line = br.readLine(); // һ�ζ���һ������  
            	if(line==null)
            		break;
                String sline[]=line .split("\t");
                Long  id = null;
                try {
                	id=Long.parseLong(sline[0]);
                }catch(NumberFormatException e)
                {
                	
                }
                //String Name=sline[2];
                getLatAndLngByAddress GlAndLngByAddress=new getLatAndLngByAddress();
                Map<String, BigDecimal> map=GlAndLngByAddress.getLatAndLngByAddress2(sline[2]);
                
                BigDecimal lng=map.get("lng");
                BigDecimal lat=map.get("lat");
        		String output=id+"\t"+sline[2]+"\t"+lng+"\t"+lat;
        		out.write(output);  
        		out.write("\r\n");// \r\n��Ϊ���� 
        		System.out.println(output );
        		
            }  
  
            
            
            out.flush(); // �ѻ���������ѹ���ļ�  
            out.close(); // ���ǵùر��ļ�  
  
        } catch (Exception e) {  
            e.printStackTrace();  
        
    }  
		
	}
	public Map<String, BigDecimal> getLatAndLngByAddress2(String addr){       
		 String address = "";   
		 String lat = "";       
		 String lng = "";
		 String  status="";
		 Map<String, BigDecimal> map = new HashMap<String, BigDecimal>(); 
		        try {  
		            address = java.net.URLEncoder.encode(addr,"UTF-8");  
		        } catch (UnsupportedEncodingException e1) {  
		            e1.printStackTrace();  
		        } 
		        String url = String.format("http://api.map.baidu.com/geocoder/v2/?"
		        +"ak=0BnmmjUc3oredzfcbsZTG0Zh&output=json&address=%s",address);
		        URL myURL = null;
		        URLConnection httpsConn = null;  
		        //����ת��
		        try {
		            myURL = new URL(url);
		        } 
		        catch (MalformedURLException e) {
		 
		        }
		        try {
		            httpsConn = (URLConnection) myURL.openConnection();            
		            if (httpsConn != null) {
		                InputStreamReader insr = new InputStreamReader(
		                httpsConn.getInputStream(), "UTF-8");
		                BufferedReader br = new BufferedReader(insr);               
		                 String data = null;               
		                  if ((data = br.readLine()) != null) {
		                	  status=data.substring(data.indexOf("{\"status\":") 
		  		                    + ("{\"status\":").length(), data.indexOf(","));
		                	  System .out .println(status );
		                	 if(new Integer(status)!=0)
		                	 {
		                		
		                		 map.put("lat", null); 		       		       
		        		         map.put("lng", null);
		        		         
		                	 }
		                	 else {
		                    lat = data.substring(data.indexOf("\"lat\":") 
		                    + ("\"lat\":").length(), data.indexOf("},\"precise\""));
		                    lng = data.substring(data.indexOf("\"lng\":") 
		                    + ("\"lng\":").length(), data.indexOf(",\"lat\""));
		                    
		                    map.put("lat", new BigDecimal(lat)); 		       		       
		   		         map.put("lng", new BigDecimal(lng)); 
		                	 }
		                }
		                insr.close();
		            }
		        } catch (IOException e) {
		 
		        } 
		          
		                 
		         return map;
		          
		}
}

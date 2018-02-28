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
		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw  
			  
			/* 写入Txt文件 */  
            File writename = new File("E:\\我的毕设\\数据\\communityWH.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
            //writename.createNewFile(); // 创建新文件  
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
            
            /* 读入TXT文件 */  
            String pathname = "E:\\我的毕设\\数据\\shequ（武汉）.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径  
            File filename = new File(pathname); // 要读取以上路径的input。txt文件  
            InputStreamReader reader = new InputStreamReader(  
                    new FileInputStream(filename),"UTF-8"); // 建立一个输入流对象reader  
            BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言  
            String line =  "";
            line=br.readLine();  
              
            while (line != null) {  
            	line = br.readLine(); // 一次读入一行数据  
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
        		out.write("\r\n");// \r\n即为换行 
        		System.out.println(output );
        		
            }  
  
            
            
            out.flush(); // 把缓存区内容压入文件  
            out.close(); // 最后记得关闭文件  
  
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
		        //进行转码
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

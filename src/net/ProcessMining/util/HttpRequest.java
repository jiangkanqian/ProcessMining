package net.ProcessMining.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	
	public Map<Integer,String> httpGet(String reqUrl){
        URL url;
        HttpURLConnection conn = null;
        try {
            url = new URL(reqUrl);
            // 打开和URL之间的连接
            conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
//          conn.setDoInput(true);
//          conn.setUseCaches(false);
//          //默认为GET请求
            conn.setRequestMethod("GET");

            // 设置通用的请求属性
//            for(Entry<String, String> entry : otherHeaders.entrySet()){
//            	conn.setRequestProperty(entry.getKey(), entry.getValue());               
//            }
            // 建立实际的连接
            conn.connect();            
            // 定义 BufferedReader输入流来读取URL的响应
            StringBuilder sb = new StringBuilder();
            int responseCode = conn.getResponseCode();
            HashMap<Integer,String> map = new HashMap<Integer,String>();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),"utf-8"));           
            String line="";
            while ((line = reader.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
            map.put(responseCode,sb.toString());
            return map;
          
        } catch (IOException e) {
        	HashMap<Integer,String> map = new HashMap<Integer,String>();
        	map.put(-1, "error");
            return map;
        }
        finally{
        	if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }
    }
	
	public int httpPut(String urlStr,String filePath) {
		URL url;
		HttpURLConnection conn = null;
        try {
             url = new URL(urlStr);
             conn = (HttpURLConnection)url.openConnection();
             conn.setRequestMethod("PUT");
             conn.setDoInput(true);
             conn.setDoOutput(true);
//             conn.setConnectTimeout(5000);  
//	         conn.setReadTimeout(30000);
             
             if(filePath != null){
            	 OutputStream out = new DataOutputStream(conn.getOutputStream());	         
            	 //上传文件
            	 File file = new File(filePath);
            	 DataInputStream in = new DataInputStream(new FileInputStream(file));  
            	 int bytes = 0;  
            	 byte[] bufferOut = new byte[1024];  
            	 while ((bytes = in.read(bufferOut)) != -1) {  
            		 out.write(bufferOut, 0, bytes);  
            	 }  
            	 in.close();             
            	 out.flush();
            	 out.close(); 
             }	         
	            
	         int responseCode = conn.getResponseCode();
		     return responseCode;
	        	            
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally {  
            if (conn != null) {  
                conn.disconnect();  
                conn = null;  
            }  
        }
    }
	
}

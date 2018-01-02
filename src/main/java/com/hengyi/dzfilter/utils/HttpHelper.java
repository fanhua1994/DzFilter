package com.hengyi.dzfilter.utils;


import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpHelper
{
	private static HttpHelper http = null;
	private static final int TIME_OUT = 1000 * 8;
	private static final String METHOD_POST = "POST";
	private static final String METHOD_GET = "GET";
	private static final int HTTP_OK = 200;
   
	public static HttpHelper getInstance(){
		if(http == null){
			http = new HttpHelper();
		}
		return http;
	}
	/**
	 *GET方法
	 */
	public String httpGet(String urlStr)
	{
		URL url = null;
		HttpURLConnection conn = null;
		InputStream inStream = null;
		String response = "";
		try
		{
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setConnectTimeout(TIME_OUT);
			conn.setRequestProperty("Charset", "GBK");
			conn.setRequestMethod(METHOD_GET);
			conn.setRequestProperty("accept", "*/*");
			conn.connect();
			int responseCode = conn.getResponseCode();
			if (responseCode == HTTP_OK)
			{
				inStream = conn.getInputStream();
				response = getResponse(inStream);
				inStream.close();
			}
			else
			{
				response = "000"+responseCode;
			}
		} catch (Exception e){
			
		}
		finally
		{
			
			conn.disconnect();
		}
		return response.trim();
	}
	
	/*
	 * POST方法
	 */

	public String httpPost(String urlStr, String params)
	{
		byte[] data = null;
			data = params.getBytes();
		URL url = null;
		HttpURLConnection conn = null;
		InputStream inStream = null;
		String response = null;
		try
		{
			url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod(METHOD_POST);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			conn.setRequestProperty("Content-Length",String.valueOf(data.length));
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.connect();
			DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
			int responseCode = conn.getResponseCode();
			if (responseCode == HTTP_OK)
			{
				inStream = conn.getInputStream();
				response = getResponse(inStream);
				inStream.close();
			}
			else
			{
				response = "000"+responseCode;
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			conn.disconnect();
		}
		return response.trim();
	}
	
	private String getResponse(InputStream inStream) throws IOException
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = -1;
		byte[] buffer = new byte[1024];
		while((len=inStream.read(buffer))!=-1)
		{
			outputStream.write(buffer, 0, len);
		}
		byte[] data = outputStream.toByteArray();
		outputStream.close();
		return new String(data).trim();
	}
}
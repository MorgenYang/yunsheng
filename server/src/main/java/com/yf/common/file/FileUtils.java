package com.yf.common.file;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件处理工具类
 */
public class FileUtils
{

    /**
     * 输出指定文件的byte数组
     * 
     * @param filePath 文件
     * @param os 文件
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException
    {
        FileInputStream fis = null;
        try
        {
            File file = new File(filePath);
            if (!file.exists())
            {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0)
            {
                os.write(b, 0, length);
            }
        }
        catch (IOException e)
        {
            throw e;
        }
        finally
        {
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
            if (fis != null)
            {
                try
                {
                    fis.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     * 
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath)
    {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists())
        {
            file.delete();
            flag = true;
        }
        return flag;
    }
    
    /**
     * 读取图片地址 输出到页面
     * @param request
     * @param response
     * @param fileurl
     * @throws IOException 
     */
    public static void readIMGTohtml(HttpServletRequest request, HttpServletResponse response,String fileurl) throws IOException{
    	//读取本地图片输入流
    			FileInputStream inputStream = new FileInputStream(fileurl);
    			int i = inputStream.available();
    			//byte数组用于存放图片字节数据
    			byte[] buff = new byte[i];
    			inputStream.read(buff);
    			//记得关闭输入流
    			inputStream.close();
    			//设置发送到客户端的响应内容类型
    			response.setContentType("image/*");
    			OutputStream out = response.getOutputStream();
    			out.write(buff);
    			//关闭响应输出流
    			out.close();
    }
}

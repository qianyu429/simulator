package com.shhxzq.bs.util;

import com.shhxzq.bs.constants.AppConstants;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * Created by brave on 2015/8/31.
 */
public class FileDownload {

    /**
     * 下载文件
     *
     * @param path 路径
     * @param fileName 文件名
     * @param response HttpServletResponse对象
     * @return 下载结果
     * @throws UnsupportedEncodingException 不支持的字符集编码异常
     */
    public static String downloadFile(String path, String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition",
                "attachment;fileName=" + new String(fileName.getBytes("utf-8"), "utf-8"));

        File file = new File(AppConstants.APP_ROOT + path + fileName);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(file);
            os = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = is.read(b)) > 0) {
                os.write(b, 0, length);
            }
            return "success";
        } catch (FileNotFoundException e) {
            return "文件没有找到！";
        } catch (IOException e) {
            return "输入输出错误！";
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                }
            }
        }
    }
}


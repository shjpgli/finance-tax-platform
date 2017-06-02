package com.abc12366.cms.web;

import com.abc12366.cms.util.SFTPUtil;
import com.jcraft.jsch.ChannelSftp;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 附件下载
 * Created by xieyanmao on 2017-6-2.
 */

@RestController
@Scope("prototype")
@RequestMapping(value = "/download")
public class DownLoadController {

    @ResponseBody
    @RequestMapping(value = "/fj/{downId}", method = RequestMethod.GET)
    public void getFj(@PathVariable("downId") String downId,
                      final HttpServletResponse response) {
//        DownLoadVo downLoadVo = downLoadService.selectOne(downId);

//        if (downLoadVo != null && downLoadVo.getDownContent() != null) {
        if (true) {
//            downLoadVo.setDownCount(downLoadVo.getDownCount() + 1);
//            downLoadService.update(downLoadVo);
//            String filename = downLoadVo.getDownName();
//            final String downfile = downLoadVo.getWjmc();
//            String type;
//            //判断后缀
//            if(downfile != null){
//                int s = downfile.lastIndexOf(".");
//                type = downfile.substring(s + 1, downfile.length());
//            }else{
//                int s = filename.lastIndexOf(".");
//                type = filename.substring(s + 1, filename.length());
//            }
//            //组装文件名
//            if (type != null && !"".equals(type) && !filename.endsWith("."+type)) {
//                filename = filename + "." + type;
//            }


            String filename = "aaaaa.png";
            int s = filename.lastIndexOf(".");
            String type = filename.substring(s + 1, filename.length());

            String restype = "application/octet-stream";
            if (type != null && !"".equals(type)) {
                if (type.equalsIgnoreCase("xml") || type.equalsIgnoreCase("xls") || type.equalsIgnoreCase("xlsx")) {
                    restype = "application/vnd.ms-excel";
                } else if (type.equalsIgnoreCase("txt")) {
                    restype = "text/plain";
                } else if (type.equalsIgnoreCase("exe")) {
                    restype = "application/octet-stream";
                } else if (type.equalsIgnoreCase("zip")) {
                    restype = "application/zip";
                } else if (type.equalsIgnoreCase("rar")) {
                    restype = "application/octet-stream";
                } else if (type.equalsIgnoreCase("doc") || type.equalsIgnoreCase("docx")) {
                    restype = "application/msword";
                } else if (type.equalsIgnoreCase("jpg")) {
                    restype = "image/jpeg";
                } else if (type.equalsIgnoreCase("pdf")) {
                    restype = "application/pdf";
                }else if (type.equalsIgnoreCase("ppt")) {
                    restype = "application/vnd.ms-powerpoint";
                }else if (type.equalsIgnoreCase("mp3")) {
                    restype = "audio/mpeg";
                }else if (type.equalsIgnoreCase("avi")) {
                    restype = "video/x-msvideo";
                }
            }
            final String finalRestype = restype;
            final String finalFileName = filename;
            Thread thread = new Thread() {
                public void run() {
                    String fileName = null;
                    try {
                        fileName = URLEncoder.encode(finalFileName, "utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    response.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", fileName));
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType(finalRestype);
                    OutputStream out = null;
                    try {
                        out = response.getOutputStream();
//                        out.write(downLoadVo.getDownContent());

                        SFTPUtil sf = new SFTPUtil();
                        String host = "118.118.116.202";
                        int port = 22;
                        String username = "root";
                        String password = "hngs_123";
                        String directory = "/root";
                        String uploadFile = "D:/upload/2_201083103325.png";
                        String downloadFile = "123.png";
                        String saveFile = "D:/upload/2_201083103325_download.png";
                        String deleteFile = "123.png";
                        ChannelSftp sftp=sf.connect(host, port, username, password);

                        InputStream in = sf.getInputStream("root",fileName,sftp);

                        int byteCount = 0;
                        byte[] buffer = new byte[4096];

                        int bytesRead1;
                        for(boolean bytesRead = true; (bytesRead1 = in.read(buffer)) != -1; byteCount += bytesRead1) {
                            out.write(buffer, 0, bytesRead1);
                        }


                        out.flush();
                        out.close();
                    } catch (IOException e) {
//                        _log.error("IOException: " + e);
                        e.printStackTrace();
                    } finally {
                        try {
                            if (out != null)
                                out.close();
                        } catch (IOException e) {
//                            _log.error("IO close exception: " + e);
                            e.printStackTrace();
                        }
                    }
                }
            };
            thread.run();
        }
    }

}
package com.abc12366.message.util;

import com.jcraft.jsch.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xieyanmao
 */
public class SFTPUtil {

    /**
     * 连接sftp服务器
     * @param host 主机
     * @param port 端口
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public ChannelSftp connect(String host, int port, String username,
                                      String password) {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(username, host, port);
            Session sshSession = jsch.getSession(username, host, port);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
            System.out.println("Connected to " + host + ".");
        } catch (Exception e) {

        }
        return sftp;
    }

    /**
     * 上传文件
     * @param directory 上传的目录
     * @param uploadFile 要上传的文件
     * @param sftp
     */
    public void upload(String directory, String uploadFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(uploadFile);
            sftp.put(new FileInputStream(file), file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param directory 上传的目录
     * @param content 要上传的文件
     * @param sftp
     */
    public Map<String, String> uploadByByte(String directory, List<Byte> content,String fileName, ChannelSftp sftp) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            sftp.cd(directory);
            String storeName = rename(fileName);
            String filePath = directory + storeName;
            OutputStream outputStream = sftp.put(filePath);
            byte[] buffer = null;
            List<Byte> content1 = (List<Byte>) content;
            if (content1 != null) {
                buffer = SFTPUtil.listToByteArray(content1);
            }
            outputStream.write(buffer);
            map.put("fileName",fileName);
            map.put("storeName",storeName);
            map.put("filePath",filePath);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            return map;
        }
    }

    /**
     * 下载文件
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param saveFile 存在本地的路径
     * @param sftp
     */
    public void download(String directory, String downloadFile,String saveFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            File file=new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取下载文件流
     * @param directory 下载目录
     * @param downloadFile 下载的文件
     * @param sftp
     */
    public InputStream getInputStream(String directory, String downloadFile,ChannelSftp sftp) {
        InputStream in = null;
        try {
            sftp.cd(directory);
            in = sftp.get(downloadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return in;
    }

    /**
     * 删除文件
     * @param directory 要删除文件所在目录
     * @param deleteFile 要删除的文件
     * @param sftp
     */
    public void delete(String directory, String deleteFile, ChannelSftp sftp) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 列出目录下的文件
     * @param directory 要列出的目录
     * @param sftp
     * @return
     * @throws com.jcraft.jsch.SftpException
     */
    public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException {
        return sftp.ls(directory);
    }

    /**
     * 将上传的文件进行重命名
     *
     * @author geloin
     * @date 2012-3-29 下午3:39:53
     * @param name
     * @return
     */
    private static String rename(String name) {

        Long now = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date()));
        Long random = (long) (Math.random() * now);
        String fileName = now + "" + random;

        if (name.indexOf(".") != -1) {
            fileName += name.substring(name.lastIndexOf("."));
        }

        return fileName;
    }

    /**
     * 数组转byte数组
     * @param list 数组
     * @return byte[]
     */
    public static byte[] listToByteArray(List<Byte> list) {
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i<list.size();i++) {
            bytes[i] = list.get(i).byteValue();
        }
        return bytes;
    }

    public static void main(String[] args) {
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
        sf.upload(directory, uploadFile, sftp);
//sf.download(directory, downloadFile, saveFile, sftp);
//sf.delete(directory, deleteFile, sftp);
        try{
            sftp.cd(directory);
            sftp.mkdir("ss");
            System.out.println("finished");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

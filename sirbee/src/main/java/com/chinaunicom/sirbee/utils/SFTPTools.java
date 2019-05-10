package com.chinaunicom.sirbee.utils;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * @author YangHua
 * 转载请注明出处：http://www.xfok.net/2009/10/124485.html
 */
public class SFTPTools {

//    private static String path="/QPSafetyPictures";
//    private static String addr="172.25.242.23";
//    private static Integer port=22;
//    private static String username="root";
//    private static String password="Shunc#$10";

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
            System.out.println("Session connect error");

        }
        return sftp;
    }

    /**
     * 上传文件
     * @param directory
     * @param pictureName
     * @param stream
     * @param sftp
     */
    public void upload(String directory, String pictureName, InputStream stream, ChannelSftp sftp) {
        try {
            if (!isDirExist(sftp,directory)){
                sftp.mkdir(directory);
            }
            sftp.cd(directory);
//            File file=new File(filePath);
            sftp.put(stream,pictureName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /** 判断Ftp目录是否存在 */
    public boolean isDirExist(ChannelSftp sftp, String directory) {
        try{
            sftp.cd(directory);
        } catch (Exception e) {
            return false;
        }
        return true;
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
            try {
                sftp.cd(directory);
            }catch (Exception e){

            }

            File file=new File(saveFile);
            sftp.get(downloadFile, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
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
     * @throws SftpException
     */
    public Vector listFiles(String directory, ChannelSftp sftp) throws SftpException{
        return sftp.ls(directory);
    }










//    public static void main(String[] args) {
//        SFTPTools sf = new SFTPTools();
//        String host = "192.168.0.1";
//        int port = 22;
//        String username = "root";
//        String password = "root";
//        String directory = "/home/httpd/test/";
//        String uploadFile = "D:\\tmp\\upload.txt";
//        String downloadFile = "upload.txt";
//        String saveFile = "D:\\tmp\\download.txt";
//        String deleteFile = "delete.txt";
//        ChannelSftp sftp=sf.connect(host, port, username, password);
//        sf.upload(directory, uploadFile, sftp);
//        sf.download(directory, downloadFile, saveFile, sftp);
//        sf.delete(directory, deleteFile, sftp);
//        try{
//            sftp.cd(directory);
//            sftp.mkdir("ss");
//            System.out.println("finished");
//        }catch(Exception e){
//            e.printStackTrace();
//        }

//        SFTPTools sf = new SFTPTools();
//        String path=ToolsUtil.getProperties("/config/db-config.properties","img.path");
//        String addr=ToolsUtil.getProperties("/config/db-config.properties","img.addr");
//        Integer port=Integer.valueOf(ToolsUtil.getProperties("/config/db-config.properties","img.port"));
//        String username=ToolsUtil.getProperties("/config/db-config.properties","img.username");
//        String password=ToolsUtil.getProperties("/config/db-config.properties","img.password");
//
//
//        ChannelSftp sftp=sf.connect(addr, port, username, password);
//
//        String aa="C:\\Users\\47079\\AppData\\Local\\Temp\\1546409654.png";
////        sf.upload(path, aa, sftp);
//    }


    public  String ymlPropertiesValue(String key){
        try {
            InputStream is = this.getClass().getResourceAsStream("/application.yml");
            Properties props = new Properties();
            props.load(is);
            String retValue =props.getProperty(key);
            is.close();
            return retValue;
        }catch (Exception e){
            System.out.println("异常"+e);
            return "异常"+e;
        }

    }


}
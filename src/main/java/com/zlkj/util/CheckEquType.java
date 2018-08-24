package com.zlkj.util;

import com.zlkj.bean.DiskBean;
import com.zlkj.commons.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckEquType {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    public static List<DiskBean> checkEquType() {

        File[] files = File.listRoots();
        FileSystemView fileSystemView = FileSystemView.getFileSystemView();
        List<DiskBean> disks = new ArrayList<DiskBean>();
        for (int i = 0; i < files.length; i++) {
            String diskType = fileSystemView.getSystemTypeDescription(files[i]);
            String name = fileSystemView.getSystemDisplayName(files[i]);
//            System.out.println(name);
            String regex = "\\((.*)\\)";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(name);//匹配类
//            System.out.println(matcher.find());
            while (matcher.find()) {
                if (!diskType.equals("本地磁盘")) {
                    DiskBean diskBean = new DiskBean();
                    diskBean.setDiskid(matcher.group(1));
                    diskBean.setTotalSpace(FormetFileSize(files[i].getTotalSpace()));
                    diskBean.setFreeSpace(FormetFileSize(files[i].getFreeSpace()));
                    diskBean.setTrainInfo(diskBean.getDiskid());
                    disks.add(diskBean);
                }
            }
        }
        return disks;
    }

    /**
     * 转long为GB
     *
     * @param fileS
     * @return
     */
    public static String FormetFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "GB";
        }
        return fileSizeString;
    }

    public static List<DiskBean> checkEquTypeString() {
        List<DiskBean> disks = checkEquType();
        StringBuffer buffer = new StringBuffer();
        List<String> dlist = new ArrayList<>();
        String trainInfoStr = "";
        if (disks != null && disks.size() > 0) {
            for (DiskBean dinfo : disks) {
                String trainInfo = new ReadWriteProperties().getProperty(Constant.TRAININFO, dinfo.getDiskid().concat(Constant.WRITEINITNAME));
                try {
                    trainInfoStr = new String(trainInfo.getBytes("ISO-8859-1"), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                dinfo.setTrainInfo(trainInfoStr);
            }
        }
        return disks;
    }

    public static void main(String[] args) {
//        List<String> disks = checkEquType();
//        for (int i = 0; i < disks.size(); i++) {
////            System.out.println(disks.get(i));
//        }
    }

}

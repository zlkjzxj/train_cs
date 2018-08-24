package com.zlkj.fx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 写一段程序，将指定文件夹（包含该文件夹下的所有子文件夹及文件），
 * 复制到另一个新的路径内， 复制后的文件目录结构要和原目录结构保持一致；
 *
 * @author ytf.site
 */
public class Copyfile {

    public static void main(String[] args) throws IOException {
        DecimalFormat df = new DecimalFormat("#.##%");
        File file = new File("D:\\temp");//要复制的文件夹路径
        File mbpath = new File("F:\\copy");//目标文件夹路径
        List<String> list = copyPath(file);
//        int num = list.size();//总文件路径数目
        int num = 172294784;//总文件路径数目

        double count = 0.0;
        for (String s : list) {
            File path = new File(s);
            String name = path.getPath();//获取源文件路径
            String newName = name.replace(file.getParent(), mbpath.getPath());//设置复制后的新路径
            File newFile = new File(newName);
            if (path.isDirectory()) {//空文件夹创建绝对路径
                boolean b = newFile.mkdirs();
                if (b) {
                    count+=1;
                    double d = count / num;//把已经复制的文件数量作为显示进度的参数
                    System.out.println(newFile.getPath() + "复制完成" + df.format(d));
                } else {
                    System.out.println(newFile.getPath() + "复制失败！");
                }
            }
            if (path.isFile()) {
                int i = newName.lastIndexOf("\\");
                String temppath = newName.substring(0, i);
                File ftemp = new File(temppath);
                if (!ftemp.exists()) {
                    ftemp.mkdirs();
                }
                if (!newFile.exists()) {
                    boolean b = newFile.createNewFile();
                    //目标创建成功，开启流
                    if (b) {
                        boolean result = Stream(path, newFile);
                        if (result) {
                            count++;
                            double d = count / num;
                            System.out.println(newFile.getPath() + "复制完成" + df.format(d));
                        } else {
                            System.out.println(newFile.getPath() + "复制失败！");
                        }
                    }
                }

            }
        }

    }

    //创建复制内容的流方法
    public static boolean Stream(File old, File newfile) throws IOException {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(old);
            fos = new FileOutputStream(newfile);
            int len = fis.available();
            for (int i = 0; i < len; i++) {
                fos.write(fis.read());
            }

        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    //创建一个存储指定文件夹下所有目录和嵌套文件的绝对路径，存在list集合中
    public static List<String> copyPath(File file) {
        List<String> list = new ArrayList<String>();
        if (file.isDirectory()) {//如果文件是目录
            File[] files = file.listFiles();//把目录下所有目录和文件存在数组中
            if (file.length() > 0) {//该目录非空
                for (File f : files) {//遍历数组内所有对象
                    if (f.isDirectory()) {//如果还是目录
                        List<String> mlist = copyPath(f);//调用自身方法循环
                        list.addAll(mlist);//将每一个子目录文件夹添加到list集合中
                    } else {//如果遍历出来是文件
                        list.add(f.getPath());//将文件路径存入list集合
                    }
                }
            } else {//如果目录为空
                list.add(file.getPath());//将空目录路径添加到list集合
            }
        } else {//如果传进来的file对象是文件
            list.add(file.getPath());//将文件路径添加到list集合
        }
        return list;

    }
}
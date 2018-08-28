package com.zlkj.util;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

public class FileCopy {

    /**
     * 复制文件传已经上传byte[]的大小给HashTable
     *
     * @param sourcePath 源文件/文件夹
     * @param targetPath 目标文件/文件夹
     */
    public static void copy(String sourcePath, String targetPath, ProgressBar progressBar, Label label) {
        double fileRestSize = 0;
        try {
            Path startingDir = Paths.get(sourcePath);
            List<Path> result = new LinkedList<Path>();
            //读取文件列表
            Files.walkFileTree(startingDir, new FindJavaVisitor(result));
            double totalSize = getTotalSize(sourcePath);
            label.setText("导出中");
            for (Path p : result) {
                //读取需要复制的文件
                File sourceFile = new File(p.toString());
                //替换源文件路径到目标文件夹
                String tarPath = p.getParent().toString().replace(sourcePath, targetPath);
                //创建目标文件文件夹
                File fileTarPath = new File(tarPath);
                if (!fileTarPath.exists()) {
                    fileTarPath.mkdirs();
                }
                //源文件文件名
                String fName = p.getFileName().toString();
                //拼接目标文件路径和文件名
                tarPath = tarPath.concat("\\").concat(fName);
                //创建目标文件
                File targetFile = new File(tarPath);
                if (!targetFile.exists()) {
                    targetFile.createNewFile();
                }
                //io流固定格式
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(targetFile));
                int i = -1;//记录获取长度
                long j = 0;
                byte[] bt = new byte[10 * 1024];//缓冲区
                double xx = 0.00;
                while ((i = bis.read(bt)) != -1) {
                    j++;
                    fileRestSize = fileRestSize + i;
                    xx = fileRestSize / totalSize;
                    //向单例哈希表写入进度
                    if (j % 1000 == 0) {
                        System.out.println(xx + "|" + totalSize + "|" + fileRestSize);
                        progressBar.setProgress(xx);
                    }
                    if (xx == 1.0) {
                        System.out.println(xx + "|" + totalSize + "|" + fileRestSize);
                        progressBar.setProgress(xx);
                    }
                    bos.write(bt, 0, i);
                }
                bis.close();
                bos.close();
            }
            //直接在这更新lable线程会报错，所以改成这样
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //更新JavaFX的主线程的代码放在此处
                    label.setText("导出完成");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取文件列表的方法
     */
    private static class FindJavaVisitor extends SimpleFileVisitor<Path> {
        private List<Path> result;
        private String fileSuffix = null;

        public FindJavaVisitor(List<Path> result) {
            this.result = result;
        }

        public FindJavaVisitor(List<Path> result, String fileSuffix) {
            this.result = result;
            this.fileSuffix = fileSuffix;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
            //是否判断后缀名
            if (fileSuffix != null && !"".equals(fileSuffix)) {
                if (file.toString().endsWith(fileSuffix)) {
                    result.add(file);
                }
            } else {
                //result.add(file.getFileName());
                result.add(file);
            }
            //System.out.println(file.toUri());
            return FileVisitResult.CONTINUE;
        }
    }


    public static double getTotalSize(String sourcePath) {
        double total = 0.00;

        try {
            Path startingDir = Paths.get(sourcePath);
            List<Path> result = new LinkedList<Path>();
            //读取文件列表
            Files.walkFileTree(startingDir, new FindJavaVisitor(result));
            for (Path p : result) {
                //读取需要复制的文件
                File sourceFile = new File(p.toString());
                total += sourceFile.length();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return total;
    }

    public static void main(String[] args) {
        copy("H:", "F:\\test1", null, null);
    }
}

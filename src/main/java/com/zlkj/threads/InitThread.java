package com.zlkj.threads;


import com.zlkj.util.CheckEquType;

public class InitThread implements Runnable {
    private boolean flag;
    private String disks;

    private static InitThread initThread;

    private InitThread() {
        this.flag = flag;
    }

    public static synchronized InitThread getInstance() {
        if (initThread == null) {
            initThread = new InitThread();
        }
        return initThread;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }


    public void run() {
        int i = 0;
        while (flag) {
            i++;
            try {
//                disks = CheckEquType.checkEquTypeString();
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        Thread thread = new Thread(new InitThread());
        thread.start();
    }

    public String getDisks() {
        return disks;
    }

    public void setDisks(String disks) {
        this.disks = disks;
    }
}

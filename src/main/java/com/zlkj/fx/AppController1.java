package com.zlkj.fx;

import com.zlkj.bean.DiskBean;
import com.zlkj.util.CheckEquType;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class AppController1 {
    @FXML
    private ChoiceBox cd;
    @FXML
    private ChoiceBox cz;
    @FXML
    private ChoiceBox cc;
    @FXML
    private ChoiceBox cx;
    @FXML
    private Label lcxx;
    @FXML
    private Text qrxx;
    @FXML
    private ImageView initImg;

    @FXML
    private ProgressIndicator aaa;
    @FXML
    private ProgressIndicator bbb;

    @FXML
    private ProgressBar progressBar1;

    //用来拼接初始化列车信息
    private Label label1 = new Label();
    private Label label2 = new Label();
    private Label label3 = new Label();
    private Label label4 = new Label();

    @FXML
    private ImageView imageView0;
    @FXML
    private Label totalSpace0;
    @FXML
    private Label freeSpace0;
    @FXML
    private Label trainInfo0;
    @FXML
    private ImageView imageView1;
    @FXML
    private Label totalSpace1;
    @FXML
    private Label freeSpace1;
    @FXML
    private Label trainInfo1;
    @FXML
    private ImageView imageView2;
    @FXML
    private Label totalSpace2;
    @FXML
    private Label freeSpace2;
    @FXML
    private Label trainInfo2;

    //用来存放页面上所有格子(位置：盘符)
    private static Map<Integer, String> allDisks = new HashMap<>();
    //用来存放页面上已经被占用的格子(位置：盘符)
    private static Map<Integer, String> useDisks = new HashMap<>();
    private static Map<Integer, String> disks1 = new HashMap<>();

    //获取扫描到的盘符
    private List<DiskBean> disks;

    public AppController1() {

    }

    @FXML
    public void initialize() throws Exception {
        /*Thread thread = new Thread(InitThread.getInstance());
        InitThread initThread = InitThread.getInstance();
        initThread.setFlag(true);
        thread.start();
        String disks = initThread.getDisks();
        System.out.println(disks);*/
//        Thread thread = new Thread(new AppController());
//        thread.start();
        this.getDisks();
        this.outData2();
        final String[] cds = new String[]{"兰州车队", "乌鲁木齐车队", "西安车队"};
        final String[] cz0 = new String[]{"兰1组", "兰2组", "兰3组"};
        final String[] cz1 = new String[]{"乌1组", "乌2组", "乌3组"};
        final String[] cz2 = new String[]{"西1组", "西2组", "西3组"};
        final String[] ccs = new String[]{"G2812", "G55", "G5812"};
        final String[] cxs = new String[]{"1-1", "1-2", "2-1"};
//        Map map = JsonFile.readJsonFile();
        cd.setItems(FXCollections.observableArrayList(cds));
        cd.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        System.out.println(new_value);//需要的值
                        if (new_value.toString().equals("0")) {
                            cz.setItems(FXCollections.observableArrayList(cz0));
                        } else if (new_value.toString().equals("1")) {
                            cz.setItems(FXCollections.observableArrayList(cz1));
                        } else if (new_value.toString().equals("2")) {
                            cz.setItems(FXCollections.observableArrayList(cz2));
                        }
                        label1.setText(cds[new_value.intValue()]);
                    }
                });
        cz.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        cc.setItems(FXCollections.observableArrayList(ccs));
                        label2.setText(cz0[new_value.intValue()]);
                    }
                });
        cc.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        cx.setItems(FXCollections.observableArrayList(cxs));
                        label3.setText(ccs[new_value.intValue()]);
                    }
                });
        cx.getSelectionModel().selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    public void changed(ObservableValue ov, Number value, Number new_value) {
                        label4.setText(cxs[new_value.intValue()]);
                        lcxx.setText(label1.getText() + "-" + label2.getText() + "-" + label3.getText() + "-" + label4.getText());
                    }
                });

    }

    @FXML
    public void init(ActionEvent event) {
        System.out.println(lcxx.getText());
        if (disks.size() > 0) {
            if (lcxx.getText().equals("")) {
                qrxx.setText("车次信息不能为空！");
            } else if (label1.getText().equals("") || label2.getText().equals("") || label3.getText().equals("") || label4.getText().equals("")) {
                qrxx.setText("车次信息不完整");
            } else {
                qrxx.setText("初始化成功！");
            }
        } else {
            qrxx.setText("未检测到设备！");
        }


    }

    @FXML
    public void outputData(ActionEvent event) throws InterruptedException {
        aaa.progressProperty().bind(service.progressProperty());
        bbb.progressProperty().bind(service.progressProperty());
        service.restart();
    }

    Service<Integer> service = new Service<Integer>() {

        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {

                @Override
                protected Integer call() throws Exception {
                    int i = 0;
                    while (i++ < 100) {
                        updateProgress(i, 100);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }

                ;
            };
        }

    };

    /**
     * 初始化检测磁盘是否插入，如果插入则图片变亮
     *
     * @throws IOException
     */

    public void getDisks() throws IOException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        disks = CheckEquType.checkEquType();
                        if (disks.size() > 0) {
                            initImg.setImage(new Image("image/disk.png"));
                        } else {
                            initImg.setImage(new Image("image/disk1.png"));
                        }
                    }

                });
            }
        }, 1000, 5000);
    }

    /**
     * 导出数据，如果检测到有合格的磁盘插入，则把磁盘信息显示到界面上，然后开始导数据
     */

    public void outData() throws Exception {
        //获取到了所有插入磁盘的信息
        disks = CheckEquType.checkEquType();

        for (DiskBean d : disks) {
            //获取到被占用的界面上最小的那个
            int i = (int) getMinKey();
            Class c = this.getClass();
            Field f1 = c.getDeclaredField("totalSpace" + (i + 1));
            Field f2 = c.getDeclaredField("freeSpace" + (i + 1));
            Field f3 = c.getDeclaredField("trainInfo" + (i + 1));
            Field f4 = c.getDeclaredField("imageView" + (i + 1));
            f1.set(c, new Label(d.getTotalSpace()));
            f2.set(c, new Label(d.getFreeSpace()));
            f3.set(c, new Label(d.getTrainInfo()));
            f4.set(c, new ImageView("image/train1.png"));
            System.out.println(totalSpace1.getText() + "@@@");
//            useDisks.put(1, d.getDiskid());
        }
    }

    public void outData2() throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //获取到了所有插入磁盘的信息
                        disks = CheckEquType.checkEquType();
                        //有磁盘插入则往数组里面添加一个放入的位置，磁盘拔出时，把这个位置删除
                        //得有一个另外的数组和她比对
                        int v = (int) getChangeKey(disks);//这个是发生变化的值
                        if (v != -1) {
                            switch (v) {
                                case 0:
                                    imageView0.setImage(new Image("image/train2.png"));
                                    totalSpace0.setText("");
                                    freeSpace0.setText("");
                                    trainInfo0.setText("");
                                    useDisks.remove(0);
                                    break;
                                case 1:
                                    imageView1.setImage(new Image("image/train2.png"));
                                    totalSpace1.setText("");
                                    freeSpace1.setText("");
                                    trainInfo1.setText("");
                                    useDisks.remove(1);
                                    break;
                                case 2:
                                    imageView2.setImage(new Image("image/train2.png"));
                                    totalSpace2.setText("");
                                    freeSpace2.setText("");
                                    trainInfo2.setText("");
                                    useDisks.remove(2);
                                    break;
                            }
                        }
                        for (int i = 0; i < disks.size(); i++) {
                            int j = (int) getMinKey();
                            if (!useDisks.containsKey(j) || useDisks.size() < disks.size()) {
                                int k= j+1;
                                switch (j) {
                                    case -1:
                                        imageView0.setImage(new Image("image/train1.png"));
                                        totalSpace0.setText(disks.get(k).getTotalSpace());
                                        freeSpace0.setText(disks.get(k).getFreeSpace());
                                        trainInfo0.setText(disks.get(k).getDiskid());
                                        useDisks.put(0, "0");
                                        break;
                                    case 0:
                                        imageView1.setImage(new Image("image/train1.png"));
                                        totalSpace1.setText(disks.get(k).getTotalSpace());
                                        freeSpace1.setText(disks.get(k).getFreeSpace());
                                        trainInfo1.setText(disks.get(k).getDiskid());
                                        useDisks.put(1, "1");
                                        break;
                                    case 1:
                                        imageView2.setImage(new Image("image/train1.png"));
                                        totalSpace2.setText(disks.get(k).getTotalSpace());
                                        freeSpace2.setText(disks.get(k).getFreeSpace());
                                        trainInfo2.setText(disks.get(k).getDiskid());
                                        useDisks.put(2, "2");
                                        break;
                                }
                            }
                        }
                    }

                });
            }
        }, 1000, 10000);

    }

    public void outData1() throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //获取到了所有插入磁盘的信息
                        disks = CheckEquType.checkEquType();
                        for (int i = 0; i < 3; i++) {
                            //获取到被占用的界面上最小的那个
                            if (i < disks.size()) {
                                switch (i) {
                                    case 0:
                                        imageView0.setImage(new Image("image/train1.png"));
                                        totalSpace0.setText(disks.get(i).getTotalSpace());
                                        freeSpace0.setText(disks.get(i).getFreeSpace());
                                        trainInfo0.setText(disks.get(i).getDiskid());
//                                        useDisks.put(0, disks.get(i).getDiskid());
                                        break;
                                    case 1:
                                        imageView1.setImage(new Image("image/train1.png"));
                                        totalSpace1.setText(disks.get(i).getTotalSpace());
                                        freeSpace1.setText(disks.get(i).getFreeSpace());
                                        trainInfo1.setText("xxxxxxxx");
//                                        useDisks.put(1, disks.get(i).getDiskid());
                                        break;
                                    case 2:
                                        imageView2.setImage(new Image("image/train1.png"));
                                        totalSpace2.setText(disks.get(i).getTotalSpace());
                                        freeSpace2.setText(disks.get(i).getFreeSpace());
                                        trainInfo2.setText("xxxxxxxx");
//                                        useDisks.put(2, disks.get(i).getDiskid());
                                        break;
                                }
                            } else {
                                switch (i) {
                                    case 0:
                                        imageView0.setImage(new Image("image/train2.png"));
//                                        totalSpace0.setText(disks.get(i).getTotalSpace());
//                                        freeSpace0.setText(disks.get(i).getFreeSpace());
//                                        trainInfo0.setText(disks.get(i).getDiskid());
//                                        useDisks.put(0, disks.get(i).getDiskid());
                                        break;
                                    case 1:
                                        imageView1.setImage(new Image("image/train2.png"));
//                                        totalSpace1.setText(disks.get(i).getTotalSpace());
//                                        freeSpace1.setText(disks.get(i).getFreeSpace());
//                                        trainInfo1.setText("xxxxxxxx");
//                                        useDisks.put(1, disks.get(i).getDiskid());
                                        break;
                                    case 2:
                                        imageView2.setImage(new Image("image/train2.png"));
//                                        totalSpace2.setText(disks.get(i).getTotalSpace());
//                                        freeSpace2.setText(disks.get(i).getFreeSpace());
//                                        trainInfo2.setText("xxxxxxxx");
//                                        useDisks.put(2, disks.get(i).getDiskid());
                                        break;
                                }
                            }

                        }
/*
                            for (DiskBean d : disks) {
                            //获取到被占用的界面上最小的那个
                            int i = (int) getMinKey();
                            switch (i) {
                                case 0:
                                    imageView0.setImage(new Image("image/train1.png"));
                                    totalSpace0.setText(d.getTotalSpace());
                                    freeSpace0.setText(d.getFreeSpace());
                                    trainInfo0.setText(d.getDiskid());
                                    useDisks.put(0, d.getDiskid());
                                    break;
                                case 1:
                                    imageView1.setImage(new Image("image/train1.png"));
                                    totalSpace1.setText(d.getTotalSpace());
                                    freeSpace1.setText(d.getFreeSpace());
                                    trainInfo1.setText("xxxxxxxx");
                                    useDisks.put(1, d.getDiskid());
                                    break;
                                case 2:
                                    imageView2.setImage(new Image("image/train1.png"));
                                    totalSpace2.setText(d.getTotalSpace());
                                    freeSpace2.setText(d.getFreeSpace());
                                    trainInfo2.setText("xxxxxxxx");
                                    useDisks.put(2, d.getDiskid());
                                    break;
                            }
*/
                    }

                });
            }
        }, 1000, 10000);

    }


    /**
     * 25 * 求Map<K,V>中Key(键)的最小值
     * 26 * @param map
     * 27 * @return
     * 28
     */
    public static Object getMinKey() {
        Map<Integer, String> newMap = new HashMap<>();
        if (useDisks.size() > 0) {
            Set<Integer> set = useDisks.keySet();
            Object[] obj = set.toArray();
            Arrays.sort(obj);
            for (int i = 0;i<(Integer)obj[0];i++){
                if((Integer)obj[0]>0&&!useDisks.containsKey(i)){
                    return i;
                }
            }
            return obj[0];
        }
        return -1;
    }

    public static Object getChangeKey(List<DiskBean> disks) {
        Map<Integer, String> disks2 = new HashMap<>();
        for (int i = 0; i < disks.size(); i++) {
            disks2.put(i, disks.get(i).getDiskid());
        }
//        Iterator<Integer> iter1 = disks1.keySet().iterator();
        for (Map.Entry<Integer, String> entry : disks1.entrySet()) {
            if (disks2.size() > 0 && !disks2.containsValue(entry.getValue())) {
                return entry.getKey();
            }
        }
//        while (iter1.hasNext()) {
//            Integer m1Key = (Integer) iter1.next();
//            if (disks2.size() > 0 && !disks2.containsKey(m1Key)) {//若两个map中相同key对应的value不相等
//                return iter1;
//            }
//        }
        for (int i = 0; i < disks.size(); i++) {
            disks1.put(i, disks.get(i).getDiskid());
        }
        return -1;
    }
}

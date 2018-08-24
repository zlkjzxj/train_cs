package com.zlkj.fx;

import com.zlkj.bean.DiskBean;
import com.zlkj.bean.DiskMapBean;
import com.zlkj.commons.Constant;
import com.zlkj.util.AlertInfo;
import com.zlkj.util.CheckEquType;
import com.zlkj.util.ReadWriteProperties;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AppController {
    @FXML
    private AnchorPane mainPane;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab initTab;
    @FXML
    private Tab outdataTab;
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
    private ProgressBar progressBar0;
    @FXML
    private ImageView imageView1;
    @FXML
    private Label totalSpace1;
    @FXML
    private Label freeSpace1;
    @FXML
    private Label trainInfo1;
    @FXML
    private ProgressBar progressBar1;
    @FXML
    private ImageView imageView2;
    @FXML
    private Label totalSpace2;
    @FXML
    private Label freeSpace2;
    @FXML
    private Label trainInfo2;
    @FXML
    private ProgressBar progressBar2;

    //用来存放页面上所有格子(位置：盘符)
    private static Map<Integer, String> allDisks = new HashMap<>();
    //用来存放页面上已经被占用的格子(位置：盘符)
    private static Map<Integer, String> useDisks = new HashMap<>();
    private static Map<Integer, String> disks1 = new HashMap<>();

    private static Map<Integer, ProgressBar> ProgressBars = new HashMap<>();

    private static Map<Integer, String> oldDisks = new HashMap<>();//存放循环上一次的状态

    //获取扫描到的盘符
    private List<DiskBean> disks;

    //确认是否初始化的时候设置为开关
    private static boolean isInit = false;

    public AppController() {

    }

    @FXML
    public void initialize() throws Exception {
        SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
        selectionModel.select(outdataTab); //select by object
        selectionModel.select(1); //select by index starting with 0
        selectionModel.clearSelection(0);//clear your selection
        /*Thread thread = new Thread(InitThread.getInstance());
        InitThread initThread = InitThread.getInstance();
        initThread.setFlag(true);
        thread.start();
        String disks = initThread.getDisks();
        System.out.println(disks);*/
//        Thread thread = new Thread(new AppController());
//        thread.start();
        allDisks.put(0, "0");
        allDisks.put(1, "0");
        allDisks.put(2, "0");
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
        //先判断有几个盘符
        List<DiskBean> initDisks = CheckEquType.checkEquTypeString();
        if (initDisks.size() > 1) {
            AlertInfo.f_alert_informationDialog(mainPane, "初始化只能插入一个盘");
//            qrxx.setText("初始化只能插入一个盘");
            return;
        } else if (initDisks.size() == 1) {
            //判断是否存在init文件
            String filePath = initDisks.get(0).getDiskid().concat(Constant.WRITEINITPATH).concat(Constant.WRITEINITNAME);
            System.out.println(filePath);
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println(lcxx.getText());
                if (lcxx.getText().equals("")) {
//                    qrxx.setText("车次信息不能为空！");
                    AlertInfo.f_alert_informationDialog(mainPane, "车次信息不能为空");
                } else if (label1.getText().equals("") || label2.getText().equals("") || label3.getText().equals("") || label4.getText().equals("")) {
//                    qrxx.setText("车次信息不完整");
                    AlertInfo.f_alert_informationDialog(mainPane, "车次信息不完整");
                } else {
                    ReadWriteProperties rif = new ReadWriteProperties();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    rif.setProperty(Constant.TRAININFO, lcxx.getText(), filePath);
//                    qrxx.setText("初始化成功！");
                    AlertInfo.f_alert_informationDialog(mainPane, "初始化成功");
                }
            } else {
//                qrxx.setText("设备已经初始化！");
//                if (!isInit) {
                AlertInfo.f_alert_confirmDialog(mainPane, tabPane, initTab, "设备已经初始化！,是否继续初始化", isInit);
//                } else {
//                    init(event);
//                }
            }
        } else {
            AlertInfo.f_alert_informationDialog(mainPane, "未检测到设备");
//            qrxx.setText("未检测到设备！");
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
        }, 0, 10000);
    }


    public void outData2() throws Exception {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        //获取到了所有插入磁盘的信息
                        disks = CheckEquType.checkEquTypeString();
//                        List<DiskBean> disks = getdisks();
                        //循环吧盘符放到新的map
                        Map<Integer, String> newDisks = new HashMap<>();
                        //循环把其他信息放到新的map
                        Map<Integer, DiskBean> newInfoDisks = new HashMap<>();
                        for (int i = 0; i < disks.size(); i++) {
                            newDisks.put(i, disks.get(i).getTrainInfo());
                            newInfoDisks.put(i, disks.get(i));

                        }

                        //新的map和旧的map去做比较。返回新增和删除的值
//                        Map<String, Map<Integer, String>> addrmmap = calcDisksChange(newDisks, newInfoDisks);
                        DiskMapBean addrmmap = calcDisksChange(newDisks, newInfoDisks);
                        //获取删除的盘
                        Map<Integer, String> rmmap = addrmmap.getRm();
                        //循环删除的盘，改变 allDiskd的状态
                        for (Map.Entry<Integer, String> entry : rmmap.entrySet()) {
                            if (allDisks.containsValue(entry.getValue())) {
                                //修改移除位置的样式
                                List<Integer> list = getKeyByValue(entry.getValue());
                                updateRmStyle(list.get(0));
                                //修改移除位置的状态
                                allDisks.put(list.get(0), "0");
                            }
                        }
                        //获取增加的盘
                        Map<Integer, String> addmap = addrmmap.getAdd();
                        Map<Integer, DiskBean> addInfoMap = addrmmap.getAddInfo();
                        //循环增加的盘，从小到大去添加
                        for (Map.Entry<Integer, String> entry : addmap.entrySet()) {
                            int minkey = (int) getMinKey();
                            if (allDisks.containsKey(minkey)) {
                                //修改添加位置的样式
                                updateAddStyle(minkey, entry.getValue(), addInfoMap.get(entry.getKey()));
                                //修改添加位置的状态
                                allDisks.put(minkey, entry.getValue());
                            }
                        }


                    }

                });
            }
        }, 0, 10000);

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
                                        break;
                                    case 1:
                                        imageView1.setImage(new Image("image/train1.png"));
                                        totalSpace1.setText(disks.get(i).getTotalSpace());
                                        freeSpace1.setText(disks.get(i).getFreeSpace());
                                        trainInfo1.setText("xxxxxxxx");
                                        break;
                                    case 2:
                                        imageView2.setImage(new Image("image/train1.png"));
                                        totalSpace2.setText(disks.get(i).getTotalSpace());
                                        freeSpace2.setText(disks.get(i).getFreeSpace());
                                        trainInfo2.setText("xxxxxxxx");
                                        break;
                                }
                            } else {
                                switch (i) {
                                    case 0:
                                        imageView0.setImage(new Image("image/train2.png"));
//                                        totalSpace0.setText(disks.get(i).getTotalSpace());
//                                        freeSpace0.setText(disks.get(i).getFreeSpace());
//                                        trainInfo0.setText(disks.get(i).getName());
//                                        useDisks.put(0, disks.get(i).getName());
                                        break;
                                    case 1:
                                        imageView1.setImage(new Image("image/train2.png"));
//                                        totalSpace1.setText(disks.get(i).getTotalSpace());
//                                        freeSpace1.setText(disks.get(i).getFreeSpace());
//                                        trainInfo1.setText("xxxxxxxx");
//                                        useDisks.put(1, disks.get(i).getName());
                                        break;
                                    case 2:
                                        imageView2.setImage(new Image("image/train2.png"));
//                                        totalSpace2.setText(disks.get(i).getTotalSpace());
//                                        freeSpace2.setText(disks.get(i).getFreeSpace());
//                                        trainInfo2.setText("xxxxxxxx");
//                                        useDisks.put(2, disks.get(i).getName());
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
                                    trainInfo0.setText(d.getName());
                                    useDisks.put(0, d.getName());
                                    break;
                                case 1:
                                    imageView1.setImage(new Image("image/train1.png"));
                                    totalSpace1.setText(d.getTotalSpace());
                                    freeSpace1.setText(d.getFreeSpace());
                                    trainInfo1.setText("xxxxxxxx");
                                    useDisks.put(1, d.getName());
                                    break;
                                case 2:
                                    imageView2.setImage(new Image("image/train1.png"));
                                    totalSpace2.setText(d.getTotalSpace());
                                    freeSpace2.setText(d.getFreeSpace());
                                    trainInfo2.setText("xxxxxxxx");
                                    useDisks.put(2, d.getName());
                                    break;
                            }
*/
                    }

                });
            }
        }, 10000, 10000);

    }


    /**
     * 25 * 求Map<K,V>中Key(键)的最小值
     * 26 * @param map
     * 27 * @return
     * 28
     */
    public static Object getMinKey1() {
        Map<Integer, String> newMap = new HashMap<>();
        if (useDisks.size() > 0) {
            Set<Integer> set = useDisks.keySet();
            Object[] obj = set.toArray();
            Arrays.sort(obj);
            for (int i = 0; i < (Integer) obj[0]; i++) {
                if ((Integer) obj[0] > 0 && !useDisks.containsKey(i)) {
                    return i;
                }
            }
            return obj[0];
        }
        return -1;
    }

    public static DiskMapBean calcDisksChange
            (Map<Integer, String> newDisks, Map<Integer, DiskBean> newInfoDisks) {
        Map<Integer, String> addDisks = new HashMap<>();//新旧比较存放增加的状态
        Map<Integer, DiskBean> addInfoDisks = new HashMap<>();//新旧比较存放增加的信息
        Map<Integer, String> rmDisks = new HashMap<>();//新旧比较存放移除的状态
        Map<String, Map<Integer, String>> adrmDisks = new HashMap<>();

        //循环旧的，判断新的是不否包含旧的，那就是移除的
        for (Map.Entry<Integer, String> entry : oldDisks.entrySet()) {
            if (!newDisks.containsValue(entry.getValue())) {
                rmDisks.put(entry.getKey(), entry.getValue());
            }
        }
        //循环新的，判断旧的是不否包含新的，那就是新增的
        for (Map.Entry<Integer, String> entry : newDisks.entrySet()) {
            if (!oldDisks.containsValue(entry.getValue())) {
                addDisks.put(entry.getKey(), entry.getValue());
//                addInfoDisks.put(entry.getKey(),diskBean.getTrainInfo()+"@"+diskBean.getTotalSpace()+"@"+diskBean.getFreeSpace());
                addInfoDisks.put(entry.getKey(), newInfoDisks.get(entry.getKey()));
            }
        }
        oldDisks = newDisks;

//        adrmDisks.put("add", addDisks);
//        adrmDisks.put("addInfo", addInfoDisks);
//        adrmDisks.put("rm", rmDisks);
        DiskMapBean bean = new DiskMapBean(addDisks, rmDisks, addInfoDisks);

        return bean;
    }

    public static Object getMinKey() {
        if (allDisks.size() > 0) {
            Set<Integer> set = allDisks.keySet();
            Object[] obj = set.toArray();
            Arrays.sort(obj);
            for (int i = 0; i < obj.length; i++) {
                if (allDisks.get(i).equals("0")) {
                    return obj[i];
                }
            }
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

    public void updateRmStyle(int key) {
        switch (key) {
            case 0:
                imageView0.setImage(new Image("image/train2.png"));
                totalSpace0.setText("");
                freeSpace0.setText("");
                trainInfo0.setText("");
                progressBar0.setProgress(0.00);
                break;
            case 1:
                imageView1.setImage(new Image("image/train2.png"));
                totalSpace1.setText("");
                freeSpace1.setText("");
                trainInfo1.setText("");
                progressBar1.setProgress(0.00);

                break;
            case 2:
                imageView2.setImage(new Image("image/train2.png"));
                totalSpace2.setText("");
                freeSpace2.setText("");
                trainInfo2.setText("");
                progressBar2.setProgress(0.00);
                break;
        }
    }

    public void updateAddStyle(int key, String trainInfo, DiskBean diskBean) {
        switch (key) {
            case 0:
                imageView0.setImage(new Image("image/train1.png"));
                totalSpace0.setText(diskBean.getTotalSpace());
                freeSpace0.setText(diskBean.getFreeSpace());
                trainInfo0.setText(trainInfo);
                //启动线程给progressBar赋值
                updateProgress(progressBar0, diskBean);
                ProgressBars.put(0, progressBar0);
                break;
            case 1:
                imageView1.setImage(new Image("image/train1.png"));
                totalSpace1.setText(diskBean.getTotalSpace());
                freeSpace1.setText(diskBean.getFreeSpace());
                trainInfo1.setText(trainInfo);
                updateProgress(progressBar1, diskBean);
                ProgressBars.put(0, progressBar1);
                break;
            case 2:
                imageView2.setImage(new Image("image/train1.png"));
                totalSpace2.setText(diskBean.getTotalSpace());
                freeSpace2.setText(diskBean.getFreeSpace());
                trainInfo2.setText(trainInfo);
                updateProgress(progressBar2, diskBean);
                ProgressBars.put(0, progressBar2);
                break;
        }
    }


    public static List<DiskBean> getdisks() {
        File file = new File("E:/fuck.txt");
        List<DiskBean> yy = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        String s = "";
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            while ((s = br.readLine()) != null) {
//            sb.append(s + "\n");
                sb.append(s);
            }

            br.close();
            String str = sb.toString();
            System.out.println(str);
            if (!str.equals("")) {
                String[] strs = str.split(",");
                for (int i = 0; i < strs.length; i++) {
                    DiskBean diskBean = new DiskBean();
                    diskBean.setTrainInfo(strs[i]);
                    diskBean.setFreeSpace("free|" + strs[i]);
                    diskBean.setTotalSpace("total|" + strs[i]);
                    yy.add(diskBean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return yy;
    }

    public static List<Integer> getKeyByValue(String value) {
        List<Integer> keyList = new ArrayList<>();
        for (Object key : allDisks.keySet()) {
            if (allDisks.get(key).equals(value)) {
                keyList.add((Integer) key);
            }
        }
        return keyList;
    }

    //假的进度条
    public void updateProgress(final ProgressBar progressBar, DiskBean diskBean) {
        if (diskBean.getTrainInfo().equals("")) {//判断是否有类车编号，如果没有则提示，有则自动导入
            AlertInfo.f_alert_confirmDialog(mainPane, tabPane, initTab, "设备尚未初始化！,是否初始化", isInit);
        } else {


            final double[] i = {0.00};
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (i[0] <= 0.30) {
                        progressBar.setStyle("-fx-accent: red;");
                    } else if (i[0] > 0.30 && i[0] <= 0.60) {
                        progressBar.setStyle("-fx-accent: yellow;");
                    } else if (i[0] > 0.60 && i[0] <= 0.90) {
                        progressBar.setStyle("-fx-accent: blue;");
                    } else if (i[0] >= 1.00) {
                        progressBar.setStyle("-fx-accent: green;");
                        this.cancel();
                        System.out.println("任务结束");
                    }
                    i[0] += Math.random() * 0.1;
                    progressBar.setProgress(i[0]);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 1000);
        }
    }
}

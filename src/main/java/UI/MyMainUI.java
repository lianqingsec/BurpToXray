/*
 * Created by JFormDesigner on Fri May 19 09:48:53 CST 2023
 */

package UI;

import javax.swing.border.*;
import javax.swing.event.*;

import Tools.Constants;
import burp.BurpExtender;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.*;

/**
 * @author Anonymous
 */
public class MyMainUI extends JPanel {
    private BurpExtender burpExtender;
    static DefaultTableModel tableModel;
    // 存放历史记录的 HashMap
    public static HashMap<Integer, String[]> hisoryList = new HashMap();

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel2;
    private JPanel proxyToXray;
    private JLabel label1;
    private JLabel label2;
    private JTextField ProxyIP;
    private JTextField ProxyPort;
    private JButton StartButton;
    private JCheckBox AllCheckBox;
    private JCheckBox ProxyCheckBox;
    private JCheckBox RepeaterCheckBox;
    private JCheckBox IntruderCheckBox;
    private JButton SaveButtonSeting;
    private JButton SaveButtonSeting2;
    private JPanel QuickCall;
    private JLabel label3;
    private JTextField cmdPathTXT;
    private JLabel label4;
    private JTextField pythonPathTXT;
    private JLabel label5;
    private JCheckBox checkBox1;
    private JCheckBox checkBox2;
    private JCheckBox checkBox3;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JPanel QuickCall2;
    private JLabel label6;
    private JTextField callPath;
    private JButton button4;
    private JCheckBox checkBox4;
    private JLabel label7;
    private JTextField callArgs;
    private JButton button6;
    private JPanel QuickCall3;
    private JLabel label8;
    private JTextField callPath2;
    private JButton button5;
    private JCheckBox checkBox5;
    private JLabel label9;
    private JTextField callArgs2;
    private JButton button7;
    private JPanel QuickCall4;
    private JLabel label10;
    private JTextField callPath3;
    private JButton button8;
    private JCheckBox checkBox6;
    private JLabel label11;
    private JTextField callArgs3;
    private JButton button9;
    private JScrollPane scrollPane1;
    private static JTable hisory;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
    public void init() {

        // 加载初始化配置进来
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("Lian-Tools.properties"));

            Constants.IP = (String) properties.get("IP");
            Constants.PORT = Integer.parseInt(String.valueOf(properties.get("PORT")));

            Constants.allProxy = Boolean.parseBoolean((String) properties.get("allProxy"));
            Constants.Proxy = Boolean.parseBoolean((String) properties.get("Proxy"));
            Constants.RepeaterProxy = Boolean.parseBoolean((String) properties.get("repeaterProxy"));
            Constants.Intruder = Boolean.parseBoolean((String) properties.get("intruderProxy"));

            // 根据配置文件进行勾选框
            if (Constants.allProxy) {
                AllCheckBox.setSelected(true);
            }
            if (Constants.Proxy) {
                ProxyCheckBox.setSelected(true);
            }
            if (Constants.RepeaterProxy) {
                RepeaterCheckBox.setSelected(true);
            }
            if (Constants.Intruder) {
                IntruderCheckBox.setSelected(true);
            }
            if (Constants.IP != null) {
                ProxyIP.setText(Constants.IP);
            }
            if (Constants.PORT > 0) {
                ProxyPort.setText(String.valueOf(Constants.PORT));
            }

            // 全局调用变量设置
            // 读取到全局变量当中
            Constants.pathCmdText = String.valueOf(properties.get("pathCmdText"));
            Constants.pythonPathText = String.valueOf(properties.get("pythonPathText"));
            Constants.Startcall01 = Boolean.parseBoolean((String) properties.get("Startcall01"));
            Constants.Startcall02 = Boolean.parseBoolean((String) properties.get("Startcall02"));
            Constants.Startcall03 = Boolean.parseBoolean((String) properties.get("Startcall03"));

            if (!Objects.equals(Constants.pathCmdText, "")) {
                cmdPathTXT.setText(Constants.pathCmdText);
            }
            if (!Objects.equals(Constants.pythonPathText, "")) {
                pythonPathTXT.setText(Constants.pythonPathText);
            }
            if (Constants.Startcall01) {
                checkBox1.setSelected(true);
            }
            if (Constants.Startcall02) {
                checkBox2.setSelected(true);
            }
            if (Constants.Startcall03) {
                checkBox3.setSelected(true);
            }

            // 快捷调用一
            Constants.callPathText = String.valueOf(properties.get("callPathText"));
            Constants.callArgsText = String.valueOf(properties.get("callArgsText"));
            Constants.selected04 = Boolean.parseBoolean((String) properties.get("selected04"));

            if (!Objects.equals(Constants.callPathText, "")) {
                callPath.setText(Constants.callPathText);
            }
            if (!Objects.equals(Constants.callArgsText, "")) {
                callArgs.setText(Constants.callArgsText);
            }
            if (Constants.selected04) {
                checkBox4.setSelected(true);
            }

            // 快捷调用二
            Constants.callPathText2 = String.valueOf(properties.get("callPathText2"));
            Constants.callArgsText2 = String.valueOf(properties.get("callArgsText2"));
            Constants.selected042 = Boolean.parseBoolean((String) properties.get("selected042"));

            if (!Objects.equals(Constants.callPathText2, "")) {
                callPath2.setText(Constants.callPathText2);
            }
            if (!Objects.equals(Constants.callArgsText2, "")) {
                callArgs2.setText(Constants.callArgsText2);
            }
            if (Constants.selected042) {
                checkBox5.setSelected(true);
            }

            // 快捷调用三
            Constants.callPathText3 = String.valueOf(properties.get("callPathText3"));
            Constants.callArgsText3 = String.valueOf(properties.get("callArgsText3"));
            Constants.selected043 = Boolean.parseBoolean((String) properties.get("selected043"));

            if (!Objects.equals(Constants.callPathText3, "")) {
                callPath3.setText(Constants.callPathText3);
            }
            if (!Objects.equals(Constants.callArgsText3, "")) {
                callArgs3.setText(Constants.callArgsText3);
            }
            if (Constants.selected043) {
                checkBox6.setSelected(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 初始化历史记录框
        // 创建表格模型
        hisoryInit();
    }

    public static void hisoryInit() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("序号");
        tableModel.addColumn("URL");
        tableModel.addColumn("来自");

        hisory.setModel(tableModel);
        // 测试数据
//        for (int i = 0; i < 200; i++) {
//            String[] testDate = {"http://www.baidu.com", "自发"};
//            hisoryList.put(hisoryList.size() + 1, testDate);
//        }
        try {
            for (Map.Entry<Integer, String[]> Entry : hisoryList.entrySet()) {
                String[] mergedArray = new String[Entry.getValue().length + 1];
                mergedArray[0] = String.valueOf(Entry.getKey());
                System.arraycopy(Entry.getValue(), 0, mergedArray, 1, Entry.getValue().length);

                tableModel.addRow(mergedArray);
                tableModel.fireTableDataChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setHisory(String url, String source) {
        boolean isCz = false;
        // 判断map中是否存在这个url了
        for (Map.Entry<Integer, String[]> entry : hisoryList.entrySet()) {
            if (entry.getValue()[0].equals(url)) {
                isCz = true;
            }
        }
        if (!isCz) {
            String[] testDate = {url, source};
            hisoryList.put(hisoryList.size() + 1, testDate);
        }
        // 更新
        hisoryInit();
    }

    public MyMainUI(BurpExtender burp) {
        burpExtender = burp;
        // 初始化
        init();
    }

    // 全局变量cmd终端路径选择按钮
    private void button2(ActionEvent e) {
        // 创建一个默认打开用户文件夹的文件选择器
        JFileChooser chooser = new JFileChooser();
        int flag = chooser.showOpenDialog(this);
        //若选择了文件，则打印选择了什么文件
        if (flag == JFileChooser.APPROVE_OPTION) {
            String cmdPath = chooser.getSelectedFile().getPath();
            BurpExtender.appCallbacks.printOutput("\n选择文件路径" + cmdPath);
            cmdPathTXT.setText(cmdPath);
        }
    }


    private void button3(ActionEvent e) {
        // 创建一个默认打开用户文件夹的文件选择器
        JFileChooser chooser = new JFileChooser();
        int flag = chooser.showOpenDialog(this);
        //若选择了文件，则打印选择了什么文件
        if (flag == JFileChooser.APPROVE_OPTION) {
            String cmdPath = chooser.getSelectedFile().getPath();
            BurpExtender.appCallbacks.printOutput("\n选择文件路径" + cmdPath);
            pythonPathTXT.setText(cmdPath);
        }
    }


    private void button4(ActionEvent e) {
        // 创建一个默认打开用户文件夹的文件选择器
        JFileChooser chooser = new JFileChooser();
        int flag = chooser.showOpenDialog(this);
        //若选择了文件，则打印选择了什么文件
        if (flag == JFileChooser.APPROVE_OPTION) {
            String cmdPath = chooser.getSelectedFile().getPath();
            BurpExtender.appCallbacks.printOutput("\n选择文件路径" + cmdPath);
            callPath.setText(cmdPath);
        }
    }

    /*
     * 转发设置的保存
     * */
    private void SaveButtonSeting(ActionEvent e) {
        // 读取当前的所有配置并写入文件
        String IP = ProxyIP.getText();
        String PORT = ProxyPort.getText();

        boolean all = AllCheckBox.isSelected();
        boolean proxy = ProxyCheckBox.isSelected();
        boolean repeater = RepeaterCheckBox.isSelected();
        boolean intruder = IntruderCheckBox.isSelected();


        // 写配置文件
        Properties properties = new Properties();
        properties.setProperty("IP", IP);
        properties.setProperty("PORT", PORT);
        properties.setProperty("allProxy", String.valueOf(all));
        properties.setProperty("Proxy", String.valueOf(proxy));
        properties.setProperty("repeaterProxy", String.valueOf(repeater));
        properties.setProperty("intruderProxy", String.valueOf(intruder));


        // 全局变量设置
        String pathCmdText = cmdPathTXT.getText();
        String pythonPathText = pythonPathTXT.getText();
        boolean Startcall01 = checkBox1.isSelected();
        boolean Startcall02 = checkBox2.isSelected();
        boolean Startcall03 = checkBox3.isSelected();

        // 写配置文件
        properties.setProperty("pathCmdText", pathCmdText);
        properties.setProperty("pythonPathText", pythonPathText);
        properties.setProperty("Startcall01", String.valueOf(Startcall01));
        properties.setProperty("Startcall02", String.valueOf(Startcall02));
        properties.setProperty("Startcall03", String.valueOf(Startcall03));


        // 快捷调用一的配置
        String callPathText = callPath.getText();
        String callArgsText = callArgs.getText();
        boolean selected04 = checkBox4.isSelected();
        properties.setProperty("callPathText", callPathText);
        properties.setProperty("callArgsText", callArgsText);
        properties.setProperty("selected04", String.valueOf(selected04));


        // 快捷调用二的配置
        String callPathText2 = callPath2.getText();
        String callArgsText2 = callArgs2.getText();
        boolean selected042 = checkBox5.isSelected();
        properties.setProperty("callPathText2", callPathText2);
        properties.setProperty("callArgsText2", callArgsText2);
        properties.setProperty("selected042", String.valueOf(selected042));

        // 快捷调用三的配置
        String callPathText3 = callPath3.getText();
        String callArgsText3 = callArgs3.getText();
        boolean selected043 = checkBox6.isSelected();
        properties.setProperty("callPathText3", callPathText3);
        properties.setProperty("callArgsText3", callArgsText3);
        properties.setProperty("selected043", String.valueOf(selected043));

        try {
            properties.store(new FileWriter("Lian-Tools.properties"), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        init();
    }

    // 流量转发监听器
    private void Start(ActionEvent e) {
        // 无论开启还是关闭都读取配置文件
        init();

        // 判断当前是监听状态还是停止状态
        if (e.getActionCommand().equals("开启流量转发")) {
            Constants.startProxy = true;

            JButton source = (JButton) e.getSource();
            source.setText("关闭流量转发");
            source.setBackground(Color.red);
        } else {
            Constants.startProxy = false;

            JButton source = (JButton) e.getSource();
            source.setText("开启流量转发");
            source.setBackground(Color.green);
        }
    }

    // 清空历史记录
    private void clearHistry(ActionEvent e) {
        hisoryList.clear();
        hisoryInit();
    }

    {
        initComponents();
    }

    // 确认配置监听器
    public void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel2 = new JPanel();
        proxyToXray = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        ProxyIP = new JTextField();
        ProxyPort = new JTextField();
        StartButton = new JButton();
        AllCheckBox = new JCheckBox();
        ProxyCheckBox = new JCheckBox();
        RepeaterCheckBox = new JCheckBox();
        IntruderCheckBox = new JCheckBox();
        SaveButtonSeting = new JButton();
        SaveButtonSeting2 = new JButton();
        QuickCall = new JPanel();
        label3 = new JLabel();
        cmdPathTXT = new JTextField();
        label4 = new JLabel();
        pythonPathTXT = new JTextField();
        label5 = new JLabel();
        checkBox1 = new JCheckBox();
        checkBox2 = new JCheckBox();
        checkBox3 = new JCheckBox();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        QuickCall2 = new JPanel();
        label6 = new JLabel();
        callPath = new JTextField();
        button4 = new JButton();
        checkBox4 = new JCheckBox();
        label7 = new JLabel();
        callArgs = new JTextField();
        button6 = new JButton();
        QuickCall3 = new JPanel();
        label8 = new JLabel();
        callPath2 = new JTextField();
        button5 = new JButton();
        checkBox5 = new JCheckBox();
        label9 = new JLabel();
        callArgs2 = new JTextField();
        button7 = new JButton();
        QuickCall4 = new JPanel();
        label10 = new JLabel();
        callPath3 = new JTextField();
        button8 = new JButton();
        checkBox6 = new JCheckBox();
        label11 = new JLabel();
        callArgs3 = new JTextField();
        button9 = new JButton();
        scrollPane1 = new JScrollPane();
        hisory = new JTable();

        //======== this ========
        setLayout(new BorderLayout());

        //======== panel2 ========
        {
            panel2.setLayout(null);

            //======== proxyToXray ========
            {
                proxyToXray.setBorder(new CompoundBorder(
                    new TitledBorder("\u6d41\u91cf\u8f6c\u53d1\u5230 Xray \u914d\u7f6e\u533a"),
                    Borders.DLU2_BORDER));
                proxyToXray.setLayout(null);

                //---- label1 ----
                label1.setText("\u4ee3\u7406 IP");
                proxyToXray.add(label1);
                label1.setBounds(70, 35, 60, label1.getPreferredSize().height);

                //---- label2 ----
                label2.setText("\u4ee3\u7406 PORT");
                proxyToXray.add(label2);
                label2.setBounds(70, 70, 75, 17);

                //---- ProxyIP ----
                ProxyIP.setText("127.0.0.1");
                proxyToXray.add(ProxyIP);
                ProxyIP.setBounds(170, 30, 105, 25);

                //---- ProxyPort ----
                ProxyPort.setText("9006");
                proxyToXray.add(ProxyPort);
                ProxyPort.setBounds(170, 65, 105, 25);

                //---- StartButton ----
                StartButton.setText("\u5f00\u542f\u6d41\u91cf\u8f6c\u53d1");
                StartButton.addActionListener(e -> {
			Start(e);
			Start(e);
		});
                proxyToXray.add(StartButton);
                StartButton.setBounds(70, 100, 158, StartButton.getPreferredSize().height);

                //---- AllCheckBox ----
                AllCheckBox.setText("\u76d1\u63a7 All \u6d41\u91cf");
                proxyToXray.add(AllCheckBox);
                AllCheckBox.setBounds(new Rectangle(new Point(70, 140), AllCheckBox.getPreferredSize()));

                //---- ProxyCheckBox ----
                ProxyCheckBox.setText("\u76d1\u63a7 Proxy");
                proxyToXray.add(ProxyCheckBox);
                ProxyCheckBox.setBounds(70, 160, 93, 22);

                //---- RepeaterCheckBox ----
                RepeaterCheckBox.setText("\u76d1\u63a7 Repeater");
                proxyToXray.add(RepeaterCheckBox);
                RepeaterCheckBox.setBounds(70, 180, 110, 22);

                //---- IntruderCheckBox ----
                IntruderCheckBox.setText("\u76d1\u63a7 Intruder");
                proxyToXray.add(IntruderCheckBox);
                IntruderCheckBox.setBounds(70, 200, 105, 22);

                //---- SaveButtonSeting ----
                SaveButtonSeting.setText("\u4fdd\u5b58\u914d\u7f6e");
                SaveButtonSeting.addActionListener(e -> SaveButtonSeting(e));
                proxyToXray.add(SaveButtonSeting);
                SaveButtonSeting.setBounds(70, 230, 158, 30);

                //---- SaveButtonSeting2 ----
                SaveButtonSeting2.setText("\u6e05\u7a7a\u5386\u53f2");
                SaveButtonSeting2.addActionListener(e -> {
			SaveButtonSeting(e);
			clearHistry(e);
		});
                proxyToXray.add(SaveButtonSeting2);
                SaveButtonSeting2.setBounds(75, 280, 158, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < proxyToXray.getComponentCount(); i++) {
                        Rectangle bounds = proxyToXray.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = proxyToXray.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    proxyToXray.setMinimumSize(preferredSize);
                    proxyToXray.setPreferredSize(preferredSize);
                }
            }
            panel2.add(proxyToXray);
            proxyToXray.setBounds(1560, 50, 330, 350);

            //======== QuickCall ========
            {
                QuickCall.setBorder(new CompoundBorder(
                    new TitledBorder("\u5feb\u6377\u8c03\u7528\u5168\u5c40\u53d8\u91cf\u914d\u7f6e\u533a"),
                    Borders.DLU2_BORDER));
                QuickCall.setLayout(null);

                //---- label3 ----
                label3.setText("\u8c03\u7528\u9ed8\u8ba4\u7ec8\u7aef\u7a0b\u5e8f\u8def\u5f84\uff1a");
                QuickCall.add(label3);
                label3.setBounds(5, 35, 145, 30);
                QuickCall.add(cmdPathTXT);
                cmdPathTXT.setBounds(155, 35, 295, cmdPathTXT.getPreferredSize().height);

                //---- label4 ----
                label4.setText("python \u7a0b\u5e8f\u8def\u5f84\uff1a");
                QuickCall.add(label4);
                label4.setBounds(5, 95, 130, 30);
                QuickCall.add(pythonPathTXT);
                pythonPathTXT.setBounds(155, 100, 295, 30);

                //---- label5 ----
                label5.setText("\u9009\u62e9\u5f00\u542f\u54ea\u51e0\u4e2a\u5feb\u6377\u8c03\u7528");
                QuickCall.add(label5);
                label5.setBounds(195, 150, 175, 40);

                //---- checkBox1 ----
                checkBox1.setText("\u5feb\u6377\u8c03\u7528\u4e00");
                QuickCall.add(checkBox1);
                checkBox1.setBounds(65, 205, 120, checkBox1.getPreferredSize().height);

                //---- checkBox2 ----
                checkBox2.setText("\u5feb\u6377\u8c03\u7528\u4e8c");
                QuickCall.add(checkBox2);
                checkBox2.setBounds(205, 205, 120, 22);

                //---- checkBox3 ----
                checkBox3.setText("\u5feb\u6377\u8c03\u7528\u4e09");
                QuickCall.add(checkBox3);
                checkBox3.setBounds(360, 205, 120, 22);

                //---- button1 ----
                button1.setText("\u4fdd\u5b58\u5f53\u524d\u8bbe\u7f6e");
                button1.addActionListener(e -> {
			SaveButtonSeting(e);
			SaveButtonSeting(e);
		});
                QuickCall.add(button1);
                button1.setBounds(160, 260, 180, button1.getPreferredSize().height);

                //---- button2 ----
                button2.setText("\u6253\u5f00");
                button2.addActionListener(e -> button2(e));
                QuickCall.add(button2);
                button2.setBounds(470, 35, 90, button2.getPreferredSize().height);

                //---- button3 ----
                button3.setText("\u6253\u5f00");
                button3.addActionListener(e -> button3(e));
                QuickCall.add(button3);
                button3.setBounds(470, 100, 90, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < QuickCall.getComponentCount(); i++) {
                        Rectangle bounds = QuickCall.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = QuickCall.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    QuickCall.setMinimumSize(preferredSize);
                    QuickCall.setPreferredSize(preferredSize);
                }
            }
            panel2.add(QuickCall);
            QuickCall.setBounds(35, 50, 575, 320);

            //======== QuickCall2 ========
            {
                QuickCall2.setBorder(new CompoundBorder(
                    new TitledBorder("\u5feb\u6377\u8c03\u7528\u914d\u7f6e\u533a \u4e00"),
                    Borders.DLU2_BORDER));
                QuickCall2.setLayout(null);

                //---- label6 ----
                label6.setText("\u8c03\u7528\u7a0b\u5e8f\u8def\u5f84\uff1a");
                QuickCall2.add(label6);
                label6.setBounds(5, 50, 130, 30);
                QuickCall2.add(callPath);
                callPath.setBounds(115, 50, 325, callPath.getPreferredSize().height);

                //---- button4 ----
                button4.setText("\u6253\u5f00");
                button4.addActionListener(e -> button4(e));
                QuickCall2.add(button4);
                button4.setBounds(455, 50, 90, button4.getPreferredSize().height);

                //---- checkBox4 ----
                checkBox4.setText("\u662f\u5426\u4e3a python \u7a0b\u5e8f");
                QuickCall2.add(checkBox4);
                checkBox4.setBounds(new Rectangle(new Point(220, 120), checkBox4.getPreferredSize()));

                //---- label7 ----
                label7.setText("\u8c03\u7528\u53c2\u6570\uff1a");
                QuickCall2.add(label7);
                label7.setBounds(5, 195, 130, 30);
                QuickCall2.add(callArgs);
                callArgs.setBounds(115, 195, 325, 30);

                //---- button6 ----
                button6.setText("\u4fdd\u5b58\u5f53\u524d\u8bbe\u7f6e");
                button6.addActionListener(e -> SaveButtonSeting(e));
                QuickCall2.add(button6);
                button6.setBounds(205, 260, 180, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < QuickCall2.getComponentCount(); i++) {
                        Rectangle bounds = QuickCall2.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = QuickCall2.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    QuickCall2.setMinimumSize(preferredSize);
                    QuickCall2.setPreferredSize(preferredSize);
                }
            }
            panel2.add(QuickCall2);
            QuickCall2.setBounds(35, 650, 575, 320);

            //======== QuickCall3 ========
            {
                QuickCall3.setBorder(new CompoundBorder(
                    new TitledBorder("\u5feb\u6377\u8c03\u7528\u914d\u7f6e\u533a \u4e8c"),
                    Borders.DLU2_BORDER));
                QuickCall3.setLayout(null);

                //---- label8 ----
                label8.setText("\u8c03\u7528\u7a0b\u5e8f\u8def\u5f84\uff1a");
                QuickCall3.add(label8);
                label8.setBounds(5, 50, 130, 30);
                QuickCall3.add(callPath2);
                callPath2.setBounds(115, 50, 325, callPath2.getPreferredSize().height);

                //---- button5 ----
                button5.setText("\u6253\u5f00");
                button5.addActionListener(e -> button4(e));
                QuickCall3.add(button5);
                button5.setBounds(455, 50, 90, button5.getPreferredSize().height);

                //---- checkBox5 ----
                checkBox5.setText("\u662f\u5426\u4e3a python \u7a0b\u5e8f");
                QuickCall3.add(checkBox5);
                checkBox5.setBounds(new Rectangle(new Point(220, 120), checkBox5.getPreferredSize()));

                //---- label9 ----
                label9.setText("\u8c03\u7528\u53c2\u6570\uff1a");
                QuickCall3.add(label9);
                label9.setBounds(5, 195, 130, 30);
                QuickCall3.add(callArgs2);
                callArgs2.setBounds(115, 195, 325, 30);

                //---- button7 ----
                button7.setText("\u4fdd\u5b58\u5f53\u524d\u8bbe\u7f6e");
                button7.addActionListener(e -> SaveButtonSeting(e));
                QuickCall3.add(button7);
                button7.setBounds(205, 260, 180, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < QuickCall3.getComponentCount(); i++) {
                        Rectangle bounds = QuickCall3.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = QuickCall3.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    QuickCall3.setMinimumSize(preferredSize);
                    QuickCall3.setPreferredSize(preferredSize);
                }
            }
            panel2.add(QuickCall3);
            QuickCall3.setBounds(680, 650, 575, 320);

            //======== QuickCall4 ========
            {
                QuickCall4.setBorder(new CompoundBorder(
                    new TitledBorder("\u5feb\u6377\u8c03\u7528\u914d\u7f6e\u533a \u4e09"),
                    Borders.DLU2_BORDER));
                QuickCall4.setLayout(null);

                //---- label10 ----
                label10.setText("\u8c03\u7528\u7a0b\u5e8f\u8def\u5f84\uff1a");
                QuickCall4.add(label10);
                label10.setBounds(5, 50, 130, 30);
                QuickCall4.add(callPath3);
                callPath3.setBounds(115, 50, 325, callPath3.getPreferredSize().height);

                //---- button8 ----
                button8.setText("\u6253\u5f00");
                button8.addActionListener(e -> button4(e));
                QuickCall4.add(button8);
                button8.setBounds(455, 50, 90, button8.getPreferredSize().height);

                //---- checkBox6 ----
                checkBox6.setText("\u662f\u5426\u4e3a python \u7a0b\u5e8f");
                QuickCall4.add(checkBox6);
                checkBox6.setBounds(new Rectangle(new Point(220, 120), checkBox6.getPreferredSize()));

                //---- label11 ----
                label11.setText("\u8c03\u7528\u53c2\u6570\uff1a");
                QuickCall4.add(label11);
                label11.setBounds(5, 195, 130, 30);
                QuickCall4.add(callArgs3);
                callArgs3.setBounds(115, 195, 325, 30);

                //---- button9 ----
                button9.setText("\u4fdd\u5b58\u5f53\u524d\u8bbe\u7f6e");
                button9.addActionListener(e -> SaveButtonSeting(e));
                QuickCall4.add(button9);
                button9.setBounds(205, 260, 180, 30);

                {
                    // compute preferred size
                    Dimension preferredSize = new Dimension();
                    for(int i = 0; i < QuickCall4.getComponentCount(); i++) {
                        Rectangle bounds = QuickCall4.getComponent(i).getBounds();
                        preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                        preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                    }
                    Insets insets = QuickCall4.getInsets();
                    preferredSize.width += insets.right;
                    preferredSize.height += insets.bottom;
                    QuickCall4.setMinimumSize(preferredSize);
                    QuickCall4.setPreferredSize(preferredSize);
                }
            }
            panel2.add(QuickCall4);
            QuickCall4.setBounds(1320, 650, 575, 320);

            //======== scrollPane1 ========
            {
                scrollPane1.setViewportView(hisory);
            }
            panel2.add(scrollPane1);
            scrollPane1.setBounds(640, 50, 895, 565);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel2.getComponentCount(); i++) {
                    Rectangle bounds = panel2.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel2.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel2.setMinimumSize(preferredSize);
                panel2.setPreferredSize(preferredSize);
            }
        }
        add(panel2, BorderLayout.WEST);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on

        // 开启和关闭流量转发功能
        StartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 无论开启还是关闭都读取配置文件
                init();

                // 判断当前是监听状态还是停止状态
                if (e.getActionCommand().equals("开启流量转发")) {
                    Constants.startProxy = true;

                    JButton source = (JButton) e.getSource();
                    source.setText("关闭流量转发");
                    source.setBackground(Color.red);
                } else {
                    Constants.startProxy = false;

                    JButton source = (JButton) e.getSource();
                    source.setText("开启流量转发");
                    source.setBackground(Color.green);
                }
            }
        });

        // 初始化
        init();
    }

    public JComponent $$$getRootComponent$$$() {
        return this;
    }

}

package UI;

import Tools.Constants;
import burp.BurpExtender;
import burp.IContextMenuInvocation;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author Lian
 * @version 1.0
 * @description: TODO
 * @date 2023/5/12 21:42
 */
public class MainUI {
    public void init() {
        // 加载初始化配置进来
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("Lian-Tools.properties"));

            Constants.IP = (String) properties.get("IP");
            Constants.PORT = Integer.parseInt((String) properties.get("PORT"));

            Constants.allProxy = Boolean.parseBoolean((String) properties.get("allProxy"));
            Constants.Proxy = Boolean.parseBoolean((String) properties.get("Proxy"));
            Constants.RepeaterProxy = Boolean.parseBoolean((String) properties.get("repeaterProxy"));
            Constants.Intruder = Boolean.parseBoolean((String) properties.get("intruderProxy"));
            Constants.sendProxy = Boolean.parseBoolean((String) properties.get("sendProxy"));

            // 根据配置文件进行勾选框
            if (Constants.allProxy) {
                AllCheckBox.setSelected(true);
            }
            if (Constants.sendProxy) {
                sendProxyCheckBox.setSelected(true);
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
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private JPanel Root;
    public JTextField ProxyIP;
    public JTextField ProxyPort;
    public JCheckBox ProxyCheckBox;
    public JCheckBox RepeaterCheckBox;
    public JCheckBox IntruderCheckBox;
    public JCheckBox AllCheckBox;
    public JButton SaveButtonSeting;
    public JButton StartButton;
    public JCheckBox sendProxyCheckBox;

    public MainUI(BurpExtender burpExtender) {

        // 确认配置监听器
        SaveButtonSeting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 读取当前的所有配置并写入文件
                String IP = ProxyIP.getText();
                String PORT = ProxyPort.getText();

                boolean all = AllCheckBox.isSelected();
                boolean proxy = ProxyCheckBox.isSelected();
                boolean repeater = RepeaterCheckBox.isSelected();
                boolean intruder = IntruderCheckBox.isSelected();
                boolean sendProxy = sendProxyCheckBox.isSelected();

                // 写配置文件
                Properties properties = new Properties();
                properties.setProperty("IP", IP);
                properties.setProperty("PORT", PORT);
                properties.setProperty("allProxy", String.valueOf(all));
                properties.setProperty("Proxy", String.valueOf(proxy));
                properties.setProperty("repeaterProxy", String.valueOf(repeater));
                properties.setProperty("intruderProxy", String.valueOf(intruder));
                properties.setProperty("sendProxy", String.valueOf(sendProxy));

                try {
                    properties.store(new FileWriter("Lian-Tools.properties"), null);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                init();
            }
        });

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

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        Root = new JPanel();
        Root.setLayout(new FormLayout("right:965px:grow,left:4dlu:noGrow,left:144px:noGrow", "center:d:noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow,top:4dlu:noGrow,center:max(d;4px):noGrow"));
        ProxyIP = new JTextField();
        ProxyIP.setText("127.0.0.1");
        CellConstraints cc = new CellConstraints();
        Root.add(ProxyIP, cc.xy(3, 1, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label1 = new JLabel();
        label1.setText("代理 PORT");
        Root.add(label1, cc.xy(1, 3));
        ProxyCheckBox = new JCheckBox();
        ProxyCheckBox.setText("监控 Proxy");
        Root.add(ProxyCheckBox, cc.xy(3, 11));
        RepeaterCheckBox = new JCheckBox();
        RepeaterCheckBox.setText("监控 Repeater");
        Root.add(RepeaterCheckBox, cc.xy(3, 13));
        IntruderCheckBox = new JCheckBox();
        IntruderCheckBox.setText("监控 Intruder");
        Root.add(IntruderCheckBox, cc.xy(3, 15));
        AllCheckBox = new JCheckBox();
        AllCheckBox.setEnabled(true);
        AllCheckBox.setText("监控 All 流量");
        Root.add(AllCheckBox, cc.xy(3, 7));
        SaveButtonSeting = new JButton();
        SaveButtonSeting.setText("确认配置");
        Root.add(SaveButtonSeting, cc.xy(3, 17, CellConstraints.FILL, CellConstraints.DEFAULT));
        StartButton = new JButton();
        StartButton.setBackground(new Color(-12714968));
        StartButton.setText("开启流量转发");
        Root.add(StartButton, cc.xy(3, 5, CellConstraints.FILL, CellConstraints.DEFAULT));
        ProxyPort = new JTextField();
        ProxyPort.setText("9006");
        Root.add(ProxyPort, cc.xy(3, 3, CellConstraints.FILL, CellConstraints.DEFAULT));
        final JLabel label2 = new JLabel();
        label2.setText("代理     IP");
        Root.add(label2, cc.xy(1, 1));
        sendProxyCheckBox = new JCheckBox();
        sendProxyCheckBox.setText("监控 自发 流量");
        Root.add(sendProxyCheckBox, cc.xy(3, 9));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return Root;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
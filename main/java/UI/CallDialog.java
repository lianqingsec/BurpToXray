/*
 * Created by JFormDesigner on Sun May 21 18:30:45 CST 2023
 */

package UI;

import Tools.Constants;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

/**
 * @author Anonymous
 */
public class CallDialog extends JDialog {
    public static final JPanel mainJPanel = new JPanel();
    public boolean isDefaultCMD = false;

    // 标识符，标识是调用的哪一个快捷程序
    public int Flag = 1;
    // 判断当前快捷程序是否可用
    public boolean flag = false;

    public CallDialog(int flag) {
        Flag = flag;
        initComponents();
    }

    // 调用 cmd 来运行命令
    private void StartCMD(ActionEvent e) {
        String cmd = "";
        // 判断是哪一个快捷调用
        if (Flag == 1) {
            // 判断是否是 python 程序
            if (Constants.selected04) {
                cmd = Constants.pythonPathText + " " + this.getToolPath().getText() + " " + this.getToolArgs().getText() + " " + this.getEndCMD().getText();
            } else {
                cmd = this.getToolPath().getText() + " " + this.getToolArgs().getText() + " " + this.getEndCMD().getText();
            }
        } else if (Flag == 2) {
            // 判断是否是 python 程序
            if (Constants.selected042) {
                cmd = Constants.pythonPathText + " " + this.getToolPath().getText() + " " + this.getToolArgs().getText() + " " + this.getEndCMD().getText();
            } else {
                cmd = this.getToolPath().getText() + " " + this.getToolArgs().getText() + " " + this.getEndCMD().getText();
            }
        } else if (Flag == 3) {
            // 判断是否是 python 程序
            if (Constants.selected043) {
                cmd = Constants.pythonPathText + " " + this.getToolPath().getText() + " " + this.getToolArgs().getText() + " " + this.getEndCMD().getText();
            } else {
                cmd = this.getToolPath().getText() + " " + this.getToolArgs().getText() + " " + this.getEndCMD().getText();
            }
        }

        // 判断 当前快捷调用是否能够使用
        if (Flag == 1 && Constants.Startcall01) {
            flag = true;
        }
        if (Flag == 2 && Constants.Startcall02) {
            flag = true;
        }
        if (Flag == 3 && Constants.Startcall03) {
            flag = true;
        }

        // 调用默认的 cmd
        if (Constants.pathCmdText.contains("cmd.exe")) {
            isDefaultCMD = true;
        }

        if (isDefaultCMD) {
            if (flag) {
                try {
                    Process process = Runtime.getRuntime().exec("cmd /c start " + WriteBat(cmd));
                    process.waitFor(); // 等待
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            if (flag) {
                try {
                    Process process = Runtime.getRuntime().exec(Constants.pathCmdText + " " + WriteBat(cmd));
                    process.waitFor(); // 等待
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }

        this.setVisible(false);
    }

    public String WriteBat(String text) throws Exception {
        String path = "C:\\Windows\\Temp\\liantools.bat";
        FileWriter writer = new FileWriter(new File(path));
        writer.write(text);
        writer.close();
        return path;
    }

    private void exit(ActionEvent e) {
        this.setVisible(false);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        panel1 = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        button1 = new JButton();
        button2 = new JButton();
        ToolPath = new JTextField();
        ToolArgs = new JTextField();
        endCMD = new JTextField();

        //======== this ========
        var contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setLayout(null);

            //---- label1 ----
            label1.setText("\u8c03\u7528\u5de5\u5177\u8def\u5f84\uff1a");
            panel1.add(label1);
            label1.setBounds(0, 0, 120, 30);

            //---- label2 ----
            label2.setText("\u8c03\u7528\u5de5\u5177\u53c2\u6570");
            panel1.add(label2);
            label2.setBounds(0, 65, 120, 30);

            //---- label3 ----
            label3.setText("\u8bf7\u6c42\u8def\u5f84");
            panel1.add(label3);
            label3.setBounds(0, 135, 120, 30);

            //---- button1 ----
            button1.setText("\u786e\u5b9a");
            button1.addActionListener(e -> StartCMD(e));
            panel1.add(button1);
            button1.setBounds(160, 215, 85, 30);

            //---- button2 ----
            button2.setText("\u53d6\u6d88");
            button2.addActionListener(e -> exit(e));
            panel1.add(button2);
            button2.setBounds(365, 215, 85, 30);
            panel1.add(ToolPath);
            ToolPath.setBounds(120, 0, 475, ToolPath.getPreferredSize().height);
            panel1.add(ToolArgs);
            ToolArgs.setBounds(120, 70, 475, 30);
            panel1.add(endCMD);
            endCMD.setBounds(120, 135, 475, 30);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel1);
        panel1.setBounds(10, 10, 615, 265);

        {
            // compute preferred size
            Dimension preferredSize = new Dimension();
            for (int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    private JPanel panel1;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JButton button1;
    private JButton button2;
    private JTextField ToolPath;
    private JTextField ToolArgs;
    private JTextField endCMD;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on


    public JTextField getToolPath() {
        return ToolPath;
    }

    public JTextField getToolArgs() {
        return ToolArgs;
    }

    public JTextField getEndCMD() {
        return endCMD;
    }
}

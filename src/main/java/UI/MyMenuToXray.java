package UI;

import Tools.Constants;
import Tools.ProxyToXray;
import burp.BurpExtender;
import burp.IContextMenuInvocation;
import burp.IHttpRequestResponse;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Lian
 * @version 1.0
 * @description: TODO
 * @date 2023/5/13 19:08
 */
public class MyMenuToXray {
    public java.util.List<JMenuItem> createMenuItemsForBurp(IContextMenuInvocation invocation) {
        List<JMenuItem> JMenuItemList = new ArrayList<>();

        JMenuItem sendhost = new JMenuItem("^_^ Send To Xray ^_^");
        sendhost.addActionListener(new sendToXray(invocation));

        JMenuItemList.add(sendhost);
        return JMenuItemList;
    }

    public class sendToXray implements ActionListener {
        public IContextMenuInvocation invocation;

        public sendToXray(IContextMenuInvocation invocation) {
            this.invocation = invocation;
        }


        // 处理点击后的事情，判断后直接发送流量到Xray
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<byte[]> list = new ArrayList<>();
            for (IHttpRequestResponse selectedMessage : invocation.getSelectedMessages()) {
                byte[] request = selectedMessage.getRequest();
                list.add(request);
            }

            byte[] bytes = list.get(0);

            BurpExtender.appCallbacks.printOutput("================ Send To Xray ================\n");
            BurpExtender.appCallbacks.printOutput(new String(bytes, 0, bytes.length));


//            // 判断是否开启了，自主发送
            if (Constants.sendProxy) {
                try {
                    ProxyToXray.reqProxy(new String(bytes, 0, bytes.length));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
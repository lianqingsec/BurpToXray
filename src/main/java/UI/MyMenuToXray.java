package UI;

import Tools.Constants;
import Tools.ProxyToXray;
import burp.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author Lian
 * @version 1.0
 * @description: TODO
 * @date 2023/5/13 19:08
 */
public class MyMenuToXray {
    public IContextMenuInvocation inv;

    public java.util.List<JMenuItem> createMenuItemsForBurp(IContextMenuInvocation invocation) {
        inv = invocation;
        List<JMenuItem> JMenuItemList = new ArrayList<>();

        JMenuItem sendhost = new JMenuItem("^_^ Send To Xray ^_^");
        JMenuItem sendhost01 = new JMenuItem("^_^ Quick Call One ^_^");
        JMenuItem sendhost02 = new JMenuItem("^_^ Quick Call Two ^_^");
        JMenuItem sendhost03 = new JMenuItem("^_^ Quick Call Three ^_^");
        sendhost.addActionListener(new sendToXray());
        sendhost01.addActionListener(new sendhost01());
        sendhost02.addActionListener(new sendhost02());
        sendhost03.addActionListener(new sendhost03());


        JMenuItemList.add(sendhost);
        JMenuItemList.add(sendhost01);
        JMenuItemList.add(sendhost02);
        JMenuItemList.add(sendhost03);
        return JMenuItemList;
    }

    public class sendToXray implements ActionListener {
        // 处理点击后的事情，判断后直接发送流量到Xray
        @Override
        public void actionPerformed(ActionEvent e) {
            ArrayList<byte[]> list = new ArrayList<>();
            for (IHttpRequestResponse selectedMessage : inv.getSelectedMessages()) {
                byte[] request = selectedMessage.getRequest();
                list.add(request);
            }

            byte[] bytes = list.get(0);

            BurpExtender.appCallbacks.printOutput("================ Send To Xray ================\n");
            BurpExtender.appCallbacks.printOutput(new String(bytes, 0, bytes.length));


//            // 判断是否开启了，自主发送
            if (Constants.sendProxy) {
                try {
                    ProxyToXray.reqProxy(inv.getSelectedMessages()[0]);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public class sendhost01 implements ActionListener {
        byte[] request;
        IHttpService httpService;

        @Override
        public void actionPerformed(ActionEvent e) {
            IHttpRequestResponse[] selectedMessages = inv.getSelectedMessages();
            request = selectedMessages[0].getRequest();
            httpService = selectedMessages[0].getHttpService();
            IRequestInfo iRequestInfo = BurpExtender.appCallbacks.getHelpers().analyzeRequest(httpService, request);
            BurpExtender.appCallbacks.printOutput(iRequestInfo.getUrl().toString());

            // 请求路径
            String Url = iRequestInfo.getUrl().toString();
            CallDialog callDialog = new CallDialog(1);

            callDialog.getToolPath().setText(Constants.callPathText);
            callDialog.getToolArgs().setText(Constants.callArgsText);
            callDialog.getEndCMD().setText(Url);


            callDialog.setVisible(true);
        }
    }

    public class sendhost02 implements ActionListener {
        byte[] request;
        IHttpService httpService;

        @Override
        public void actionPerformed(ActionEvent e) {
            IHttpRequestResponse[] selectedMessages = inv.getSelectedMessages();
            request = selectedMessages[0].getRequest();
            httpService = selectedMessages[0].getHttpService();
            IRequestInfo iRequestInfo = BurpExtender.appCallbacks.getHelpers().analyzeRequest(httpService, request);
            BurpExtender.appCallbacks.printOutput(iRequestInfo.getUrl().toString());

            // 请求路径
            String Url = iRequestInfo.getUrl().toString();
            CallDialog callDialog = new CallDialog(2);

            callDialog.getToolPath().setText(Constants.callPathText2);
            callDialog.getToolArgs().setText(Constants.callArgsText2);
            callDialog.getEndCMD().setText(Url);


            callDialog.setVisible(true);
        }
    }

    public class sendhost03 implements ActionListener {
        byte[] request;
        IHttpService httpService;

        @Override
        public void actionPerformed(ActionEvent e) {
            IHttpRequestResponse[] selectedMessages = inv.getSelectedMessages();
            request = selectedMessages[0].getRequest();
            httpService = selectedMessages[0].getHttpService();
            IRequestInfo iRequestInfo = BurpExtender.appCallbacks.getHelpers().analyzeRequest(httpService, request);
            BurpExtender.appCallbacks.printOutput(iRequestInfo.getUrl().toString());

            // 请求路径
            String Url = iRequestInfo.getUrl().toString();
            CallDialog callDialog = new CallDialog(3);

            callDialog.getToolPath().setText(Constants.callPathText3);
            callDialog.getToolArgs().setText(Constants.callArgsText3);
            callDialog.getEndCMD().setText(Url);


            callDialog.setVisible(true);
        }
    }
}
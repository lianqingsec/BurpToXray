package burp;

import Tools.Constants;
import Tools.ProxyToXray;
import UI.MyMainUI;
import UI.MyMenuToXray;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.List;

/**
 * @author Lian
 * @version 1.0
 * @description: TODO
 * @date 2023/5/11 22:03
 */
public class BurpExtender implements IBurpExtender, IHttpListener, ITab, IContextMenuFactory {
    public static IBurpExtenderCallbacks appCallbacks;
    MyMainUI myMainUI;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks iBurpExtenderCallbacks) {
        appCallbacks = iBurpExtenderCallbacks;
        // 设置名字
        appCallbacks.setExtensionName("Lian-Tools");

        appCallbacks.printOutput("Loader Success!");

        // 注册请求监听
        iBurpExtenderCallbacks.registerHttpListener(this::processHttpMessage);

        // 注册窗口监听
        myMainUI = new MyMainUI(this);
        appCallbacks.addSuiteTab(this);

        // 注册右键菜单
        appCallbacks.registerContextMenuFactory(this::createMenuItems);

    }

    // 转发请求的主要方法
    public void sendRequest(boolean messageIsRequest, IHttpRequestResponse messageInfo) throws Exception {
        if (messageIsRequest) {
            try {
                ProxyToXray.reqProxy(messageInfo);
            } catch (IOException e) {
                appCallbacks.printOutput("Send Failed!");
            }

            appCallbacks.printOutput("========================================================\n");


        }
    }

    // Burp 全流量监听
    @Override
    public void processHttpMessage(int toolFlag, boolean messageIsRequest, IHttpRequestResponse messageInfo) {
        // 先判断是否开启转发请求
        if (Constants.startProxy) {
            // 判断是否是响应，且该代码作用域为：REPEATER、INTRUDER、PROXY（分别对应toolFlag 64、32、4）
            // 判断是请求还是响应，响应不发送
            // 判断是否允许全流量监听
            try {
                if (Constants.allProxy) {
                    String url = BurpExtender.appCallbacks.getHelpers().analyzeRequest(messageInfo.getHttpService(), messageInfo.getRequest()).getUrl().toString();
                    MyMainUI.setHisory(url, "全流量监听");
                    sendRequest(messageIsRequest, messageInfo);
                } else if (Constants.RepeaterProxy && (toolFlag == 64)) {

                    String url = BurpExtender.appCallbacks.getHelpers().analyzeRequest(messageInfo.getHttpService(), messageInfo.getRequest()).getUrl().toString();
                    MyMainUI.setHisory(url, "REPEATER");
                    sendRequest(messageIsRequest, messageInfo);
                } else if (Constants.Proxy && (toolFlag == 4)) {
                    String url = BurpExtender.appCallbacks.getHelpers().analyzeRequest(messageInfo.getHttpService(), messageInfo.getRequest()).getUrl().toString();
                    MyMainUI.setHisory(url, "PROXY");
                    sendRequest(messageIsRequest, messageInfo);
                } else if (Constants.Intruder && (toolFlag == 32)) {
                    String url = BurpExtender.appCallbacks.getHelpers().analyzeRequest(messageInfo.getHttpService(), messageInfo.getRequest()).getUrl().toString();
                    MyMainUI.setHisory(url, "INTRUDER");
                    sendRequest(messageIsRequest, messageInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 页面显示名字
    @Override
    public String getTabCaption() {
        return "Lian-Tools";
    }

    // 返回 JPanel 页面
    @Override
    public Component getUiComponent() {
        return myMainUI.$$$getRootComponent$$$();
    }


    // 返回右键菜单
    @Override
    public List<JMenuItem> createMenuItems(IContextMenuInvocation invocation) {
        return new MyMenuToXray().createMenuItemsForBurp(invocation);
    }
}

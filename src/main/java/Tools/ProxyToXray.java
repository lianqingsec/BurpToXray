package Tools;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import static burp.BurpExtender.appCallbacks;

/**
 * @author Lian
 * @version 1.0
 * @description: TODO
 * @date 2023/5/12 21:35
 */
public class ProxyToXray {
    public ProxyToXray() {
    }

    public static void reqProxy(String req) throws IOException {
        Socket socket = null;
        OutputStream out = null;

        try {
            //1.创建Socket对象，指明服务器IP和端口号
            socket = new Socket(Constants.IP, Constants.PORT);
            //2.获取一个输出流用于输出数据
            out = socket.getOutputStream();
            //3.写出数据的操作(这里也可以用转换流OutputStreamWriter)
            out.write(req.getBytes(StandardCharsets.UTF_8));
            appCallbacks.printOutput(req);
        } finally {
            socket.close();
            out.close();
        }
    }
}

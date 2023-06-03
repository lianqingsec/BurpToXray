package Tools;

import burp.BurpExtender;
import burp.IHttpRequestResponse;
import burp.IHttpService;
import burp.IRequestInfo;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Lian
 * @version 1.0
 * @description: TODO
 * @date 2023/5/12 21:35
 */
public class ProxyToXray {
    public ProxyToXray() {
    }

    public static void reqProxy(IHttpRequestResponse HttpRequestResponse) throws Exception {
        Map<String, String> response = Proxy(HttpRequestResponse);
    }

    public static Map<String, String> Proxy(IHttpRequestResponse requestResponse) throws InterruptedException {
        byte[] req = requestResponse.getRequest();
        String url = null;
        byte[] reqbody = null;
        List<String> headers = null;

        IHttpService httpService = requestResponse.getHttpService();
        IRequestInfo reqInfo = BurpExtender.appCallbacks.getHelpers().analyzeRequest(httpService, req);

        if (reqInfo.getMethod().equals("POST")) {
            int bodyOffset = reqInfo.getBodyOffset();
            String body = null;
            try {
                body = new String(req, bodyOffset, req.length - bodyOffset, "UTF-8");
                reqbody = body.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //BurpExtender.stderr.println("[+] url: " + resInfo.getUrl());
        headers = reqInfo.getHeaders();
        url = reqInfo.getUrl().toString();
        if (httpService.getProtocol().equals("https")) {
            return HttpsProxy(url, headers, reqbody, Constants.IP, Constants.PORT);
        } else {
            return HttpProxy(url, headers, reqbody, Constants.IP, Constants.PORT);
        }
    }

    public static Map<String, String> HttpsProxy(String url, List<String> headers, byte[] body, String proxy, int port) {
        Map<String, String> mapResult = new HashMap<String, String>();
        String status = "";
        String rspHeader = "";
        String result = "";

        HttpsURLConnection httpsConn = null;
        PrintWriter out = null;
        BufferedReader in = null;

        BufferedReader reader = null;

        try {

            URL urlClient = new URL(url);
            SSLContext sc = SSLContext.getInstance("SSL");
            // 指定信任https
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
            //创建代理虽然是https也是Type.HTTP
            Proxy proxy1 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy, port));
            //设置代理
            httpsConn = (HttpsURLConnection) urlClient.openConnection(proxy1);

            httpsConn.setSSLSocketFactory(sc.getSocketFactory());
            httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
            // 设置通用的请求属性
            for (String header : headers) {
                if (header.startsWith("GET") ||
                        header.startsWith("POST") ||
                        header.startsWith("PUT")) {
                    continue;
                }
                String[] h = header.split(":");
                String header_key = h[0].trim();
                String header_value = h[1].trim();
                httpsConn.setRequestProperty(header_key, header_value);
            }
            // 发送POST请求必须设置如下两行
            httpsConn.setDoOutput(true);
            httpsConn.setDoInput(true);


            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(httpsConn.getOutputStream());

            if (body != null) {
                // 发送请求参数
                out.print(new String(body));
            }
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(httpsConn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
                result += "\r\n";
            }
            // 断开连接
            httpsConn.disconnect();
            //BurpExtender.stdout.println("====result===="+result);
            // 获取响应头
            Map<String, List<String>> mapHeaders = httpsConn.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : mapHeaders.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                String value = "";
                for (String v : values) {
                    value += v;
                }

                if (key == null) {
                    String header_line = String.format("%s\r\n", value);
                    rspHeader += header_line;
                } else {
                    String header_line = String.format("%s: %s\r\n", key, value);
                    rspHeader += header_line;
                }
            }

            //BurpExtender.stdout.println("返回结果https：" + httpsConn.getResponseMessage());
            status = String.valueOf(httpsConn.getResponseCode());

        } catch (Exception e) {
            e.printStackTrace();
            BurpExtender.appCallbacks.printOutput("\n[*] " + e.getMessage() + "\n");
            result = e.getMessage();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }

        try {
            status = String.valueOf(httpsConn.getResponseCode());
        } catch (IOException e) {
            status = e.getMessage();
            BurpExtender.appCallbacks.printOutput("\n[*] " + e.getMessage() + "\n");
        }

        mapResult.put("status", status);
        mapResult.put("header", rspHeader);
        mapResult.put("result", result);
        return mapResult;
    }

    public static Map<String, String> HttpProxy(String url, List<String> headers, byte[] body, String proxy, int port) {
        Map<String, String> mapResult = new HashMap<String, String>();
        String status = "";
        String rspHeader = "";
        String result = "";


        HttpURLConnection httpsConn = null;
        PrintWriter out = null;
        BufferedReader in = null;
        BufferedReader reader = null;
        try {
            URL urlClient = new URL(url);
            SSLContext sc = SSLContext.getInstance("SSL");
            // 指定信任https
            sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());

            //创建代理
            Proxy proxy1 = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy, port));
            //设置代理
            httpsConn = (HttpURLConnection) urlClient.openConnection(proxy1);


            // 设置通用的请求属性
            for (String header : headers) {
                if (header.startsWith("GET") ||
                        header.startsWith("POST") ||
                        header.startsWith("PUT")) {
                    continue;
                }
                String[] h = header.split(":");
                String header_key = h[0].trim();
                String header_value = h[1].trim();
                //BurpExtender.stdout.println("key: " + h[0].trim());
                //BurpExtender.stdout.println("value: " + h[1].trim());
                httpsConn.setRequestProperty(header_key, header_value);
            }
            //设置控制请求方法的Flag
            String methodFlag = "";
            // 设置通用的请求属性
            for (String header : headers) {
                if (header.startsWith("GET") ||
                        header.startsWith("POST") ||
                        header.startsWith("PUT")) {
                    if (header.startsWith("GET")) {
                        methodFlag = "GET";
                    } else if (header.startsWith("POST") ||
                            header.startsWith("PUT")) {
                        methodFlag = "POST";
                    }//在循环中重复设置了methodFlag，代码非常的丑陋冗余，请见谅
                    continue;
                }//判断结束后以键值对的方式获取header
                String[] h = header.split(":");
                String header_key = h[0].trim();
                String header_value = h[1].trim();
                httpsConn.setRequestProperty(header_key, header_value);
            }

            if (methodFlag.equals("GET")) {
                // 发送GET请求必须设置如下两行
                httpsConn.setDoOutput(false);
                httpsConn.setDoInput(true);

                // 获取URLConnection对象的连接
                httpsConn.connect();
            } else if (methodFlag.equals("POST")) {
                // 发送POST请求必须设置如下两行
                httpsConn.setDoOutput(true);
                httpsConn.setDoInput(true);

                // 获取URLConnection对象对应的输出流
                out = new PrintWriter(httpsConn.getOutputStream());
                if (body != null) {
                    // 发送请求参数
                    out.print(new String(body));
                }
                // flush输出流的缓冲
                out.flush();
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
                result += "\r\n";
            }
            // 断开连接
            httpsConn.disconnect();
            Map<String, List<String>> mapHeaders = httpsConn.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : mapHeaders.entrySet()) {
                String key = entry.getKey();
                List<String> values = entry.getValue();
                String value = "";
                for (String v : values) {
                    value += v;
                }

                if (key == null) {
                    String header_line = String.format("%s\r\n", value);
                    rspHeader += header_line;
                } else {
                    String header_line = String.format("%s: %s\r\n", key, value);
                    rspHeader += header_line;
                }
            }

            //BurpExtender.stdout.println("====result===="+result);
            //BurpExtender.stdout.println("返回结果http：" + httpConn.getResponseMessage());
            status = String.valueOf(httpsConn.getResponseCode());
        } catch (Exception e) {
            //e.printStackTrace();
            BurpExtender.appCallbacks.printOutput("\n[*] " + e.getMessage() + "\n");
            result = e.getMessage();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (out != null) {
                out.close();
            }
        }

        try {
            status = String.valueOf(httpsConn.getResponseCode());
        } catch (IOException e) {
            status = e.getMessage();
            BurpExtender.appCallbacks.printOutput("\n[*] " + e.getMessage() + "\n");
        }
        mapResult.put("status", status);
        mapResult.put("header", rspHeader);
        mapResult.put("result", result);
        return mapResult;
    }


    private static class TrustAnyTrustManager implements X509TrustManager {

        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }

    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }
}

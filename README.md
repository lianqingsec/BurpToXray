# 前言

在渗透测试工作中，我喜欢使用工具加手工的方式去测试，这样测试出的系统才比较放心。

我一般采用 Burp + Xray 被动扫描的方式去测试，不过有的系统有流量检测设备或者有一些登录处不能使用 Xray 去进行测试，这个时候就需要手动的去关闭代理有的时候如果忘了可能就导致客户给的账号登录不上去（Xray Sql 检查把用户名登异常），这是我们不想看到的。

于是就有了自己开发一个 Burp 插件的想法，这是我开发的第一个插件，但肯定不会是最后一个。

后需可能会在这个项目上开发出更多的模块。

# 使用截图

![image-20230513200725587](https://gitee.com/lianqing_xyz/md_image02/raw/master/img/image-20230513200725587.png)



![image-20230513200755530](https://gitee.com/lianqing_xyz/md_image02/raw/master/img/image-20230513200755530.png)

中间还有很大的空间，后面可以用来开发其他功能。

# 2023-05-21 更新

- 优化了界面布局
- 添加了设置全局 python 路径功能
- 添加了设置默认调用 cmd 终端的功能，可以调用其他好看 cmd 终端
- 添加了三个快捷调用的功能，指定程序路径，指定是否是 python 程序，指定默认参数。
- 使用 URL 作为参数的 exe 或 python 程序都可以通过配置，来快捷调用程序，实现了比较高的自由度。
- 目前只支持 Windows 系统，对于 Linux 还未进行适配。

![image-20230521210442442](https://gitee.com/lianqing_xyz/md_image02/raw/master/img/image-20230521210442442.png)

可以快捷调用 python 或 exe 程序

![image-20230521210628832](https://gitee.com/lianqing_xyz/md_image02/raw/master/img/image-20230521210628832.png)

默认填充当前数据包的 url，也可以临时添加工具参数

![image-20230521210708558](https://gitee.com/lianqing_xyz/md_image02/raw/master/img/image-20230521210708558.png)

## 使用截图

![image-20230521210854011](https://gitee.com/lianqing_xyz/md_image02/raw/master/img/image-20230521210854011.png)


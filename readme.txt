下载地址：
http://archive.apache.org/dist/activemq/5.13.0/

如何配置ActiveMQ的服务器端：

	注意：apache-activemq-5.13.0需要jdk1.7或更高版本的jdk，且测试环境需要JAVA_HOME

1、下载软件
	去官方网站下载：http://activemq.apache.org/ 我下载的是apache-activemq-5.13.0.bin 版本，
	当然开源的也是支持下载source的，需要自己编译下，这里不做过多介绍

2、解压后，进入D:\Program_Files\Server\apache-activemq-5.13.0\bin\win64启动activemq.bat。
	系统会自动执行启动过程，当然一般安装失败的情况是没有装JVM环境，启动成功会在命令提示符中输出环境jvm信息，如下所示：
	启动过程中输出内容：
	wrapper  | --> Wrapper Started as Console
	wrapper  | Launching a JVM...
	jvm 1    | Wrapper (Version 3.2.3) http://wrapper.tanukisoftware.org
	jvm 1    |   Copyright 1999-2006 Tanuki Software, Inc.  All Rights Reserved.
	jvm 1    |
	jvm 1    | Java Runtime: Oracle Corporation 1.7.0_25 D:\Program_Files\Java\jdk1.7.0_25\jre
	jvm 1    |   Heap sizes: current=124416k  free=116605k  max=932096k
	jvm 1    |     JVM args: -Dactivemq.home=../.. -Dactivemq.base=../.. -Djavax.net.ssl.keySto
	...........
	...中间省略...
	...........
	\bin\win64\..\..\data only has 24183 mb of usable space - resetting to maximum available disk space: 24183 mb
	jvm 1    |  INFO | No Spring WebApplicationInitializer types detected on classpath
	jvm 1    |  INFO | ActiveMQ WebConsole available at http://0.0.0.0:8161/
	jvm 1    |  INFO | ActiveMQ Jolokia REST API available at http://0.0.0.0:8161/api/jolokia/
	jvm 1    |  INFO | Initializing Spring FrameworkServlet 'dispatcher'
	jvm 1    |  INFO | No Spring WebApplicationInitializer types detected on classpath
	jvm 1    |  INFO | jolokia-agent: No access restrictor found at classpath:/jolokia-access.xml, access to all MBeans is allowed
	见到如上JVM信息输出，则说明启动成功！

3、打开浏览器输入http://localhost:8161/admin/默认配置是这个，当然你也可以更改这个配置
	访问时输入访问账号密码：admin/admin
	访问密码可在activeMQ安装目录下的conf中的users.properties中找；如：我的路径为：D:\Program_Files\Server\apache-activemq-5.13.0\conf\users.properties

4、至此，服务端启动完毕，接下来就该写代码了	
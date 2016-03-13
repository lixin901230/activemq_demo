
工程目录说明：
	src/main/java
		com.lx.jms.activemq.demo	官方点到点消息模式示例
		com.lx.jms.activemq.p2p		点对点模式(point-to-point)
		com.lx.jms.activemq.topic	订阅-发布模式
		com.lx.jms.activemq.utils	ActiveMQ工具类
		com.lx.jms.utils			系统工具类
	
简介：
	activemq是JMS消息通信规范的一个实现。总的来说，消息规范里面定义最常见的几种消息通信模式主要有 
	1、发布-订阅模式、
	2、点对点模式这两种。
	另外，通过结合这些模式的具体应用，我们在处理某些应用场景的时候也衍生出来了一种模式，请求-应答模式。
	下面，我们针对这几种方式一一讨论一下。

基础流程：
	在讨论具体方式的时候，我们先看看使用activemq需要启动服务的主要过程。
	按照JMS的规范，我们首先需要获得一个JMS connection factory.，通过这个connection factory来创建connection。
	在这个基础之上我们再创建session, destination, producer和consumer。
	因此主要的几个步骤如下：
	1. 获得JMS connection factory. 通过我们提供特定环境的连接信息来构造factory。
	2. 利用factory构造JMS connection
	3. 启动connection
	4. 通过connection创建JMS session.
	5. 指定JMS destination.
	6. 创建JMS producer或者创建JMS message并提供destination.
	7. 创建JMS consumer或注册JMS message listener.
	8. 发送和接收JMS message.
	9. 关闭所有JMS资源，包括connection, session, producer, consumer等。

消息模式说明：
	publish-subscribe（发布订阅模式）
		发布订阅模式有点类似于我们日常生活中订阅报纸。每年到年尾的时候，邮局就会发一本报纸集合让我们来选择订阅哪一个。
		在这个表里头列了所有出版发行的报纸，那么对于我们每一个订阅者来说，我们可以选择一份或者多份报纸。比如北京日报、潇湘晨报等。
		那么这些个我们订阅的报纸，就相当于发布订阅模式里的topic。有很多个人订阅报纸，也有人可能和我订阅了相同的报纸。
		那么，在这里，相当于我们在同一个topic里注册了。对于一份报纸发行方来说，它和所有的订阅者就构成了一个1对多的关系。
	
	p2p（点对点模式）
		p2p的过程则理解起来更加简单。它好比是两个人打电话，这两个人是独享这一条通信链路的。一方发送消息，另外一方接收，
		就这么简单在实际应用中因为有多个用户对使用p2p的链路
	
	request-response（请求-应答方式）（非JMS规范默认提供的通信方式）
		和前面两种方式比较起来，request-response的通信方式很常见，但是不是默认提供的一种模式。
		在前面的两种模式中都是一方负责发送消息而另外一方负责处理。而我们实际中的很多应用相当于一种一应一答的过程，需要双方都能给对方发送消息。
		于是请求-应答的这种通信方式也很重要。它也应用的很普遍。 
		请求-应答方式并不是JMS规范系统默认提供的一种通信方式，而是通过在现有通信方式的基础上稍微运用一点技巧实现的
		在JMS里面，如果要实现请求/应答的方式，可以利用JMSReplyTo和JMSCorrelationID消息头来将通信的双方关联起来。
		另外，QueueRequestor和TopicRequestor能够支持简单的请求/应答过程。

原文引用：
http://shmilyaw-hotmail-com.iteye.com/blog/1897635



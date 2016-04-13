
工程目录说明：
	src/main/java
		com.lx.jms.activemq.demo	官方点到点消息模式示例
		com.lx.jms.activemq.queue	点对点模式(point-to-point)，包含纯java的activeMQ示例和集成spring的示例
		com.lx.jms.activemq.topic	订阅-发布模式，即topic消息(publish-subscribe)，包含纯java的activeMQ示例和集成spring的示例
		com.lx.jms.activemq.utils	ActiveMQ工具类
		com.lx.jms.utils			系统工具类
		
	demo中附上了activemq服务的xml配置实例：/src/main/java/activemq.xml
	
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
	p2p（点对点模式）
		p2p的过程则理解起来更加简单。它好比是两个人打电话，这两个人是独享这一条通信链路的。一方发送消息，另外一方接收，
		就这么简单在实际应用中因为有多个用户对使用p2p的链路
		
	publish-subscribe（发布订阅模式）
		发布订阅模式有点类似于我们日常生活中订阅报纸。每年到年尾的时候，邮局就会发一本报纸集合让我们来选择订阅哪一个。
		在这个表里头列了所有出版发行的报纸，那么对于我们每一个订阅者来说，我们可以选择一份或者多份报纸。比如北京日报、潇湘晨报等。
		那么这些个我们订阅的报纸，就相当于发布订阅模式里的topic。有很多个人订阅报纸，也有人可能和我订阅了相同的报纸。
		那么，在这里，相当于我们在同一个topic里注册了。对于一份报纸发行方来说，它和所有的订阅者就构成了一个1对多的关系。
	
	request-response（请求-应答方式）（非JMS规范默认提供的通信方式）
		和前面两种方式比较起来，request-response的通信方式很常见，但是不是默认提供的一种模式。
		在前面的两种模式中都是一方负责发送消息而另外一方负责处理。而我们实际中的很多应用相当于一种一应一答的过程，需要双方都能给对方发送消息。
		于是请求-应答的这种通信方式也很重要。它也应用的很普遍。 
		请求-应答方式并不是JMS规范系统默认提供的一种通信方式，而是通过在现有通信方式的基础上稍微运用一点技巧实现的
		在JMS里面，如果要实现请求/应答的方式，可以利用JMSReplyTo和JMSCorrelationID消息头来将通信的双方关联起来。
		另外，QueueRequestor和TopicRequestor能够支持简单的请求/应答过程。
		
消息持久化：
	（ActiveMQ持久化方式：AMQ、KahaDB、JDBC、LevelDB，本示例只做KahaDB 和 JDBC 方式的持久化配置demo）
	
	持久化配置修改activemq/conf/activemq.xml内容，参考本示例附带的activemq.xml文件中的配置：
	
	一、JDBC 方式消息持久化
	1、使用mysql进行持久化方式：
		1）、将mysql-connector-java-5.1.9.jar包拷贝到activemq安装目录下的lib目录下
		2）、在mysql数据库中新建数据库，如我创建的库名为：activemq_persistence，后面配置消息持久化数据源时使用（注意：不需要建表，activemq会自动在我们指定的数据源的库中建表）
		3）、修改activemq安装目录下conf目录中的active.xml配置：
			（1）在broker配置上面加上mysql数据源配置：
			<!-- JDBC：mysql持久化bean -->
			<bean id="mysql-ds" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
				<property name="driverClassName" value="com.mysql.jdbc.Driver"/>
				<property name="url" value="jdbc:mysql://localhost/activemq_persistence?relaxAutoCommit=true"/>
				<property name="username" value="root"/>
				<property name="password" value="admin"/>
				<property name="poolPreparedStatements" value="true"/>
			</bean>
			说明：Oracle 与 SQL Server 的持久化数据源 bean配置 此处不再配置，自行查阅官方文档：http://activemq.apache.org/jdbc-support.html，或自己到网上查阅其他资料
		
			（2）修改persistenceAdapter持久化适配器配置：
			<!-- 消息持久化适配器 
				方式1：MySql 数据库存储持久化；
					属性dataSource指定持久化数据库数据源的bean配置，createTablesOnStartup是否在启动的时候创建数据表，
					默认值是true，这样每次启动都会去创建数据表了，一般是第一次启动的时候设置为true，之后改成false。
					属性useDatabaseLock：设置activemq启动时不锁数据库（在启动多个activemq服务并共用同一个数据库进行消息持久化时需要加该属性）  -->
			<persistenceAdapter>
				<jdbcPersistenceAdapter dataSource="#mysql-ds" createTablesOnStartup="true" useDatabaseLock="false"/>
	        </persistenceAdapter>
		
			（3）配置完毕 ，启动activemq服务，会发现刚新建的数据库中多了三张表，分别是：
				ACTIVEMQ_ACKS：持久订阅者列表
				EMQ_LOCK：进行数据访问的排斥锁
				ACTIVEMQ_MSGS：持久化消息表（存储Queue和Topic消息的表） 
			
				<一>表说明：
				当在启动ActiveMQ时,先判断表是否存在,如果不存在,将去创建表,如下: 
				(1)、ACTIVEMQ_ACKS：持久订阅者列表 
					1.CONTAINER：类型：//主题 （如：topic://basicInfo.topic） 
					2.SUB_DEST：应该是描述,与1内容相同 
					3.CLIENT_ID：持久订阅者的标志ID,必须唯一 
					4.SUB_NAME：持久订阅者的名称.(durableSubscriptionName) 
					5.SELECTOR：消息选择器,consumer可以选择自己想要的 
					6.LAST_ACKED_ID：最后一次确认ID,这个字段存的该该订阅者最后一次收到的消息的ID 
				
				(2)、ACTIVEMQ_LOCK：进行数据访问的排斥锁 
					1.ID：值为1 
					2.TIME：时间 
					3.BROKER_NAME：broker的名称 
					   这个表似为集群使用,但现在ActiveMQ并不能共享数据库. 
				
				(3)、ACTIVEMQ_MSGS：持久化消息表（存储Queue和Topic消息的表） 
					1.ID：消息的ID 
					2.CONTAINER： 类型：//主题 （如：queue://my.queue、Topic://basicInfo.topic ） 
					3.MSGID_PROD：发送消息者的标志 
					MSGID_PROD =ID:[computerName][…..] 
					注意computerName,不要使用中文,消息对象中会存储这个部分,解析connectID时会出现Bad String错误. 
					4.MSGID_SEQ：还不知用处 
					5.EXPIRATION：到期时间. 
					6.MSG：消息本身,Blob类型. 
					可以在JmsTemplate发送配置中,加上<property name=”timeToLive” value=”432000000”/>,5天的生命期,如果消息一直没有被处理,消息会被删除,但是表中会存在CONTAINER为queue://ActiveMQ.DLQ的记录.也就是说,相当于将过期的消息发给了一个ActiveMQ自定义的删除队列.. 
				
				<二>关于ActiveMQ的持久订阅消息删除操作 
					1.主题消息只有一条,所有订阅了这个消息的持久订阅者都要收到消息,只有所有订阅者收到消息并确认(Acknowledge)之后.才会删除. 
					说明：ActiveMQ支持批量(optimizeAcknowledge为true)确认,以提高性能 
					2.ActiveMQ执行删除Topic消息的cleanup()操作的时间间隔为5 minutes..
			
			（4）运行代码demo示例测试
			
	二、Kahadb 文件数据库持久化消息（activemq默认持久化方式）
		<!-- 消息持久化适配器 
			方式2：KahaDB 文件储存持久化； -->
		<persistenceAdapter>
			<kahaDB directory="${activemq.data}/kahadb" journalMaxFileLength="32mb"/>
        </persistenceAdapter>
		
部分引用：
	http://shmilyaw-hotmail-com.iteye.com/blog/1897635
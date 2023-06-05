# hmyg-backend
黑马优购-->在此基础上进一步修改-->生活优购，为此搭建的后端，springboot框架
生活优购

## 项目流程

1. 完成前端部署（跟着视频敲小程序）✔
2. 提取所有数据接口、分析响应数据✔
3. 生成sql文件、创建数据库✔
4. 创建springboot项目，根据接口和数据库构建自己的后端

### 需求分析

对已有的小程序前端应用，搭建自己的后端，将数据事先保存在自己的数据库，分析sql逻辑，给小程序提供数据接口。

- 用户登录

- 获取首页轮播图

- 获取首页分类信息

- 获取首页楼层信息

- 获取商品列表

- 获取商品详情

  

### 技术选型

**后端**

- Java
- SpringMVC+SpringBoot
- 接口采用RESTful风格的URL请求
- MybatisPlus
- Mysql 数据库
- Junit 单元测试
  - Hutool工具类

### 前置准备

#### 准备小程序

将小程序敲完了，除了结算功能不完整，其他都还可以，核心就是涉及到四个数据请求。

登录功能之后可以再改。

#### 生成sql文件

花费两天时间才将四个主要访问数据的接口，访问到的数据提取出来。写了个脚本提取json数据，生成sql建表和insert语句；这个过程并不轻松，但其实是不难实现的，主要是第一次写，再加上数据量比较庞大，不好肉眼比较（近1000条数据），中间在字段上吃了多次亏，比如有的字段没考虑到，但是中间有一两条数据又会出现到，等。需要发请求才能获取到的数据（比如商品详情），就用循环去执行。

过程中学习到很多知识，包括**文件读写流的使用**、**String和JSON数据格式的转换**、**MySql的建表语句**、**字符串的提取与分离**、**计算耗时**、**HuTool工具类发送http的使用**等。

但是皇天不负有心人，当数据成功导入数据库的那一刻，还蛮有成就感的，虽然不难...

![image-20230517213919921](黑马优购.assets/image-20230517213919921.png)



## 项目初始化

### 1.初始化springboot

项目名hmyg-backend，黑马优购-后端

SDK选择jdk11.0.17 本地版本：指选用哪个jdk

Java 11 ：指的是jdk的语法版本。即便选jdk11，依然可以限定只能使用java8的语法

Mysql本地是5.7 （5.7.24）

```sh
cmd>mysql -uroot -p
password:1234
mysql>select version();
或者
cmd>mysql --help
```

Maven 本地是3.6.0 版本

引入依赖

- Spring Boot DevTools（热部署）

- Spring Configuration Processor（加载的yml或者properties等配置文件）

- LomBok （自动生成get、set方法）

- Spring Web 

- MySQL Driver（使用MySQL数据库驱动包）

- Mybatis-Plus 

- Junit（测试）

  

[热部署](https://so.csdn.net/so/search?q=热部署&spm=1001.2101.3001.7020)是对修改的类和配置文件进行重新加载，所以在重新加载的过程中会看到项目启动的过程，其本质上只是对修改类和配置文件的重新加载，所以速度极快。

spring-boot-configuration-processor其实是一个注解处理器，在编译阶段干活的，一般在maven的声明都是

### 2.连接mysql

用户名：root  密码：1234    表名：hmyg

**配置源**

```yml
spring:
  application:
    name: hmyg-backend
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/hmyg
    username: root
    password: 1234
```

一开始把项目目录放在中文路径下，然后把它剪切到 ideaProjects目录下，重新打开可以成功运行。

使用**<u>MybatisX</u>**在DataBase上创建**entity**--**service**--**impl**--**mapper**--**resources**--**mapper**

### 3.单元测试

出现了自动注入失败的报错，原因是MybatisPlus配置了mapper文件路径，我还重复配置了，在踩坑里已经写了。

```java
@SpringBootTest
class HmygBackendApplicationTests {

    @Resource
    private SwiperdataMapper swiperdataMapper;

    @Test
    void test() {
        List<Swiperdata> swiperdata = swiperdataMapper.selectList(null);
        swiperdata.forEach(System.out::println);
    }

}
```

赏心悦目的绿色勾勾

![image-20230518171724300](黑马优购.assets/image-20230518171724300.png)

## 阶段一✔

1、完成7个数据请求接口

- 获取主页轮播图数据
- 获取主页分类信息
- 获取主页楼层数据
- 获取分类页面所有分类（三级分类）
- 获取商品分页查询
- 获取商品详情信息
- 根据关键词查询商品

2、使用MP查询数据库，体会MP的不同

- MP不需要在配置文件中指定mapper文件路径
- Mapper和IService的接口功能重复，用IService就可以
- controller层获取参数
  - Get：用@RequestParam；或者用HttpServletRequest可以获取指定参数
  - Post：用@RquestBody接收JSON数据，是Map类型

## 阶段二✔

1、完善登录接口

- 能获取微信用户信息 ✘ （对非个人开发者开放）
- 填写用户信息，获取用户信息（类似黑马点评的登录）
- 将签名认证做成SDK✔

2、~~创建拦截器~~ ✘

- 未登录点击加入购物车，跳转到登录页面
- 未登录点击购物车导航栏，跳转到登录页面

3、创建过滤器

- 对所有请求进行过滤，需要检验的过滤出来校验信息✔
- 未登录点击加入购物车，跳转到登录页面
- 未登录点击购物车导航栏（获取购物车信息），跳转到登录页面

## 阶段三

1、设计购物车、收货地址数据库✔

2、点击加入购物车需要走接口，查看购物车也要请求数据✔

3、创建github仓库，更新ssh信息。把项目做git管理，经常更新。gitee也同步

gitee怎么同步呢？

4、登录时检验手机号格式

5、登录时返回的token在请求敏感数据的时候加入请求头中，每次调用刷新请求头的有效期，过期失效重登。

6、手机号保留在小程序的缓存中

7、获取微信头像和昵称✔

8、敏感信息是带上token还是带上userid

## 阶段四

部署到虚拟机

**mysql** 

- 初始密码：Id-kFgUuu050

- 组：mysql      用户：mysql

- 登录mysql ： /usr/local/mysql/bin/mysql -u root -p

- 位置：/usr/local/mysql

- 查询状态： service mysql status

- 启动服务：service mysql start   

- 重新启动：service mysql restart

  

虚拟机

- ip：192.168.85.130
- 用户：baozi
- 密码：1234
- 项目端口：7000
- 进入超级管理员：su root



腾讯云服务器

CentoOs 7.9

- 管理员：root  密码：********* （需要包含字母大小写和数字）
- 公网IP ：111.230.69.234
- 用户：lighthouse 免密登陆
- 安装 mysql 初始密码： uj.bv96gDTy5
- 登录mysql ： /usr/local/mysql/bin/mysql -u root -p
- 后台运行：nohup java -jar XXX.jar &

&表示后台挂起运行。通过 ps -ef | grep *.jar 查看进程运行。因为jobs只能查看当前登录在后台挂起运行的进程。





### **购物车设计**✔

1. 购物车id
2. 用户id （新用户登录手机号，存进数据库，赋予一个id）
3. 商品列表（goods_id，count（1），goods_name，goods_price，goods_small_logo，true）还需要一个userId，用来绑定数据库的用户id
4. 是否勾选 true
5. 用户绑定收货地址

**第一步：添加购物车信息**✔

开发记录：（踩坑）

前端穿的数据是小写下划线，后端用@Requestbody接收需要给属性加上@JsonProperty("user_id")注解自动转换。

goods_state是bool类型的，数据库是bigint类型的，不能直接用mp的save。三种方法解决：

- 再创建一个类，state是long类型的，做一下对象的复制再写入数据库
- 给类加一个属性，转换了state的值再写入数据库
- 不用mp的save，用原始mybatis配置文件，手写sql语句

写mybatis配置时，sql语句有多个参数，要用@Param（"userId"）注解区分。

**第二步：获取购物车**✔

在购物车页面加入 获取购物车列表的函数

```vue
		computed: {
			// 将 m_user 模块中的 userInfo 用户信息映射到当前页面中使用
			...mapState('m_user', ['userinfo']),
		},
		onLoad() {
			this.getCartList()
		},
		method:{
			// 把m_cart模块的updateCartInfo更新cart的函数拿到当前模块使用
			...mapMutations('m_cart', ['updateGoodsState', 'updateGoodsCount', 'removeGoodsById', 'updateCartInfo']),
				async getCartList() {
				let user_id = this.userinfo.user_id
				const {
					data: res
				} = await uni.$http.get('/api/my/getCartList', {
					user_id
				})

				if (res.meta.status !== 200) {
					return uni.$showMsg('获取购物车信息失败')
				}
				// console.log('输出res.message')
				// console.log(res.message)
				this.updateCartInfo(res.message)
			},
		}
```

调用updateCartInfo更新购物车

```
	mutations:{
			// 更新购物车数据
		updateCartInfo(state, cartList) {
			state.cart = cartList
		}
	}
```

**第三步：修改购物车商品数量✔**

商品的数量发生了变化，调用接口

```vue
			// 商品的数量发生了变化
			async numberChangeHandler(e) {
				// 这里不调用m_cart模块的updateGoodsCount方法了，改为发请求到后端，然后更新购物车信息
				// this.updateGoodsCount(e)
				let user_id = this.userinfo.user_id
				// let goods_count = e.goods_count
				const {
					data: res
				} = await uni.$http.post('/api/my/updateGoodsCount', {
					user_id,
					e
				})
				if (res.meta.status !== 200) {
					return uni.$showMsg('更新商品数量失败')
				}
				this.getCartList() // 重新获取购物车

			},
```

**第四步：修改购物车商品状态**✔

state是布尔值，数据库是0和1，需要判断转化再传入

**第五步：修改全选商品状态✔**

```java
Integer newState = state.equals("true") ? 1 : 0; // 不能用== 判断
```

**第六步：删除购物车信息✔**

发送uni.$http.delete('/api/my/deleteCartPro',{user_id,Goods_id}) 拼接不了参数，为什么？

可以成功发送delete请求，就是没有参数



### **收货地址设计✔**

1. 地址id
2. 用户id
3. 用户名字
4. 电话
5. 详细地址（省、市、区、详细地址）
6. 是否为默认地址 （暂时没什么用）

**步骤：**

1. 设计收货地址表
2. 点击购物车导航栏，请求地址信息，为空就显示按钮
3. 更换收货地址，更换最新默认地址（保留之前的地址，但取消默认地址）



## **API签名认证**

**本质：**

- 签发签名
- 使用签名（校验签名）

**为什么需要？**

- 保证安全性，不能随便一个人调用

**怎么实现？**

通过http request header头传递参数

- 参数1：accessKey：调用的标识 userA,userB （复杂、无序、无规律）
- 参数2：secretKey：密钥

(类似用户名和密码，区别：ak、sk是无状态的)

这两个字段加入到数据库user表中

自己生成ak、sk

不能直接传递秘钥

参数3：用户参数（手机号等）

参数4：**sign**

- 加密方式：对称加密、非对称加密、md5签名（不可逆、不可解密）（签名生成算法）
- 用户参数：abc+秘钥 ==》签名算法
- jwt可以吗？

- jwt是实现api签名认证的一个环节的一种方式，签名认证算法

**怎么校验签名？**

- 服务端用一模一样的参数和算法生成签名，再对比是否一致。（不能避免重放）
- 加时间戳也不能避免重放

**怎么防重放？**

- 参数5：加nonce随机数，后端只能用一次（后端要保存随机数，增加压力）RandomUtil.randomNumbers(100)
- 参数6：加timestamp时间戳校验随机数是否过期                 String.valueOf(System.currentTimeMillis()/1000)



**token本质上也是不安全的**

可以通过重放攻破token



前端发送数据（返回的数据放入请求头中）

```java
private Map<string,String>getHeaderMap(String body){
Map<String,String>hashMap new HashMap<>();
hashMap.put("accessKey",accessKey);
//一定不能直接发送
// hashMap.put("secretKey",secretKey);
hashMap.put("nonce",Randomutil.randomNumbers(4));// 随机数的长度
hashMap.put("body",body);
hashMap.put("timestamp",String.valueof(System.currentTimeMillis()/ 1000));
hashMap.put("sign",getsign(body,secretKey));
return hashMap;
```

使用了Hutool 摘要加密

测试时数据用英文，防止Hutool工具乱码报错。

```java
private String gensign(String body,String secretKey){
    Digester md5 = new Digester(DigestAlgorithm.SHA256);
    String content body+"."+secretKey;
    return md5.digestHex(content);
}
```

后端接收登录请求时校验

```java
@PostMapping("/user")
public String getUsernameByPost(@RequestBody Useruser,HttpServletRequest request){
    String accessKey request.getHeader("accessKey");
    String nonce request.getHeader("nonce");
    String timestamp = request.getHeader("timestamp");
    String sign = request.getHeader("sign");
    String body = request.getHeader("body");
    // todo 实际情况应该是去数据库中查是否已分配给用户
    if (!accessKey.equals("yupi")){
    	throw new RuntimeException("无权限")
    }
    if (Long.parseLong(nonce)>10000){
    	throw new RuntimeException("无权限");
    }
    // todo 时间和当前时间不能超过5分钟
    // if (timestamp){}
    // todo 实际情况中是从数据库中查出secretKey
    String serversign = Signutils.gensign(body,"abcdefgh");
    if (!sign.equals(serversign)){
        throw new RuntimeException("无权限");
    }
    return "Post"+User.getUsername();
   }
```



## 后期计划

- 请求拦截
- 登录返回token✔
- Sa-taken，一个轻量级 java 权限认证框架，加入登录认证
- 添加@Valid校验前端数据有效性，需要配置全局异常处理器
- 每次打开小程序都能记住登录用户信息，是怎么做的
- 商家管理页面（仿用户中心？）
- ✘ redis储存数据：这个不需要，因为小程序一次请求所有数据然后保存在前端了。
- 打jar包、买服务器部署上去、服务穿透
- 做一个SDK（签名认证）、手写一个springboot-starter✔
- 手写一个注解，类注解，可以给属性都自动加上@JSONField（name=。。。）的注解
- mybatisPlus的page分页查询的getTotal获取到的是未分页之前的总数。为什么？怎么改。
- 完善my页面（收藏的商品、足迹等）



## 开发一个简易的SDK

（第二集 2：15：00）

starter

更方便的使用SDK，提供一个starter，类似springboot的starter

好处：在配置文件写配置，自动创建客户端（关键jar包：Spring Configuration Processor）自动补全、代码提示

名称：\****-client-sdk

- lombok
- Spring Configuration Processor

删除pom.xml 的 \<build> 项目构建方式

删除main运行入口类

创建YupiClientConfig类

```java
@Configuration
@ConfigurationProperties("yuapi.client")  // 给所有配置加上前缀
@Data
@ComponenScan // 扫包
public class YuApiclientConfig{
    private String accessKey;

    private String secretKey;
    
    @Bean
    public YupiApiClient yupiApiClient() {
        return new YupiApiClient(accessKey,secretKey);
    }
}

```

在resources创建META-INF包，创建spring.factories文件，指向YupiClientConfig类的路径

```yml
# spring boot starter
org.springframework.boot.autoconfigure.EnableAutoConfiguration=com.zdzhai.zdzhaiclientsdk.ZdzhaiApiClientConfig
```

要删掉测试文件

在maven的lifecycle执行install打包，安装为本地依赖

可以看Idea的拉取下来的项目。作用是前端发送ak、sk的时候直接代用签名认证sdk生成sign等。简要还是要后端自己写。

 **怎么用？**

在依赖中引入依赖，然后在配置文件，传入YuApiclientConfig的属性值

```yml
yuapi:
    client:
        access-key: yupi
        secret-key: abcd
```

之后可以直接使用YupiApiClient类，当做自己有这个类 

测试：

```java
@SpringBootTest
class YuapiInterfaceApplicationTests{
    @Resource
    private YuApiclientyuApiclient;

    @Test
    void contextLoads(){
        String result yuApiclient.getNameByGet("yupi");
        Useruser new User();
        user.setUsername("liyupi");
        String usernameByPost yuApiclient.getUsernameByPost(user);
        System.out.println(result);
        System.out.println(usernameByPost);
    }
}

```



## 知识点学习

### 文件读写流的使用

FileOutputStream可以加true表示追加模式，包装成OutputStreamWriter，在加上Buffer可以更好地读写。

**记得用完要关掉close()**

```java
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("文件路径",true));
        BufferedWriter bw = new BufferedWriter(osw);
		bw.write(string);
		bw.close();
```

### HuTool工具类发送http

HuTool类的使用非常简单。

```java
 String result1 = HttpUtil.get(url);
```

再将响应数据解析成JSON，使用alibaba.fastjson

```java
JSONObject jsonObject = JSON.parseObject(result1);
String message = jsonObject.getString("message"); // 可以获取JSON的属性
JSONArray attrs = detail.getJSONArray("attrs"); // 可以获取JSON的列表属性
String s = JSON.toJSONString(attr, SerializerFeature.PrettyFormat,
SerializerFeature.WriteMapNullValue,SerializerFeature.WriteDateUseDateFormat);// 格式化JSON
```

### 字符串的提取与分离

```java
string.trim()   // 去除收尾空格
string.substring() // 截取
string.split(","); // 分隔，返回数组
string.split(",|:"); // 多个分隔符用|隔开
LinkedHashMap<String, String> map = new LinkedHashMap<>(); // 有序Map，按插入顺序
```

**Map的遍历删除**

- 使用迭代器删除
- fori正序循环，删除之后i回退，i=i-1
- fori倒序循环

```java
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()){
            String s = it.next();
            if(map.get(s).equals("123")){
                it.remove();
            }
        }
```

### 计算耗时

使用系统lang自带的获取当前毫秒数

```java
        long startTime = System.currentTimeMillis();
		...
        System.out.println("setLengthTest()耗时:" + (System.currentTimeMillis() - startTime) + "ms");
```

### **SpringMVC是什么**

 SpringMVC原理
在没有使用SpringMVC之前我们都是使用Servlet在做Web开发。但是使用Servlet开发在接收请求参数，数据共享，页面跳转等操作相对比较复杂。servlet是java进行web开发的标准，既然springMVC是对servlet的封装，那么很显然SpringMVC底层就是Servlet，SpringMVC就是对Servlet进行深层次的封装。

MVC分别是：模型model(javabean)、视图view(jsp/img)、控制器Controller(Action/servlet)。

Springbootd依赖包包括了SpringMVC所需的依赖包

```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>4.3.16.RELEASE</version>
</dependency>
```

### @RestController注解

**@Controller 与 @RestController的区别**

使用于controller层

1. @Controller是在 Spring 中将类标记为控制器类的注解，而@RestController用于REST Web 服务，类似于@Controller和@ResponseBody。
2. @Controller注释表示该类是控制器，如 Web 控制器，而@RestController注释表示该类是控制器，其中**@RequestMapping方法默认采用@ResponseBody**（即 REST API）。
3. 关键区别在于，一旦用@RestController注释类，就不需要在每个处理程序方法上都使用**@ResponseBody**。
4. @Controller创建模型对象的映射并查找视图，而**@RestController**只是简单地返回对象和对象数据，直接以 JSON 或 XML 形式写入 http 响应。

### List和ArraysList的区别





### LambdaQueryWrapper为什么用lambda表达式

```java
LambdaQueryWrapper<Floordata> query=new LambdaQueryWrapper<>();   
query.eq(Floordata::getLevel,i);                                  
```

用字符串 `query.eq("level",i);`  容易拼错，而且还能自动转换驼峰和小写下划线。    

LambdaQueryWrapper对QueryWrapper改进，主要就是lambda，参数支持l**ambda写法**，并且可以**链式编程**。



### 对象转为json格式时属性名转为小写下划线

在属性定义时加上@JSONField(name="XXXX") 注解，在序列化时使用别名

然后使用JSON.toJSON(XXXX)转换属性的名字

```java
   	// 属性定义时
	@JSONField(name = "image_src")
    private String imageSrc;
    
	// 方法使用时
    @GetMapping("/swiperdata")                                                        
    public R<Object> getSwiperData(){                                                 
        List<Swiperdata> list = swiperdataService.list();                             
        Object toJSON = JSON.toJSON(list); 
        return R.success(toJSON,"获取成功");                                              
    }                                                                                 
```

**了解更多：**

通常前端是下划线命名，而后端是驼峰命名，所以导致数据的传递需要转换

```java
@JsonProperty("user_id")
@JSONField(name = "user_id")
private String userId;
```

这两个注解一个是@JsonProperty可以把前端带下划线传回来的参数在使用@RequestBody接收时自动转成驼峰命名并注入到类中，return的对象也会自动转换为小写下划线给前端
@JSONField是把实体类的属性改成带下划线的属性名再发送给前端

顺便提一下数据库里面的命名也是带下划线的，查询数据返回注入到类中，类里面的属性名也是驼峰命名，需要开启Mybatis的一个配置

```properties
mybatis.configuration.map-underscore-to-camel-case=true
```



### 关于MySQL的数据类型

**BOOLEAN、TINYINT**

- MySQL没有内置的布尔类型。 但是它使用TINYINT(1)。 为了更方便，MySQL提供`BOOLEAN`或`BOOL`作为`TINYINT(1)`的同义词。

**VARCHAR、TEXT**

- 温习了varchar，VARCHAR是变长字符串。默认最大65536字节，去掉null标识和长度标识，可能就65533。

- 最大长度(字符数) = （行存储最大字节数 - NULL标识列占用字节数 - 长度标识字节数） / 字符集单字符最大字节数

- 要储存更大的文本，使用**TEXT**，可以存储64kb的大小。

### GetMapping怎么接收可变参数

```java
    @GetMapping("/getGoodsList")
    public R<Object> getGoodsList(HttpServletRequest request) { // 可以获取可变参数，指定参数
        System.out.println("query = " + request.getParameter("query"));
        System.out.println("cid = " + request.getParameter("cid"));
        System.out.println("pagenum = " + request.getParameter("pagenum"));
        System.out.println("pagesize = " + request.getParameter("pagesize"));
        // 拿不到的就是null
        // request.getParameter("cid") == null
    }
```

### List列表没有指定泛型

没有泛型的时候，我们声明的 List 集合默认是可以存储任意类型元素的，乍一看你可能还会觉得挺好，这样功能强大，啥类型都可以存储......但是开发的时候由于不知道集合中元素的确切类型，遍历的时候我们拿到的 item 其实是 **Object** 类型，如果要使用就必须强转**，强转就必须得判断当前元素的具体类型**，否则直接使用强转很可能会发生类型转换异常。

**总结起来就是一句话，它不安全！**



### LinkedHashMap

有序字典，可以按插入顺序取出。好用！

```java
LinkedHashMap<String, String> map = new LinkedHashMap<>();
```

### MyBatist Log 插件

最新版收费，下载历史版本。

使用方法：开启Mybatis/Plus 的log控制台日志输出，查询时选中sql语句，右键选择Restore Sql From Selection，就能在栏目里看到带实际参数的sql语句，而不是？？占位符

![image-20230523093751583](黑马优购.assets/image-20230523093751583.png)

![image-20230523093919743](黑马优购.assets/image-20230523093919743.png)

### 启动时报黄色警告

![image-20230523164938999](黑马优购.assets/image-20230523164938999.png)

这是使用mybatis-Plus导致的，因为类没有自增主键的注解，MP要求类的主键属性要有自增注解

这也是MP包的注解。

```java
@TableId(type = IdType.AUTO)
private Integer id;
```

### Idea生成javadoc文档

[(326条消息) 使用IDEA生成JavaDoc文档（IDEA2020.3.2）_idea doc一键转为阅读_Gyur Miyonesqn的博客-CSDN博客](https://blog.csdn.net/qq_44122193/article/details/114789427)

就是api文档



### 调用接口的方法

1. HttpClient
2. RestTamplate
3. 第三方库：OKHTTP、HuTool

### 打印请求链接url

```java
    @GetMapping("/getCartList")
    public R<Object> getCartList(@RequestParam("user_id") Long userId, HttpServletRequest request){
        System.out.println(request.getRequestURL());
    }
```



## 踩坑

### MybatisPlus

使用MybatisX插件自动生成代码之后，项目启动不了

![image-20230518165000941](黑马优购.assets/image-20230518165000941.png)

可能的原因：在启动类加上@MapperScan("com.baozi.hmygbackend.mapper")之后，在配置文件中还添加了如下配置，导致mapper中的类扫描冲突。

```yml
# 指定Mapper文件路径
mybatis-plus:
  config-location: classpath:mapper/*xml
```

解决：配置文件中去掉路径指定

**另外一个可能的原因**：不是因为MybatisX，而是MybatisPlus已经做了配置了映射文件的默认地址classpath*:/mapper/**/*.xml，重复配置就报错了。而Mybatis才需要自己配置。

mybatisPlus源码（全局搜索MybatisPlusProperties 类可以找到）

![image-20230522170356561](黑马优购.assets/image-20230522170356561.png)

还要注意一个地方**，在启动类加上@MapperScan之后，就可以自动扫描mapper类了，否则就要在每一个mapper类加上@Mappe注解。**



<font color='red'>**具体的原因**</font>：是因为我配置写错了，把 mapper-locations 写成了配置文件路劲 config-location

```yml
## 指定Mybatis的Mapper文件
mybatis-plus:
  mapper-locations: classpath:mapper/*xml
```

**（MybatisPlus和Mybatis 的差异）！！！**

#### 分页查询不生效

需要有 分页插件配置类，加上之后分页查询就生效了（能在打印的sql语句看到 **LIMIT** ）

```java
package com.baozi.hmygbackend.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MyBatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType("mysql");
        return page;
    }
}

```

#### Mapper和IService的api功能重复

[关于mybatis-plus中Service和Mapper的分析 - 知乎 (zhihu.com)](https://zhuanlan.zhihu.com/p/114451975)

我们继承的ServiceImpl依旧实现了BaseMapper接口和Iservice接口，明明我们单独写了RestDeptMapper，并且继承了BaseMapper，现在ServiceImpl还是实现了BaseMapper。

本质：Service简直是BaseMapper的大扩充，不但包含了所有基本方法，还加入了很多批处理功能。

mapper能处理的，IService也能处理，只不过函数名称不一样，比如分页查询：

**在mapper层处理**

```java
@Service
public class GoodsDetailServiceImpl extends ServiceImpl<GoodsDetailMapper, GoodsDetail>
    implements GoodsDetailService{

    @Resource
    private GoodsDetailMapper goodsDetailMapper;

    @Override
    public IPage<GoodsDetail> getGoodsList(String query,Integer cid,Integer pageNum,Integer pageSize){
        LambdaQueryWrapper<GoodsDetail> queryWrapper = new LambdaQueryWrapper<>();
        if(cid!=null){
            queryWrapper.eq(GoodsDetail::getCatId,cid);
        }
        if(query!=null){
            queryWrapper.like(GoodsDetail::getGoodsName,query);
        }
        Page<GoodsDetail> page = new Page<>(pageNum,pageSize);
        IPage<GoodsDetail> goodsListPage = goodsDetailMapper.selectPage(page, queryWrapper);
        return goodsListPage;
    }

}

// controller层调用IService
        IPage<GoodsDetail> page = goodsDetailService.getGoodsList(query, cid, pagenum, pagesize);
        List<GoodsDetail> list = page.getRecords();
```

**在controller层直接用IService的方法**

```java
        LambdaQueryWrapper<GoodsDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GoodsDetail::getCatId, cid);
        Page page = new Page(pagenum, pagesize);
        goodsDetailService.page(page, queryWrapper);
        List list = page.getRecords();
```



**深圳新润邦科技有限公司  软件开发**

- 对cdr软件进行二次开发，使用go语言开发后处理接口，包括gin、gorm、gRPC等框架的使用；
- 对接第三方ERP系统，对接口进行后处理和封装；熟悉json和基本类型的转化
- 用three.js创建3D模型和运动控制；



#### cartService.update(null, updateWrapper);这个返回值是boolean 是什么意思？

执行失败或成功

#### 数据库编码

user表的nick_name字段不能存储表情字符，因为建表的编码是utf8，三字节，要改成utf8mb4。

使用的mysql依赖版本mysql-connector-java 比较低（可能？）5.1.47的，要在配置上加上

```yml
&characterEncoding=UTF-8
```

#### insert返回主键id

使用MybatisPlus的IService的save方法，执行成功会自动注入id到对象，注意两点

- 数据库的表的主键设置为自增
- 实体类加上@TableId(type = IdType.AUTO)

# tcp-netty

基于netty实现的客户端和服务器,支持动态创建服务器和客户端

项目特点:
1. **支持通过接口的方式动态创建TCP服务端和客户端的连接,数据传输和断开连接**
2. 支持读取mysql数据库数据,主动创建tcp连接或者监听
3. 提供TCP服务端和客户端的连接,数据传输和断开连接的公共抽象

ps: [TcpAbstract.java](netty%2Fsrc%2Fmain%2Fjava%2Fcom%2Fcn%2Fnetty%2FTcpAbstract.java)
的DEVICE_MAP中使用设备连接的remoteAddress作为唯一标识

未实现:
1. 只支持的String的编解码,16进制等暂不支持
2. 重连重试和短线检查未实现

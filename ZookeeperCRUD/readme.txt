zookeeper 原生客户端
1.
远程客户端登陆：
./zkCli.sh -server ip:端口号；

2.如果有权限控制，远程登陆的时候，使用如下语句：
addauth digest root:123456        解释：addauth digest   创建时候的用户名：密码
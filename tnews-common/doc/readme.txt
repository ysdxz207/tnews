1、Change path in generatorConfig-tnews.xml
<!ENTITY mysqlDir "D:\javaDir\java\apache-maven-3.3.9\repository\mysql\mysql-connector-java\5.1.30\">目录改为本地mysql路径
<!ENTITY targetBase "D:/workspace/eclipse/java/">
to your path directory
2、Change the config file path in tnews-common pom.xml
3、maven build goals:mybatis-generator:generate
or command: maven：mvn mybatis-generator:generate

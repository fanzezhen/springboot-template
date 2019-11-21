# 基于SpringBoot-Security、MyBatis、Thymeleaf的项目模板

备注
    
    原本使用JPA作为数据库连接框架， 但国内项目用到复杂查询的场景和次数太多了， 用JPA明显不如使用MyBatis-Plus配合xml文件合适
    
problems:

    1. 在启动时遇到Logging内存溢出
        报错信息：
            （Exception in thread "main" java.lang.StackOverflowError）
        解决：
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-web</artifactId>
                <exclusions><!-- 去掉默认配置 -->
                    <exclusion>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-starter-logging</artifactId>
                    </exclusion>
                    <exclusion>
                        <groupId>ch.qos.logback</groupId>
                        <artifactId>logback-classic</artifactId>
                    </exclusion>
                </exclusions>
                <version>${spring-boot-starter-web.version}</version>
            </dependency>



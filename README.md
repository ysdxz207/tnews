# tnews
- news server by spring mvc + mybatis + dubbo

- no sql db use redis

- admin UI using B-JUI

    项目中使用了[MyBatis通用Mapper3](//github.com/abel533/Mapper)
    
    **tnews-service-news**为新闻服务
    **tnews-service-member**为用户服务
    **tnews-service-dictionary**为字典服务
    **tnews-web**为接口访问入口
    **tnews-manager**为管理后台
    
    新闻数据来自易源数据，因使用字典筛选数据导致访问接口极其耗时，
    而使用了forkjoinpool。
    
    
- 此项目仅用于学习交流之用
# RateLimit
基于redis的接口做的接口请求频率限制
# 使用方法
在方法上使用@AccessLimit(limit = 2,sec = 10)注解；
limit标识 指定sec时间段内的访问次数限制
sec标识 时间段
本实例中使用的时间颗粒度为秒，sec=10 则表示在10秒中允许请求2次，如需修改时间颗粒度可修改@AccessLimit注解添加颗粒度字段标识，并在AccessLimitInterceptor中做相应修改

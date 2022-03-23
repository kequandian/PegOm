# timeReview
### 使用
1. 使用 @TimeReview 注解来标识目标方法
2. 运行目标方法 运行的时间将记录在redis中
3. 调用/api/crud/timeReview/info 来获取redis中记录的时间
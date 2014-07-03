YeBin
=====

这个文件夹是YeBin的
但是被Yao做了修改...

=========================
下面添加 大概的App构建步骤，大家看看：

讲座网App 类与View

1 AppStart－开机界面，为此2秒左右转入第3步开启主界面，如果是第一次开启软件则转入第2步

2 AppGuide－第一次开启的向导

3 MainView－主界面，点击筛选讲座添加一个view，不转换页面，在当前页面进行这个类在第4步

4 Filtrate－过滤界面，提供选项选择失去焦点时过滤当前讲座（可能是subscribe讲座或者hotlecture）

5 主界面的五个功能：SubscribeCenter、HotLectureCenter、NoticeCenter、SubmitCenter、MyCenter
先写SubscribeCenter，其他的都是一样的，同时要预留MyCenter中的设置与开机时的默认筛选
然后MyCenter点击筛选设置FiltrateSetting进行设置，把设置存进SQLite数据库

6 SubMainView点进去任何一则讲座则开启SubMainView显示详细内容（做好数据库的功能映射设计）

7 点击分享按钮开启ShareCenter，进行分享

8 点击评论按钮开启CommentCenter，进行评论

9 点击Like按钮不开启任何东西，只改变图标颜色，显示按赞数，后台数据库更新

10 点击提醒按钮开启RemindCenter进行设置，设置完返回当前Activity









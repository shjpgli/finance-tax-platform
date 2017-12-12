#UC后台存储redis的key命名规范：唯一id+下划线+内容英文

1.用户信息(/user/{userId})：userid+'_UserInfo'
2.用户扩展信息(/user/{userId}):userid+'_UserExtend'
3.用户经验值信息(/experience/{userId})：userid+'_MyExperience'
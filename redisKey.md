#UC后台存储redis的key命名规范：唯一id+下划线+内容英文

1.用户信息(/user/{userId})：userid+'_UserInfo'
2.用户扩展信息(/user/{userId}):userid+'_UserExtend'
3.用户经验值信息(/experience/{userId})：userid+'_MyExperience'
4.电子申报绑定列表(/bind/dzsb/{userId})：userid+'_DzsbList'
5.电子税局绑定列表(/bind/hngs/{userId})：userid+'_HngsList'
6.湖南地税绑定列表(/bind/hnds/{userId})：userid+'_HndsList'
7.字典管理-根据dictId查找(/dict/kv/{dictId})：dictId+'_Dict'
8.UC用户根据用户名或者电话查询用户(/user/u/{usernameOrPhone})：phone+'_UserPhoneInfo'
9.分管理-根据用户ID查询用户积分情(/points/{userId})：userid+'_Points'
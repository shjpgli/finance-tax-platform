#UC后台存储redis的key命名规范：唯一id+下划线+内容英文

1.用户信息(/user/{userId})：userid+'_UserInfo'
2.用户扩展信息(/user/{userId}):userid+'_UserExtend'
3.用户经验值信息(/experience/{userId})：userid+'_MyExperience'
4.电子申报绑定列表(/bind/dzsbs/{userId})：userid+'_DzsbList'
5.电子税局绑定列表(/bind/hngss/{userId})：userid+'_HngsList'
6.湖南地税绑定列表(/bind/hndss/{userId})：userid+'_HndsList'
7.字典管理-根据dictId查找(/dict/kv/{dictId})：dictId+'_Dict'
8.UC用户根据用户名或者电话查询用户(/user/u/{usernameOrPhone})：phone+'_UserPhoneInfo'
9.分管理-根据用户ID查询用户积分情(/points/{userId})：userid+'_Points'
10.地区管理-根据省ID查市(/city/{provinceId})：provinceId+"_City"
11.地区管理-根据市ID查区(/area/{cityId})：cityId+"_Area"
12.获取用户累计签到天数(/check/total)：userid+'_Check'
13.查看用户本月任务概况(/task/mytask/survey/{userId})：userid+'_Tasks'
14.根据等级编码查看会员等级(/uvip/level/bo/{levelCode})：levelCode+"_VipLevel"
15.个人消息(/user/message)：userid+'_MessageForqt'
16.业务消息服务(/business)：userid+'_BusinessForqt'
17.广告轮播图(/adpages)：CMS_AdpageListFqt
18.财税资讯列表(/content/selectListcszxw)：CMS_SelectListcszxwFqt
19.通知公告(/notices)：CMS_NoticeListFqt
20.热点问题(/questionbb/selectListryForqt)：Bangb_HotUnClassifyList
21.帮友热议(/questionbb/selectListry)：CMS_SelectListryForqt
22.活动中心(/event/topone/{category})：category+"_ToponeForqt"
23.推荐课程(/curriculum/selectRecommend)：CMS_Curriculum
24.知识库分类(/knowledgeCategory/listAll)：Bangb_KnowledgeCategoryList
25.财税资讯(/contentType)：Cms_ContentTypeList
26.财税网首页 最新问题、知识(/knowledgeBase/nearestList)：Bangb_NearestLists
27.问题受理 统计查询(缓存)(/accepted/statis)：Bangb_Accepted
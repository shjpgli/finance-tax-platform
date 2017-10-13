package com.abc12366.bangbang.config;

import com.abc12366.gateway.component.AppInterceptor;
import com.abc12366.gateway.component.LogInterceptor;
import com.abc12366.gateway.component.TokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 10:24 AM
 * @since 1.0.0
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public LogInterceptor logInterceptor() {
        return new LogInterceptor();
    }

    @Bean
    public AppInterceptor appInterceptor() {
        return new AppInterceptor();
    }

    @Bean
    public TokenInterceptor tokenInterceptor() {
        return new TokenInterceptor();
    }

    @Bean
    public SensitiveWordsInterceptor getSensitiveWordsInterceptor() {
        return new SensitiveWordsInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 前置日志、黑名单、后置日志、接口计数拦截
        registry.addInterceptor(logInterceptor())
                .excludePathPatterns("/druid/**");

        // App验证、授权拦截
        registry.addInterceptor(appInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/app/**")
                .excludePathPatterns("/appsetting/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/blacklist/**")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/test");

        //前台用户访问拦截器迁移到网关后的
        registry.addInterceptor(tokenInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/app/**")
                .excludePathPatterns("/appsetting/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/blacklist/**")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/test")
                        //根据课程评价信息
                .excludePathPatterns("/currEvaluate/selectListBycurrId")
                        //根据课程ID获取讲师信息
                .excludePathPatterns("/lecturer/selectListByCurr")
                        //获取课程分类
                .excludePathPatterns("/classify/selectListsy")
                        //获取课程标签列表
                .excludePathPatterns("/curriculum/selectLabelList")
                        //根据分类查询相关标签
                .excludePathPatterns("/classify/selectClassifyTagList")
                        //获取课程大类小类标签集合
                .excludePathPatterns("/classify/selectClassifyListsy")
                        //获取最新课程
                .excludePathPatterns("/curriculum/selectListNew")
                        //获取最热课程
                .excludePathPatterns("/curriculum/selectListWatch")
                        //获取推荐课程
                .excludePathPatterns("/curriculum/selectRecommend")
                        //获取活动
                .excludePathPatterns("/event/**")
                        //获取课程详情信息
                .excludePathPatterns("/curriculum/selectCurriculum/**")
                        //查询热门问题
                .excludePathPatterns("/questionbb/selectListByBrowseNum")
                        //查询等你回答的问题
                .excludePathPatterns("/questionbb/selectListWait")
                        //查询已解决的问题
                .excludePathPatterns("/questionbb/selectListAccept")
                        //帮友热议列表查询
                .excludePathPatterns("/questionbb/selectListry")
                        //查询热议标签
                .excludePathPatterns("/questionbb/selectTagList")
                        //查询最新问题
                .excludePathPatterns("/questionbb/selectListNew")
                        //问题分类列表查询
                .excludePathPatterns("/queclassify/selectClassifyList")
                        //根据分类查询相关标签
                .excludePathPatterns("/queclassify/selectClassifyTagList")
                        //问题回复列表查询
                .excludePathPatterns("/queAnswer/selectListByQuestionId")
                        //问题回复评论列表查询
                .excludePathPatterns("/queAnswer/selectListByParentId")
                        //最新回答
                .excludePathPatterns("/questionbb/selectListNew")
                        //查询单个问题
                .excludePathPatterns("/questionbb/selectQuestion/**")
                        //问题更新浏览量
                .excludePathPatterns("/questionbb/updateBrowseNum/**")
                        //优秀邦派列表查询
                .excludePathPatterns("/queFaction/selectListExcellent")
                .excludePathPatterns("/hotspot/**")
                .excludePathPatterns("/knowledgeBase/uc/list","/knowledgeBase/hotList","/knowledgeBase/interestedList/**","/knowledgeBase/relatedList/**",
                        "/knowledgeBase/vote/add","/knowledgeBase/view/**","/knowledgeBase/pv/**","/knowledgeCategory/listAll",
                        "/KnowledgeTag/listHot/**","/KnowledgeTag/listHot","/knowledgeBase/hotUnClassifyList","/knowledgeBase/wx/hotUnClassifyList");

        // 敏感词拦截
        registry.addInterceptor(getSensitiveWordsInterceptor())
                .excludePathPatterns("/")
                .excludePathPatterns("/app/**")
                .excludePathPatterns("/appsetting/**")
                .excludePathPatterns("/api/**")
                .excludePathPatterns("/blacklist/**")
                .excludePathPatterns("/druid/**")
                .excludePathPatterns("/test");

    }
}

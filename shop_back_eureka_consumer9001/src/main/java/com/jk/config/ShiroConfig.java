package com.jk.config;

import com.jk.shiro.AdminRealm;
import com.jk.shiro.CustomerCredentialMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName : ShiroConfig
 * @Author : xiaoqiang
 * @Date : 2018/10/31 18:50:50
 * @Description :
 * @Version ： 1.0
 */
@Configuration
public class ShiroConfig {

    @Bean(name = "lifecycleBeanPostProcessor")//配置Shiro在Spring中的生命周期的控制操作
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    //shiro 安全管理器 shiro的主入口

    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
   public AdminRealm adminRealm(){
        AdminRealm adminRealm = new AdminRealm();
        //加盐后的密码匹配器
        adminRealm.setCredentialsMatcher(new CustomerCredentialMatcher());
        return adminRealm;
   }

    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm.注入缓存管理器;
        //securityManager.setCacheManager(ehCacheManager());
        //在安全管理器中 注入realm.数据源
        securityManager.setRealm(adminRealm());
        //配置rememberMe管理器
        securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    //shiro的主要核心过滤器
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        //创建一个shiro过滤器工厂
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        // 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
        //shiro过滤器链 按照过滤器链顺序执行过滤内容

        //anon  放过权限拦截过滤器 不需要权限也能访问的资源
        filterChainDefinitionMap.put("/loginUrl","anon");
        filterChainDefinitionMap.put("/logout", "logout");
        filterChainDefinitionMap.put("/infos/**","user");
        //一般将"/**"这个选项放置到最后，否则在其后面的配置路径可能就无法使用了
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        //出现错误之后的跳转路径的配置
        shiroFilterFactoryBean.setLoginUrl("/shiroLogin");
        // 登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/toIndex");
        // 未授权界面;
        shiroFilterFactoryBean.setUnauthorizedUrl("/warning");

        //内置登录处理配置不成功先删除了
        return shiroFilterFactoryBean;
    }

    //为当前shiro配置aop切面 使shiro与springAOP结合
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    //设置当前aop动态代理为cglib代理
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator daap = new DefaultAdvisorAutoProxyCreator();
        //ProxyTargetClass 代理目标(实例) 类 把当前代理目标设置为类代理 默认为interface接口代理
        daap.setProxyTargetClass(true);
        return daap;
    }

    //配置rememberMe的保存cookie
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager rememberMeManager = new CookieRememberMeManager();

        //设置Cookie在浏览器中保存内容的名字，有用户自己来设置
        SimpleCookie simpleCookie = new SimpleCookie("MLDNJAVA-rememberMe");
        //保证该系统不会受到跨域的脚本操作攻击，默认就位true
        simpleCookie.setHttpOnly(true);
        //定义Cookie的过期时间位一小时
        simpleCookie.setMaxAge(3600);

        rememberMeManager.setCookie(simpleCookie);
        return rememberMeManager;
    }
}

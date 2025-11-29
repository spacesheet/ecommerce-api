package com.ps.app.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Role
import org.springframework.beans.factory.config.BeanDefinition

/**
 * AOP 설정
 * 
 * @EnableAspectJAutoProxy: AspectJ 기반 AOP 활성화
 * - proxyTargetClass = true: CGLIB 프록시 사용 (인터페이스 없이도 프록시 생성)
 * - exposeProxy = true: AopContext를 통해 현재 프록시 객체에 접근 가능
 */
@Configuration
@EnableAspectJAutoProxy(
    proxyTargetClass = true,  // CGLIB 프록시 사용
    exposeProxy = true        // 프록시 객체 노출 (self-invocation 처리)
)
class AopConfig {

    /**
     * DefaultAdvisorAutoProxyCreator 빈 등록
     * 스프링 AOP 어드바이저를 자동으로 프록시로 적용
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    fun defaultAdvisorAutoProxyCreator(): DefaultAdvisorAutoProxyCreator {
        return DefaultAdvisorAutoProxyCreator().apply {
            isProxyTargetClass = true
        }
    }
}

package com.geqian.evalution.teachingevalution.aop;

import com.geqian.common.common.ResponseResult;
import com.geqian.evalution.teachingevalution.annotation.CustomTransaction;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.annotation.Resource;

@Component
@Aspect
@Slf4j
public class TransactionAop {

    @Resource
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Around("@annotation(customTransaction)")
    public Object openTransaction(ProceedingJoinPoint point, CustomTransaction customTransaction) {
        //开启事务
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
        log.info("开启事务");
        try {
            //执行方法
            Object result = point.proceed();
            //执行成功提交事务
            dataSourceTransactionManager.commit(transactionStatus);
            log.info("事务提交");
            return result;
        } catch (Throwable e) {
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transactionStatus);
            log.info("事务回滚");
            return ResponseResult.fail(customTransaction.value());
        }
    }
}

package com.taotao.myktdistributedluck.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

/**
 * @ClassName TranslationUtils
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
@Component
//@Scope("prototype")
public class TranslationUtils {
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    public TransactionStatus begin() {
        return dataSourceTransactionManager.getTransaction(new DefaultTransactionAttribute());
    }

    public void commit(TransactionStatus transactionStatus) {
        dataSourceTransactionManager.commit(transactionStatus);

    }

    public void rollback(TransactionStatus transactionStatus) {
        dataSourceTransactionManager.rollback(transactionStatus);
    }
}

package com.mtl.service;

public interface RequestServiceManager {

    /**
     * 管理爬虫服务的开启和关闭
     */
    boolean serviceShutdown();

    boolean serviceStratUp();
}

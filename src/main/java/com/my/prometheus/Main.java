package com.zxk.prometheus;

import com.zxk.prometheus.api.IndexEntrance;
import io.prometheus.client.exporter.HTTPServer;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.spi.ResteasyDeployment;

import java.util.List;

/**
 * Created by zhuxiaokang on 2018/3/23.
 */
public class Main {

    public static void main(String[] args) throws Exception{
        new HTTPServer(1234);
        NettyJaxrsServer netty = new NettyJaxrsServer();
        netty.setPort(9099);
        netty.setDeployment(getResteasyDeployment());
        netty.setIdleTimeout(60);
        netty.setIoWorkerCount(0);
        netty.setExecutorThreadCount(256);
        netty.setRootResourcePath("");
        netty.start();
    }

    private static final ResteasyDeployment getResteasyDeployment() {
        final ResteasyDeployment resteasyDeployment = new ResteasyDeployment();
        resteasyDeployment.setActualResourceClasses(setActualResourceClasses());
        return resteasyDeployment;
    }

    private static final List<Class> setActualResourceClasses() {
        final List<Class> actualResourceClasses = new java.util.ArrayList<>();
        actualResourceClasses.add(IndexEntrance.class);
        return actualResourceClasses;
    }
}

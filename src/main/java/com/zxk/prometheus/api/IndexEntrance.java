package com.zxk.prometheus.api;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.Summary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhuxiaokang on 2018/3/23.
 */
@Path("/")
public class IndexEntrance {

    /** 记录请求数量*/
    static final Counter counterBuild = Counter.build().name("my_http_req_total").labelNames("service").help("http request total").register();
    static final Gauge gaugeBuild = Gauge.build().name("my_inprogress_g").labelNames("server","url").help("Inprogress requests.").register();
    static final Summary summaryBuild = Summary.build().name("my_requests_summ").quantile(0.1,0.00).quantile(0.5,0.00).quantile(1,0.00).labelNames("server").help("Request size in bytes.").register();
    static final Histogram histogramBuild = Histogram.build().name("my_requests_h").labelNames("server","url").help("Request latency in seconds.").register();

    @GET
    @Path("/c")
    @Produces(MediaType.APPLICATION_JSON)
    public Response counter(){
        Map<String, Object> results = new HashMap<String,Object>();
        results.put("0","succcess");
        System.err.println("afasfasfsfsfsfasfasfasdf");
        counterBuild.labels("counter").inc();
        return Response.ok(results).build();
    }

    @GET
    @Path("/c1")
    @Produces(MediaType.APPLICATION_JSON)
    public Response counter1(){
        Map<String, Object> results = new HashMap<String,Object>();
        results.put("0","succcess");
        System.err.println("afasfasfsfsfsfasfasfasdf");
        counterBuild.labels("counter1").inc();
        return Response.ok(results).build();
    }

    @GET
    @Path("/g")
    @Produces(MediaType.APPLICATION_JSON)
    public Response gague(){
        Map<String, Object> results = new HashMap<String,Object>();
        results.put("0","succcess");
        System.err.println("afasfasfsfsfsfasfasfasdf");

         gaugeBuild.labels("gague","http://localhost:9099/g").setToTime(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.err.println(gaugeBuild.labels("gague","http://localhost:9099/g").get());
        return Response.ok(results).build();

    }

    @GET
    @Path("/h")
    @Produces(MediaType.APPLICATION_JSON)
    public Response histogram(){
        Map<String, Object> results = new HashMap<String,Object>();
        results.put("0","succcess");
        System.err.println("afasfasfsfsfsfasfasfasdf");
       io.prometheus.client.Histogram.Timer timer = histogramBuild.labels("histogram","http://localhost:9099/h").startTimer();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        timer.observeDuration();
        System.err.println(histogramBuild.labels("histogram","http://localhost:9099/h").get());
        return Response.ok(results).build();
    }

    @GET
    @Path("/s")
    @Produces(MediaType.APPLICATION_JSON)
    public Response summary(){
        long t1 = System.currentTimeMillis();
        Summary.Child child = summaryBuild.labels("summary");

//        Summary.Timer timer = child.startTimer();
        System.err.println("afasfasfsfsfsfasfasfasdf");
        int b=(int)(Math.random()*10)*1000;
        System.err.println(b);
        try {
            Thread.sleep(b);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
//            timer.observeDuration();
        }
        child.observe(System.currentTimeMillis() - t1);
        Map<String, Object> results = new HashMap<String,Object>();
        results.put("0","succcess");
        return Response.ok(results).build();
    }
}

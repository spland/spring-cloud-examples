package com.peterwanghao.spring.cloud.sleuth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**   
 * @ClassName:  SleuthService
 * @Description:一个简单服务
 * @author: wanghao
 * @date:   2018年4月11日 上午11:27:58
 * @version V1.0
 * 
 */
@Service
public class SleuthService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final Tracer tracer;

    @Autowired
    public SleuthService(Tracer tracer) {
        this.tracer = tracer;
    }
    
	public void doSomeWorkSameSpan() throws InterruptedException {
        Thread.sleep(1000L);
        logger.info("Doing some work");
    }
	
	public void doSomeWorkNewSpan() throws InterruptedException {
        logger.info("I'm in the original span");

		Span newSpan = tracer.createSpan("newSpan");
        try {
            Thread.sleep(1000L);
            logger.info("I'm in the new span doing some cool work that needs its own span");
        } finally {
            tracer.close(newSpan);
        }

        logger.info("I'm in the original span");
    }
	
	@Async
    public void asyncMethod() throws InterruptedException {
        logger.info("Start Async Method");
        Thread.sleep(1000L);
        logger.info("End Async Method");
    }
}

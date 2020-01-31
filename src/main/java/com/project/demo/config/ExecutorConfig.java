package com.project.demo.config;

import com.project.demo.utils.MyLog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Auther: Yzl
 * @Date: 2019/11/25 14:02
 * @Description:
 */
@Configuration
@EnableAsync
public class ExecutorConfig {


    @Bean
    public Executor asyncServiceExecutor() {
        ThreadPoolTaskExecutor executor = new VisiableThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(10);
        //配置最大线程数
        executor.setMaxPoolSize(10);
        //配置队列大小
        executor.setQueueCapacity(20);
        //配置线程池中线程的名称前缀
        executor.setThreadNamePrefix("async-service-");
        //rejection-policy:当pool已经达到max-size,处理新任务
        //CALLER_RUNS:不在新线程中执行任务，由调用者所在线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    private static final class VisiableThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {
        private void showThreadPoolInfo(String prefix) {
            ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
            if (threadPoolExecutor == null) {
                return;
            }
            MyLog.info("{},{},taskCount [{}],completedTaskCount [{}],activeCount [{}],queueSize [{}]",
                    this.getThreadNamePrefix(),
                    prefix,
                    threadPoolExecutor.getTaskCount(),
                    threadPoolExecutor.getCompletedTaskCount(),
                    threadPoolExecutor.getActiveCount(),
                    threadPoolExecutor.getQueue().size());
        }

        @Override
        public void execute(Runnable task) {
            showThreadPoolInfo("1. do execute");
            super.execute(task);
        }

        @Override
        public void execute(Runnable task, long startTimeout) {
            showThreadPoolInfo("2. do execute");
            super.execute(task, startTimeout);
        }

        @Override
        public Future<?> submit(Runnable task) {
            showThreadPoolInfo("1. do submit");
            return super.submit(task);
        }

        @Override
        public <T> Future<T> submit(Callable<T> task) {
            showThreadPoolInfo("2. do submit");
            return super.submit(task);
        }

        @Override
        public ListenableFuture<?> submitListenable(Runnable task) {
            showThreadPoolInfo("1. do submitListenable");
            return super.submitListenable(task);
        }

        @Override
        public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
            showThreadPoolInfo("2. do submitListenable");
            return super.submitListenable(task);
        }
    }

}

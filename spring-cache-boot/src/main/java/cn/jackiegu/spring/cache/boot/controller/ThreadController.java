package cn.jackiegu.spring.cache.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 线程接口
 *
 * @author JackieGu
 * @date 2023/2/20
 */
@RestController
@RequestMapping("thread")
public class ThreadController {

    private List<String> content = new ArrayList<>();

    private boolean threadFlag = false;

    private Thread thread;

    @GetMapping("create")
    public String create() {
        thread = new Thread("mine-thread") {
            @Override
            public void run() {
                while (true) {
                    content.add(UUID.randomUUID().toString());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        return "create ok\n";
    }

    @GetMapping("start")
    public String start() {
        if (!threadFlag) {
            thread.start();
        } else {
            threadFlag = false;
        }
        return "start ok\n";
    }

    @GetMapping("stop")
    public String stop() {
        threadFlag = true;
        return "stop ok\n";
    }
}

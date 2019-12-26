package com.github.fanzezhen.template.service.thread;

import com.github.fanzezhen.template.service.BaseService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class LogThread<S extends BaseService<O>, O> implements Runnable {

    S service;
    O logObject;
    String[] logMessages = {};

    public LogThread(S service, O logObject) {
        this.service = service;
        this.logObject = logObject;
    }

    @Override
    public void run() {
        for (String logMessage : logMessages) {
            log.info(logMessage);
        }
        service.save(logObject);
    }
}

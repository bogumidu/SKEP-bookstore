package com.bsd.skep;

import com.bsd.skep.service.LuceneService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class LuceneRunner  {

    private final LuceneService luceneService;

    public LuceneRunner(LuceneService luceneService) {
        this.luceneService = luceneService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void run() throws Exception {
        luceneService.createIndex();
    }
}

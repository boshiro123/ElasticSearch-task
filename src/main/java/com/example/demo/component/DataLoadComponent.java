package com.example.demo.component;

import com.example.demo.services.ESService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DataLoadComponent {

    private final ESService esService;

    @Scheduled(fixedRate = 600000)
    public void loadDataAndIndex() throws IOException {
        esService.createAnIndex();
        esService.indexingDB();
    }
}

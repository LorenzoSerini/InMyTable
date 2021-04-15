package com.inMyTable.view.spring;

import com.inMyTable.view.View;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringView implements View {

    @Override
    public void start() {
        SpringApplication.run(SpringView.class);
    }
}

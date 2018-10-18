package com.example.demo.repository.init;

import com.example.demo.repository.IRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(1)
public class RepositoryInitializer implements ApplicationRunner {

    private final List<IRepository> repositories;

    @Autowired
    public RepositoryInitializer(List<IRepository> repositories) {
        this.repositories = repositories;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        repositories.forEach(IRepository::initRepository);
    }
}

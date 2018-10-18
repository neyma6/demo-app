package com.example.demo.repository.init;

import com.example.demo.repository.IGraph;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Order(2)
public class GraphInitializer implements ApplicationRunner {

    private final List<IGraph> graphs;

    public GraphInitializer(List<IGraph> graphs) {
        this.graphs = graphs;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        graphs.forEach(IGraph::initGraph);
    }
}

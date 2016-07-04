package io.yawp.repository.query;

import io.yawp.repository.annotations.Endpoint;

@Endpoint
public class MyModel {

    private String name;

    public MyModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

package com.qcaldwell.user.controller;

import org.testcontainers.containers.PostgreSQLContainer;

public class DocumentPostgresContainer extends PostgreSQLContainer <DocumentPostgresContainer> {
    private static final String IMAGE_VERSION = "postgres:11.1";
    private static DocumentPostgresContainer container;

    private DocumentPostgresContainer() {
        super(IMAGE_VERSION);
    }

    public static DocumentPostgresContainer getInstance() {
        if (container == null) {
            container = new DocumentPostgresContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {    //do nothing, JVM handles shut down}
    }
}

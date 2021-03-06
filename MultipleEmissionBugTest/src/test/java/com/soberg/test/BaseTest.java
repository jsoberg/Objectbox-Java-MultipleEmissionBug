package com.soberg.test;

import org.junit.After;
import org.junit.Before;

import java.io.File;

import io.objectbox.BoxStore;
import io.objectbox.DebugFlags;

public class BaseTest {
    private static final File TEST_DIRECTORY = new File("objectbox-example/test-db");
    protected BoxStore store;

    @Before
    public void setUp() throws Exception {
        // delete database files before each test to start with a clean database
        BoxStore.deleteAllFiles(TEST_DIRECTORY);
        store = MyObjectBox.builder()
                // add directory flag to change where ObjectBox puts its database files
                .directory(TEST_DIRECTORY)
                // optional: add debug flags for more detailed ObjectBox log output
                .debugFlags(DebugFlags.LOG_QUERIES | DebugFlags.LOG_QUERY_PARAMETERS)
                .build();

        System.out.println("BoxStore created");
    }

    @After
    public void tearDown() throws Exception {
        if (store != null) {
            store.close();
            store = null;
            System.out.println("BoxStore closed");
        }
        boolean wasDeleted = BoxStore.deleteAllFiles(TEST_DIRECTORY);
        System.out.println("BoxStore deletion: " + wasDeleted);
    }
}

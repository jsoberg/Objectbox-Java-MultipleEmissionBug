package com.soberg.test;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.rx.RxQuery;
import io.reactivex.observers.TestObserver;

public class ExpectedSingleEmissionTest extends BaseTest {
    private Box<TestEntity> box;

    /** Submitting an empty runnable to the store's thread pool and waiting for its completion will
     * cause expected output from our tests (single emission), "fixing" the bug observed in
     * {@link UnexpectedMultipleEmissionBugTest}. */
    @Before
    public void testSetup() throws Exception {
        box = store.boxFor(TestEntity.class);

        TestEntity testEntity = new TestEntity();
        testEntity.anotherField = null;
        box.put(testEntity);

        store.internalThreadPool().submit(() -> {
            // Do nothing.
        }).get();
    }

    @Test
    public void test1() {
        TestObserver<List<TestEntity>> test = RxQuery.observable(box.query().build()).test();
        test.awaitCount(1);
        test.assertValueCount(1);
    }

    @Test
    public void test2() {
        TestObserver<List<TestEntity>> test = RxQuery.observable(box.query().build()).test();
        test.awaitCount(1);
        test.assertValueCount(1);
    }
}

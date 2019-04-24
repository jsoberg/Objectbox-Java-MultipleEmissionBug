package com.soberg.test;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.rx.RxQuery;
import io.reactivex.observers.TestObserver;

/** When run as a suite, {@link #test2()} will fail in its assertion. It will have observed 2 emissions,
 * even though only a single put has occurred and therefore it should only be observing a single emission.*/
public class UnexpectedMultipleEmissionBugTest extends BaseTest {

    private Box<TestEntity> box;

    @Before
    public void testSetup() {
        box = store.boxFor(TestEntity.class);

        TestEntity testEntity = new TestEntity();
        testEntity.anotherField = null;
        box.put(testEntity);
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

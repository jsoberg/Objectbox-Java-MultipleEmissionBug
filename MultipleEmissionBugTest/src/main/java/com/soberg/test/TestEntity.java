package com.soberg.test;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class TestEntity {
    @Id
    public long id;

    public Long anotherField;
}

package io.yawp.repository.query.condition;

public abstract class SafeBaseCondition<T> {

    public abstract BaseCondition toUnsafe();
    public abstract <T> SafeBaseCondition<T> not();
}

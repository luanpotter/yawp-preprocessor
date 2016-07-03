package io.yawp.repository.query.condition;

import io.yawp.repository.query.FieldsRef;
import io.yawp.repository.query.Op;

import java.util.Arrays;

public abstract class SafeCondition<T> {

    private SafeCondition() {
    }

    public static <T> SafeBaseCondition<T> c(FieldsRef<T> field, Op operator, Object comparison) {
        return new SafeSimpleCondition(field, operator.toWhereOperator(), comparison);
    }

    public static <T> SafeBaseCondition<T> and(SafeBaseCondition<T>... conditions) {
        if (conditions.length == 1) {
            return conditions[0];
        }
        return new SafeJoinedCondition(LogicalOperator.AND, Arrays.asList(conditions));
    }

    public static <T> SafeBaseCondition<T> or(SafeBaseCondition<T>... conditions) {
        if (conditions.length == 1) {
            return conditions[0];
        }
        return new SafeJoinedCondition(LogicalOperator.OR, Arrays.asList(conditions));
    }

    public static <T> SafeBaseCondition<T> not(SafeBaseCondition<T> c) {
        return c.not();
    }
}
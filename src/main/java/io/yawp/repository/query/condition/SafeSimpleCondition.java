package io.yawp.repository.query.condition;

import io.yawp.repository.query.FieldsRef;

public class SafeSimpleCondition<T> extends SafeBaseCondition<T> {

    private FieldsRef<T> field;
    private WhereOperator whereOperator;
    private Object value;

    public SafeSimpleCondition(FieldsRef<T> field, WhereOperator whereOperator, Object value) {
        this.field = field;
        this.whereOperator = whereOperator;
        this.value = value;
    }

    @Override
    public BaseCondition toUnsafe() {
        return new SimpleCondition(field.originalName(), whereOperator, value);
    }

    @Override
    public <T1> SafeBaseCondition<T1> not() {
        return new SafeSimpleCondition(field, whereOperator.reverse(), value);
    }
}

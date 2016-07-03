package io.yawp.repository.query.condition;

import java.util.List;
import java.util.stream.Collectors;

public class SafeJoinedCondition<T> extends SafeBaseCondition<T> {

    private LogicalOperator logicalOperator;

    private List<SafeBaseCondition<T>> conditions;

    public SafeJoinedCondition(LogicalOperator logicalOperator, List<SafeBaseCondition<T>> conditions) {
        this.logicalOperator = logicalOperator;
        this.conditions = conditions;
    }

    @Override
    public BaseCondition toUnsafe() {
        return new JoinedCondition(logicalOperator, conditions.stream().toArray(size -> new BaseCondition[size]));
    }

    @Override
    public <T1> SafeBaseCondition<T1> not() {
        return new SafeJoinedCondition(logicalOperator.not(), conditions.stream().map(c -> c.not()).collect(Collectors.toList()));
    }
}

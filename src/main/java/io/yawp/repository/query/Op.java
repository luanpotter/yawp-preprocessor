package io.yawp.repository.query;

import io.yawp.repository.query.condition.WhereOperator;

import static io.yawp.repository.query.condition.WhereOperator.*;

public enum Op {
    EQ("=", EQUAL),
    GT(">", GREATER_THAN),
    GTEQ(">=", GREATER_THAN_OR_EQUAL),
    LT("<", LESS_THAN),
    LTEQ("<=", LESS_THAN_OR_EQUAL),
    NEQ("!=", NOT_EQUAL),
    IN("in", WhereOperator.IN);

    private String value;
    private WhereOperator whereOp;

    Op(String value, WhereOperator whereOp) {
        this.value = value;
        this.whereOp = whereOp;
    }

    public String getValue() {
        return value;
    }

    public WhereOperator toWhereOperator() {
        return whereOp;
    }
}

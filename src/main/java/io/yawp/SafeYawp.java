package io.yawp;

import io.yawp.repository.IdRef;
import io.yawp.repository.query.SafeQueryBuilder;

import static io.yawp.repository.Yawp.yawp;

public class SafeYawp {

    private SafeYawp() {}

    public static <T> SafeQueryBuilder<T> query(Class<T> c) {
        return new SafeQueryBuilder<>(yawp.query(c));
    }

    public static <T> T save(T t) {
        return yawp.save(t);
    }

    public static void destroy(IdRef<?> id) {
        yawp.destroy(id);
    }
}

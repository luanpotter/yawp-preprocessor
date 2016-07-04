package io.yawp.repository.query;

import org.junit.Assert;
import org.junit.Test;

import static io.yawp.repository.Yawp.yawp;

public class SimpleSafeQueryTest {

    public <T> SafeQueryBuilder<T> yawp(Class<T> c) {
        return new SafeQueryBuilder<>(yawp.query(c));
    }

    // TODO @Test
    public void testWhere() {
        yawp.save(new MyModel("t1"));
        yawp.save(new MyModel("t2"));
        yawp.save(new MyModel("t3"));

        MyModel t2 = yawp(MyModel.class).where(() -> "name", Op.EQ, "t2").only();

        Assert.assertEquals("t2", t2.getName());
    }
}

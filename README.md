# yawp-preprocessor

This is an annotation preprocessor that allows for a better and type-safer approach for queries.

Instead of using unsafe and dangerous strings to represent field names, just add this dependency and enums will be generated for all your `@Endpoint` models with constants for each field.

This will allow you to use a much better API:

```java
    List<Person> people = query(Person.class).where(PersonFields.NAME, Op.EQ, "Luan").list();
```

The where method only accepts the enum for the specified class. How does it work? The query method is static to the new `SafeYawp` class, that replaces the old `Yawp` one.

Every method then becomes type safe using this feature. Of course the old one keeps being avaliable to use, but you should then avoid it. Importing `io.yawp.SafeYawp.*` statically will help you use the new approach.

A complete example:

```java
	package yawpapp.models.person;

	import java.util.List;

	import io.yawp.commons.http.annotation.GET;
	import io.yawp.repository.actions.Action;
	import io.yawp.repository.query.Op;

	import static io.yawp.SafeYawp.*;

	public class PersonAction extends Action<Person> {

	    @GET("dummy")
	    public List<Person> dummy() {
	        return query(Person.class).where(PersonFields.NAME, Op.EQ, "Luan").list();
	    }

	}
```
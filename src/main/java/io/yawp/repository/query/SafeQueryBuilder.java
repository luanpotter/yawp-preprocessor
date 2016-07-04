package io.yawp.repository.query;

import io.yawp.repository.IdRef;
import io.yawp.repository.Repository;
import io.yawp.repository.models.ObjectModel;
import io.yawp.repository.query.condition.*;

import java.util.List;

public class SafeQueryBuilder<T> {

    private QueryBuilder<T> q;

    public SafeQueryBuilder(QueryBuilder<T> q) {
        this.q = q;
    }

    private SafeQueryBuilder(Class<T> clazz, Repository r) {
        this.q = QueryBuilder.q(clazz, r);
    }

    public static <T> SafeQueryBuilder<T> q(Class<T> clazz, Repository r) {
        return new SafeQueryBuilder<T>(clazz, r);
    }

    public SafeQueryBuilder<T> and(FieldsRef<T> field, Op op, Object value) {
        return where(field, op, value);
    }

    public SafeQueryBuilder<T> where(FieldsRef<T> field, Op operator, Object value) {
        return where(SafeCondition.c(field, operator, value));
    }

    public SafeQueryBuilder<T> where(SafeBaseCondition<T> c) {
        q.where(c.toUnsafe());
        return this;
    }

    public SafeQueryBuilder<T> and(SafeBaseCondition<T> c) {
        return where(c);
    }

    public SafeQueryBuilder<T> from(IdRef<?> parentId) {
        q.from(parentId);
        return this;
    }

    public SafeQueryBuilder<T> order(String property) {
        q.order(property);
        return this;
    }

    public SafeQueryBuilder<T> order(String property, String direction) {
        q.order(property, direction);
        return this;
    }

    public SafeQueryBuilder<T> sort(String property) {
        q.sort(property, null);
        return this;
    }

    public SafeQueryBuilder<T> sort(String property, String direction) {
        q.sort(null, property, direction);
        return this;
    }

    public SafeQueryBuilder<T> sort(String entity, String property, String direction) {
        q.sort(entity, property, direction);
        return this;
    }

    public SafeQueryBuilder<T> limit(int limit) {
        q.limit(limit);
        return this;
    }

    public SafeQueryBuilder<T> cursor(String cursor) {
        q.cursor(cursor);
        return this;
    }

    public IdRef<?> getParentId() {
        return q.getParentId();
    }

    public String getCursor() {
        return q.getCursor();
    }

    public void setCursor(String cursor) {
        q.setCursor(cursor);
    }

    public SafeQueryBuilder<T> options(QueryOptions options) {
        q.options(options);
        return this;
    }

    public Integer getLimit() {
        return q.getLimit();
    }

    public List<QueryOrder> getPreOrders() {
        return q.getPreOrders();
    }

    public BaseCondition getCondition() {
        return q.getCondition();
    }

    public Repository getRepository() {
        return q.getRepository();
    }

    public ObjectModel getModel() {
        return q.getModel();
    }

    public List<T> executeQueryList() {
        return q.executeQueryList();
    }

    public List<T> list() {
        return q.list();
    }

    public T first() {
        return q.first();
    }

    public T only() throws NoResultException, MoreThanOneResultException {
        return q.only();
    }

    // TODO whats this????
    public void sortList(List<?> objects) {
        q.sortList(objects);
    }

    public boolean hasPreOrder() {
        return q.hasPreOrder();
    }

    private boolean hasPostOrder() {
        return q.hasPreOrder();
    }

    protected Class<T> getClazz() {
        return q.getClazz();
    }

    public QueryBuilder<T> whereById(String operator, IdRef<T> id) {
        return q.whereById(operator, id);
    }

    public T fetch(IdRef<T> idRef) {
        return q.fetch(idRef);
    }

    public T fetch(Long id) {
        return q.fetch(id);
    }

    public T fetch(String name) {
        return q.fetch(name);
    }

    public List<IdRef<T>> ids() {
        return q.ids();
    }

    public IdRef<T> onlyId() throws NoResultException, MoreThanOneResultException {
        return q.onlyId();
    }
}

package net.ProcessMining.base.dao;

import java.util.List;

import net.ProcessMining.base.dao.util.CommonConditionQuery;
import net.ProcessMining.base.dao.util.CommonOrderBy;
import net.ProcessMining.base.dao.util.ConditionQuery;
import net.ProcessMining.base.dao.util.OrderBy;
/*
 * 主要定义了一些常用的数据访问接口方法，包括实体对象的增加、修改、删除、列表查询、条件查询等。
 *各具体业务模块数据访问接口通过继承此基类后，不再需要编写常用的数据访问接口方法。
 * */ 
public interface BaseDao<E extends java.io.Serializable, PK extends java.io.Serializable> {

    public PK save(E entity);
    public void saveOrUpdate(E entity);
    public void deleteByPK(PK pk);
    public void delete(E entity);
    public void update(E entity);
    public void evict(E entity);
    public E getByPK(PK pk);
    public E loadByPK(PK pk);
    public boolean exists(PK pk);
    
    public void evict();
    public List<E> list();
    public List<E> list(boolean cacheable);
    public <T> T count();
    public <T> T count(boolean cacheable);
    public void flush();
    public void clear();
    
    public int executeBulk(final String hql,CommonConditionQuery query);
    public int executeNativeBulk(final String nativeSQL, CommonConditionQuery query);
    
    public <T> T count (ConditionQuery query);
    public <T> T count (ConditionQuery query,boolean cacheable);
    public List<E> list(ConditionQuery query, OrderBy orderBy);
    public List<E> list(ConditionQuery query, OrderBy orderBy,boolean cacheable);
    public List<E> list(ConditionQuery query, OrderBy orderBy,
    		final int pageNum, final int pageSize);
    public List<E> list(ConditionQuery query, OrderBy orderBy,
    		final int pageNum, final int pageSize,boolean cacheable);
    
    
    public <T> T count(CommonConditionQuery query);
    public <T> T count(CommonConditionQuery query,boolean cacheable);
    public <T> T count(String hql,CommonConditionQuery query);
    public <T> T count(String hql,CommonConditionQuery query,boolean cacheable);
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy);
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy,boolean cacheable);
    public List<E> list(String hql,CommonConditionQuery query,CommonOrderBy orderBy);
    public List<E> list(String hql,CommonConditionQuery query,CommonOrderBy orderBy,boolean cacheable);
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize);
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize,boolean cacheable);
    public List<E> list(String hql,CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize);
    public List<E> list(String hql,CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize,boolean cacheable);
    
    public <T> List<T> find(String hql,CommonConditionQuery query,CommonOrderBy orderBy);
    public <T> List<T> find(String hql,CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize);
    
    
    public <T> T nativeCount(String sql,CommonConditionQuery query);
    public <T> List<T> nativeList(String sql,CommonConditionQuery query,CommonOrderBy orderBy);
    public <T> List<T> nativeList(String sql,CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize);
    

}

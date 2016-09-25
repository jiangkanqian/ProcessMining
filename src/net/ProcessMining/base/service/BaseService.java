package net.ProcessMining.base.service;

import java.util.List;

import net.ProcessMining.base.dao.util.CommonConditionQuery;
import net.ProcessMining.base.dao.util.CommonOrderBy;
import net.ProcessMining.base.dao.util.ConditionQuery;
import net.ProcessMining.base.dao.util.OrderBy;

public interface BaseService <E extends java.io.Serializable, PK extends java.io.Serializable>{
	
	public PK save(E entity);
    public void saveOrUpdate(E entity);
    public void deleteByPK(PK pk);
    public void delete(E entity);
    public void update(E entity);
    public E getByPK(PK pk);
    public E loadByPK(PK pk);
    public boolean exists(PK pk);
    public List<E> list();
    public List<E> list(boolean cacheable);
    public <T> T count();
    public <T> T count(boolean cacheable);

    
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
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy);
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy,boolean cacheable);
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize);
    public List<E> list(CommonConditionQuery query,CommonOrderBy orderBy,
    		final int pageNum, final int pageSize,boolean cacheable);

}

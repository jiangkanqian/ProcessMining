package net.ProcessMining.logManagement.dao.impl;

import net.ProcessMining.base.dao.impl.BaseDaoImpl;
import net.ProcessMining.logManagement.dao.LogDao;
import net.ProcessMining.logManagement.entity.Log;

import org.springframework.stereotype.Repository;

@Repository
public class LogDaoImpl extends BaseDaoImpl<Log,String> implements LogDao{

}

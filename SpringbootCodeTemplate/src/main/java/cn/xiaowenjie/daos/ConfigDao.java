package cn.xiaowenjie.daos;

import java.util.Collection;

import cn.xiaowenjie.beans.Config;

public interface ConfigDao {

	Collection<Config> getAll();

	long add(Config config);

	boolean delete(long id);

}

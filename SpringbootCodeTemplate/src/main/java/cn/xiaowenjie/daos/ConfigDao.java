package cn.xiaowenjie.daos;

import org.springframework.data.repository.PagingAndSortingRepository;

import cn.xiaowenjie.beans.Config;

public interface ConfigDao extends PagingAndSortingRepository<Config, Long> {
	Config findByName(String name);
}
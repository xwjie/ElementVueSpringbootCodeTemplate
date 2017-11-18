package cn.xiaowenjie.services;

import static cn.xiaowenjie.common.utils.CheckUtil.check;
import static cn.xiaowenjie.common.utils.CheckUtil.notEmpty;
import static cn.xiaowenjie.common.utils.CheckUtil.notNull;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.daos.ConfigDao;

/**
 * 配置业务处理类
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@Service
public class ConfigService {

	private static final Logger logger = LoggerFactory.getLogger(ConfigService.class);

	@Autowired
	ConfigDao dao;

	public Collection<Config> getAll() {
		// 校验通过后打印重要的日志
		logger.info("getAll start ...");

		List<Config> data = Lists.newArrayList(dao.findAll());

		logger.info("getAll end, data size:" + data.size());

		return data;
	}

	public long add(Config config) {
		// 参数校验
		notNull(config, "param.is.null");
		notEmpty(config.getName(), "name.is.null");
		notEmpty(config.getValue(), "value.is.null");

		// 校验通过后打印重要的日志
		logger.info("add config:" + config);

		// 校验重复
		check(null == dao.findByName(config.getName()), "name.repeat");

		config = dao.save(config);

		// 修改操作需要打印操作结果
		logger.info("add config success, id:" + config.getId());

		return config.getId();
	}

	public boolean delete(long id) {
		// 参数校验
		check(id > 0L, "id.error", id);

		dao.delete(id);

		// 修改操作需要打印操作结果
		logger.info("delete config success, id:" + id);

		return true;
	}
}

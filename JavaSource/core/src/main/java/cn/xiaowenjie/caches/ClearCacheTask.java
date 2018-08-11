package cn.xiaowenjie.caches;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时清空cache
 * 
 * @author 肖文杰  https://github.com/xwjie
 */
@Component
@EnableScheduling
public class ClearCacheTask {

	/**
	 * 定时清空缓存
	 */
	@Scheduled(fixedRate = 60 * 60 * 1000L)
	@CacheEvict(value = { CacheNames.CONFIG }, allEntries = true)
	public void clearCaches() {
		System.out.println("\n------------ clear caches ------------\n");
	}

}

package cn.xiaowenjie.common.beans;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Data;

/**
 * 分页响应对象
 * 
 * @author 肖文杰 https://github.com/xwjie/
 */
@Data
public class PageResp<T> {
	private List<T> rows;

	private int page;

	private int pagesize;

	private long total;

	public PageResp(Page<T> page) {
		this.rows = page.getContent();
		this.page = page.getNumber() + 1;
		this.pagesize = page.getSize();
		this.total = page.getTotalElements();
	}

}

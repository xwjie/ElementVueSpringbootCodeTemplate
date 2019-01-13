package cn.xiaowenjie.common.beans;

import com.alibaba.fastjson.annotation.JSONType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

import lombok.Data;

/**
 * 分页请求参数
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@Data
@JSONType(ignores = "pageable") // 不加fastjson toJson的时候 报 StackOverflowError
public class PageReq {

	private int page = 1;

	private int pagesize = 10;

	private String sortfield = "";

	private String sort = "";

	private String keyword = "";

	public PageReq() {
		super();
	}

	public PageReq(int page, int pagesize, String sortfield, String sort,
			String keyword) {
		super();
		this.page = page;
		this.pagesize = pagesize;
		this.sortfield = sortfield;
		this.sort = sort;
		this.keyword = keyword;
	}

	public PageReq getPageable() {
		return new PageReq(page, pagesize, sortfield, sort, keyword);
	}

	public Pageable toPageable() {
		// pageable里面是从第0页开始的。
		Pageable pageable = null;

		if (StringUtils.isEmpty(sortfield)) {
			pageable = new PageRequest(page - 1, pagesize);
		}
		else {
			pageable = new PageRequest(page - 1, pagesize,
					sort.toLowerCase().startsWith("desc") ? Direction.DESC
							: Direction.ASC,
					sortfield);
		}

		return pageable;
	}
}

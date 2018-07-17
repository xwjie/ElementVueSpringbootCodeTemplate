package cn.xiaowenjie.controllers;

import cn.xiaowenjie.beans.UploadRecord;
import cn.xiaowenjie.common.beans.PageReq;
import cn.xiaowenjie.common.beans.PageResp;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.services.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 *  上传日志并分析成图表
 *
 *
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@RequestMapping("/upload")
@RestController
@CrossOrigin
public class UploadController {

	@Autowired
    UploadFileService uploadFileService;

	@PostMapping("/")
	public  ResultBean<UploadRecord> upload(@RequestBody() MultipartFile file){
		return new ResultBean<UploadRecord>(uploadFileService.upload(file));
	}

	@GetMapping(value = "/list")
	public ResultBean<PageResp<UploadRecord>> list(PageReq param) {
		return new ResultBean<>(uploadFileService.listPage(param.toPageable(), param.getKeyword()));
	}

}

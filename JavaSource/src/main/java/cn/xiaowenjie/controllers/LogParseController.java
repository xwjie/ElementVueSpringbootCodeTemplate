package cn.xiaowenjie.controllers;

import cn.xiaowenjie.beans.Config;
import cn.xiaowenjie.beans.UploadRecord;
import cn.xiaowenjie.common.beans.PageReq;
import cn.xiaowenjie.common.beans.PageResp;
import cn.xiaowenjie.common.beans.ResultBean;
import cn.xiaowenjie.services.ConfigService;
import cn.xiaowenjie.services.LogParseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.List;

/**
 *  上传日志并分析成图表
 *
 *
 * 
 * @author 肖文杰 https://github.com/xwjie/
 *
 */
@RequestMapping("/logparse")
@RestController
@CrossOrigin
public class LogParseController {

	@Autowired
	LogParseService logParseService;

	@PostMapping("/upload")
	public  ResultBean<UploadRecord> upload(@RequestBody() MultipartFile file){
		return new ResultBean<UploadRecord>(logParseService.upload(file));
	}

}

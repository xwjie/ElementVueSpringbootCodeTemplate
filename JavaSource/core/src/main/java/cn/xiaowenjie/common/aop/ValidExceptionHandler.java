package cn.xiaowenjie.common.aop;

import cn.xiaowenjie.common.beans.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 处理hibernate valid异常
 *
 * @author 肖文杰
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class ValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultBean<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {

        // 异常信息
        String msg = extractErrorMsg(e.getBindingResult());

        // 返回对象
        ResultBean<Object> resultBean = new ResultBean<>();

        resultBean.setCode(ResultBean.FAIL);
        resultBean.setMsg(msg);

        return resultBean;
    }

    /**
     * 异常信息
     *
     * @param result
     * @return
     */
    private String extractErrorMsg(BindingResult result) {
        List<FieldError> errors = result.getFieldErrors();

        return errors.stream().map(e -> e.getField()+ ":" + e.getDefaultMessage())
                .reduce((s1, s2) -> s1 + " ; " +s2).orElseGet( ()->"参数非法");
    }
}
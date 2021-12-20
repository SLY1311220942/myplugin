package com.sly.myplugin.validate.handler;

import com.sly.myplugin.base.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 参数验证异常处理器
 *
 * @author SLY
 * @date 2021/11/25
 */
@ResponseBody
@ControllerAdvice
public class ValidateExceptionHandler {

    /**
     * JSR303异常
     *
     * @param e       异常
     * @param request 请求
     * @return {@link Result}<?>
     * @author SLY
     * @date 2021/11/25
     */
    @ExceptionHandler({BindException.class})
    public Result<?> handleMethodArgumentNotValidException(BindException e, HttpServletRequest request) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        ObjectError err = errors.get(0);
        String msg = err.getDefaultMessage();
        return Result.failed(msg);
    }
}

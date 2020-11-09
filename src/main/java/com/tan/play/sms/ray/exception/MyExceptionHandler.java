package com.tan.play.sms.ray.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/** @Author 谭婧杰 */
@ControllerAdvice
@Slf4j
public class MyExceptionHandler {

  @ExceptionHandler(value = Exception.class)
  public ModelAndView exceptionHandler(Exception e) {
    log.error("系统异常！原因是:" + e);
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("error/500");
    return modelAndView;
  }
}

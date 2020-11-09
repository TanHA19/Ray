package com.tan.play.sms.ray.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** @Author tan */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
  private Integer code;

  private String message;

  private T data;
}

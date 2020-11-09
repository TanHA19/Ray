package com.tan.play.sms.ray;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author 谭婧杰
 */
@SpringBootApplication
@MapperScan(basePackages = "com.tan.play.sms.ray.mapper")
public class RayApplication {

  public static void main(String[] args) {
    SpringApplication.run(RayApplication.class, args);
  }
}

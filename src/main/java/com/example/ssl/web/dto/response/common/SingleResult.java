package com.example.ssl.web.dto.response.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SingleResult<T> extends CommonResult {
  private T data;
}
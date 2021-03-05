package com.open.capacity.api.config;

import com.alibaba.fastjson.JSONObject;
import com.open.capacity.common.exception.service.ServiceException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Slf4j
public class NotBreakerConfiguration {

    @Bean
    public FeignClientErrorDecoder errorDecoder(){
        return new FeignClientErrorDecoder();
    }

    public class FeignClientErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String s, Response response) {
            log.info("feign client response:", response);
            Exception exception = null;
            try {
                String json = Util.toString(response.body().asReader());
                JSONObject jsonObject = JSONObject.parseObject(json);
                return new ServiceException(jsonObject.getString("message"));
            } catch (IOException e) {
                log.error("feign.IOException", e);
                e.printStackTrace();
            }
            return exception;
        }
    }

}

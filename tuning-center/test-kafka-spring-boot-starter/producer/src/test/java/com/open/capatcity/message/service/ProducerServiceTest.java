package com.open.capatcity.message.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.open.capatcity.message.MessageApp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = { MessageApp.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 配置启动类
public class ProducerServiceTest {

	@Resource
	private ProducerService producerService;

	@Test
	public void test() {

		IntStream.rangeClosed(1, 50000).forEach((i) -> {
			Map map = new HashMap<>();
			map.put("hello", "world") ;
			boolean flag = producerService.sendMsg(map);
			System.out.println(flag);
		});

	}

}

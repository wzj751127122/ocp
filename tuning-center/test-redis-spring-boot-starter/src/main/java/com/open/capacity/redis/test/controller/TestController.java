package com.open.capacity.redis.test.controller;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.map.HashedMap;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metric;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.open.capacity.redis.util.RedisUtil;

@RestController
public class TestController {

	@Autowired
	private RedisUtil redisUtil ;
	@Autowired
	private StringRedisTemplate stringRedisTemplate ;
	@Autowired
	private RedissonClient  redissonClient ;
	
	@GetMapping("/test")
	public String opt(){
		System.out.println(redisUtil.incr("hello", 1));
		stringRedisTemplate.opsForValue().set("2019-08-15", "hello") ;
		System.out.println(stringRedisTemplate.opsForValue().get("2019-08-15"));
		return "ok" ;
	}
	
	@GetMapping("/send")
	public String optMSG(){
		redisUtil.publishMsg("hello1", "topic1", "world") ;
		return "ok" ;
	}
	
	@GetMapping("/say")
	public String getMsg(){
		System.out.println(redisUtil.getMsg("hello1", "topic1") );
		new Runnable() {
			@Override
			public void run() {
				while(true){
					// TODO Auto-generated method stub
					System.out.println(redisUtil.getMsg("hello1", "topic1") );
				}
			}
		}; 
		return "ok" ; 
	}
	
	@GetMapping("/testLock")
	public String lock(){
		RLock lock = redissonClient.getLock("anyLock");
		lock.lock();
		try {
				System.out.println(lock);
				Thread.sleep(TimeUnit.SECONDS.toMillis(30));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
		    lock.unlock();
		}
		return "ok" ;
	}
	 
	@GetMapping("/testGeo")
	public String testGeo(){
		 
		Map<Object,Point> maps = new HashedMap<>();
		maps.put("北京" ,new Point(116.405285 ,39.904989));
		maps.put("上海" ,new Point(120.1614 ,30.2936));
		maps.put("广西" ,new Point(108.3167 ,22.8167));
		maps.put("四川" ,new Point(104.0667 ,30.6667));
		
		redisUtil.addGeoPoint("中国", maps) ;
		return "ok";
	}
	
	
	@GetMapping("/testPoint")
	public Point testPoint(){
		return redisUtil.geoGetPoint("中国", "上海");
	}
	
	
	@GetMapping("/testDistance")
	public String testDistance(){
		Distance a = redisUtil.geoDistance("中国" ,"北京","上海") ;
		return a.getValue() +"";
	}
	
	@GetMapping("/geoRadiusByMember")
	public GeoResults geoRadiusByMember(){
		GeoResults aaa = redisUtil.geoRadiusByMember("中国", "北京",  10000D);
		System.out.println();
		return aaa ;
	}
	
	
	
}

package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.bean.Goods;
import com.example.demo.service.GoodsService;
import com.example.demo.util.PageUtils;

@Controller
public class GoodsController {
	@Resource
	private GoodsService goodsService;
	@Resource
	private RedisTemplate<String, Object> redisTemplate; 
	@RequestMapping("list")
	public String list(Model model,
			@RequestParam(defaultValue="1")int cpage,
			@RequestParam(defaultValue="")String orders) {
		BoundListOperations<String,Object> boundListOps = redisTemplate.boundListOps("goodsList");
		Map<String, Long> pageMap = PageUtils.pageUtils(boundListOps.size(), cpage);
		List<Object> range = boundListOps.range(pageMap.get("start"), pageMap.get("end"));
		model.addAttribute("goodsList", range);
		if (orders.length()>0) {
			BoundZSetOperations<String,Object> boundZSetOps = redisTemplate.boundZSetOps("sortGoods");
			for (Object object : range) {
				Goods goods = (Goods) object;
				boundZSetOps.add(goods, goods.getSaleCount()/goods.getGcount());
			}
			Set<Object> range2 = boundZSetOps.range(0, -1);
			model.addAttribute("goodsList", range2);
			
			
		}
		model.addAttribute("pages", pageMap.get("pages"));
		model.addAttribute("cpage", pageMap.get("cpage"));
		
		
		
		return "list";
	}
	@RequestMapping("toAdd")
	public String toAdd() {
		return "add";
	}
	@RequestMapping("addGoods")
	public String addGoods(Goods goods) {
		//RedisTemplate操作list集合的第一种方式
		ListOperations<String, Object> opsForList = redisTemplate.opsForList();

		Long push = opsForList.leftPush("goodsList", goods);
		System.out.println(push);
		return "add";
	}
	
}

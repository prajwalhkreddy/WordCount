package com.prajwal.wc.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.prajwal.wc.service.WordCountService;

@Controller
public class WordCountController {

	@Autowired
	private WordCountService wcService;

	@RequestMapping(value = "/calculate", method = RequestMethod.GET)
	public @ResponseBody String getUser(HttpServletResponse response) {
		wcService.readFiles("txtfiles");
		return "Success";
	}
	
	@RequestMapping(value = "/get", method = RequestMethod.GET) 
	public @ResponseBody String getWorks(@RequestParam("word") String word, HttpServletResponse response) {
		String count=null;
		
		count=wcService.getCount(word.toLowerCase());
		return count;
	}

}

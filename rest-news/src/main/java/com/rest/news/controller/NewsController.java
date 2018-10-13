package com.rest.news.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rest.news.client.NewsClient;
import com.rest.news.domain.HeadlineAndSourceDto;

@RestController
@RequestMapping("/news")
public class NewsController {
	@Autowired
	NewsClient newsClient;
	
	@RequestMapping(method = RequestMethod.GET, value = "getEveryNews/{country}/{category}")
	public HeadlineAndSourceDto getTopHeadlines(@PathVariable String country,@PathVariable String category) {
		return new HeadlineAndSourceDto(country, category, newsClient.getTopHeadlinesFromApi(country, category));
	}
}

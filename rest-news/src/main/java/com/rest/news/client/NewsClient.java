package com.rest.news.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.rest.news.domain.TopHeadlineDto;

@Component
public class NewsClient {
	@Autowired
	RestTemplate restTemplate;
	
	@Value("${news.api.endpoint}")
	private String newsApiEndpoint;
	
	@Value("${news.api.key}")
	private String newsApiKey;
	
	public TopHeadlineDto querySearchTopHeadlines(String query) {
		URI url = UriComponentsBuilder.fromHttpUrl(newsApiEndpoint + "/top-headlines")
				.queryParam("apiKey", newsApiKey)
				.queryParam("q", query).build().encode().toUri();
		
		return restTemplate.getForObject(url, TopHeadlineDto.class);
	}
	
	public TopHeadlineDto getTopHeadlinesFromApi(String country, String category) {
		URI url = UriComponentsBuilder.fromHttpUrl(newsApiEndpoint + "/top-headlines")
				.queryParam("apiKey", newsApiKey)
				.queryParam("country", country)
				.queryParam("category", category).build().encode().toUri();
		
		return restTemplate.getForObject(url, TopHeadlineDto.class);
	}
}
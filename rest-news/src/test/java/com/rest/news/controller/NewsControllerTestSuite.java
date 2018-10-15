package com.rest.news.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.rest.news.domain.ArticleDto;
import com.rest.news.domain.HeadlineAndSourceDto;
import com.rest.news.domain.SourceDto;
import com.rest.news.domain.TopHeadlineDto;

@RunWith(SpringRunner.class)
@WebMvcTest(NewsController.class)
public class NewsControllerTestSuite {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private NewsController newsController;
	
	@Test
	public void shouldFetchHeadlinesAndSources() throws Exception {
		//Given
		SourceDto sourceDto = new SourceDto("Test source");
		ArticleDto articleDto = new ArticleDto(sourceDto, "Test author", "Test title", "Test description", "Test URL", "Test URL to image", "Test date");
		
		List<ArticleDto> articles = new ArrayList<>();
		articles.add(articleDto);
		
		TopHeadlineDto topHeadlineDto = new TopHeadlineDto(articles);
		HeadlineAndSourceDto headlineAndSourceDto = new HeadlineAndSourceDto("Test country", "Test technology", topHeadlineDto);
		
		when(newsController.getTopHeadlines(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(headlineAndSourceDto);
		
		//When&Then
		String mockArticlePath = "$.topHeadlineDto.articles[0]";
		
		mockMvc.perform(get("/news/{country}/{category}", "anyCountry", "anyTechnology").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.country", is("Test country")))
			.andExpect(jsonPath("$.category", is("Test technology")))
			.andExpect(jsonPath(mockArticlePath + ".source.name", is("Test source")))
			.andExpect(jsonPath(mockArticlePath + ".author", is("Test author")))
			.andExpect(jsonPath(mockArticlePath + ".title", is("Test title")))
			.andExpect(jsonPath(mockArticlePath + ".description", is("Test description")))
			.andExpect(jsonPath(mockArticlePath + ".url", is("Test URL")))
			.andExpect(jsonPath(mockArticlePath + ".urlToImage", is("Test URL to image")))
			.andExpect(jsonPath(mockArticlePath + ".publishedAt", is("Test date")));
	}
}

package com.rest.news.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
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
	
	private String mockArticlePath = "$.topHeadlineDto.articles[0]";
	
	@Test
	public void shouldFetchEmptyArticleListWCountryCategory() throws Exception {
		//Given
		HeadlineAndSourceDto headlineAndSourceDto = new HeadlineAndSourceDto("Wrong country", "Wrong category", new TopHeadlineDto(new ArrayList<>()));
		
		when(newsController.getTopHeadlines(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(headlineAndSourceDto);
		
		//When&Then
		mockMvc.perform(get("/news/{country}/{category}", "wrongCountry", "wrongCategory").contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.country", is("Wrong country")))
			.andExpect(jsonPath("$.category", is("Wrong category")))
			.andExpect(jsonPath("$.topHeadlineDto.articles", hasSize(0)));
	}
	
	@Test
	public void shouldFetchHeadlinesAndSourcesWCountryCategory() throws Exception {
		//Given
		SourceDto sourceDto = new SourceDto("Test source");
		ArticleDto articleDto = new ArticleDto(sourceDto, "Test author", "Test title", "Test description", "Test URL", "Test URL to image", "Test date");
		
		List<ArticleDto> articles = new ArrayList<>();
		articles.add(articleDto);
		
		TopHeadlineDto topHeadlineDto = new TopHeadlineDto(articles);
		HeadlineAndSourceDto headlineAndSourceDto = new HeadlineAndSourceDto("Test country", "Test technology", topHeadlineDto);
		
		when(newsController.getTopHeadlines(ArgumentMatchers.anyString(), ArgumentMatchers.anyString())).thenReturn(headlineAndSourceDto);
		
		//When&Then
		mockMvc.perform(get("/news/{country}/{category}", "existingCountry", "existingCategory").contentType(MediaType.APPLICATION_JSON))
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
	
	@Test
	public void shouldFetchEmptySearchedArticleList() throws Exception {
		//Given
		HeadlineAndSourceDto headlineAndSourceDto = new HeadlineAndSourceDto(new TopHeadlineDto(new ArrayList<>()));
		
		when(newsController.getTopHeadlinesByQuery(ArgumentMatchers.anyString())).thenReturn(headlineAndSourceDto);
		
		//When&Then
		mockMvc.perform(get("/news/search")
				.contentType(MediaType.APPLICATION_JSON)
				.param("query", "test"))
					.andExpect(status().isOk())
					.andExpect(jsonPath("$.topHeadlineDto.articles", hasSize(0)));
	}
	
	@Test
	public void shouldFetchSearchedHeadlinesAndSources() throws Exception {
		//Given
		SourceDto sourceDto = new SourceDto("Search source");
		ArticleDto articleDto = new ArticleDto(sourceDto, "Search author", "Search title", "Search description", "Search URL", "Search URL to image", "Search date");
		
		List<ArticleDto> articles = new ArrayList<>();
		articles.add(articleDto);
		
		TopHeadlineDto topHeadlineDto = new TopHeadlineDto(articles);
		HeadlineAndSourceDto headlineAndSourceDto = new HeadlineAndSourceDto(topHeadlineDto);
		
		when(newsController.getTopHeadlinesByQuery(ArgumentMatchers.anyString())).thenReturn(headlineAndSourceDto);
		
		//When&Then
		mockMvc.perform(get("/news/search")
				.contentType(MediaType.APPLICATION_JSON)
				.param("query", "test"))
					.andExpect(status().isOk())
					.andExpect(jsonPath(mockArticlePath + ".source.name", is("Search source")))
					.andExpect(jsonPath(mockArticlePath + ".author", is("Search author")))
					.andExpect(jsonPath(mockArticlePath + ".title", is("Search title")))
					.andExpect(jsonPath(mockArticlePath + ".description", is("Search description")))
					.andExpect(jsonPath(mockArticlePath + ".url", is("Search URL")))
					.andExpect(jsonPath(mockArticlePath + ".urlToImage", is("Search URL to image")))
					.andExpect(jsonPath(mockArticlePath + ".publishedAt", is("Search date")));
	}
}

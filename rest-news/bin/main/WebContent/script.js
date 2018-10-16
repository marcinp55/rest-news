$(document).ready(function() {
	var datatableRowTemplate = $('[data-article-template]').children()[0];
	var articlesContainer = $('[data-articles-container]');
	var apiRoot = 'http://localhost:8080/news/';
	
	$('#search_form').on('submit', handleSearchSubmit);
	$('#show_articles_button').on('click', handleShowArticles);
	
	function handleSearchSubmit(event) {
		event.preventDefault();
		var query = $('#search_query').val();
		var requestUrl = apiRoot + "search?query=" + query;
		
		$.ajax({
			url: requestUrl,
			method: 'GET',
			dataType: 'json',
			success: createArticleElements,
			error: function(errorThrown) {
				alert("Searching error.")
				console.log(errorThrown);
			}
		});
	}
	
	function handleShowArticles() {
		var selectedCountry = $('#country_select').val();
		var selectedCategory = $('#category_select').val();
		var requestUrl = apiRoot + selectedCountry + '/' + selectedCategory;
		
		$.ajax({
			url: requestUrl,
			method: 'GET',
			dataType: 'json',
			success: createArticleElements,
			error: function(errorThrown) {
				alert("Show articles error.")
				console.log(errorThrown);
			}
		});
	}
	
	function createArticleElements(data) {
		articlesContainer.empty();
		
		if (data.country === null) {
			$('[data-articles-country]').html("All");
		} else {
			$('[data-articles-country]').html(data.country);
		}
		
		if(data.category === null) {
			$('[data-articles-category]').html("All");
		} else {
			$('[data-articles-category]').html(data.category);
		}
		
		$.each(data.topHeadlineDto.articles, function() {
			var element = $(datatableRowTemplate).clone();
			
			if(this.title != null) {
				element.find('[data-article-title]').html(this.title);
			} else {
				element.find('[data-article-title]').html("No title provided.")
			}
			
			if(this.urlToImage != null) {
				element.find('[data-article-img]').attr('src', this.urlToImage);	
			} else {
				element.find('[data-article-img]').attr('alt', "Sorry. No image available.");
			}
			
			if(this.description != null) {
				element.find('[data-article-desc]').html(this.description);	
			} else {
				element.find('[data-article-desc]').html("No description provided.");
			}
			
			if(this.author != null) {
				element.find('[data-article-auth]').html(this.author);	
			} else {
				element.find('[data-article-auth]').html("No author provided.");
			}
			
			if(this.url != null) {
				element.find('[data-article-link]').attr('href', this.url);
			} else {
				element.find('[data-article-link]').html("No link provided.");
			}
			
			if(this.publishedAt != null) {
				element.find('[data-article-date]').html((this.publishedAt).substring(0, 10));	
			} else {
				element.find('[data-article-date]').html("No article date provided.");
			}
			
			if(this.source.name != null) {
				element.find('[data-article-src]').html(this.source.name);
			} else {
				element.find('[data-article-src]').html("No source provided.");
			}
			
			element.appendTo(articlesContainer);
		})
	}
});
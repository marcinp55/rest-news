$(document).ready(function() {
	var datatableRowTemplate = $('[data-article-template]').children()[0];
	var articlesContainer = $('[data-articles-container]');
	
	$('#my_custom_button').click(function() {
		$.ajax({
			url: "http://localhost:8080/news/us/technology"
		}).then(function(data) {
			articlesContainer.empty();
			
			$.each(data.topHeadlineDto.articles, function(entryIndex, entry) {
				var element = $(datatableRowTemplate).clone();
				
				element.find('[data-article-title]').html(this.title);
				element.find('[data-article-img]').attr('src', this.urlToImage);
				element.find('[data-article-desc]').html(this.description);
				element.find('[data-article-auth]').html(this.author);
				element.find('[data-article-link]').attr('href', this.url);
				element.find('[data-article-date]').html(this.publishedAt);
				element.find('[data-article-src]').html(this.source.name);
				element.appendTo(articlesContainer);
			});
		});
	});
	
	function createElement(data) {
		var element = $(datatableRowTemplate).clone();
		
		console.log(element);
		
		element.find('[data-article-title]').html(data.title);
		
		return element;
	}
});


/*
			$('#country_p').html(data.country);
			$('#category_p').html(data.category);
			$('#article_title').html(data.topHeadlineDto.articles[0].title);
			$('#article_img').attr('src', data.topHeadlineDto.articles[0].urlToImage);
			$('#article_desc').html(data.topHeadlineDto.articles[0].description);
			$('#article_auth').html(data.topHeadlineDto.articles[0].author);
			$('#article_link').attr('href', data.topHeadlineDto.articles[0].url);
			$('#article_date').html(data.topHeadlineDto.articles[0].publishedAt);
			$('#article_src').html(data.topHeadlineDto.articles[0].source.name);
*/
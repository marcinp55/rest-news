$(document).ready(function() {
	$('#my_custom_button').click(function() {
		$.ajax({
			url: "http://localhost:8080/news/us/technology"
		}).then(function(data) {
			$('#country_p').html(data.country);
			$('#category_p').html(data.category);
			$('#article_title').html(data.topHeadlineDto.articles[0].title);
			$('#article_img').attr('src', data.topHeadlineDto.articles[0].urlToImage);
			$('#article_desc').html(data.topHeadlineDto.articles[0].description);
			$('#article_auth').html(data.topHeadlineDto.articles[0].author);
			$('#article_link').attr('href', data.topHeadlineDto.articles[0].url);
			$('#article_date').html(data.topHeadlineDto.articles[0].publishedAt);
			$('#article_src').html(data.topHeadlineDto.articles[0].source.name);
		});
	});
});
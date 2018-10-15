$(document).ready(function() {
	var datatableRowTemplate = $('[data-article-template]').children()[0];
	var articlesContainer = $('[data-articles-container]');
	var apiRoot = 'http://localhost:8080/news/';
	
	$('#show_articles_button').click(function() {
		var selectedCountry = $('#country_select').val();
		var selectedCategory = $('#category_select').val();
		
		$.ajax({
			url: apiRoot + selectedCountry + '/' + selectedCategory
		}).then(function(data) {
			articlesContainer.empty();
			$('[data-articles-country]').html(data.country);
			$('[data-articles-category]').html(data.category);
			
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
					element.find('[data-article-date]').html(this.publishedAt);	
				} else {
					element.find('[data-article-date]').html("No article date provided.");
				}
				
				if(this.source.name != null) {
					element.find('[data-article-src]').html(this.source.name);
				} else {
					element.find('[data-article-src]').html("No source provided.");
				}
				
				element.appendTo(articlesContainer);
			});
		});
	});
});
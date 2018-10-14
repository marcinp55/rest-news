$(document).ready(function() {
	$('#my_custom_button').click(function() {
		$.ajax({
			url: "http://localhost:8080/news/us/business"
		}).then(function(data) {
			$('.my_custom_div').empty();
			$('.my_custom_div').append(data.country);
			$('.my_custom_div').append(data.category);
			$('.my_custom_div').append(data.topHeadlineDto.articles[0].title);
		});
	});
});
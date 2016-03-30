
$(document).ready(function() {
	var flag = false;
	$("#slide").click(function() {
		if (flag == true)

		{

			$("#slideTable").slideUp();
			$("#slide").text("Показать детализацию");
			flag = !flag;

		}

		else

		{

			$("#slideTable").slideDown();
			$("#slide").text("Скрыть детализацию");
			flag = !flag;

		}

	});

});

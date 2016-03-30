
$(document).ready(function() {
	var flag = true;
	$("#slide").click(function() {
		if (flag == false)

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

function addReply(img, nickname, hour, title, content){
  return "<div class=\"media\">"+
  	"<div class=\"media-img-wrap\">"+
  		"<div class=\"avatar avatar-sm\">"+
  			"<img th:src=\"@{/dist/img/avatar2.jpg}\" alt=\"user\" class=\"avatar-img rounded-circle\">"+
  		"</div>"+
  	"</div>"+
  	"<div class=\"media-body\">"+
  		"<div class=\"mb-10\">"+
  			"<span class=\"d-block mb-5\">"+
  				"<span class=\"font-weight-500 text-dark text-capitalize\">홍길동님</span>"+
  				"<div class=\"d-flex mb-10\">"+
  					"<span class=\"font-14 text-light mr-15\">1 hr</span>"+
  					"<a href=\"#\" class=\"font-14 text-light text-capitalize font-weight-500\">reply</a>"+
  				"</div>"+
  				"<span class=\"mt-10\" style=\"margin-bottom:10px !important;\"></span>"+
  			"</span>"+
  		"</div>"+
  	"</div>"+
  "</div>";
}

function postAPI(url, data, successCallback){
	  $.ajax({
	      type : 'POST',
	      url : url,
	      dataType : 'text',
	      data : data,
	      contentType : false,
	      processData: false,
	      success : function(result) {
	        successCallback(result);
	      },
	      error: function(request, status, error) {
	        console.log("Error post Api state : " + status);
	        console.log("Error post Api error : " + error);
	      }
	  }) // End Ajax Request
}

function getAPI(url, data, successCallback){
	  $.ajax({
	      type : 'GET',
	      url : url,
	      dataType : 'text',
	      data : data,
	      contentType : false,
	      processData: false,
	      success : function(result) {
	        successCallback(result);
	      },
	      error: function(request, status, error) {
	        console.log("Error post Api state : " + status);
	        console.log("Error post Api error : " + error);
	      }
	  }) // End Ajax Request
}

var apiApp = angular.module('apiApp', []);
var markers = [];
apiApp.controller('ApiCtrl', function($scope, $http) {
	if($scope.map === undefined) {
		var mapOptions = {
		        zoom: 2,
		        center: new google.maps.LatLng(37.775, -122.434),
		        mapTypeId: google.maps.MapTypeId.HYBRID
		}
		$scope.map = new google.maps.Map(document.getElementById('map'), mapOptions);

	}

	$scope.startTweet = 0;
	$scope.sizeOfEachCall = 200;
	
	//$scope.pointArray = new google.maps.MVCArray();
	//$scope.heatmap = new google.maps.visualization.HeatmapLayer({
       // data: $scope.pointArray,
       // map: $scope.map
   // });

	$scope.getTweets = function(term, startTweet) {
		//if($scope.pointArray.length > 0) {
			//$scope.heatmap.setMap(null);
			//$scope.pointArray = new google.maps.MVCArray();
			//$scope.heatmap = new google.maps.visualization.HeatmapLayer({
		        //data: $scope.pointArray,
		      //  map: $scope.map
		   // });
		//}
		DeleteMarkers();
		callApi(term, startTweet);
	}
	
	function callApi(term, startTweet) {
		$http.get("http://twirrmap555-env.us-west-2.elasticbeanstalk.com/rest/tweets/" + term + "/" + startTweet + "/" 
				+ $scope.sizeOfEachCall).
		success(function(data, status, headers, config) {
			if(data.length > 0) {
				getPoints(data);
				var newStart = startTweet + $scope.sizeOfEachCall;
				callApi(term, newStart);
			}
		});
	}
	
	function DeleteMarkers()
	{
	    for (var j=0;j< markers.length;j++)
	    {
	        markers[j].setMap(null);
	    }
	    markers = [];
	}
	
	function getPoints(objects) {
		var length = objects.length;
	
			for(i = 0; i < length; i++) {
                              var latLng= new google.maps.LatLng(objects[i].latitude, objects[i].longitude);
                                
                              if(objects[i].sentiment == "NEGATIVE")
                                {
                                 var marker = new google.maps.Marker({
                                 position: latLng,
                                 map: $scope.map
                                 });
				                markers.push(marker);
				                }
                              else
                                { 
                                  marker = new google.maps.Marker({
                                 position: latLng,
                                 icon: {
                                    path: google.maps.SymbolPath.CIRCLE,
                                    scale: 5
                                       },
                                 map: $scope.map
                                 });
                                 markers.push(marker);

 
                                 }
				//$scope.pointArray.push(new google.maps.LatLng(objects[i].latitude, objects[i].longitude));
			}
		
	}
})
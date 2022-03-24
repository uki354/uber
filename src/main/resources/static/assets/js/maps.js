
var input;
var placeListener;
var map;
var googleMap;
var destinationMaps;
var marker;         
var position;
var latLng;
var dId;   
function initialize() {
var destination;
destination = document.getElementById('destination');
destinationMaps = new google.maps.places.Autocomplete(destination);
destinationMaps.setFields(['geometry']);

destinationMaps.addListener('place_changed', onPlaceChanged);
input = document.getElementById('autocomplete');
placeListener = new google.maps.places.Autocomplete(input);
placeListener.setFields(['geometry']);
placeListener.addListener('place_changed', onPlaceChanged);
googleMap = new google.maps.Map(document.getElementById("map"), {
mapTypeControl: false,
center: { lat: 44.787197, lng: 20.457273},
zoom: 13,
styles: [],
});
marker = new google.maps.Marker();      

}   

function onPlaceChanged(){
var place = placeListener.getPlace();
var lat = place.geometry.location.lat();
var lng = place.geometry.location.lng();
$.ajax({
   url: 'http://localhost:8080/api/ride/driver?l=' + lat + ',' + lng,
   type: 'get',            
headers: {
Authorization: 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJsdWthMTIzIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6Imh0dHBzOi8vZXViZXItdGVzdC5oZXJva3VhcHAuY29tL2FwaS9sb2dpbiIsImV4cCI6MTY1NTIwMDk0MH0.mnCGKSFpoc_sGu2nKJkDjOPLaCKcmaAoMvdEkKc0uHqfTc-Movm66zWlS8uJxmDVNorws8fGEOBAVLogfam1vfPThVrChHLIk_jSiqAsEiYlxAFquWTjFbV-tIEqwMuokwTZzgEuSA5D8FTa8V1g8DE5j7-A5HZNoUH3pLwXpyTr3anNx4640ckMphXw4IZj2zrQG-7m_y8IemzxaTiNXjQP751NXr7s0wgWMbrBkc-_LUDLm-ENrdnY_jSGdXu4lutEfnYk_xCUvl3CYtRuSzs8sRtwID_Hh7uv8ek62Rzi69SBVeovq3wab_kHcGGgnryEFGHSg6kRMh_AXYdUWQ',   //If your header name has spaces or any other char not appropriate

           },
dataType: 'json',
success: function (data) {
console.log(data);
dId = data.id;       
latLng = {lat: parseFloat(data.currentLocation.latitude), lng: parseFloat(data.currentLocation.longitude)};        

marker.setTitle("Currently nearest Driver");
marker.setPosition(latLng);
marker.setMap(googleMap);  


}
});              


       
}

function saveRide(location, destination, driver){

$.ajax({
   url:"http://localhost:8080/api/ride/save?l=" + location.geometry.location.lat() + ',' + location.geometry.location.lng() +
   '&d=' + destination.lat + ',' + destination.lng + '&dId=' + driver,
   type: "GET",
   headers: {
      Authorization: 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJsdWthMTIzIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6Imh0dHBzOi8vZXViZXItdGVzdC5oZXJva3VhcHAuY29tL2FwaS9sb2dpbiIsImV4cCI6MTY1NTIwMDk0MH0.mnCGKSFpoc_sGu2nKJkDjOPLaCKcmaAoMvdEkKc0uHqfTc-Movm66zWlS8uJxmDVNorws8fGEOBAVLogfam1vfPThVrChHLIk_jSiqAsEiYlxAFquWTjFbV-tIEqwMuokwTZzgEuSA5D8FTa8V1g8DE5j7-A5HZNoUH3pLwXpyTr3anNx4640ckMphXw4IZj2zrQG-7m_y8IemzxaTiNXjQP751NXr7s0wgWMbrBkc-_LUDLm-ENrdnY_jSGdXu4lutEfnYk_xCUvl3CYtRuSzs8sRtwID_Hh7uv8ek62Rzi69SBVeovq3wab_kHcGGgnryEFGHSg6kRMh_AXYdUWQ',   //If your header name has spaces or any other char not appropriate
       //for object property name, use quoted notation shown in second
                 }
});
}




function submitRide(){
var place = placeListener.getPlace();        
var destination = destinationMaps.getPlace();
var location = JSON.stringify({"origin":{"latitude": place.geometry.location.lat(), "longitude": place.geometry.location.lng()},
           "destination": {"latitude": destination.geometry.location.lat(), "longitude": destination.geometry.location.lng() }});
var test = JSON.stringify({"name":"ime", "nesto":"drugo"});
var locationJson = {"origin":{"latitude":44.8125449,"longitude":20.46123}};
var destinationJson = {"destination":{"latitude":44.6619928,"longitude":20.9302332}};
var geoLocationJson = {"origin":{"latitude":latLng.lat,"longitude":latLng.lng},
                   "destination":{"latitude":place.geometry.location.lat(),"longitude":place.geometry.location.lng()}};
saveRide(place,latLng,dId);           
$.ajax({
   url:"http://localhost:8080/api/ride/direction",
   type: "post",
   data: JSON.stringify(geoLocationJson),
   contentType: "application/json",

   headers: {
Authorization: 'Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJsdWthMTIzIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlzcyI6Imh0dHBzOi8vZXViZXItdGVzdC5oZXJva3VhcHAuY29tL2FwaS9sb2dpbiIsImV4cCI6MTY1NTIwMDk0MH0.mnCGKSFpoc_sGu2nKJkDjOPLaCKcmaAoMvdEkKc0uHqfTc-Movm66zWlS8uJxmDVNorws8fGEOBAVLogfam1vfPThVrChHLIk_jSiqAsEiYlxAFquWTjFbV-tIEqwMuokwTZzgEuSA5D8FTa8V1g8DE5j7-A5HZNoUH3pLwXpyTr3anNx4640ckMphXw4IZj2zrQG-7m_y8IemzxaTiNXjQP751NXr7s0wgWMbrBkc-_LUDLm-ENrdnY_jSGdXu4lutEfnYk_xCUvl3CYtRuSzs8sRtwID_Hh7uv8ek62Rzi69SBVeovq3wab_kHcGGgnryEFGHSg6kRMh_AXYdUWQ',   //If your header name has spaces or any other char not appropriate
 //for object property name, use quoted notation shown in second
           },
dataType: 'json',
success: function (data) {
console.log(data);        
marker.setAnimation(google.maps.Animation.BOUNCE);             
for( var j = 0; j < data.routes[0].legs[0].steps.length-1; j++){
   position = [latLng.lat, latLng.lng];
   var dest;
   if(j%2 == 0){
       dest = [data.routes[0].legs[0].steps[j].end_location.lat, data.routes[0].legs[0].steps[j].end_location.lng];
   }else{
       dest = [data.routes[0].legs[0].steps[j].start_location.lat, data.routes[0].legs[0].steps[j].start_location.lng];

   }            
   transition(dest);            
}



        
} 



   
       
   

})


}

var numDeltas = 100;
var delay = 10; //milliseconds
var i = 0;
var deltaLat;
var deltaLng;

function moveMarker(){
console.log("in here");
console.log(position);
position[0] += deltaLat;
position[1] += deltaLng;
var latlng = new google.maps.LatLng(position[0], position[1]);   
marker.setPosition(latlng);    
if(i!=numDeltas){
i++;
setTimeout(moveMarker, 1000);
}
}



function transition(result){
i = 0;
deltaLat = (result[0] - position[0])/numDeltas;
deltaLng = (result[1] - position[1])/numDeltas;
moveMarker();
} 


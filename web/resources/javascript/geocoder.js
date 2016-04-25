/* 
 * Created by Joseph Sebastian on 2016.04.24  * 
 * Copyright © 2016 Joseph Sebastian. All rights reserved. * 
 */

var geocoder;
var _radiusSlider;
var _itemList;
var _sliderval; 
// User's current location (if granted)
var _currentLat; 
var _currentLong;

var rad = function(x) {
  return x * Math.PI / 180;
};

/*
 * Implementation of Haversine formula 
 */
function getDistance(lat1, long1, lat2, long2) {
  var R = 6378137; // Earth’s mean radius in meter
  var dLat = rad(lat2 - lat1);
  var dLong = rad(long2 - long1);
  var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(rad(lat1)) * Math.cos(rad(lat2)) *
    Math.sin(dLong / 2) * Math.sin(dLong / 2);
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  var d = R * c;
  return d * 0.000621371; // returns the distance in meter
}

function geocodeList() {
    var items = _itemList.getElementsByTagName('li');
    for (var i = 0; i < items.length; i++) {
        var element = items[i].getElementsByClassName('result-zipcode')[0];
        var zipcode = element.innerHTML;
        geocoder.geocode({'address': zipcode}, function(element, results, status) {
            if (status === google.maps.GeocoderStatus.OK) {
              var lat = results[0].geometry.location.lat();
              var long = results[0].geometry.location.lng();
              var dist = getDistance(_currentLat, _currentLong, lat, long)
              element.innerHTML += ' (' + dist.toFixed(2) + ' miles away)'
            } else {
              alert('Geocode was not successful for the following reason: ' + status);
            }
        }.bind(this, element));
    }
} 

function updateList() {
  var label = document.querySelector('.sliderLabel');
  var sliderValue = Number(label.innerHTML.split(' ')[0]);
  
  if (sliderValue === _sliderval) {
      // Nothing changed, do not refresh list
      return;
  }
  var resultsFound = false;
  _sliderval = sliderValue;

  var items = _itemList.getElementsByTagName('li');
  for (var i = 0; i < items.length; i++) {
      var distance = items[i].getElementsByClassName('result-zipcode')[0].innerText.split('(')[1].split(' ')[0];
      if (Number(distance) > _sliderval) {
          items[i].style.display = 'none';
      } else {
          items[i].style.display = 'block';
          resultsFound = true;
      }
  }

   if (!resultsFound) { 
      // Respond accordingly
  }
}

function locationAvailable(currentLocation) { 
    // Read current location
    _currentLat = currentLocation.coords.latitude;
    _currentLong = currentLocation.coords.longitude;
    // Show slider
    document.querySelector('.ui-slider').style.display = 'block';
    document.querySelector('.sliderLabel').style.display = 'block';
    // Create google maps API
    geocoder = new google.maps.Geocoder();
    // MutationObserver for slider manipulation
    var observer = new MutationObserver(updateList);    
    var config = { attributes: true, childList: true, characterData: true };
    observer.observe(_radiusSlider, config);
    // Geocode current list items
    geocodeList();
}

function locationBlocked() {
    console.log('Blocked');
}

window.addEventListener('load', function() {
    _radiusSlider = document.querySelector('.ui-slider-handle');
    _itemList = document.querySelector('.ui-datascroller-list');
    if(navigator.geolocation) {
        browserSupportFlag = true;
        navigator.geolocation.getCurrentPosition(locationAvailable, locationBlocked);
    } else {
        locationBlocked();
    }
});

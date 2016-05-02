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
 * Source: http://stackoverflow.com/questions/27928/calculate-distance-between-two-latitude-longitude-points-haversine-formula  
 */
function getDistance(lat1, long1, lat2, long2) {
  var R = 6378137;
  var dLat = rad(lat2 - lat1);
  var dLong = rad(long2 - long1);
  var a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(rad(lat1)) * Math.cos(rad(lat2)) *
    Math.sin(dLong / 2) * Math.sin(dLong / 2);
  var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  var d = R * c;
  return d * 0.000621371; // returns the distance in meter
}

/**
 * Geocode all items in the search results list 
 */
function geocodeList() {
    var items = _itemList.getElementsByTagName('li');
    // Iterate through each item in the search results
    for (var i = 0; i < items.length; i++) {
        var element = items[i].getElementsByClassName('result-zipcode')[0];
        var zipcode = element.innerHTML;
        // Extract the zipcode and send to Google API for geocoding
        geocoder.geocode({'address': zipcode}, function(element, results, status) {
            if (status === google.maps.GeocoderStatus.OK) {
              var lat = results[0].geometry.location.lat();
              var long = results[0].geometry.location.lng();
              // Calculate distances between current and desired locations 
              var dist = getDistance(_currentLat, _currentLong, lat, long)
              element.innerHTML += ' (' + dist.toFixed(2) + ' miles away)'
            } else {
              alert('Geocode was not successful for the following reason: ' + status);
            }
        }.bind(this, element));
    }
} 

/**
 * Ensure all elements of search list are within current current radius
 */
function updateList() {
  var label = document.querySelector('.sliderLabel');
  var sliderValue = Number(label.innerHTML.split(' ')[0]);
  
  if (sliderValue === _sliderval) {
      // Nothing changed, do not refresh list
      return;
  }
  var resultsFound = false;
  _sliderval = sliderValue;

  // Iterate through all items in list 
  var items = _itemList.getElementsByTagName('li');
  for (var i = 0; i < items.length; i++) {
      var distance = items[i].getElementsByClassName('result-zipcode')[0].innerText.split('(')[1].split(' ')[0];
      if (Number(distance) > _sliderval) {
          // Make list item hidden
          items[i].style.display = 'none';
      } else {
          // Make list item visible
          items[i].style.display = 'block';
          resultsFound = true;
      }
  }

   if (!resultsFound) { 
      // Respond accordingly
  }
}

/**
 * Granted access to browser location data, respond accordingly
 */
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

/**
 * No access to browser location data, no further action needed
 */
function locationBlocked() {
    console.log('Blocked');
}

/**
 * Link event listeners upon page load
 */
window.addEventListener('load', function() {
    _radiusSlider = document.querySelector('.ui-slider-handle');
    _itemList = document.querySelector('.ui-datascroller-list');
    // Prompt user for current location
    if(navigator.geolocation) {
        browserSupportFlag = true;
        navigator.geolocation.getCurrentPosition(locationAvailable, locationBlocked);
    } else {
        locationBlocked();
    }
});

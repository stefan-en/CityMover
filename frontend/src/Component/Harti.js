import React, { useState, useEffect } from 'react';
import { GoogleMap, DirectionsRenderer } from '@react-google-maps/api';
import './Harti.css';

const MapContainer = (props) => {
 
  //console.log(props)
  const [origin, setOrigin] = useState(props.origin);
  const [destination, setDestination] = useState(props.destination);
  const [directions, setDirections] = useState(null);
  const [travelMode, setTravelMode] = useState('DRIVING');
  const [isGoogleMapsLoaded, setIsGoogleMapsLoaded] = useState(false);


  useEffect(() => {
    const loadScript = () => {
      const script = document.createElement('script');
      script.src = `https://maps.googleapis.com/maps/api/js?key=${process.env.REACT_APP_GOOGLE_MAPS_API_KEY}&libraries=places`;
      script.async = true;
      script.onload = () => {
        setIsGoogleMapsLoaded(true);
      };
      document.body.appendChild(script);
    };

    loadScript();
  }, []);

  useEffect(() => {
    if (isGoogleMapsLoaded) {
      const autocompleteOrigin = new window.google.maps.places.Autocomplete(document.getElementById('origin-input'));
      const autocompleteDestination = new window.google.maps.places.Autocomplete(document.getElementById('destination-input'));

      autocompleteOrigin.addListener('place_changed', () => {
        const place = autocompleteOrigin.getPlace();
        setOrigin(place.formatted_address);
      });

      autocompleteDestination.addListener('place_changed', () => {
        const place = autocompleteDestination.getPlace();
        setDestination(place.formatted_address);
      });
    }
  }, [isGoogleMapsLoaded]);

  const handleTravelModeChange = (event) => {
    setTravelMode(event.target.value);
  };

  const handleFormSubmit = () => {
    if ((origin && destination) || (props.origin && props.destination)) {
      const directionsService = new window.google.maps.DirectionsService();

      directionsService.route(
        {
          origin,
          destination,
          travelMode: travelMode,
        },
        (result, status) => {
          if (status === 'OK') {
            setDirections(result);
          } else {
            console.error(`Eroare la calcularea rutei: ${status}`);
          }
          console.log(origin)
          console.log(destination)
          console.log(travelMode)
          console.log(result.routes[0].legs[0].distance.text)
          console.log(result.routes[0].legs[0].duration.text)
        }
      );
    }
  };

  const mapContainerStyle = {
    width: '100%',
    height: '110%',
  };

  return (
    <div className="main-container">
      <div className="form-container">
        <form>
          <div className="form-field">
            <label className="larger-text">Destinația inițială:</label>
            <input id="origin-input" type="text" className="transparent-input" />
          </div>
          <div className="form-field">
            <label className="larger-text">Destinația finală:</label>
            <input id="destination-input" type="text" className="transparent-input" />
          </div>
          <div className="form-field">
            <label className="larger-text">Mod de transport:</label>
            <select value={travelMode} onChange={handleTravelModeChange} className="transparent-input">
              <option value="DRIVING">Mașină</option>
              <option value="WALKING">Mers pe jos</option>
              <option value="TRANSIT">Transport public</option>
            </select>
          </div>
          <button className="submit-button" type="button" onClick={handleFormSubmit}>
            Caută rută
          </button>
        </form>
      </div>
  
      <div className="map-wrapper">
        {isGoogleMapsLoaded ? (
          <GoogleMap
            mapContainerStyle={mapContainerStyle}
            zoom={14}
            center={{ lat: 47.1569, lng: 27.5903 }}
            options={{ gestureHandling: 'cooperative' }}
          >
            {directions && (
              <DirectionsRenderer
                options={{
                  directions: directions,
                }}
              />
            )}
          </GoogleMap>
        ) : (
          <div className="loading">Loading...</div>
        )}
      </div>
    </div>
  );
  
};

export default MapContainer;

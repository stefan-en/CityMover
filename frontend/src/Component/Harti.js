import React, { useState, useEffect } from 'react';
import { useLocation } from 'react-router-dom';
import { GoogleMap, useJsApiLoader, DirectionsService, DirectionsRenderer } from '@react-google-maps/api';
import './Harti.css';

const Harti = () => {
  const { state } = useLocation();
  const [origin, setOrigin] = useState(state?.origin || '');
  const [destination, setDestination] = useState(state?.destination || '');
  const [directions, setDirections] = useState(null);
  const [travelMode, setTravelMode] = useState('TRANSIT');
  const [isGoogleMapsLoaded, setIsGoogleMapsLoaded] = useState(false);

  const containerStyle = {
    width: '100%',
    height: '100%'
  };

  const center = {
    lat: 47.151726,
    lng: 27.587914
  };

  const { isLoaded } = useJsApiLoader({
    id: 'google-map-script',
    googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
    libraries: ['places']
  });

  const onLoad = (map) => {
    const bounds = new window.google.maps.LatLngBounds(center);
    // map.fitBounds(bounds);
  };

  const onUnmount = () => {
    setDirections(null);
  };

  useEffect(() => {
    const loadGoogleMapsAPI = () => {
      if (!window.google) {
        const script = document.createElement('script');
        script.src = `https://maps.googleapis.com/maps/api/js?key=${process.env.REACT_APP_GOOGLE_MAPS_API_KEY}&libraries=places`;
        script.onload = () => {
          setIsGoogleMapsLoaded(true);
        };
        document.body.appendChild(script);
      } else {
        setIsGoogleMapsLoaded(true);
      }
    };

    loadGoogleMapsAPI();
  }, []);

  const handleTravelModeChange = (event) => {
    setTravelMode(event.target.value);
  };

  const handleFormSubmit = () => {
    
    if (origin && destination) {
      const directionsService = new window.google.maps.DirectionsService();
      directionsService.route(
        {
          origin: origin,
          destination: destination,
          travelMode: travelMode
        },
        (result, status) => {
          if (status === 'OK') {
            setDirections(result);
          } else {
            console.error(`Eroare la calcularea rutei: ${status}`);
          }
        }
      );
    }
  };

  useEffect(() => {
    if (isGoogleMapsLoaded) {
      const autocompleteOrigin = new window.google.maps.places.Autocomplete(
        document.getElementById('origin-input')
      );
      const autocompleteDestination = new window.google.maps.places.Autocomplete(
        document.getElementById('destination-input')
      );

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

  return (
    <div className="main-container">
      <div className="map-wrapper">
        {isLoaded ? (
          <GoogleMap
            mapContainerStyle={containerStyle}
            center={center}
            zoom={14}
            onLoad={onLoad}
            onUnmount={onUnmount}
          >
            {directions && <DirectionsRenderer directions={directions} />}
          </GoogleMap>
        ) : (
          <div className="loading">Loading...</div>
        )}
      </div>
  
      <div className="form-container">
        <form>
          <div className="form-field">
            <label className="larger-text">Destinația inițială:</label>
            <input
              id="origin-input"
              type="text"
              className="transparent-input"
              placeholder="Introduceți locația inițială"
              value={origin}
              onChange={(e) => setOrigin(e.target.value)}
            />
          </div>
          <div className="form-field">
            <label className="larger-text">Destinația finală:</label>
            <input
              id="destination-input"
              type="text"
              className="transparent-input"
              placeholder="Introduceți destinația finală"
              value={destination}
              onChange={(e) => setDestination(e.target.value)}
            />
          </div>
          <div className="form-field">
            <label className="larger-text">Mod de transport:</label>
            <select
              value={travelMode}
              onChange={handleTravelModeChange}
              className="transparent-input"
            >
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
    </div>
  );
  
  
};

export default Harti;

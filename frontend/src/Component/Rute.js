import React, { useState, useEffect } from 'react';
import MapContainer from './Harti';
import "./Rute.css"
import { useNavigate } from 'react-router-dom';

const Route = () => {
  const [, setResponse] = useState(null);
  const [routes, setRoutes] = useState([]);
  const [apiResponse, setAPIResponse] = useState(null);
  const [origin, setOrigin] = useState('');
  const [destination, setDestination] = useState('');
  const navigate = useNavigate();

  const handleClick = () => {
    fetch("https://api.tranzy.dev/v1/opendata/routes", {
      method: "GET",
      headers: {
        "Accept": "application/json",
        "X-API-KEY": process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
        "X-Agency-Id": 1
      }
    })
      .then(response => response.json())
      .then(data => {
        console.log("Routes data:", data);
        setResponse(data);
        const routes = data.routes;
        setRoutes(routes);

        // Make the POST request with the data
        fetch("http://localhost:8083/api/v1/service/harti/autovehicule", {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            "X-API-KEY": process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
            "X-Agency-Id": 1
          },
          body: JSON.stringify(data)
        })
          .then(response => response.text())
          .then(responseData => {
            console.log("POST response:", responseData);
            // Handle the POST response as needed
          })
          .catch(error => console.error(error));
      })
      .catch(error => console.error(error));
  };

  const callAPI = (endpoint) => {
    fetch(`http://localhost:8083/api/v1/service/harti/autovehicule/${endpoint}`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    })
      .then(response => response.json())
      .then(data => {
        setAPIResponse(data);
      })
      .catch(error => console.error(error));
  };

  useEffect(() => {
    if (routes && routes.length > 0) {
      const routeInfo = {
        shortName: routes[0].route_short_name,
        longName: routes[0].route_long_name,
        type: routes[0].route_type
      };
      console.log("Route info:", routeInfo);
    }
  }, [routes]);

  const prepareAPIResponse = () => {
    if (apiResponse && apiResponse.length > 0) {
      return apiResponse.map(item => ({
        numeTraseu: item.numeTraseu,
        numarTraseu: item.numarTraseu,
        statieInitiala: item.statieInitiala,
        statieFinala: item.statieFinala,
        tip: item.tip
      }));
    } else {
      return null;
    }
  };
  const handleTableRowClick = (origin, destination) => {
    setOrigin(origin);
    setDestination(destination);
    console.log("origin")

    console.log(origin)
    console.log(destination)

    navigate("/maps");
  };
  
  const renderTable = () => {
    const preparedResponse = prepareAPIResponse();
  
    if (preparedResponse) {
      return (
        <div className="container">
          <table className="wallet-table">
            <thead>
              <tr>
                <th className="text-left">Nume Traseu</th>
                <th className="text-left">Număr Traseu</th>
                <th className="text-left">Stație Inițială</th>
                <th className="text-left">Stație Finală</th>
                <th className="text-left">Tip</th>
              </tr>
            </thead>
            <tbody>
              {preparedResponse.map((item, index) => (
                <tr key={index} onClick={() => handleTableRowClick(item.statieInitiala, item.statieFinala)}>
                  <td className="text-left">{item.numeTraseu}</td>
                  <td className="text-left">{item.numarTraseu}</td>
                  <td className="text-left">{item.statieInitiala}</td>
                  <td className="text-left">{item.statieFinala}</td>
                  <td className="text-left">{item.tip}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      
      );
    } else {
      return <p>Nu sunt date disponibile</p>;
    }
  };

  return (
    <div className="info-container">
    <h1 className="info-title">Informatii rute</h1>
    <p className='info-title2'>Aici puteți vizualiza date pe care le doriți</p>
    <button className="info-button" onClick={handleClick}>Cautati informatii</button>
    <button className="info-button" onClick={() => callAPI("autobuze")}>Date despre autobuze</button>
    <button className="info-button" onClick={() => callAPI("tramvaie")}>Date despre tramvaie</button>
    {apiResponse && renderTable()}
    {origin && destination && (
  <MapContainer origin={origin} destination={destination} travelMode={"TRANZIT"} />
)}
  </div>
  );
};

export default Route;

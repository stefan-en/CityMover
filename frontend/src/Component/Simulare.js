import React, { useState } from 'react';
import Cookies from 'js-cookie';
import {useNavigate} from 'react-router-dom'
import './Simulare.css'
function Simulare() {
  const [, setResponse] = useState([]);
  const [finalData, setFinalData] = useState([]);
  const [showFirstButton, setShowFirstButton] = useState(true);
  const [showSecondButton, setShowSecondButton] = useState(false);
  const [showLastButton, setShowLastButton] = useState(false);

  let Navigates = useNavigate()
  var cookie_username = Cookies.get('username')

  if (!cookie_username) {
      
    alert("Trebuie să fiți conectat pentru a face o simulare.");
    Navigates("/login");
    return; 
  }

  const postDataToServer = (url, data) => {
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    })
    .then(response => response.text())
    .then(responseData => {
      console.log("POST response:", responseData);
      // Puteți manipula răspunsul POST așa cum aveți nevoie aici
    })
    .catch(error => console.error(error));
  };

  const fetchData = () => {
    const url_autovehicule = "https://api.tranzy.dev/v1/opendata/vehicles";
    const url_rute = "https://api.tranzy.dev/v1/opendata/routes";
    const url_statii = "https://api.tranzy.dev/v1/opendata/stops";
    const url_denumiri = "https://api.tranzy.dev/v1/opendata/stop_times";

    fetch(url_autovehicule, {
      method: "GET",
      headers: {
        "Accept": "application/json",
        "X-API-KEY": process.env.REACT_APP_TRANZIT_API_KEY,
        "X-Agency-Id": 1
      }
    })
    .then(response => response.json())
    .then(data => {
      console.log("Autovehicule data:", data);
      setResponse(data);
      postDataToServer("http://localhost:8084/api/v1/service/simulare/autovehicule", data);
    })
    .catch(error => {
      console.error('Eroare:', error);
    });

    fetch(url_rute, {
      method: "GET",
      headers: {
        "Accept": "application/json",
        "X-API-KEY": process.env.REACT_APP_TRANZIT_API_KEY,
        "X-Agency-Id": 1
      }
    })
    .then(response => response.json())
    .then(data => {
      console.log("Routes data:", data);
      setResponse(data);
      postDataToServer("http://localhost:8084/api/v1/service/simulare/rute", data);
    })
    .catch(error => {
      console.error('Eroare:', error);
    });

    fetch(url_statii, {
      method: "GET",
      headers: {
        "Accept": "application/json",
        "X-API-KEY": process.env.REACT_APP_TRANZIT_API_KEY,
        "X-Agency-Id": 1
      }
    })
    .then(response => response.json())
    .then(data => {
      console.log("Statii data:", data);
      setResponse(data);
      postDataToServer("http://localhost:8084/api/v1/service/simulare/statii", data);
    })
    .catch(error => {
      console.error('Eroare:', error);
    });

    fetch(url_denumiri, {
      method: "GET",
      headers: {
        "Accept": "application/json",
        "X-API-KEY": process.env.REACT_APP_TRANZIT_API_KEY,
        "X-Agency-Id": 1
      }
    })
    .then(response => response.json())
    .then(data => {
      console.log("Denumiri data:", data);
      setResponse(data);
      postDataToServer("http://localhost:8084/api/v1/service/simulare/reuniune", data);
    })
    .catch(error => {
      console.error('Eroare:', error);
    });

    setShowFirstButton(false);
    setShowSecondButton(true);
    setShowLastButton(false);
  };

  const handleSecondButtonClick= async ()=> {
        try {
          const response1 = await fetch("http://localhost:8084/api/v1/service/simulare/autoRute", {
            method: "GET",
            headers: {
              "Accept": "application/json"
            }
          });
          const data1 = await response1.json();
          console.log("ceva1");
          console.log(data1);
      
          const response2 = await fetch("http://localhost:8084/api/v1/service/simulare/reuniuneStatii", {
            method: "GET",
            headers: {
              "Accept": "application/json"
            }
          });
          const data2 = await response2.json();
          console.log("ceva2");
          console.log(data2);
      
          const response3 = await fetch("http://localhost:8084/api/v1/service/simulare/agregate", {
            method: "GET",
            headers: {
              "Accept": "application/json"
            }
          });
          const data3 = await response3.json();
          console.log("ceva3");
          console.log(data3);
      
          // Aici puteți adăuga cod pentru a afișa datele sau a face alte operații
          // după finalizarea tuturor cererilor
      
        } catch (error) {
          console.error("Eroare:", error);
        }
        setShowSecondButton(false);
        setShowLastButton(true);
      };
      const handleLastButtonClick= async ()=> {
        try {
          const response1 = await fetch("http://localhost:8084/api/v1/service/simulare/start", {
            method: "GET",
            headers: {
              "Accept": "application/json"
            }
          });
          const data1 = await response1.json();
          console.log("ceva1");
          console.log(data1);
          setFinalData(data1)
      
        } catch (error) {
          console.error("Eroare:", error);
        }
        setShowLastButton(false);
      };
      return (
        <div className="dop" style={{ display: 'flex', justifyContent: 'center' }}>
          {showFirstButton && (
            <button className="info-button" onClick={fetchData}>Cautati informatii</button>
          )}
      
          {showSecondButton && (
            <button className="info-button" onClick={handleSecondButtonClick}>Proceseaza datele obtinute</button>
          )}
      
          {showLastButton && (
            <button className="info-button" onClick={handleLastButtonClick}>Afiseaza datele obtinute</button>
          )}
      
          {finalData && Object.keys(finalData).length > 0 && (
            <div className="scroll-container" style={{ background: 'rgba(255, 255, 255, 0.9)', padding: '10px' }}>
              <div className="scroll-content">
                <table style={{ borderCollapse: 'collapse' }}>
                  <tbody>
                    {Object.entries(finalData).map(([key, value]) => (
                      <tr key={key}>
                        <td style={{ border: '1px solid black', padding: '8px', maxWidth: '300px', overflow: 'hidden', whiteSpace: 'nowrap' }}>{key}</td>
                        <td style={{ border: '1px solid black', padding: '8px', maxWidth: '300px', overflow: 'hidden' }}>
                          <div className="scroll-inner">
                            {Object.entries(value).map(([k, v]) => (
                              <div key={k} style={{ marginBottom: '8px' }}>{v}</div>
                            ))}
                          </div>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          )}
        </div>
      );
      
};

export default Simulare;

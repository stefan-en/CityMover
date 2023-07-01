import React, { useState } from 'react';
import '../App.css';
import Cookies from 'js-cookie';
import {useNavigate} from 'react-router-dom'
function Ticket() {
  
  const [selectedBoxes, setSelectedBoxes] = useState([]);
  const [selectedBox, setSelectedBox] = useState(null);
  const [, setShowDetails] = useState(false);
  // const [username,setUsername] = useState(null);
  let Navigates = useNavigate()
  const boxes = [
    { id: 1, title: 'Abonament Lunar', description: 'Pret: 50', details: 'Valabilitate 30 zile' },
    { id: 2, title: 'Abonament Student', description: 'Pret 40', details: 'Valabilitate 30 zile' },
    { id: 3, title: 'Abonament Elev', description: 'Pret 35', details: 'Valabilitate 30 zile' },
    { id: 4, title: 'Abonament Pensionar', description: 'Pret 30', details: 'Valabilitate 30 zile' },
    { id: 5, title: 'Abonament Anual', description: 'Pret 365', details: 'Valabilitate 1 an' },
    { id: 6, title: 'Bilet calatorie', description: 'Pret 2.5', details: 'Valabilitate 1 zile' }
  ];

  const toggleBoxSelection = (box) => {
    if (selectedBoxes.length === 1 && selectedBoxes[0].id === box.id) {
      setSelectedBoxes([]);
    } else {
      setSelectedBoxes([box]);
    }
  }

  const handleShowDetails = (box) => {
    
    setSelectedBox(box);
    setShowDetails(true);
  }

  const handleHideDetails = () => {
    setSelectedBox(null);
    setShowDetails(false);
  }

  const handleSendData = () => {
    console.log(selectedBoxes);
    // Aici poți adăuga codul de trimitere a datelor către API
  }
  const capitalize = (word) => {
    return word.charAt(0).toUpperCase() + word.slice(1);
  }
  
  const formatText = (text) => {
    return text.split(' ').map((word) => capitalize(word)).join('_').toUpperCase();
  }

  const handleBuyButtonClick = (event) => {
    var cookie_username = Cookies.get('username')
    event.preventDefault();
    setShowDetails(true);
  
    const url = "http://localhost:8091/api/v1/service/user/" + cookie_username;
  
    fetch(url, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
    })
    .then(response => response.json())
    .then(responseText => {
      console.log("Raspuns primul request:", responseText);
      return responseText["id"]; // returnează valoarea pentru a o folosi în al doilea request
    })
    .then(id => {
      fetch('http://localhost:8080/api/v1/service/tickets/buy', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          "idUser": id,
          "type": formatText(selectedBox.title),
        })
      })
      .then(response => {
        if (response.status === 200) {
          Navigates("/home");
        } else {
          // Afișează un mesaj de eroare și redirectează către aceeași pagină
          alert("A apărut o eroare! Încercați din nou mai târziu.");
          Navigates("/tickets");
        }
      })
      .catch(error => {
        console.error('Eroare:', error);
      });
        }
      
  )}


  return (
    <div className="App">
      <div className="box-container">
        {boxes.map(box => (
          <div
            key={box.id}
            className={`box ${selectedBoxes.map(b => b.id).includes(box.id) ? 'selected' : ''}`}
            onClick={() => toggleBoxSelection(box)}
          >
            <h3>{box.title}</h3>
            <button onClick={() => handleShowDetails(box)}>Detalii</button>
            {selectedBox && selectedBox.id === box.id && (
              <div className="details">
                <p>{selectedBox.description}</p>
                <button onClick={handleHideDetails}>Înapoi</button>
              </div>
            )}
            {selectedBoxes.length === 1 && selectedBoxes[0].id === box.id && (
              <div className="buy">
                <button onClick={handleBuyButtonClick}>Cumpără</button>
              </div>
            )}
          </div>
        ))}
      </div>
      <button onClick={handleSendData}>Trimite datele selectate</button>
    </div>
  );
}
export default Ticket;
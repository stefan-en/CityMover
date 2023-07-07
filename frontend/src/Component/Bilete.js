import React, { useState } from 'react';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';
import { PayPalButton } from 'react-paypal-button-v2';
import ticketImage1 from '../images/bilet60.png';
import ticketImage2 from '../images/bilet50.png';
import ticketImage3 from '../images/bilet35.png';
import ticketImage4 from '../images/bilet30.png';
import ticketImage5 from '../images/bilet365.png';
import ticketImage6 from '../images/bilet3lei.png';

function Ticket() {
  const [selectedBox, setSelectedBox] = useState(null);
  const [, setShowDetails] = useState(false);
  const [pret, setPret] = useState(false);
  const Navigates = useNavigate();

  const boxes = [
    { id: 1, title: 'Abonament Lunar', description: 60, details: 'Valabilitate 30 zile', image: ticketImage1 },
    { id: 2, title: 'Abonament Student', description: 50, details: 'Valabilitate 30 zile', image: ticketImage2 },
    { id: 3, title: 'Abonament Elev', description: 35, details: 'Valabilitate 30 zile', image: ticketImage3 },
    { id: 4, title: 'Abonament Pensionar', description: 30, details: 'Valabilitate 30 zile', image: ticketImage4 },
    { id: 5, title: 'Abonament Anual', description: 365, details: 'Valabilitate 1 an', image: ticketImage5 },
    { id: 6, title: 'Bilet calatorie', description: 3, details: 'Valabilitate 1 zi', image: ticketImage6 }
  ];

  const toggleBoxSelection = (box) => {
    // const cookie_username = Cookies.get('username');
    // if (!cookie_username) {
    //   alert('Trebuie să fiți conectat pentru a cumpăra un bilet.');
    //   Navigates('/login');
    //   return;
    // }
    if (selectedBox && selectedBox.id === box.id) {
      setSelectedBox(null);
    } else {
      setSelectedBox(box);
    }
    setPret((box.description/4.56).toFixed(2))
    console.log(pret)
  };

  const handleBuyButtonClick = (event) => {
    const cookie_username = Cookies.get('username');
    event.preventDefault();
    const url = `http://localhost:8091/api/v1/service/user/${cookie_username}`;

    fetch(url, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    })
      .then((response) => response.json())
      .then((responseText) => {
        const userId = responseText.id;

        fetch('http://localhost:8080/api/v1/service/tickets/buy', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            idUser: userId,
            type: selectedBox.title
          })
        })
          .then((response) => {
            if (response.status === 200) {
              alert('Biletul dumneavoastră a fost achiziționat cu succes!');
              Navigates('/home');
            } else {
              alert('A apărut o eroare! Vă rugăm să încercați din nou mai târziu.');
             Navigates('/tickets');
            }
          })
          .catch((error) => {
            console.error('Eroare:', error);
          });
      });
  };

  return (
    <div className="App">
      <div className="box-container">
        {boxes.map((box) => (
          <div
            key={box.id}
            className={`box ${selectedBox && selectedBox.id === box.id ? 'selected' : ''}`}
            onClick={() => toggleBoxSelection(box)}
          >
            <img src={box.image} alt={box.title} className="ticket-image" />
            {selectedBox && selectedBox.id === box.id && (
              <div className="details">
                <PayPalButton
                  amount={box.description}
                  currency="USD"
                  onSuccess={handleBuyButtonClick}
                  onError={(error) => console.error('Eroare de plată:', error)}
                  onCancel={() => console.log('Plată anulată')}
                />
              </div>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default Ticket;

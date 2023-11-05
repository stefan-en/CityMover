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
  var cookie_username = Cookies.get('username')

  if (!cookie_username) {
      
    alert("Trebuie să fiți conectat pentru a cumpara un bilet.");
    Navigates("/login");
    return; 
  }


  const boxes = [
    { id: 1, title: 'Abonament Lunar', description: 60, details: 'Valabilitate 30 zile', image: ticketImage1 },
    { id: 2, title: 'Abonament Student', description: 50, details: 'Valabilitate 30 zile', image: ticketImage2 },
    { id: 3, title: 'Abonament Elev', description: 35, details: 'Valabilitate 30 zile', image: ticketImage3 },
    { id: 4, title: 'Abonament Pensionar', description: 30, details: 'Valabilitate 30 zile', image: ticketImage4 },
    { id: 5, title: 'Abonament Anual', description: 365, details: 'Valabilitate 1 an', image: ticketImage5 },
    { id: 6, title: 'Bilet calatorie', description: 3, details: 'Valabilitate 1 zi', image: ticketImage6 }
  ];

  const toggleBoxSelection = (box) => {
    if (selectedBox && selectedBox.id === box.id) {
      //console.log(selectedBox.description)
      setSelectedBox(null);
    } else {
      setSelectedBox(box);
      //console.log(selectedBox.description)
    }
    setPret((box.description / 4.56).toFixed(2));
  };
  const capitalize = (word) => {
    return word.charAt(0).toUpperCase() + word.slice(1);
  }

  const formatText = (text) => {
    return text.split(' ').map((word) => capitalize(word)).join('_').toUpperCase();
  }

  const handleBuyButtonClick = (event) => {
  
    const cookie_username = Cookies.get('username');
    
    console.log(cookie_username)
    const url = `http://localhost:8091/api/v1/service/user/${cookie_username}`;
    console.log(url)
    fetch(url, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    })
      .then((response) => response.json())
      .then((responseText) => {
        console.log(responseText)
        const userId = responseText.id;
        console.log(userId)
        if (selectedBox) {
          console.log(formatText(selectedBox.title));
        } else {
          console.log("Nu a fost selectat niciun bilet.");
        }
        fetch('http://localhost:8080/api/v1/service/tickets/buy', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            idUser: userId,
            type: formatText(selectedBox.title)
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

  const createOrder = (data, actions) => {
    return actions.order.create({
      purchase_units: [
        {
          amount: {
            value: pret, // Set the ticket amount as the order value
            currency_code: 'USD' // Set the currency code
          }
        }
      ]
    });
  };

  const onPaymentSuccess = (details, data) => {
    console.log('Plată cu succes:', details);
    // Process the successful payment and perform any necessary actions
    handleBuyButtonClick(); // Call the buy ticket function after successful payment
  };

  const onPaymentError = (error) => {
    console.error('Eroare de plată:', error);
    // Handle the payment error
  };

  const onPaymentCancel = (data) => {
    console.log('Plată anulată:', data);
    // Handle the payment cancellation
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
                  createOrder={(data, actions) => createOrder(data, actions)}
                  onSuccess={onPaymentSuccess}
                  onError={onPaymentError}
                  onCancel={onPaymentCancel}
                  style={{
                    color: 'blue', // Customize the button color here
                    shape: 'rect', // Display the button as a rectangle
                    label: 'checkout', // Change the button text here
                    tagline: false // Disable the PayPal tagline
                  }}
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

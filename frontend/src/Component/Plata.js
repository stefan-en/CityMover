import React from 'react';
import { PayPalButton } from 'react-paypal-button-v2';
import '../App.css';

const Plata = () => {
    const handlePaymentSuccess = (details, data) => {
        console.log('Plată cu succes:', details);
        // Procesați plata cu succes și efectuați acțiunile necesare
      };
    
      const handlePaymentError = (error) => {
        console.error('Eroare de plată:', error);
        // Tratați eroarea în timpul procesării plății
      };
    
      const handlePaymentCancel = (data) => {
        console.log('Plată anulată:', data);
        // Tratați anularea plății
      };
    
      return (
        <div className="payment-form">
          <h2 className='hed'>Formular de plată</h2>
          <div className="paypal-button-container">
            <PayPalButton
              amount="10.00" // Specificați suma corespunzătoare biletului dvs.
              currency="USD" // Specificați valuta corespunzătoare
              onSuccess={handlePaymentSuccess}
              onError={handlePaymentError}
              onCancel={handlePaymentCancel}
              style={{
                color: 'blue', // Personalizați stilul butonului aici
                shape: 'rect', // Afișați butonul sub formă de dreptunghi
                label: 'checkout', // Schimbați textul butonului aici
                tagline: false, // Dezactivați tagline-ul PayPal
              }}
            />
          </div>
        </div>
      );
}

export default Plata;
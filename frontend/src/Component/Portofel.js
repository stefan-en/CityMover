import React, { useEffect, useState } from 'react';
import Cookies from 'js-cookie';
import QRCode from 'qrcode.react';
import './Acasa.css';

const Wallet = () => {

  const [walletData, setWalletData] = useState([]);
  const [userId, setUserId] = useState(null);
  const [qrCode, setQRCode] = useState('');
  const [isPopupOpen, setIsPopupOpen] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    fetchUserId();
  }, []);

  useEffect(() => {
    if (userId) {
      fetchWalletData();
    }
  }, [userId]);

  const fetchUserId = () => {
    const cookieUsername = Cookies.get('username');
    fetch(`http://localhost:8091/api/v1/service/user/${cookieUsername}`, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(response => {
        console.log(response)
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        const userId = data.id;
        setUserId(userId);
      })
      .catch(error => {
        setError(error.message);
      });
  };

  const fetchWalletData = () => {
    fetch(`http://localhost:8080/api/v1/service/tickets/history/${userId}`)
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok');
        }
        return response.json();
      })
      .then(data => {
        setWalletData(data);
        setIsLoading(false);
      })
      .catch(error => {
        setError(error.message);
        setIsLoading(false);
      });
  };

  const generateQRCode = (createDate, expireDate) => {
    const isExpired = isTicketExpired(expireDate);
    const formattedCreateDate = formatDate(createDate);
    const formattedExpireDate = formatDate(expireDate);
    const qrData = `Ticket Creation Date: ${formattedCreateDate}\nTicket Expiration Date: ${formattedExpireDate}\nValidity: ${isExpired ? 'INVALID' : 'VALID'}`;
    setQRCode(qrData);
    setIsPopupOpen(true);
  };

  const isTicketExpired = (expireDate) => {
    const ticketExpireDate = new Date(expireDate);
    const currentDateTime = new Date();
    return ticketExpireDate < currentDateTime;
  };

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    const formattedDate = `${day}/${month}/${year} ${hours}:${minutes}`;
    return formattedDate;
  };

  const closePopup = () => {
    setIsPopupOpen(false);
    setQRCode('');
  };

  if (isLoading) {
    return <div>Se cauta datele dumneavoastra despre biletele cumparate anterior...</div>;
  }

  if (error) {
    return <div>Eroare: {error}</div>;
  }

  return (
    <div className="wallet-container">
      <table className="wallet-table">
        <thead>
          <tr>
            <th>#</th>
            <th>Tipul Abonamentului</th>
            <th>Cumparat</th>
            <th>Expira</th>
            <th>Actiune</th>
          </tr>
        </thead>
        <tbody>
          {walletData.map((transaction, index) => (
            <tr key={transaction.id}>
              <td>{index + 1}</td>
              <td>{transaction.type}</td>
              <td>{transaction.createDate.split('T')[0]} {transaction.createDate.split('T')[1].split('.')[0]}</td>
              <td>{transaction.expireDate.split('T')[0]} {transaction.expireDate.split('T')[1].split('.')[0]}</td>
              <td>
                <button onClick={() => generateQRCode(transaction.createDate, transaction.expireDate)}>Generate QR Code</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {isPopupOpen && (
        <div className="popup">
          <div className="popup-content">
            <button className="close-button" onClick={closePopup}>Close</button>
            <div className="qr-code">
              <QRCode value={qrCode} />
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default Wallet;

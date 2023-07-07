import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Recuperare.css';

const Recuperare = () => {
  const [email, setEmail] = useState('');
  const [emailSent, setEmailSent] = useState(false);
  const [verificationCode, setVerificationCode] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [passwordReset, setPasswordReset] = useState(false);
  const [, setError] = useState('');
  const [code, setCode] = useState('');

  const navigate = useNavigate();

  const handleEmailSubmit = (event) => {
    event.preventDefault();

    // Verificați dacă adresa de email este validă
    if (!isValidEmail(email)) {
      setError('Adresa de email introdusă nu este validă.');
      return;
    }

    // Trimiteți cererea de resetare a parolei către server și primiți codul de verificare
    fetch(`http://localhost:8091/api/v1/service/users/emailcode?email=${email}`, {
      method: 'GET',
      headers: { 'Content-Type': 'application/json' },
    })
      .then((response) => response.json())
      .then((responseText) => {
        setCode(responseText.cod);
        setEmailSent(true);
        console.log("thiss")
        console.log(responseText.cod)
      })
      .catch((error) => {
        console.error('Eroare:', error);
      });
  };

  const handleVerificationSubmit = (event) => {
    event.preventDefault();
    // console.log("ssasa")
    // console.log(typeof(parseInt(verificationCode)))
    console.log(parseInt(verificationCode) === code)
    
    // Verificați dacă codul de verificare introdus este corect
    if (parseInt(verificationCode) === code) {
      setPasswordReset(true);
      setError('');
    } else {
      setError('Codul de verificare introdus nu este corect.');
    }
  };

  const handlePasswordSubmit = (event) => {
    event.preventDefault();

    // Validați confirmarea parolei
    if (newPassword !== confirmPassword) {
      setError('Parola și confirmarea parolei nu se potrivesc.');
      return;
    }

    // Actualizați parola utilizatorului
    fetch(`http://localhost:8091/api/v1/service/users/updatePassword?email=${email}&newPassword=${newPassword}`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
    })
      .then((response) => {
        if (response.status === 200) {
          alert('Parola a fost schimbată cu succes!');
          navigate('/login');
        } else {
          alert('A apărut o eroare! Vă rugăm să încercați din nou mai târziu.');
          navigate('/tickets');
        }
      })
      .catch((error) => {
        console.error('Eroare:', error);
      });
  };

  const handleBack = () => {
    navigate('/login');
  };


  const isValidEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };



  return (
    <div className="container">
      {!emailSent && !passwordReset && (
        <form className="input-container" onSubmit={handleEmailSubmit}>
          <div className="form-group">
            <label>Email:</label>
            <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} />
          </div>
          <div className="button-container">
            <button className="login-button" type="submit">Trimite email de resetare</button>
            <button className="login-button" onClick={handleBack}>Înapoi</button>
          </div>
        </form>
      )}
      {emailSent && !passwordReset && (
        <form className="input-container2" onSubmit={handleVerificationSubmit}>
          <div className="form-group">
            <label className="form-group-label">Cod de verificare:</label>
            <input className="verification-input" type="text" value={verificationCode} onChange={(e) => setVerificationCode(e.target.value)} />
          </div>
          <div className="button-container2">
            <button className="login-button" type="submit">Verifică codul</button>
            <button className="login-button" onClick={handleBack}>Înapoi</button>
          </div>
        </form>
      )}
      {emailSent && passwordReset && (
        <form className="input-container" onSubmit={handlePasswordSubmit}>
          <label className='par'>Noua parolă:</label>
          <div className="form-group">
            <input type="password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} />
          </div>
          <label className='par2'>Confirmă parola:</label>
          <div className="form-group">
            
            <input type="password" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />
          </div>
          <div className="button-container3">
            <button className="login-button" type="submit">Actualizează parola</button>
            <button className="login-1" onClick={handleBack}>Înapoi</button>
          </div>
        </form>
      )}
    </div>
  );
  
};
export default Recuperare;

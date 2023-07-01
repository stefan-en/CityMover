import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const ForgotPassword = () => {
  const [email, setEmail] = useState('');
  const [emailSent, setEmailSent] = useState(false);
  let Navigates = useNavigate()

  const handleSubmit = (event) => {
    event.preventDefault();
    // adaugă aici logica pentru trimiterea email-ului de resetare a parolei
    setEmailSent(true);
  };
  const handleBack = () => {
    Navigates("/login")
  };

  return (
    <div  className="cover">
      {!emailSent ? (
        <form onSubmit={handleSubmit}>
          <label>
            Email:
            <input
            className='inputField'
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
            />
          </label>
          <br />
          <button className="buttonul" type="submit">Send email</button>
          <p>
              
          <button className="buttonul" onClick={handleBack}>Back to login</button>
        </p>
        </form>
      ) : (
        <p>An email with password reset instructions has been sent to {email}.</p>
      )}
    </div>
  );
};

export default ForgotPassword;
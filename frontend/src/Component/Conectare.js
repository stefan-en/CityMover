import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import { useNavigate } from 'react-router-dom';
import './Conectare.css';

function NewLogin() {
  const [password, setPassword] = useState('');
  const [username, setUsername] = useState('');
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [showLogin] = useState(true);
  var [token, setToken] = useState(null);
  let navigate = useNavigate();

  useEffect(() => {
    const loginResult = Cookies.get('loginResult');
    if (loginResult) {
      setIsLoggedIn(true);
    }
  }, []);
  useEffect(() => {
    //window.location.reload();
  }, [isLoggedIn]);

  const handleSubmit = (event) => {
    event.preventDefault();
    if (isLoggedIn) {
      // Deconectare
      Cookies.remove('loginResult');
      Cookies.remove('username');
      setIsLoggedIn(false);
      setToken(null);
      navigate('/home');
    } else {
      // Conectare
      fetch('http://localhost:8081/api/v1/auth/authenticate', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          username: username,
          password: password
        })
      })
        .then(response => response.text())
        .then(responseText => {
          var responseLogin = JSON.parse(responseText);
          token = responseLogin["access_token"];
          console.log(token);
          if (token !== null) {
            Cookies.set('loginResult', token);
            Cookies.set('username', username);
            setIsLoggedIn(true);
            navigate('/home');
            window.location.reload();
          }
        })
        .catch(error => console.error(error));
    }
  };

  const handlePasswordChange = (event) => {
    setPassword(event.target.value);
  };

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handleRegister = () => {
    navigate('/register');
  };

  const handleForgotPassword = () => {
    navigate('/forgot-password');
  };


  return (
  <div>
    <h1 className='loginSpace'>CONECTARE</h1>
    <div className="cover">
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label className='byt3'>
            Nume utilizator:
            <input
              className='inputField'
              type="text"
              placeholder='utilizator'
              id="username"
              name='username'
              value={username}
              onChange={handleUsernameChange}
            />
          </label>
        </div>
        <div className="form-group">
          <label className='byt3'>
            Parola:
            <input
              className='inputField'
              type="password"
              id="password"
              placeholder='*******'
              value={password}
              onChange={handlePasswordChange}
            />
          </label>
        </div>
        <div className="form-group">
          <button
            className='login-button'
            buttonStyle='btn--primary'
            buttonSize='btn--large'
            type="submit"
            disabled={!username || !password}
            onClick={handleSubmit}
          >
            {isLoggedIn ? 'Deconectare' : 'Conectare'}
          </button>
        </div>
        <div>
          {showLogin && (
            <div className="links-container">
              <div className='ceva'>
                Nu ai un cont?{' '}

              </div>
              <button className='byt' buttonStyle='btn--primary' buttonSize='btn--large' onClick={handleRegister}>Inregistreaza-te</button>
              <div className='ceva2'>
                Ai uitat parola?{' '}
              </div>
              <button className='byt2' buttonStyle='btn--primary' buttonSize='btn--large' onClick={handleForgotPassword}>Resetaza</button>
            </div>
          )}
        </div>
      </form>
    </div>
  </div>
);

}

export default NewLogin;

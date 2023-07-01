import React, { useState, useEffect } from 'react';
import Cookies from 'js-cookie';
import { useNavigate, Link } from 'react-router-dom';
import { Button } from './Button';
import './loginform.css';

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

  const handleSubmit = (event) => {
    event.preventDefault();
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
        }
      })
      .catch(error => console.error(error));
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

  const handleLogout = () => {
    Cookies.remove('loginResult');
    Cookies.remove('username');
    setIsLoggedIn(false);
    setToken(null);
    navigate('/home');
  };

  return (
    <div>
      <h1>LOGIN</h1>
      <div className="cover">
        <h1 className='ceva'>Logi</h1>
        <form onSubmit={handleSubmit}>
          <label className='byt'>
            Username:
            <input
              className='inputField'
              type="text"
              placeholder='your username'
              id="username"
              name='username'
              value={username}
              onChange={handleUsernameChange}
            />
          </label>
          <br />
          <label className='byt'>
            Password:
            <input
              className='inputField'
              type="password"
              id="password"
              placeholder='*******'
              value={password}
              onChange={handlePasswordChange}
            />
          </label>
          <br />
          <Button
            className='bost'
            buttonStyle='btn--primary'
            buttonSize='btn--large'
            type="submit"
            disabled={!username || !password}
            onClick={handleSubmit}
          >
            {isLoggedIn ? 'Log Out' : 'Log In'}
          </Button>
          <div>
            {showLogin && (
              <div>
                <p>
                  Don't have an account?{' '}
                  <button className='byt' buttonStyle='btn--primary' buttonSize='btn--large' onClick={handleRegister}>Register here</button>
                </p>
                <p>
                  Forgot your password?{' '}
                  <button className='byt' buttonStyle='btn--primary' buttonSize='btn--large' onClick={handleForgotPassword}>Reset it here</button>
                </p>
              </div>
            )}
          </div>
        </form>
      </div>
      {isLoggedIn && (
        <div>
          <Link to="/home" onClick={handleLogout}>
            Log Out
          </Link>
        </div>
      )}
    </div>
  );
}

export default NewLogin;

import React, {useState} from 'react'
import {useNavigate} from 'react-router-dom'
import "./Inregistrare.css"

function NewRegister() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [username, setUsername] = useState('');
    const [lastname, setLastname] = useState();
    const [firstname, setFirstname] = useState(); 
    const [, setError] = useState('');
    let Navigates = useNavigate()
    
  
    const isValidEmail = (email) => {
      const emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;
      return emailRegex.test(email);
    };
  
    const handleSubmit = (event) => {
      event.preventDefault();
      if (!isValidEmail(email)) {
        setError('Ati introdus o adresa de email invalida');
        return;
      }
      fetch('http://localhost:8081/api/v1/auth/register', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            lastname : lastname,
            firstname: firstname,
            email: email,
            username: username,
            password: password
          })
        })
        
        .then(response => response.text())
      .then(responseText => {
      
      var responseLogin = JSON.parse(responseText)
      var token = responseLogin['acces_token']
       console.log(responseLogin)
       console.log(token)
        if (token !== null) {
          Navigates("/login")
        }
      })
      .catch(error => console.error(error));
    };
  
    const handlePasswordChange = (event) => {
      setPassword(event.target.value);
    };
  
    const handleConfirmPasswordChange = (event) => {
      setConfirmPassword(event.target.value);
    };
  
    const handleEmailChange = (event) =>{
      setEmail(event.target.value)
      setError('');
    }
  
    const handleUsernameChange= (event) => {
      setUsername(event.target.value);
    };
    const handleLastnameChange= (event) => {
      setLastname(event.target.value);
    };
    const handleFirstnameChange= (event) => {
      setFirstname(event.target.value);
    };
    const handleBack = () => {
      Navigates("/login")
    };
    const passwordsMatch = password === confirmPassword;
    return (
      <div className="register-container">
        <h1 className='RegisterSpace'>INREGISTRARE</h1>
        <div className="cover register-form">
          <form onSubmit={handleSubmit}>
            <label className="register-label">
              Nume:
              <input
                className='inputField'
                type="text"
                value={firstname}
                onChange={handleFirstnameChange}
              />
            </label>
            <label className="register-label">
              Prenume:
              <input
                className='inputField'
                type="text"
                value={lastname}
                onChange={handleLastnameChange}
              />
            </label>
            <label className="register-label">
              Nume utilizator:
              <input
                className='inputField'
                type="text"
                value={username}
                onChange={handleUsernameChange}
              />
            </label>
            <label className="register-label">
              Email:
              <input
                className='inputField'
                type="email"
                value={email}
                onChange={handleEmailChange}
              />
            </label>
  
            <label className="register-label">
              Parola:
              <input
                className='inputField'
                type="password"
                value={password}
                onChange={handlePasswordChange}
              />
            </label>
            <label className="register-label">
              Confirma parola:
              <input
                className='inputField'
                type="password"
                value={confirmPassword}
                onChange={handleConfirmPasswordChange}
              />
            </label>
            {!passwordsMatch && <p className="password-error">Parolele nu sunt identice</p>}
            <button className="button" type="submit" disabled={!passwordsMatch || !username || !email}>
              Inregistreaza-te
            </button>
            <p>
              <button className="back-button" onClick={handleBack}>Intoarce-te la conectare</button>
            </p>
          </form>
        </div>
      </div>
    );

}

export default NewRegister
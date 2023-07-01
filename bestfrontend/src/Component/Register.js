import React, {useState} from 'react'
import Cookies from 'js-cookie';
import {useNavigate} from 'react-router-dom'
import { Button } from './Button'
import "./loginform.css"

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
        setError('Invalid email address');
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
    <div>
       <h1>REGISTER</h1> 
    <div  className="cover">
        <h1 className='ceva'>
            Logi
        </h1>
    <form onSubmit={handleSubmit}>
      <label>
        Firstname:
        <input className='inputField' type="text" value={firstname} onChange={handleFirstnameChange} />
        </label>
        <br/>
        <label>
        Lastname:
        <input className='inputField' type="text" value={lastname} onChange={handleLastnameChange} />
        </label>
        <br/>
        <label>
        Username:
        <input className='inputField' type="text" value={username} onChange={handleUsernameChange} />
        </label>
        <br />
        <br/>
      <label>
        Email:
        <input className='inputField' type="email" value={email}onChange={handleEmailChange}/>
      </label>
      <br />
      <label>
        Password:
        <input
        className='inputField'
          type="password"
          value={password}
          onChange={handlePasswordChange}
        />
      </label>
      <br />
      <label>
        Confirm password:
        <input
        className='inputField'
          type="password"
          value={confirmPassword}
          onChange={handleConfirmPasswordChange}
        />
      </label> 
      <br />
      {!passwordsMatch && <p>Passwords do not match</p>}
      <br/>
      <button className="button" type="submit" disabled={!passwordsMatch || !username  || !email}>Register</button>
        <p>
              
          <button className="button" onClick={handleBack}>Back to login</button>
        </p>
    </form>
    </div>
    </div>
  );
}

export default NewRegister
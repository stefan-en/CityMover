import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { Button } from './Button';
import './Navbar.css';
import Cookies from 'js-cookie';

function Navbar() {
  const [click, setClick] = useState(false);
  const [button, setButton] = useState(true);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  const handleClick = () => setClick(!click);
  const closeMobileMenu = () => setClick(false);

  const showButton = () => {
    if (window.innerWidth <= 960) {
      setButton(false);
    } else {
      setButton(true);
    }
  };

  useEffect(() => {
    showButton();
    const loginResult = Cookies.get('loginResult');
    if (loginResult && loginResult !== '') {
        setIsLoggedIn(true);
      }
  }, []);

  window.addEventListener('resize', showButton);

  const handleLogout = () => {
    Cookies.remove('loginResult');
    setIsLoggedIn(false);
  };

  return (
    <>
      <nav className='navbar'>
        <div className='navbar-container'>
          <Link to='/' className='navbar-logo' onClick={closeMobileMenu}>
            MOVE <i className='fa-thin fa-car'></i>
          </Link>
          <div className='menu-icon' onClick={handleClick}>
            <i className={click ? 'fas fa-times' : 'fas fa-bars'} />
          </div>
          <ul className={click ? 'nav-menu active' : 'nav-menu'}>
            <li className='nav-item'>
              <Link to='/myWallet' className='nav-links' onClick={closeMobileMenu}>
                Biletele Mele
              </Link>
            </li>
            <li className='nav-item'>
              <Link to='/tickets' className='nav-links' onClick={closeMobileMenu}>
                Cumpara Bilete
              </Link>
            </li>
            <li className='nav-item'>
              <Link to='/route' className='nav-links' onClick={closeMobileMenu}>
                Rute
              </Link>
            </li>
            <li className='nav-item'>
              <Link to='/maps' className='nav-links' onClick={closeMobileMenu}>
                Harta
              </Link>
            </li>
          </ul>
          {button && (
            <>
              {isLoggedIn ? (
                <Button buttonStyle='btn--outline' onClick={handleLogout}>
                  Log Out
                </Button>
              ) : (
                <Button buttonStyle='btn--outline'>Log In</Button>
              )}
            </>
          )}
        </div>
      </nav>
    </>
  );
}

export default Navbar;

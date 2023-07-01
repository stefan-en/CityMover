import logo from './logo.svg';
import './App.css';
import Login from './Component/Login';
import Register from './Component/Register';
import ForgotPassword from './Component/ForgotPassword';
import Home from './Component/Home';
import MyRoute from './Component/MyRoute'
import Ticket from './Component/Ticket'
import Wallet from './Component/Wallet'
import Navbar from './Component/Navbar';
import Map from './Component/Map'
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Homess from './Component/Homess';
import Ticketss from './Component/Homess';

function App (){
  return (
    <Router>
    <Navbar></Navbar>
    <Routes>
      <Route path='/' exact element={<Home/>}></Route>
      <Route path='/login' element={<Login/>} />
      <Route path='/register' element={<Register/>} /> 
      <Route path='/forgot-password' element={<ForgotPassword/>} />  
      <Route path='/home'  element={<Home/>} />  
      <Route path='/route' element={<MyRoute/>} />
      <Route path='/tickets' element={<Ticket/>}/>
      <Route path='/myWallet' exact element={<Wallet/>}/>
      <Route path='/maps' element={<Map/>}/>
      <Route path ='/tiik' element={<Ticketss/>}/>

    </Routes>
  </Router>
  );
};

export default App;
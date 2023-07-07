import './App.css';
import { BrowserRouter as Router, Routes, Route} from 'react-router-dom';
import Conectare from './Component/Conectare';
import Inregistrare from './Component/Inregistrare';
import Recuperare from './Component/Recuperare';
import Acasa from './Component/Acasa';
import Rute from './Component/Rute'
import Bilete from './Component/Bilete'
import Portofel from './Component/Portofel'
import Navbar from './Component/Navbar';
import Harti from './Component/Harti'
import Simulare from './Component/Simulare';
import Plata from './Component/Plata';


function App (){
  return (
    <Router>
    <Navbar></Navbar>
    <Routes>
      <Route path='/' exact element={<Acasa/>}></Route>
      <Route path='/home'  element={<Acasa/>} />  
      <Route path='/login' element={<Conectare/>} />
      <Route path='/register' element={<Inregistrare/>} /> 
      <Route path='/forgot-password' element={<Recuperare/>} />  
      <Route path='/route' element={<Rute/>} />
      <Route path='/tickets' element={<Bilete/>}/>
      <Route path='/myWallet' exact element={<Portofel/>}/>
      <Route path='/maps' element={<Harti/>}/>
      <Route path='/simulare' element={<Simulare/>}/>
      <Route path='/plata' element={<Plata/>}/>
    </Routes>
  </Router>
  );
};

export default App;
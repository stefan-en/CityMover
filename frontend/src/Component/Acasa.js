import React from 'react'
import '../App.css'
import './NewHome.css'
import Cards from './Cards'

function NewHome() {
  return (
    <div className='hero-container'>
       <div className='hero-btns'>
        <Cards></Cards>
       </div>
    </div>
  )
}

export default NewHome
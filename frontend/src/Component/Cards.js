import React from 'react';
import './Card.css';
import CardItem from './CardItem';

function Cards() {
  return (
    <div className='cards'>
      <h1>Vedeti ultimele stiri CTP Iasi!</h1>
      <div className='cards__container'>
        <div className='cards__wrapper'>
          <ul className='cards__items'>
            <CardItem
              src='images/aeroport.jpg'
              text='Noi informatii despre transportul catre Aeroport'
              label='Noutati'
              path='https://www.ziaruldeiasi.ro/stiri/ctp-iasi-anunta-modificari-in-programul-curselor-care-vor-circula-catre-aeroportul-international--352405.html'
            />
            <CardItem
              src='images/solaris.jpg'
              text='CTP Iaşi a scos la stradă primele autobuze electrice'
              label='Electric'
              path='https://www.ziarulevenimentul.ro/stiri/ctp-iasi-a-scos-la-strada-primul-autobuz-electric/ctp-iasi-a-scos-la-strada-primul-autobuz-electric--217533840.htmls'
            />
          </ul>
          <ul className='cards__items'>
            <CardItem
              src='images/img-3.jpg'
              text='125 de ani de la înfiinţarea sistemului de transport public'
              label='Istoric'
              path='https://www.amosnews.ro/125-de-ani-de-la-infiintarea-sistemului-de-transport-public-cu-tramvaie-electrice-la-iasi/'
            />
            <CardItem
              src='images/scandal.jpg'
              text='Vatman versus CTP'
              label='Mister'
              path='https://www.7est.ro/2023/07/un-vatman-al-ctp-iasi-a-fost-concediat-pentru-ca-folosea-camera-video-personala-in-timpul-serviciului/'
            />
            <CardItem
              src='images/tramvai.jpg'
              text='Primăria prinde o finanțare importantă în PNRR'
              label='Proiecte publice'
              path='https://apix.ro/primaria-prinde-o-finantare-importanta-in-pnrr-inca-18-tramvaie-noi-si-25-autobuze-electrice-vor-lua-drumul-iasului/'
            />
          </ul>
        </div>
      </div>
    </div>
  );
}

export default Cards;
import React, { useState, useEffect } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import Modal from 'react-modal';
import AIRInfoBar from './AIRInfoBar';
import './styles.css';

const customStyles = {
    content : {
      top                   : '50%',
      left                  : '50%',
      right                 : 'auto',
      bottom                : 'auto',
      marginRight           : '-50%',
      transform             : 'translate(-50%, -50%)',
      backgroundColor       : '#F0AA89'      
    }
}


function Airport() {
  const [airports, setAirports] = useState(null);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [selectedICAO, setSelectedICAO] = useState("NA");
  const fetchData = async () => {
  const response = await axios.get(
      '/api/airports'
    );
    setAirports(response.data);
  };
  const setModalIsOpenToTrue =(icao)=>{
      setModalIsOpen (true)
      setSelectedICAO(icao)
	
  };

  const setModalIsOpenToFalse =()=>{
      setModalIsOpen(false)
  }
  

  return (
    <div className="App">
      <h2>Fetch Airports</h2>

      {/* Fetch data from API */}
      <div>
        <button className="fetch-button" onClick={fetchData}>
          Fetch Data
        </button>
        <br />
      </div>
              <Modal isOpen={modalIsOpen} style={customStyles}>
                <button onClick={setModalIsOpenToFalse}>x</button>
                <Top2WayPoints icao={selectedICAO} />
              </Modal>
      {/* Display data from API */}
      <div className="airports">
        {airports &&
          airports.map((airport, index) => {

            return (
              <>
              <div className="airport" key={index}>
                <h3>Airport {index + 1}</h3>
	<h2>{airport.name}</h2>

	<div className="details">
                  <p>UID : {airport.uid}</p>
                  <p>ICAO : <a title="Click for top 2 SID and STAR waypoints" href="#" onClick={() => setModalIsOpenToTrue(airport.icao)}>{airport.icao}</a> </p>
		  <p>ALT : {airport.alt}</p>
                  <p>LAT : {airport.lat}</p>
		  <p>LNG : {airport.lng}</p>
		  <p>IATA : {airport.iata} </p>
                </div>
              </div>
              </>
            );
          })}
      </div>

      <AIRInfoBar year="2021" />
    </div>
  );
}
function Top2WayPoints (props) {
 const [Top2SIDWayPoints, setTop2SIDWayPoints] = useState(null) ;
 const [Top2STARWayPoints, setTop2STARWayPoints] = useState(null);
 const fetchTopWayPoints = async  (icao) => {
    const response1 = await axios.get(
     '/api/sids/airport/' + icao + '/topWaypoints/2'
    );
    setTop2SIDWayPoints(response1.data.topWayPoints);
    const response2 = await axios.get(
     '/api/stars/airport/' + icao + '/topWaypoints/2'
    );
    setTop2STARWayPoints(response2.data.topWayPoints);
 };


 useEffect(() => {
	fetchTopWayPoints(props.icao);
 },[props.icao]); //this last param is required to avoid calling the API multiple times!


  return (
      <>
      <table>
      <th>Top 2 SID Waypoints for {props.icao}</th>
        {Top2SIDWayPoints &&
	Top2SIDWayPoints.map((wp,index) => <tr><td>{index+1}</td><td>{wp.name}</td><td>{wp.count}</td></tr>)}
      </table>
      <table>
      <th>Top 2 STAR Waypoints for {props.icao}</th>
        {Top2STARWayPoints &&
        Top2STARWayPoints.map((wp,index) => <tr><td>{index+1}</td><td>{wp.name}</td><td>{wp.count}</td></tr>)}
      </table>
      </>
  );

   
}

export default Top2WayPoints
const rootElement = document.getElementById('root');
ReactDOM.render(<Airport />, rootElement);


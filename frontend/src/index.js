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

      {/* Display data from API */}
      <div className="airports">
        {airports &&
          airports.map((airport, index) => {

            return (
              <>
              <Modal isOpen={modalIsOpen} style={customStyles}>
                <button onClick={setModalIsOpenToFalse}>x</button>
                <Top2WayPoints icao={selectedICAO} />
              </Modal>
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
 const [Top2SIDWayPoints, setTop2SIDWayPoints] = useState(null);
 const [Top2STARWayPoints, setTop2STARWayPoints] = useState(null);
 //const fetchTopWayPoints = async (icao) => {
 //const response1 = await axios.get(
 //    '/api/sids/airport/' + icao + '/topWaypoints/2'
 //   );
 //   setTop2SIDWayPoints(response1.data);
// const response2 = await axios.get(
//     '/api/stars/airport/' + icao + '/topWaypoints/2'
 //   );
 //   setTop2STARWayPoints(response2.data);
// };

 useEffect(() => {
        const resp=axios.get('api/sids/airport/' + props.icao + '/topWaypoints/2');
        setTop2SIDWayPoints(resp.data);
	const resp1=axios.get('api/stars/airport/' + props.icao + '/topWaypoints/2');
	setTop2STARWayPoints(resp1.data);
  });
    return (
	<h2>{props.icao}</h2>
   )
   
   /*   <div className="airports">
        {Top2SIDWayPoints &&
          Top2SIDWayPoints.map((wp, index) => {

            return (
              <>
              <div className="airport" key={index}>
                <h3>Waypoint {index + 1}</h3>
                <h2>{wp.name}</h2>
              </div>
              </>
            );
          })}
      </div>
*/
}

export default Top2WayPoints
const rootElement = document.getElementById('root');
ReactDOM.render(<Airport />, rootElement);


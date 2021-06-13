import React, { useState } from 'react';
import ReactDOM from 'react-dom';
import axios from 'axios';
import ScotchInfoBar from './AIRInfoBar';
import './styles.css';

function Airport() {
  const [airport, setAirports] = useState(null);

  const fetchData = async () => {
    const response = await axios.get(
      'http://localhost:3000/api/airports'
    );

    setAirports(response.data);
  };

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
        {airport &&
          airport.map((airport, index) => {

            return (
              <div className="airport" key={index}>
                <h3>Airport {index + 1}</h3>
                <h2>{airport.name}</h2>

                <div className="details">
                  <p>UID  : {airport.uid}</p>
                  <p>ICAO : {airport.icao}</p>
                  <p>LAT  : {airport.lat}</p>
		  <p>LNG: {airport.lng}</p>
                </div>
              </div>
            );
          })}
      </div>

      <ScotchInfoBar year="2021" />
    </div>
  );
}

const rootElement = document.getElementById('root');
ReactDOM.render(<Airport />, rootElement);


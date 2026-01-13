import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './components/Login';
import Signup from './components/Signup';
import TaskList from './components/TaskList';

function App() {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [showSignup, setShowSignup] = useState(false);

  const handleLogout = () => {
    localStorage.removeItem('token');
    setToken(null);
  };

  if (!token) {
    return (
      <div className="container mt-5">
        <div className="row justify-content-center">
          <div className="col-md-6">
            {showSignup ? (
              <Signup setToken={setToken} setShowSignup={setShowSignup} />
            ) : (
              <Login setToken={setToken} setShowSignup={setShowSignup} />
            )}
          </div>
        </div>
      </div>
    );
  }

  return (
    <div className="container mt-4">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2>ðŸ“ Gerenciador de Tarefas</h2>
        <button className="btn btn-danger" onClick={handleLogout}>Sair</button>
      </div>
      <TaskList token={token} />
    </div>
  );
}

export default App;

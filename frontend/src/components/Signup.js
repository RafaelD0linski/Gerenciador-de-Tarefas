import React, { useState } from 'react';
import axios from 'axios';

function Signup({ setToken, setShowSignup }) {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleSignup = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/api/auth/signup', {
        username,
        email,
        password
      });
      const response = await axios.post('http://localhost:8080/api/auth/signin', {
        username,
        password
      });
      localStorage.setItem('token', response.data.token);
      setToken(response.data.token);
    } catch (err) {
      setError('Cadastro falhou! Username jÃ¡ existe.');
    }
  };

  return (
    <div className="card">
      <div className="card-body">
        <h3 className="card-title text-center">Cadastro</h3>
        {error && <div className="alert alert-danger">{error}</div>}
        <form onSubmit={handleSignup}>
          <div className="mb-3">
            <input
              type="text"
              className="form-control"
              placeholder="Username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <input
              type="email"
              className="form-control"
              placeholder="Email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>
          <div className="mb-3">
            <input
              type="password"
              className="form-control"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          <button type="submit" className="btn btn-success w-100">Cadastrar</button>
        </form>
        <p className="text-center mt-3">
          JÃ¡ tem conta? <button className="btn btn-link p-0" onClick={() => setShowSignup(false)}>Login</button>
        </p>
      </div>
    </div>
  );
}

export default Signup;
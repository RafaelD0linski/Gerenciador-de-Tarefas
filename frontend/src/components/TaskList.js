import React, { useState, useEffect } from 'react';
import axios from 'axios';

function TaskList({ token }) {
  const [tasks, setTasks] = useState([]);
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const fetchTasks = async () => {
    try {
      setLoading(true);
      setError('');
      const response = await axios.get('http://localhost:8080/api/tasks', {
        headers: { Authorization: `Bearer ${token}` }
      });
      setTasks(response.data || []);
    } catch (err) {
      console.error('Erro ao buscar tarefas:', err);
      setError('Erro ao carregar tarefas. Verifique se o servidor backend está rodando.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (token) {
      fetchTasks();
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [token]);

  const createTask = async (e) => {
    e.preventDefault();
    try {
      setError('');
      await axios.post('http://localhost:8080/api/tasks', 
        { title, description, status: 'PENDENTE' },
        { headers: { Authorization: `Bearer ${token}` } }
      );
      setTitle('');
      setDescription('');
      await fetchTasks();
    } catch (err) {
      console.error('Erro ao criar tarefa:', err);
      setError('Erro ao criar tarefa. Verifique se o servidor backend está rodando na porta 8080.');
    }
  };

  const deleteTask = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/tasks/${id}`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      fetchTasks();
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      {error && (
        <div className="alert alert-danger" role="alert">
          {error}
        </div>
      )}
      <div className="card mb-4">
        <div className="card-body">
          <h5>Nova Tarefa</h5>
          <form onSubmit={createTask}>
            <div className="mb-2">
              <input
                type="text"
                className="form-control"
                placeholder="Título"
                value={title}
                onChange={(e) => setTitle(e.target.value)}
                required
              />
            </div>
            <div className="mb-2">
              <textarea
                className="form-control"
                placeholder="Descrição"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
              />
            </div>
            <button type="submit" className="btn btn-primary">Adicionar</button>
          </form>
        </div>
      </div>

      <div className="row">
        {loading ? (
          <div className="col-12">
            <div className="alert alert-info">Carregando tarefas...</div>
          </div>
        ) : tasks.length === 0 ? (
          <div className="col-12">
            <div className="alert alert-info">Nenhuma tarefa cadastrada ainda. Adicione uma nova tarefa acima!</div>
          </div>
        ) : (
          tasks.map(task => (
            <div key={task.id} className="col-md-4 mb-3">
              <div className="card">
                <div className="card-body">
                  <h5 className="card-title">{task.title}</h5>
                  <p className="card-text">{task.description}</p>
                  <span className={`badge ${task.status === 'PENDENTE' ? 'bg-warning' : 'bg-success'}`}>
                    {task.status}
                  </span>
                  <button 
                    className="btn btn-danger btn-sm float-end"
                    onClick={() => deleteTask(task.id)}
                  >
                    Deletar
                  </button>
                </div>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
}

export default TaskList;

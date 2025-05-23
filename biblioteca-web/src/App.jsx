import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/layout/Navbar';
import HomePage from './pages/HomePage';
import LivrosPage from './pages/LivrosPage';
import ClientesPage from './pages/ClientesPage';
import EmprestimosPage from './pages/EmprestimosPage';
import './App.css';

function App() {
    return (
        <Router>
            <div className="App">
                <Navbar />
                <div className="container" style={{ padding: '20px' }}>
                    <Routes>
                        <Route path="/" element={<HomePage />} />
                        <Route path="/livros" element={<LivrosPage />} />
                        <Route path="/clientes" element={<ClientesPage />} />
                        <Route path="/emprestimos" element={<EmprestimosPage />} />
                    </Routes>
                </div>
            </div>
        </Router>
    );
}

export default App;
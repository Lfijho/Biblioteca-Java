import React from 'react';
import { Link } from 'react-router-dom';
import './Navbar.css';

function Navbar() {
    return (
        <nav className="navbar">
            <Link to="/" className="navbar-brand">Biblioteca LF</Link>
            <ul className="navbar-nav">
                <li className="nav-item">
                    <Link to="/livros" className="nav-link">Livros</Link>
                </li>
                <li className="nav-item">
                    <Link to="/clientes" className="nav-link">Clientes</Link>
                </li>
                <li className="nav-item">
                    <Link to="/emprestimos" className="nav-link">Empréstimos</Link>
                </li>

            </ul>
        </nav>
    );
}

export default Navbar;
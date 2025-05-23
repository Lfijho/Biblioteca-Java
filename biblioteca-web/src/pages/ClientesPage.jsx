import React, { useState, useEffect } from 'react';
import ListaClientes from '../components/cliente/ListaClientes';
import FormularioCliente from '../components/cliente/FormularioCliente';
import { listarTodosClientes } from '../services/apiService';

function ClientesPage() {
    const [mostrarFormulario, setMostrarFormulario] = useState(false);
    const [clienteAtual, setClienteAtual] = useState(null);
    const [clientes, setClientes] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const carregarClientes = async () => {
        try {
            setLoading(true);
            const response = await listarTodosClientes();
            setClientes(response.data);
            setError(null);
        } catch (err) {
            setError(err.message || 'Erro ao buscar clientes.');
            console.error("Erro ao buscar clientes:", err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        carregarClientes();
    }, []);

    const handleNovoClienteClick = () => {
        setClienteAtual(null);
        setMostrarFormulario(true);
    };

    const handleEditarCliente = (cliente) => {
        setClienteAtual(cliente);
        setMostrarFormulario(true);
    };

    const handleFecharFormulario = () => {
        setMostrarFormulario(false);
        setClienteAtual(null);
    };

    const handleClienteSalvo = () => {
        handleFecharFormulario();
        carregarClientes();
    };

    return (
        <div>
            <h2>Gerenciamento de Clientes</h2>
            <button onClick={handleNovoClienteClick} className="primary" style={{ marginBottom: '20px' }}>
                Novo Cliente
            </button>

            {mostrarFormulario && (
                <FormularioCliente
                    clienteParaEditar={clienteAtual}
                    onFechar={handleFecharFormulario}
                    onSalvar={handleClienteSalvo}
                />
            )}

            <ListaClientes
                clientes={clientes}
                loading={loading}
                error={error}
                onEditar={handleEditarCliente}
                onDeletar={handleClienteSalvo}
            />
        </div>
    );
}

export default ClientesPage;
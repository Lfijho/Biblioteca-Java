import React from 'react';
import { deletarCliente } from '../../services/apiService';

function ListaClientes({ clientes, loading, error, onEditar, onDeletar }) {

    const handleDeletarClick = async (clienteId) => {
        if (window.confirm('Tem certeza que deseja deletar este cliente? Lembre-se que clientes com empréstimos ativos não podem ser deletados.')) {
            try {
                await deletarCliente(clienteId);
                alert('Cliente deletado com sucesso!');
                onDeletar();
            } catch (err) {
                console.error("Erro ao deletar cliente:", err.response?.data || err.message);
                alert(`Falha ao deletar cliente: ${err.response?.data || err.message}`);
            }
        }
    };

    if (loading) {
        return <p>Carregando clientes...</p>;
    }

    if (error) {
        return <p style={{ color: 'red' }}>Erro: {error}</p>;
    }

    return (
        <div>
            {clientes.length === 0 ? (
                <p>Nenhum cliente encontrado.</p>
            ) : (
                <ul style={{ listStyle: 'none', padding: 0 }}>
                    {clientes.map((cliente) => (
                        <li key={cliente.id} style={{ marginBottom: '15px', padding: '15px', border: '1px solid var(--border-color)', borderRadius: '6px', backgroundColor: 'var(--bg-container)' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                <div>
                                    <h4 style={{ marginTop: 0, marginBottom: '5px' }}>{cliente.nome}</h4>
                                    <small>Matrícula: {cliente.matricula}</small><br />
                                    <small>Telefone: {cliente.telefone || 'N/A'}</small><br />
                                    <small>Empréstimos Ativos: {cliente.numEmprestimos}</small>
                                </div>
                                <div style={{ display: 'flex', gap: '10px' }}>
                                    <button onClick={() => onEditar(cliente)} className="primary">Editar</button>
                                    <button onClick={() => handleDeletarClick(cliente.id)} style={{ backgroundColor: '#8B0000', color: 'white' }}>Deletar</button>
                                </div>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default ListaClientes;
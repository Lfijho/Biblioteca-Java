import React from 'react';
import { registrarDevolucao } from '../../services/apiService';

function ListaEmprestimos({ emprestimos, loading, error, onEmprestimoAtualizado }) {

    const handleDevolucaoClick = async (matriculaCliente, isbnLivro) => {
        if (window.confirm(`Registrar devolução do livro ${isbnLivro} pelo cliente ${matriculaCliente}?`)) {
            try {
                await registrarDevolucao({ matriculaCliente, isbnLivro });
                alert('Devolução registrada com sucesso!');
                if (onEmprestimoAtualizado) onEmprestimoAtualizado();
            } catch (err) {
                console.error("Erro ao registrar devolução:", err.response?.data || err.message);
                alert(`Falha ao registrar devolução: ${err.response?.data || 'Verifique os dados e tente novamente.'}`);
            }
        }
    };

    const formatDate = (dateString) => {
        if (!dateString) return 'N/A';
        const date = new Date(dateString);
        const adjustedDate = new Date(date.valueOf() + date.getTimezoneOffset() * 60 * 1000);
        return adjustedDate.toLocaleDateString('pt-BR');
    };


    if (loading) {
        return <p>Carregando empréstimos...</p>;
    }

    if (error) {
        return <p style={{ color: 'red' }}>Erro ao carregar empréstimos: {error}</p>;
    }

    return (
        <div>
            {emprestimos.length === 0 ? (
                <p>Nenhum empréstimo encontrado.</p>
            ) : (
                <ul style={{ listStyle: 'none', padding: 0 }}>
                    {emprestimos.map((emprestimo) => (
                        <li key={emprestimo.id} style={{ marginBottom: '15px', padding: '15px', border: '1px solid var(--border-color)', borderRadius: '6px', backgroundColor: 'var(--bg-container)' }}>
                            <h4 style={{ marginTop: 0, marginBottom: '5px' }}>Empréstimo ID: {emprestimo.id}</h4>
                            <p style={{ margin: '5px 0' }}>
                                <strong>Cliente:</strong> {emprestimo.cliente?.nome || 'N/A'} (Matrícula: {emprestimo.cliente?.matricula || 'N/A'})
                            </p>
                            <p style={{ margin: '5px 0' }}>
                                <strong>Livro:</strong> {emprestimo.livro?.titulo || 'N/A'} (ISBN: {emprestimo.livro?.isbn || 'N/A'})
                            </p>
                            <p style={{ margin: '5px 0' }}>
                                <small>Data Empréstimo: {formatDate(emprestimo.dataEmprestimo)}</small><br />
                                <small>Devolução Prevista: {formatDate(emprestimo.dataDevolucaoPrevista)}</small><br />
                                <small>Devolvido em: {emprestimo.dataDevolucaoReal ? formatDate(emprestimo.dataDevolucaoReal) : 'Não devolvido'}</small>
                            </p>
                            {!emprestimo.dataDevolucaoReal && emprestimo.cliente && emprestimo.livro && (
                                <button
                                    onClick={() => handleDevolucaoClick(emprestimo.cliente.matricula, emprestimo.livro.isbn)}
                                    className="primary"
                                    style={{ backgroundColor: 'var(--accent-primary)', marginTop: '10px' }}
                                >
                                    Registrar Devolução
                                </button>
                            )}
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default ListaEmprestimos;
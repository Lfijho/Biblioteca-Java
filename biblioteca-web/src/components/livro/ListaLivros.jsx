import React from 'react';
import { deletarLivro } from '../../services/apiService';

function ListaLivros({ livros, loading, error, onEditar, onDeletar }) {


    const handleDeletarClick = async (livroId) => {
        if (window.confirm('Tem certeza que deseja deletar este livro?')) {
            try {
                await deletarLivro(livroId);
                alert('Livro deletado com sucesso!');
                onDeletar();
            } catch (err) {
                console.error("Erro ao deletar livro:", err);
                alert(`Falha ao deletar livro: ${err.response?.data?.message || err.message}`);
            }
        }
    };

    if (loading) {
        return <p>Carregando livros...</p>;
    }

    if (error) {
        return <p style={{ color: 'red' }}>Erro: {error}</p>;
    }

    return (
        <div>
            {livros.length === 0 ? (
                <p>Nenhum livro encontrado.</p>
            ) : (
                <ul style={{ listStyle: 'none', padding: 0 }}>
                    {livros.map((livro) => (
                        <li key={livro.id} style={{ marginBottom: '15px', padding: '15px', border: '1px solid var(--border-color)', borderRadius: '6px', backgroundColor: 'var(--bg-container)' }}>
                            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
                                <div>
                                    <h4 style={{ marginTop: 0, marginBottom: '5px' }}>{livro.titulo}</h4>
                                    <small>ISBN: {livro.isbn}</small><br />
                                    <small>Autor: {livro.nomeAutor}</small><br />
                                    <small>Editora: {livro.editora} - Ano: {livro.anoPublicacao}</small>
                                </div>
                                <div style={{ display: 'flex', gap: '10px' }}>
                                    <button onClick={() => onEditar(livro)} className="primary">Editar</button>
                                    <button onClick={() => handleDeletarClick(livro.id)} style={{ backgroundColor: '#8B0000', color: 'white' }}>Deletar</button>
                                </div>
                            </div>
                        </li>
                    ))}
                </ul>
            )}
        </div>
    );
}

export default ListaLivros;
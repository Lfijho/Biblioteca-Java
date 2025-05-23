import React, { useState, useEffect } from 'react';
import ListaLivros from '../components/livro/ListaLivros';
import FormularioLivro from '../components/livro/FormularioLivro';
import { listarTodosLivros } from '../services/apiService';

function LivrosPage() {
    const [mostrarFormulario, setMostrarFormulario] = useState(false);
    const [livroAtual, setLivroAtual] = useState(null);
    const [livros, setLivros] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const carregarLivros = async () => {
        try {
            setLoading(true);
            const response = await listarTodosLivros();
            setLivros(response.data);
            setError(null);
        } catch (err) {
            setError(err.message || 'Erro ao buscar livros.');
            console.error("Erro ao buscar livros:", err);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        carregarLivros();
    }, []);

    const handleNovoLivroClick = () => {
        setLivroAtual(null);
        setMostrarFormulario(true);
    };

    const handleEditarLivro = (livro) => {
        setLivroAtual(livro);
        setMostrarFormulario(true);
    };

    const handleFecharFormulario = () => {
        setMostrarFormulario(false);
        setLivroAtual(null);
    };

    const handleLivroSalvo = () => {
        handleFecharFormulario();
        carregarLivros();
    };

    return (
        <div>
            <h2>Gerenciamento de Livros</h2>
            <button onClick={handleNovoLivroClick} className="primary" style={{ marginBottom: '20px' }}>
                Novo Livro
            </button>

            {mostrarFormulario && (
                <FormularioLivro
                    livroParaEditar={livroAtual}
                    onFechar={handleFecharFormulario}
                    onSalvar={handleLivroSalvo}
                />
            )}

            <ListaLivros
                livros={livros}
                loading={loading}
                error={error}
                onEditar={handleEditarLivro}
                onDeletar={handleLivroSalvo}
            />
        </div>
    );
}
export default LivrosPage;
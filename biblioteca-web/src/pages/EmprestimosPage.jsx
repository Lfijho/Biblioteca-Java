import React, { useState, useEffect } from 'react';
import FormularioEmprestimo from '../components/emprestimo/FormularioEmprestimo';
import ListaEmprestimos from '../components/emprestimo/ListaEmprestimos';
import { listarTodosEmprestimos, listarEmprestimosPorCliente, listarEmprestimosPorLivro } from '../services/apiService';

function EmprestimosPage() {
    const [mostrarFormulario, setMostrarFormulario] = useState(false);
    const [emprestimos, setEmprestimos] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [filtroMatricula, setFiltroMatricula] = useState('');
    const [filtroIsbn, setFiltroIsbn] = useState('');


    const carregarEmprestimos = async () => {
        try {
            setLoading(true);
            let response;
            if (filtroMatricula) {
                response = await listarEmprestimosPorCliente(filtroMatricula);
            } else if (filtroIsbn) {
                response = await listarEmprestimosPorLivro(filtroIsbn);
            } else {
                response = await listarTodosEmprestimos();
            }
            setEmprestimos(response.data);
            setError(null);
        } catch (err) {
            setError(err.message || 'Erro ao buscar empréstimos.');
            console.error("Erro ao buscar empréstimos:", err);
            setEmprestimos([]);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        carregarEmprestimos();
    }, [filtroMatricula, filtroIsbn]);


    const handleEmprestimoSalvoOuAtualizado = () => {
        setMostrarFormulario(false);
        carregarEmprestimos();
    };

    const limparFiltros = () => {
        setFiltroMatricula('');
        setFiltroIsbn('');

    };


    return (
        <div>
            <h2>Gerenciamento de Empréstimos</h2>
            <button onClick={() => setMostrarFormulario(!mostrarFormulario)} className="primary" style={{ marginBottom: '20px' }}>
                {mostrarFormulario ? 'Ocultar Formulário Empréstimo' : 'Novo Empréstimo'}
            </button>

            {mostrarFormulario && (
                <FormularioEmprestimo
                    onFechar={() => setMostrarFormulario(false)}
                    onSalvar={handleEmprestimoSalvoOuAtualizado}
                />
            )}

            <div style={{ marginBottom: '20px', padding: '15px', border: '1px solid var(--border-color)', borderRadius: '6px', backgroundColor: 'var(--bg-container)'}}>
                <h4>Filtrar Empréstimos</h4>
                <div style={{display: 'flex', gap: '10px', marginBottom: '10px'}}>
                    <input
                        type="text"
                        placeholder="Matrícula do Cliente"
                        value={filtroMatricula}
                        onChange={(e) => { setFiltroMatricula(e.target.value); setFiltroIsbn(''); }}
                    />
                    <input
                        type="text"
                        placeholder="ISBN do Livro"
                        value={filtroIsbn}
                        onChange={(e) => { setFiltroIsbn(e.target.value); setFiltroMatricula(''); }}
                    />
                </div>
                <button onClick={carregarEmprestimos} style={{marginRight: '10px'}}>Aplicar Filtros</button>
                <button onClick={limparFiltros}>Limpar Filtros</button>
            </div>


            <ListaEmprestimos
                emprestimos={emprestimos}
                loading={loading}
                error={error}
                onEmprestimoAtualizado={handleEmprestimoSalvoOuAtualizado}
            />
        </div>
    );
}

export default EmprestimosPage;
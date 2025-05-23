import React, { useState, useEffect } from 'react';
import { criarLivro, atualizarLivro } from '../../services/apiService';
import '../common/Form.css';

function FormularioLivro({ livroParaEditar, onFechar, onSalvar }) {
    const [formData, setFormData] = useState({
        titulo: '',
        isbn: '',
        nomeAutor: '',
        editora: '',
        anoPublicacao: ''
    });
    const [error, setError] = useState('');
    const [submitting, setSubmitting] = useState(false);

    const isEditMode = livroParaEditar !== null && livroParaEditar !== undefined;

    useEffect(() => {
        if (isEditMode && livroParaEditar) {
            setFormData({
                titulo: livroParaEditar.titulo || '',
                isbn: livroParaEditar.isbn || '',
                nomeAutor: livroParaEditar.nomeAutor || '',
                editora: livroParaEditar.editora || '',
                anoPublicacao: livroParaEditar.anoPublicacao || ''
            });
        } else {
            setFormData({
                titulo: '',
                isbn: '',
                nomeAutor: '',
                editora: '',
                anoPublicacao: ''
            });
        }
    }, [livroParaEditar, isEditMode]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSubmitting(true);

        if (!formData.titulo || !formData.isbn || !formData.nomeAutor || !formData.editora || !formData.anoPublicacao) {
            setError('Todos os campos são obrigatórios.');
            setSubmitting(false);
            return;
        }
        if (isNaN(parseInt(formData.anoPublicacao))) {
            setError('Ano de publicação deve ser um número.');
            setSubmitting(false);
            return;
        }

        const payload = {
            ...formData,
            anoPublicacao: formData.anoPublicacao ? parseInt(formData.anoPublicacao) : null
        };

        try {
            if (isEditMode) {
                await atualizarLivro(livroParaEditar.id, payload);
            } else {
                await criarLivro(payload);
            }
            onSalvar();
        } catch (err) {
            console.error("Erro ao salvar livro:", err.response?.data || err.message);
            setError(err.response?.data?.message || err.message || 'Falha ao salvar o livro.');
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div className="form-container">
            <h3>{isEditMode ? 'Editar Livro' : 'Adicionar Novo Livro'}</h3>
            {error && <p className="error-message">{error}</p>}
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="titulo">Título:</label>
                    <input type="text" name="titulo" id="titulo" value={formData.titulo} onChange={handleChange} />
                </div>
                <div className="form-group">
                    <label htmlFor="isbn">ISBN:</label>
                    <input type="text" name="isbn" id="isbn" value={formData.isbn} onChange={handleChange} />
                </div>
                <div className="form-group">
                    <label htmlFor="nomeAutor">Autor:</label>
                    <input type="text" name="nomeAutor" id="nomeAutor" value={formData.nomeAutor} onChange={handleChange} />
                </div>
                <div className="form-group">
                    <label htmlFor="editora">Editora:</label>
                    <input type="text" name="editora" id="editora" value={formData.editora} onChange={handleChange} />
                </div>
                <div className="form-group">
                    <label htmlFor="anoPublicacao">Ano de Publicação:</label>
                    <input type="number" name="anoPublicacao" id="anoPublicacao" value={formData.anoPublicacao} onChange={handleChange} />
                </div>
                <div className="form-actions"> {
                    <button type="submit" className="primary" disabled={submitting}>
                        {submitting ? 'Salvando...' : (isEditMode ? 'Atualizar Livro' : 'Adicionar Livro')}
                    </button> }
                    <button type="button" onClick={onFechar} disabled={submitting}>
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    );
}

export default FormularioLivro;
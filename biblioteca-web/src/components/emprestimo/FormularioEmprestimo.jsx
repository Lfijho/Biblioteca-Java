import React, { useState } from 'react';
import { realizarEmprestimo } from '../../services/apiService';

function FormularioEmprestimo({ onFechar, onSalvar }) {
    const [formData, setFormData] = useState({
        matriculaCliente: '',
        isbnLivro: '',
        dataDevolucaoPrevista: ''
    });
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const [submitting, setSubmitting] = useState(false);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');
        setSubmitting(true);

        if (!formData.matriculaCliente || !formData.isbnLivro) {
            setError('Matrícula do Cliente e ISBN do Livro são obrigatórios.');
            setSubmitting(false);
            return;
        }

        const payload = {
            matriculaCliente: formData.matriculaCliente,
            isbnLivro: formData.isbnLivro,
        };

        if (formData.dataDevolucaoPrevista) {
            payload.dataDevolucaoPrevista = formData.dataDevolucaoPrevista;
        }

        try {
            const response = await realizarEmprestimo(payload);
            setSuccess(`Empréstimo realizado com sucesso! ID: ${response.data.id}`);
            setFormData({ matriculaCliente: '', isbnLivro: '', dataDevolucaoPrevista: '' });
            if (onSalvar) onSalvar(); // Callback para atualizar lista, etc.
        } catch (err) {
            console.error("Erro ao realizar empréstimo:", err.response?.data || err.message);
            setError(err.response?.data || err.message || 'Falha ao realizar o empréstimo.');
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div style={{ border: '1px solid var(--border-color)', padding: '20px', marginBottom: '20px', borderRadius: '6px', backgroundColor: 'var(--bg-container)' }}>
            <h3>Realizar Novo Empréstimo</h3>
            {error && <p style={{ color: 'red' }}>Erro: {error}</p>}
            {success && <p style={{ color: 'green' }}>{success}</p>}
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label htmlFor="matriculaCliente" style={{ display: 'block', marginBottom: '5px' }}>Matrícula do Cliente:</label>
                    <input type="text" name="matriculaCliente" id="matriculaCliente" value={formData.matriculaCliente} onChange={handleChange} style={{ width: 'calc(100% - 12px)' }} />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label htmlFor="isbnLivro" style={{ display: 'block', marginBottom: '5px' }}>ISBN do Livro:</label>
                    <input type="text" name="isbnLivro" id="isbnLivro" value={formData.isbnLivro} onChange={handleChange} style={{ width: 'calc(100% - 12px)' }} />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label htmlFor="dataDevolucaoPrevista" style={{ display: 'block', marginBottom: '5px' }}>Data Devolução Prevista (Opcional):</label>
                    <input type="date" name="dataDevolucaoPrevista" id="dataDevolucaoPrevista" value={formData.dataDevolucaoPrevista} onChange={handleChange} style={{ width: 'calc(100% - 12px)' }} />
                </div>
                <div style={{ marginTop: '15px' }}>
                    <button type="submit" className="primary" disabled={submitting}>
                        {submitting ? 'Registrando...' : 'Realizar Empréstimo'}
                    </button>
                    {onFechar && (
                        <button type="button" onClick={onFechar} style={{ marginLeft: '10px' }} disabled={submitting}>
                            Fechar
                        </button>
                    )}
                </div>
            </form>
        </div>
    );
}

export default FormularioEmprestimo;
import React, { useState, useEffect } from 'react';
import { criarCliente, atualizarCliente } from '../../services/apiService';

function FormularioCliente({ clienteParaEditar, onFechar, onSalvar }) {
    const [formData, setFormData] = useState({
        nome: '',
        matricula: '',
        telefone: ''
    });
    const [error, setError] = useState('');
    const [submitting, setSubmitting] = useState(false);

    const isEditMode = clienteParaEditar !== null && clienteParaEditar !== undefined;

    useEffect(() => {
        if (isEditMode && clienteParaEditar) {
            setFormData({
                nome: clienteParaEditar.nome || '',
                matricula: clienteParaEditar.matricula || '',
                telefone: clienteParaEditar.telefone || ''
            });
        } else {
            setFormData({
                nome: '',
                matricula: '',
                telefone: ''
            });
        }
    }, [clienteParaEditar, isEditMode]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData(prev => ({ ...prev, [name]: value }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSubmitting(true);

        if (!formData.nome || !formData.matricula) {
            setError('Nome e Matrícula são obrigatórios.');
            setSubmitting(false);
            return;
        }

        const payload = { ...formData };


        try {
            if (isEditMode) {
                await atualizarCliente(clienteParaEditar.id, payload);
            } else {
                await criarCliente(payload);
            }
            onSalvar();
        } catch (err) {
            console.error("Erro ao salvar cliente:", err.response?.data || err.message);
            setError(err.response?.data || err.message || 'Falha ao salvar o cliente.');
        } finally {
            setSubmitting(false);
        }
    };

    return (
        <div style={{ border: '1px solid var(--border-color)', padding: '20px', marginBottom: '20px', borderRadius: '6px', backgroundColor: 'var(--bg-container)' }}>
            <h3>{isEditMode ? 'Editar Cliente' : 'Adicionar Novo Cliente'}</h3>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div style={{ marginBottom: '10px' }}>
                    <label htmlFor="nome" style={{ display: 'block', marginBottom: '5px' }}>Nome:</label>
                    <input type="text" name="nome" id="nome" value={formData.nome} onChange={handleChange} style={{ width: 'calc(100% - 12px)' }} />
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label htmlFor="matricula" style={{ display: 'block', marginBottom: '5px' }}>Matrícula:</label>
                    <input type="text" name="matricula" id="matricula" value={formData.matricula} onChange={handleChange} style={{ width: 'calc(100% - 12px)' }} disabled={isEditMode} />
                    {isEditMode && <small style={{display: 'block', color: 'var(--text-secondary)'}}>A matrícula não pode ser alterada após o cadastro.</small>}
                </div>
                <div style={{ marginBottom: '10px' }}>
                    <label htmlFor="telefone" style={{ display: 'block', marginBottom: '5px' }}>Telefone:</label>
                    <input type="text" name="telefone" id="telefone" value={formData.telefone} onChange={handleChange} style={{ width: 'calc(100% - 12px)' }} />
                </div>
                <div style={{ marginTop: '15px' }}>
                    <button type="submit" className="primary" disabled={submitting}>
                        {submitting ? 'Salvando...' : (isEditMode ? 'Atualizar Cliente' : 'Adicionar Cliente')}
                    </button>
                    <button type="button" onClick={onFechar} style={{ marginLeft: '10px' }} disabled={submitting}>
                        Cancelar
                    </button>
                </div>
            </form>
        </div>
    );
}

export default FormularioCliente;
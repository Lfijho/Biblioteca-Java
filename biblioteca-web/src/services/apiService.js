import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1';


const apiClient = axios.create({
    baseURL: API_BASE_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});



export const listarTodosLivros = () => {
    return apiClient.get('/livros');
};

export const buscarLivroPorId = (id) => {
    return apiClient.get(`/livros/${id}`);
};

export const criarLivro = (livroData) => {
    return apiClient.post('/livros', livroData);
};

export const atualizarLivro = (id, livroData) => {
    return apiClient.put(`/livros/${id}`, livroData);
};

export const deletarLivro = (id) => {
    return apiClient.delete(`/livros/${id}`);
};



export const listarTodosClientes = () => {
    return apiClient.get('/clientes');
};

export const buscarClientePorId = (id) => {
    return apiClient.get(`/clientes/${id}`);
};

export const buscarClientePorMatricula = (matricula) => {
    return apiClient.get(`/clientes/matricula/${matricula}`);
};

export const criarCliente = (clienteData) => {
    return apiClient.post('/clientes', clienteData);
};

export const atualizarCliente = (id, clienteData) => {
    return apiClient.put(`/clientes/${id}`, clienteData);
};

export const deletarCliente = (id) => {
    return apiClient.delete(`/clientes/${id}`);
};


export const realizarEmprestimo = (emprestimoData) => {
    return apiClient.post('/emprestimos/realizar', emprestimoData);
};

export const registrarDevolucao = (devolucaoData) => {
    return apiClient.post('/emprestimos/devolver', devolucaoData);
};

export const listarTodosEmprestimos = () => {
    return apiClient.get('/emprestimos');
};

export const listarEmprestimosPorCliente = (matriculaCliente) => {
    return apiClient.get(`/emprestimos/cliente/${matriculaCliente}`);
};

export const listarEmprestimosPorLivro = (isbnLivro) => {
    return apiClient.get(`/emprestimos/livro/${isbnLivro}`);
};



export default apiClient;
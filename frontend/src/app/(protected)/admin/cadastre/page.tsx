'use client';

import React, { useState, useMemo, useEffect } from 'react';
import Swal from "sweetalert2";
import api from '@/services/api';

type PasswordValidationResult = {
    isValid: boolean;
    errors: string[];
};

function validateStrongPassword(password: string): PasswordValidationResult {
    const errors: string[] = [];
    if (password.length < 6) errors.push("Pelo menos 6 caracteres.");
    if (!/[A-Z]/.test(password)) errors.push("Pelo menos 1 letra maiúscula.");
    if (!/[0-9]/.test(password)) errors.push("Pelo menos 1 número.");
    if (!/[!@#$%^&*()_+{}\[\]:;<>,.?~\\-]/.test(password)) errors.push("Pelo menos 1 caractere especial.");
    if (/\s/.test(password)) errors.push("Não pode conter espaços.");
    return { isValid: errors.length === 0, errors };
}

function validateEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

type User = {
    id: number | string;
    nome: string;
    perfil: string;
    telefone: string;
    email: string;
};

const EyeIcon = () => (<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6 text-gray-500"><path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" /><path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" /></svg>);
const EyeSlashIcon = () => (<svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6 text-gray-500"><path strokeLinecap="round" strokeLinejoin="round" d="M3.98 8.223A10.477 10.477 0 0 0 1.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.451 10.451 0 0 1 12 4.5c4.756 0 8.773 3.162 10.065 7.498a10.522 10.522 0 0 1-4.293 5.774M6.228 6.228 3 3m3.228 3.228 3.65 3.65m7.894 7.894L21 21m-3.228-3.228-3.65-3.65m0 0a3 3 0 1 0-4.243-4.243m4.242 4.242L9.88 9.88" /></svg>);

const RegisterForm: React.FC = () => {
    const [name, setName] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [phone, setPhone] = useState<string>('');
    const [userType, setUserType] = useState<string>('cliente');
    const [password, setPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState<boolean>(false);

    const [users, setUsers] = useState<User[]>([]);
    const [editingUserId, setEditingUserId] = useState<number | string | null>(null);
    const [userTypeSearch, setUserTypeSearch] = useState<string>('0');
    const [dataSearch, setDataSearch] = useState<string>('');

    const [emailError, setEmailError] = useState<string>('');
    const [passwordErrors, setPasswordErrors] = useState<string[]>([]);
    const [confirmPasswordError, setConfirmPasswordError] = useState<string>('');

    useEffect(() => {
        if (email && !validateEmail(email)) {
            setEmailError('Por favor, insira um email válido.');
        } else {
            setEmailError('');
        }
    }, [email]);

    useEffect(() => {
        if (password) {
            setPasswordErrors(validateStrongPassword(password).errors);
        } else {
            setPasswordErrors([]);
        }
    }, [password]);

    useEffect(() => {
        if (confirmPassword && password !== confirmPassword) {
            setConfirmPasswordError('As senhas não coincidem.');
        } else {
            setConfirmPasswordError('');
        }
    }, [password, confirmPassword]);

    const formatPhone = (value: string) => {
        let cleanValue = value.replace(/\D/g, '');
        if (cleanValue.length > 11) cleanValue = cleanValue.substring(0, 11);
        if (cleanValue.length > 7) {
            return `(${cleanValue.substring(0, 2)}) ${cleanValue.substring(2, 7)}-${cleanValue.substring(7)}`;
        } else if (cleanValue.length > 2) {
            return `(${cleanValue.substring(0, 2)}) ${cleanValue.substring(2)}`;
        } else if (cleanValue.length > 0) {
            return `(${cleanValue}`;
        }
        return cleanValue;
    };

    const fetchUsers = async () => {
        try {
            const response = await api.get('/users');
            const usersData = response.data.content;
            setUsers(Array.isArray(usersData) ? usersData : []);
        } catch (error) {
            console.error('Erro ao buscar usuários:', error);
            Swal.fire('Erro de Rede', 'Não foi possível carregar a lista de usuários.', 'error');
            setUsers([]);
        }
    };

    useEffect(() => {
        fetchUsers();
    }, []);

    const filteredUsers = useMemo(() => {
        if (!Array.isArray(users)) return [];
        return users.filter((user) => {
            const typeMatch = userTypeSearch === '0' || user.perfil.toLowerCase() === userTypeSearch.toLowerCase();
            const searchMatch = dataSearch === '' ||
                user.nome.toLowerCase().includes(dataSearch.toLowerCase()) ||
                user.telefone.replace(/\D/g, '').includes(dataSearch.replace(/\D/g, ''));
            return typeMatch && searchMatch;
        });
    }, [users, dataSearch, userTypeSearch]);

    const handlePhoneChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        setPhone(formatPhone(e.target.value));
    };

    const handleCancelEdit = () => {
        setEditingUserId(null);
        setName('');
        setEmail('');
        setPhone('');
        setUserType('cliente');
        setPassword('');
        setConfirmPassword('');
    };

    const handleEditClick = async (userId: number | string) => {
        try {
            console.log(users);
            const userToEdit = users.find((user: User) => user.id === userId);

            if (!userToEdit) {
                Swal.fire('Erro', 'Usuário não encontrado para edição.', 'error');
                return;
            }

            setEditingUserId(userToEdit.id);
            setName(userToEdit.nome);
            setEmail(userToEdit.email);
            setPhone(formatPhone(userToEdit.telefone));
            setUserType(userToEdit.perfil);
            setPassword('');
            setConfirmPassword('');


            window.scrollTo({ top: 0, behavior: 'smooth' });
        } catch (error) {
            console.error(`Erro ao buscar usuário ${userId}:`, error);
            Swal.fire('Erro', 'Não foi possível carregar os dados do usuário para edição.', 'error');
        }
    };

    const handleDeleteClick = async (userId: number | string) => {
        try {
            const response = await api.delete(`/users/${userId}`);
            if (response.status === 204)
                Swal.fire('Sucesso', 'Usuário excluído com sucesso!', 'success');
            else
                Swal.fire('Erro', 'Erro ao excluir usuário', 'error');
            fetchUsers();
        } catch (error) {
            console.error(`Erro ao buscar usuário ${userId}:`, error);
            Swal.fire('Erro', 'Não foi possível carregar os dados do usuário para edição.', 'error');
        }
    };

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();

        if (password && password !== confirmPassword) {
            Swal.fire('Erro de validação', 'As senhas não coincidem!', 'warning');
            return;
        }

        if (password && !validateStrongPassword(password).isValid) {
            Swal.fire('Senha Fraca', 'Sua senha não atende aos requisitos de segurança.', 'error');
            return;
        }

        const payload = {
            nome: name,
            email: email,
            telefone: phone,
            perfil: userType,
            ...(password && { senha: password })
        };

        try {
            if (editingUserId) {
                await api.put(`/users/${editingUserId}`, payload);
                Swal.fire('Sucesso!', 'Usuário atualizado com sucesso!', 'success');
            } else {
                await api.post('/users', payload);
                Swal.fire('Sucesso!', 'Conta criada com sucesso!', 'success');
            }
            handleCancelEdit();
            fetchUsers();
        } catch (error) {
            console.error('Erro ao salvar usuário:', error);
        }
    };

    return (
        <section className='w-full flex justify-center'>
            <div className='bg-[#D9D9D9] p-2 sm:p-5 sm:m-5 w-full md:w-[90%] mx-auto rounded-2xl grid grid-cols-1 lg:grid-cols-2 gap-8'>
                <div className="w-full">
                    <div className="bg-white rounded-2xl shadow-md w-full">
                        <h2 className="text-xl sm:text-2xl font-extrabold text-center uppercase bg-[#492004] text-white rounded-t-2xl p-4 sm:p-6">
                            {editingUserId ? 'Editar Usuário' : 'Crie uma conta'}
                        </h2>
                        <form onSubmit={handleSubmit} className="space-y-6 px-4 sm:px-8 pb-8 pt-4 w-full">
                            <div>
                                <label htmlFor="name" className="block text-sm sm:text-base uppercase font-extrabold mb-1">Nome: </label>
                                <input type="text" id="name" className="w-full border-b-2 border-gray-300 p-2 focus:outline-none focus:border-[#492004]" value={name} onChange={(e) => setName(e.target.value)} placeholder="Digite seu nome completo" required />
                            </div>
                            <div>
                                <label htmlFor="email" className="block text-sm sm:text-base uppercase font-extrabold mb-1">Email: </label>
                                <input type="email" id="email" className="w-full border-b-2 border-gray-300 p-2 focus:outline-none focus:border-[#492004]" value={email} onChange={(e) => setEmail(e.target.value)} placeholder='Digite seu email' required />
                                {emailError && <p className="mt-2 px-2 text-sm text-red-600">{emailError}</p>}
                            </div>
                            <div>
                                <label htmlFor="phone" className="block text-sm sm:text-base uppercase font-extrabold mb-1">Telefone: </label>
                                <input type="tel" id="phone" className="w-full border-b-2 border-gray-300 p-2 focus:outline-none focus:border-[#492004]" value={phone} onChange={handlePhoneChange} placeholder="(__) _____-____" minLength={14} maxLength={15} required />
                            </div>
                            <div>
                                <label htmlFor="usertype" className="block text-sm sm:text-base uppercase font-extrabold mb-1">Tipo de Usuario: </label>
                                <select name="usertype" id="usertype" className="w-full p-2 border-b-2 border-gray-300 focus:outline-none focus:border-[#492004] bg-transparent" value={userType} onChange={(e) => setUserType(e.target.value)} required>
                                    <option value="barbeiro">Barbeiro</option>
                                    <option value="cliente">Cliente</option>
                                </select>
                            </div>
                            <div>
                                <label htmlFor="password" className="block text-sm sm:text-base uppercase font-extrabold mb-1">Senha: </label>
                                <div className="relative w-full">
                                    <input type={showPassword ? 'text' : 'password'} id="password" className="w-full border-b-2 border-gray-300 p-2 focus:outline-none focus:border-[#492004] pr-10" value={password} onChange={(e) => setPassword(e.target.value)} placeholder={editingUserId ? "Deixe em branco para não alterar" : "Digite sua senha"} required={!editingUserId} />
                                    <button type="button" onClick={() => setShowPassword(!showPassword)} className="absolute inset-y-0 right-0 pr-3 flex items-center">
                                        {showPassword ? <EyeSlashIcon /> : <EyeIcon />}
                                    </button>
                                </div>
                                {password && passwordErrors.length > 0 && (
                                    <div className="mt-2 text-xs text-red-600 space-y-1">
                                        <ul className="list-disc list-inside pl-2">
                                            {passwordErrors.map(error => <li key={error}>{error}</li>)}
                                        </ul>
                                    </div>
                                )}
                            </div>
                            <div>
                                <label htmlFor="confirmPassword" className="block text-sm sm:text-base uppercase font-extrabold mb-1">Confirmar Senha: </label>
                                <div className="relative w-full">
                                    <input type={showConfirmPassword ? 'text' : 'password'} id="confirmPassword" className="w-full border-b-2 border-gray-300 p-2 focus:outline-none focus:border-[#492004] pr-10" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} placeholder="Confirme sua senha" required={!editingUserId || !!password} />
                                    <button type="button" onClick={() => setShowConfirmPassword(!showConfirmPassword)} className="absolute inset-y-0 right-0 pr-3 flex items-center">
                                        {showConfirmPassword ? <EyeSlashIcon /> : <EyeIcon />}
                                    </button>
                                </div>
                                {confirmPasswordError && <p className="mt-2 text-sm text-red-600 pl-2">{confirmPasswordError}</p>}
                            </div>
                            <div className="pt-4 flex flex-col sm:flex-row items-center gap-4">
                                <button type="submit" className="w-full py-3 px-4 border-0 rounded-md shadow-sm text-white font-extrabold text-lg uppercase bg-[#492004] hover:bg-opacity-90 transition-colors">
                                    {editingUserId ? 'Salvar Alterações' : 'Cadastrar'}
                                </button>
                                {editingUserId && (
                                    <button type="button" onClick={handleCancelEdit} className="w-full sm:w-auto py-3 px-6 border border-gray-400 rounded-md shadow-sm text-gray-700 font-bold bg-gray-200 hover:bg-gray-300 transition-colors">
                                        Cancelar
                                    </button>
                                )}
                            </div>
                        </form>
                    </div>
                </div>

                <div className="w-full p-0 sm:p-4">
                    <div className="space-y-4">
                        <div className='flex items-center justify-center bg-white rounded-lg p-2 shadow-sm'>
                            <label htmlFor="dataSearch" className="text-gray-500 mr-2"><svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6"><path strokeLinecap="round" strokeLinejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" /></svg></label>
                            <input type="text" className="w-full h-full outline-none bg-transparent" name="dataSearch" id='dataSearch' placeholder='Pesquisar por nome ou telefone' value={dataSearch} onChange={(e) => setDataSearch(e.target.value)} />
                        </div>
                        <div className='bg-white rounded-lg p-2 shadow-sm'>
                            <select name="userTypeSearch" id="userTypeSearch" className='w-full h-full p-2 border-0 focus:outline-none bg-transparent' value={userTypeSearch} onChange={(e) => setUserTypeSearch(e.target.value)}>
                                <option value="0">Filtrar por tipo...</option>
                                <option value="barbeiro">Barbeiro</option>
                                <option value="cliente">Cliente</option>
                            </select>
                        </div>
                        <div className='bg-white rounded-2xl shadow-md overflow-auto max-h-[460px]'>
                            <table className='w-full text-left text-sm sm:text-base'>
                                <thead className='text-black border-b-2 sticky top-0 bg-white'>
                                    <tr>
                                        <th className='p-3'>Nome</th>
                                        <th className='p-3'>Tipo</th>
                                        <th className='p-3'>Telefone</th>
                                        <th className='p-3'>Ações</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {filteredUsers.map((user) => (
                                        <tr key={user.id} className='border-b border-gray-200 hover:bg-gray-50'>
                                            <td className='p-3'>{user.nome}</td>
                                            <td className='p-3 capitalize'>{user.perfil}</td>
                                            <td className='p-3'>{formatPhone(user.telefone)}</td>
                                            <td className='p-3'>
                                                <button onClick={() => handleEditClick(user.id)} className='m-2 bg-transparent border border-blue-700 text-blue-700 hover:bg-blue-700 hover:text-white text-xs sm:text-sm p-2 rounded-lg transition-colors'>
                                                    Editar
                                                </button>
                                                <button onClick={() => handleDeleteClick(user.id)} className='m-2 bg-transparent border border-red-700 text-red-700 hover:bg-red-700 hover:text-white text-xs sm:text-sm p-2 rounded-lg transition-colors'>
                                                    Excluir
                                                </button>
                                            </td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    );
};

export default RegisterForm;

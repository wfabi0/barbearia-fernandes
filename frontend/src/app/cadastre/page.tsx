'use client';

import Swal from "sweetalert2";
import React, { useState, useEffect } from 'react';
import { API_PATH } from '@/constants/constants';

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
    return {
        isValid: errors.length === 0,
        errors: errors,
    };
}

function validateEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}


const RegisterForm: React.FC = () => {
    const [name, setName] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [phone, setPhone] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState<boolean>(false);

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
            const validationResult = validateStrongPassword(password);
            setPasswordErrors(validationResult.errors);
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


    const handlePhoneChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length > 11) value = value.substring(0, 11);

        if (value.length > 7) {
            value = `(${value.substring(0, 2)}) ${value.substring(2, 7)}-${value.substring(7)}`;
        } else if (value.length > 2) {
            value = `(${value.substring(0, 2)}) ${value.substring(2)}`;
        } else if (value.length > 0) {
            value = `(${value}`;
        }
        setPhone(value);
    };

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        if (password !== confirmPassword) {
            Swal.fire({ title: 'Erro', text: 'As senhas não coincidem!', icon: 'warning' });
            return;
        }
        if (!validateStrongPassword(password).isValid) {
            Swal.fire({ title: 'Senha Fraca', text: 'Sua senha não atende a todos os requisitos de segurança.', icon: 'error' });
            return;
        }

        try {
            const payload = { nome: name, email, telefone: phone, senha: password };

            const response = await fetch(API_PATH + '/users', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload),
            });

            const result = await response.json();

            if (!response.ok) {
                throw new Error(result.message || 'Falha no cadastro. Tente novamente.');
            }

            Swal.fire({
                title: 'Sucesso!',
                text: 'Conta criada com sucesso!',
                icon: 'success',
                confirmButtonText: 'OK'
            });

            setName('');
            setEmail('');
            setPhone('');
            setPassword('');
            setConfirmPassword('');
            setShowPassword(false);
            setShowConfirmPassword(false);

        } catch (error: unknown) {
            if (error instanceof Error) {
                Swal.fire({
                    title: 'Erro',
                    text: 'Ocorreu um erro ao enviar o formulário.',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            }
        }
    }

    return (
        <div className="flex items-center justify-center p-4">
            <div className="bg-white p-4 rounded-lg shadow-md max-w-lg w-full mx-auto">
                <h2 className="text-2xl font-extrabold text-center mb-6 uppercase">Crie uma conta</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="name" className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]">Nome: </label>
                            <input type="text" id="name" className="flex-grow border-black p-2 focus:outline-none border-0" value={name} onChange={(e) => setName(e.target.value)} placeholder="Digite seu nome completo" required />
                        </div>
                        <hr className='w-full text-black' />
                    </div>
                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="email" className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]">Email: </label>
                            <input type="email" id="email" className="flex-grow border-black p-2 focus:outline-none border-0" value={email} onChange={(e) => setEmail(e.target.value)} placeholder='Digite seu email' required />
                        </div>
                        <hr className='w-full text-black' />
                        {emailError && (
                            <p className="mt-2 px-2 text-sm text-red-600">{emailError}</p>
                        )}
                    </div>
                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="phone" className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]">Telefone: </label>
                            <input type="tel" id="phone" className="flex-grow border-black p-2 focus:outline-none border-0" value={phone} onChange={handlePhoneChange} placeholder="(__) _____-____" minLength={14} maxLength={15} required />
                        </div>
                        <hr className='w-full text-black' />
                    </div>

                    <div>
                        <div className="flex items-center w-full">
                            <label className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]" htmlFor="password">Senha: </label>
                            <div className="relative mt-1 flex items-center w-full">
                                <input className="grow border-0 border-black p-2 focus:outline-none" type={showPassword ? 'text' : 'password'} id="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Digite sua senha" required />
                                <button className="flex-none absolute right-1 w-auto" type="button" onClick={() => setShowPassword(!showPassword)}>
                                    {showConfirmPassword ? "🙈" : "👁️"}
                                </button>
                            </div>
                        </div>
                        <hr className='w-full text-black' />

                        {password && passwordErrors.length > 0 && (
                            <div className="mt-2 px-2 text-xs text-red-600">
                                <ul className="list-disc list-inside">
                                    {passwordErrors.map(error => <li key={error}>{error}</li>)}
                                </ul>
                            </div>
                        )}
                    </div>

                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="confirmPassword" className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]">Confirmar Senha: </label>
                            <div className="relative mt-1 flex items-center w-full">
                                <input type={showConfirmPassword ? 'text' : 'password'} id="confirmPassword" className="grow border-0 border-black p-2 focus:outline-none" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} placeholder="Confirme sua senha" required />
                                <button type="button" onClick={() => setShowConfirmPassword(!showConfirmPassword)} className="flex-none absolute right-1">
                                    {showConfirmPassword ? "🙈" : "👁️"}
                                </button>
                            </div>
                        </div>
                        <hr className='w-full text-black' />
                        {confirmPasswordError && (
                            <p className="mt-2 px-2 text-sm text-red-600">{confirmPasswordError}</p>
                        )}
                    </div>

                    <div className="flex justify-center pt-4">
                        <button type="submit" className="w-[45%] py-3 px-4 border-0 rounded-md shadow-sm text-white font-extrabold text-lg uppercase bg-gradient-to-r from-[#1100ff] to-[#ff0000] hover:from-[#4639ff] hover:to-[#fa3030]">
                            Criar
                        </button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default RegisterForm;
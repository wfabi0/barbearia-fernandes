'use client';

import Swal from "sweetalert2";
import React, { useState } from 'react';

const RegisterForm: React.FC = () => {
    const [name, setName] = useState<string>('');
    const [email, setEmail] = useState<string>('');
    const [phone, setPhone] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const [confirmPassword, setConfirmPassword] = useState<string>('');
    const [showPassword, setShowPassword] = useState<boolean>(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState<boolean>(false);

    const handlePhoneChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        let value = e.target.value.replace(/\D/g, '');
        if (value.length > 11) {
            value = value.substring(0, 11);
        }

        if (value.length > 2 && value.length <= 7) {
            value = `(${value.substring(0, 2)}) ${value.substring(2)}`;
        } else if (value.length > 7) {
            value = `(${value.substring(0, 2)}) ${value.substring(2, 7)}-${value.substring(7)}`;
        } else if (value.length > 0) {
            value = `(${value}`;
        }
        setPhone(value);
    };

    type PasswordValidationResult = {
        isValid: boolean;
        errors: string[];
    };

    function validateStrongPassword(password: string): PasswordValidationResult {
        const errors: string[] = [];

        if (password.length < 6) {
            errors.push("A senha deve ter pelo menos 6 caracteres.");
        }

        if (!/[A-Z]/.test(password)) {
            errors.push("A senha deve conter pelo menos 1 letra maiúscula.");
        }

        if (!/[0-9]/.test(password)) {
            errors.push("A senha deve conter pelo menos 1 número.");
        }

        if (!/[!@#$%^&*()_+{}\[\]:;<>,.?~\\-]/.test(password)) {
            errors.push("A senha deve conter pelo menos 1 caractere especial (!@#$%...).");
        }

        if (password.includes(' ')) {
            errors.push("A senha não deve conter espaços.");
        }

        return {
            isValid: errors.length === 0,
            errors: errors,
        };
    }


    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (password === confirmPassword) {
            if (!validateStrongPassword(password).isValid) {
                const validationResult = validateStrongPassword(password).errors;
                let errorMessage: string = "<p>A senha não atende aos requisitos de segurança:</p><ul class='list-none'>";
                validationResult.forEach(error => {
                    errorMessage += "<li> " + error + "</li>";
                });
                errorMessage += "</ul>";
                Swal.fire({
                    title: 'Erro de validação',
                    html: errorMessage,
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
                return;
            }
            //envia requisição para o backend

        }
        else {
            Swal.fire({
                title: 'Erro de validação',
                text: 'A senha e a confirmação de senha não coincidem!',
                icon: 'warning',
                confirmButtonText: 'OK'
            });
            return;
        }

        console.log({ name, email, phone, password });
        alert('Formulário enviado com sucesso! (Verifique o console para os dados)');
    };



    return (
        <div className="flex items-center justify-center p-4">
            <div className="bg-white p-4 rounded-lg shadow-md max-[480px]:w-[90%] sm:w-[60%] md:w-[50%] mx-6">
                <h2 className="text-2xl font-extrabold text-center mb-6 uppercase">Crie uma conta</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="name" className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]">Nome: </label>
                            <input
                                type="text"
                                id="name"
                                className="flex-grow border-black p-2 focus:outline-none border-0"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                placeholder="Digite seu nome completo"
                                required
                            />
                        </div>
                        <hr className='w-full text-black' />
                    </div>

                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="name" className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]">Email: </label>
                            <input
                                type="email"
                                id="email"
                                className="flex-grow border-black p-2 focus:outline-none border-0"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                placeholder='Digite seu email'
                                required
                            />
                        </div>
                        <hr className='w-full text-black' />
                    </div>

                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="phone" className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]">Telefone: </label>
                            <input
                                type="tel"
                                id="phone"
                                className="flex-grow border-black p-2 focus:outline-none border-0"
                                value={phone}
                                onChange={handlePhoneChange}
                                placeholder="(__) _____-____"
                                minLength={14}
                                maxLength={15}
                                pattern="^\(\d{2}\) \d{5}-\d{4}$"
                                title="Formato: (XX) XXXXX-XXXX"
                                required
                            />
                        </div>
                        <hr className='w-full text-black' />
                    </div>

                    <div>
                        <div className="flex items-center w-full">
                            <label className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]" htmlFor="password">Senha: </label>
                            <div className="relative mt-1 flex items-center w-full overflow-auto overflow-x-hidden">
                                <input className="grow border-0 border-black p-2 focus:outline-none" type={showPassword ? 'text' : 'password'} id="password" value={password} onChange={(e) => setPassword(e.target.value)} placeholder="Digite sua senha" required />
                                <button className="flex-none absolute right-1 w-auto" type="button" onClick={() => setShowPassword(!showPassword)}>
                                    {showPassword ? (
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                                            <path strokeLinecap="round" strokeLinejoin="round" d="M3.98 8.223A10.477 10.477 0 0 0 1.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.451 10.451 0 0 1 12 4.5c4.756 0 8.773 3.162 10.065 7.498a10.522 10.522 0 0 1-4.293 5.774M6.228 6.228 3 3m3.228 3.228 3.65 3.65m7.894 7.894L21 21m-3.228-3.228-3.65-3.65m0 0a3 3 0 1 0-4.243-4.243m4.242 4.242L9.88 9.88" />
                                        </svg>
                                    ) : (
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                                            <path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" />
                                            <path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                                        </svg>
                                    )}
                                </button>
                            </div>
                        </div>
                        <hr className='w-full text-black' />
                    </div>

                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="confirmPassword" className="uppercase font-extrabold mr-2 max-[480px]:text-[12px]">Confirmar Senha: </label>
                            <div className="relative mt-1 flex items-center w-full overflow-auto overflow-x-hidden">
                                <input
                                    type={showConfirmPassword ? 'text' : 'password'}
                                    id="confirmPassword"
                                    className="grow border-0 border-black p-2 focus:outline-none"
                                    value={confirmPassword}
                                    onChange={(e) => setConfirmPassword(e.target.value)}
                                    placeholder="Confirme sua senha"
                                    required
                                />
                                <button
                                    type="button"
                                    onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                                    className="flex-none absolute right-1"
                                >
                                    {showConfirmPassword ? (
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                                            <path strokeLinecap="round" strokeLinejoin="round" d="M3.98 8.223A10.477 10.477 0 0 0 1.934 12C3.226 16.338 7.244 19.5 12 19.5c.993 0 1.953-.138 2.863-.395M6.228 6.228A10.451 10.451 0 0 1 12 4.5c4.756 0 8.773 3.162 10.065 7.498a10.522 10.522 0 0 1-4.293 5.774M6.228 6.228 3 3m3.228 3.228 3.65 3.65m7.894 7.894L21 21m-3.228-3.228-3.65-3.65m0 0a3 3 0 1 0-4.243-4.243m4.242 4.242L9.88 9.88" />
                                        </svg>
                                    ) : (
                                        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth={1.5} stroke="currentColor" className="size-6">
                                            <path strokeLinecap="round" strokeLinejoin="round" d="M2.036 12.322a1.012 1.012 0 0 1 0-.639C3.423 7.51 7.36 4.5 12 4.5c4.638 0 8.573 3.007 9.963 7.178.07.207.07.431 0 .639C20.577 16.49 16.64 19.5 12 19.5c-4.638 0-8.573-3.007-9.963-7.178Z" />
                                            <path strokeLinecap="round" strokeLinejoin="round" d="M15 12a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
                                        </svg>
                                    )}
                                </button>
                            </div>
                        </div>
                        <hr className='w-full text-black' />
                    </div>
                    <button
                        type="submit"
                        className="m-auto w-[45%] h-[] flex items-center justify-center mt-[5%] py-4 px-4 border-0 rounded-md shadow-sm text-white font-extrabold text-lg uppercase
                        bg-gradient-to-r from-[#1100ff] to-[#ff0000] hover:bg-gradient-to-r hover:from-[#4639ff] hover:to-[#fa3030]"
                    >
                        Criar
                    </button>
                </form>
            </div>
        </div>

    );
};

export default RegisterForm;
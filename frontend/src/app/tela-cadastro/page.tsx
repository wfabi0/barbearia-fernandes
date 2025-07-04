'use client'; // This directive is important for client-side interactivity in Next.js 13+

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
        let value = e.target.value.replace(/\D/g, ''); // Remove non-digits
        if (value.length > 11) {
            value = value.substring(0, 11); // Max 11 digits for Brazilian numbers
        }

        if (value.length > 2 && value.length <= 7) {
            value = `(${value.substring(0, 2)}) ${value.substring(2)}`;
        } else if (value.length > 7) {
            value = `(${value.substring(0, 2)}) ${value.substring(2, 7)}-${value.substring(7)}`;
        } else if (value.length > 0) {
            value = `(${value}`; // Start with (
        }
        setPhone(value);
    };

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            alert('A senha e a confirmação de senha não coincidem!');
            return;
        }
        // Here you would typically send the form data to your API
        console.log({ name, email, phone, password });
        alert('Formulário enviado com sucesso! (Verifique o console para os dados)');
    };

    return (
        <div className="flex items-center justify-center p-4">
            <div className="bg-white p-8 rounded-lg shadow-md w-[50%]">
                <h2 className="text-[24px] font-extrabold text-center mb-6 uppercase">Crie uma conta</h2>
                <form onSubmit={handleSubmit} className="space-y-4">
                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="name" className="uppercase font-extrabold mr-4">Nome: </label>
                            <input
                                type="text"
                                id="name"
                                className="flex-grow border border-black p-2 focus:outline-none border-0"
                                value={name}
                                onChange={(e) => setName(e.target.value)}
                                required
                            />
                        </div>
                        <hr className='w-full text-black' />
                    </div>


                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="name" className="uppercase font-extrabold mr-4">Email: </label>
                            <input
                                type="email"
                                id="email"
                                className="flex-grow border border-black p-2 focus:outline-none border-0"
                                value={email}
                                onChange={(e) => setEmail(e.target.value)}
                                required
                            />
                        </div>
                        <hr className='w-full text-black' />
                    </div>

                    <div>
                        <div className="flex items-center w-full">
                            <label htmlFor="phone" className="uppercase font-extrabold mr-4">Telefone: </label>
                            <input
                                type="tel"
                                id="phone"
                                className="flex-grow border border-black p-2 focus:outline-none border-0"
                                value={phone}
                                onChange={handlePhoneChange}
                                placeholder="(XX) XXXXX-XXXX"
                                required
                            />
                        </div>
                        <hr className='w-full text-black' />
                    </div>

                    <div>
                        <label htmlFor="password" className="block text-sm font-medium text-gray-700">Senha: </label>
                        <div className="relative mt-1">
                            <input
                                type={showPassword ? 'text' : 'password'}
                                id="password"
                                className="flex-grow border border-black p-2 focus:outline-none border-0"
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                required
                            />
                            <button
                                type="button"
                                onClick={() => setShowPassword(!showPassword)}
                                className="absolute inset-y-0 right-0 pr-3 flex items-center text-sm leading-5"
                            >
                                {showPassword ? (
                                    <svg className="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.616-1.049A9.95 9.95 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.155 5.544L21 21" />
                                    </svg>
                                ) : (
                                    <svg className="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                                    </svg>
                                )}
                            </button>
                        </div>
                    </div>

                    <div>
                        <label htmlFor="confirmPassword" className="block text-sm font-medium text-gray-700">Confirmar Senha: </label>
                        <div className="relative mt-1">
                            <input
                                type={showConfirmPassword ? 'text' : 'password'}
                                id="confirmPassword"
                                className="block w-full px-3 py-2 pr-10 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
                                value={confirmPassword}
                                onChange={(e) => setConfirmPassword(e.target.value)}
                                required
                            />
                            <button
                                type="button"
                                onClick={() => setShowConfirmPassword(!showConfirmPassword)}
                                className="absolute inset-y-0 right-0 pr-3 flex items-center text-sm leading-5"
                            >
                                {showConfirmPassword ? (
                                    <svg className="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.543-7a9.97 9.97 0 011.563-3.029m5.616-1.049A9.95 9.95 0 0112 5c4.478 0 8.268 2.943 9.543 7a10.025 10.025 0 01-4.155 5.544L21 21" />
                                    </svg>
                                ) : (
                                    <svg className="h-5 w-5 text-gray-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                                    </svg>
                                )}
                            </button>
                        </div>
                    </div>
                    <button
                        type="submit"
                        className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-indigo-600 hover:bg-indigo-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500"
                    >
                        Cadastrar
                    </button>
                </form>
            </div>
        </div>
    );
};

export default RegisterForm;
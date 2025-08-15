'use client';

import Swal from 'sweetalert2';
import React, { useState } from "react";
import api from "@/services/api";
import { useRouter } from "next/navigation";
import Link from "next/link";

const Login: React.FC = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [mostrarSenha, setMostrarSenha] = useState(false);

  const router = useRouter();
  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    try {
      const response = await api.post('/auth/login', { email, senha });
      const token = response.data.token;

      localStorage.setItem('jwt_token', token);

      router.push('/');
    } catch (error) {
      Swal.fire({
        icon: 'error',
        title: 'Erro ao fazer login',
        text: 'Verifique suas credenciais e tente novamente.',
      });
      console.error("Falha no login:", error);
    }
  };

  return (
    <div
      className="min--dvh flex items-center justify-center bg-cover bg-center px-4 sm:px-6 md:px-8 lg:px-10 xl:px-20 py-8"
    >
      <div className="bg-white shadow-xl rounded-2xl p-6 sm:p-8 w-full max-w-xs sm:max-w-md md:max-w-lg lg:max-w-xl xl:max-w-2xl border border-gray-200">


        <h2 className="text-sm sm:text-base md:text-lg lg:text-xl font-semibold mb-4 text-center">
          ACESSE SUA CONTA
        </h2>
        <div className="min--dvh flex  bg-black items-center justify-center bg-cover bg-center pc-4 sm:px-8 md:px-1 lg:px-1 xl:px-1 h-1 mb-8 "></div>
        <form onSubmit={handleSubmit} className="space-y-4">          
          <div>
            <label htmlFor="email" className="text-xs sm:text-sm font-bold block mb-2">USUÁRIO:</label>
            <input
              type="email"
              id="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
              className="w-full border-b-2 border-gray-300 p-2 focus:outline-none focus:border-[#492004] pr-10"
              placeholder="usuariotext@gmail.com"
            />
          </div>          
          <div>
            <label htmlFor="senha" className="text-xs sm:text-sm font-bold block mb-0">SENHA:</label>
            <div className="relative">
              <input
                type={mostrarSenha ? "text" : "password"}
                id="senha"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                required
                className="w-full border-b-2 border-gray-300 p-2 focus:outline-none focus:border-[#492004] pr-10"
                placeholder="********"
              />
              <span
                onClick={() => setMostrarSenha(!mostrarSenha)}
                className="absolute right-3 top-2.5 text-gray-500 cursor-pointer select-none"
              >
                {mostrarSenha ? "🙈" : "👁️"}
              </span>
            </div>
          </div>          
          <div className="text-right">
            <Link href="/support" className="text-xs text-blue-500 hover:underline">ESQUECI MINHA SENHA</Link>
          </div>          
          <button
            type="submit"
            className="w-full bg-gradient-to-r from-blue-500 to-red-500 text-white font-bold py-2 sm:py-3 md:py-4 text-sm sm:text-base md:text-lg rounded-md hover:opacity-90 transition"
          >
            ACESSAR
          </button>

          <div className="text-center mt-2">
            <Link href="/cadastre" className="text-xs text-blue-500 hover:underline">CRIE UMA CONTA</Link>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
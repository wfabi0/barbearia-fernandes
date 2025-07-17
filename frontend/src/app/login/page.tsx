'use client';

import React, { useState } from "react";

const Login: React.FC = () => {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [mostrarSenha, setMostrarSenha] = useState(false);

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log("Usuário:", email);
    console.log("Senha:", senha);
  };

  return (
   <div
 className="min--dvh flex items-center justify-center bg-cover bg-center px-4 sm:px-6 md:px-8 lg:px-10 xl:px-20 py-8"
  style={{ backgroundImage: "url('/images/background.jpg')" }}
>
      <div className="bg-white shadow-xl rounded-2xl p-6 sm:p-8 w-full max-w-xs sm:max-w-md md:max-w-lg lg:max-w-xl xl:max-w-2xl border border-gray-200">
        
  
        <h2 className="text-sm sm:text-base md:text-lg lg:text-xl font-semibold mb-4 text-center">
          ACESSE SUA CONTA
        </h2>
           <div  className="min--dvh flex  bg-black items-center justify-center bg-cover bg-center pc-4 sm:px-8 md:px-1 lg:px-1 xl:px-1 h-1 mb-8 "></div>
        {/* Formulário */}
        <form onSubmit={handleSubmit} className="space-y-4">
          
          {/* Email */}
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

          {/* Senha */}
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

          {/* Esqueci senha */}
          <div className="text-right">
            <a href="#" className="text-xs text-blue-500 hover:underline">ESQUECI MINHA SENHA</a>
          </div>

          {/* Botão */}
          <button
            type="submit"
            className="w-full bg-gradient-to-r from-blue-500 to-red-500 text-white font-bold py-2 sm:py-3 md:py-4 text-sm sm:text-base md:text-lg rounded-md hover:opacity-90 transition"
          >
            ACESSAR
          </button>

          {/* Criar conta */}
          <div className="text-center mt-2">
            <a href="#" className="text-xs text-blue-500 hover:underline">CRIE UMA CONTA</a>
          </div>
        </form>
      </div>
    </div>
  );
};

export default Login;
// src/components/NavBar.tsx

'use client';

import Link from 'next/link';
import Image from 'next/image';
import Logo from '../../public/logo-barbearia.png';
import { useState, useEffect, useRef } from 'react';
import { useAuth } from '@/context/AuthContext'; // 1. Importar o hook de autenticação

export default function NavBar() {
  const { isAuthenticated, user, logout } = useAuth();

  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setIsDropdownOpen(false);
      }
    }
    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, []);

  const handleLogout = () => {
    logout();
    setIsDropdownOpen(false);
  };

  return (
    <nav className="bg-transparent w-full py-3">
      <div className="mx-auto px-4">
        <div className="flex justify-between items-center relative my-5">
          <Link href="/" className="bg-white rounded-md px-2 left-1/2 transform -translate-x-1/2 z-10 flex justify-center items-center relative max-[480px]:w-[200px] max-[480px]:h-[75px] w-[350px] h-[125px]">
            <Image quality={100} priority={true} src={Logo} alt="Logo Barbearia Fernandes" className="object-cover" fill />
          </Link>
          <div className="flex-grow"></div>
          <div className="flex items-center space-x-4">
            {isAuthenticated && user ? (
              <div className="relative" ref={dropdownRef}>
                <button
                  onClick={() => setIsDropdownOpen(!isDropdownOpen)}
                  className="flex items-center text-gray-300 bg-white rounded-full p-2 hover:bg-gray-200 border-2 border-[#492004]"
                  title="Menu do usuário"
                >
                  <svg className="h-6 w-6 text-[#492004]" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                  </svg>
                </button>

                {isDropdownOpen && (
                  <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-20 border border-gray-200">
                    <span className="block px-4 pt-2 pb-1 text-xs text-gray-500">Bem-vindo(a)!</span>
                    
                    {/* Link condicional baseado no papel do usuário */}
                    {localStorage.getItem('userType') === 'admin' ? (
                      <Link href="/admin" className="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                        Área Barbeiro
                      </Link>
                    ) : (
                      <Link href="/scheduling" className="block w-full text-left px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                        Área Cliente
                      </Link>
                    )}
                    
                    <div className="border-t border-gray-100 my-1"></div>
                    
                    <button
                      onClick={handleLogout}
                      className="block w-full text-left px-4 py-2 text-sm text-red-600 hover:bg-red-50"
                    >
                      Logout
                    </button>
                  </div>
                )}
              </div>
            ) : (
              // Se NÃO ESTIVER LOGADO, mostra o botão de login
              <div className="hidden md:block">
                <Link
                  href="/login"
                  className="flex items-center text-gray-300 bg-white rounded-full p-2 hover:bg-gray-200 border-2 border-[#492004]"
                  title='Login'
                >
                  <svg className="h-6 w-6 text-[#492004]" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                  </svg>
                </Link>
              </div>
            )}
          </div>
        </div>
      </div>
      <hr className="h-[5px] bg-white text-white my-3 mx-[15%] border" />
    </nav>
  );
}
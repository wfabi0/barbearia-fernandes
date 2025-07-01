"use client";

import Link from 'next/link';
import Image from 'next/image';
import Logo from '../../../public/logo-barbearia.png';
import { useState, useRef, useEffect } from 'react';

export default function NavBar() {
    const [isMenuOpen, setIsMenuOpen] = useState(false);
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
    }, [dropdownRef]);

    return (
        <nav className="bg-transparent shadow-lg">
            <div className="mx-auto px-4">
                <div className="flex justify-between items-center relative my-5">
                    <Link
                        href="/"
                        className="bg-white rounded-md px-2 absolute left-1/2 transform -translate-x-1/2 z-10 flex justify-center items-center rounded relative w-[350px] h-[125px]"
                    >
                        <Image src={Logo} alt="Logo Barbearia Fernandes" className="object-cover" fill></Image>

                    </Link>

                    <div className="flex-grow"></div>
                    <div className="flex items-center space-x-4">
                        <div className="hidden md:block relative" ref={dropdownRef}>

                            <button
                                onClick={() => setIsDropdownOpen(!isDropdownOpen)}
                                className="flex items-center text-gray-300 bg-white rounded-full p-2 hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
                            >
                                <a href="#">
                                    <svg className="h-6 w-6 text-gray-800" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                                    </svg>
                                </a>
                            </button>

                            {isDropdownOpen && (
                                <div className="absolute right-0 mt-2 w-48 bg-white rounded-md shadow-lg py-1 z-20">
                                    <Link href="/area-cliente" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                        Área Cliente
                                    </Link>
                                    <Link href="/area-barbeiro" className="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">
                                        Área Barbeiro
                                    </Link>
                                </div>
                            )}
                        </div>
                        <div className="md:hidden flex items-center">
                            <button
                                onClick={() => setIsMenuOpen(!isMenuOpen)}
                                className="inline-flex items-center justify-center p-2 rounded-md text-gray-400 hover:text-white hover:bg-gray-700 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white"
                            >
                                <span className="sr-only">Abrir menu principal</span>
                                {isMenuOpen ? (
                                    <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M6 18L18 6M6 6l12 12" />
                                    </svg>
                                ) : (
                                    <svg className="h-6 w-6" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" aria-hidden="true">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M4 6h16M4 12h16m-7 6h7" />
                                    </svg>
                                )}
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            {isMenuOpen && (
                <div className="md:hidden" id="mobile-menu">
                    <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3">
                        <Link href="/area-cliente" className="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium text-center">
                            Área Cliente
                        </Link>
                        <Link href="/area-barbeiro" className="text-gray-300 hover:bg-gray-700 hover:text-white block px-3 py-2 rounded-md text-base font-medium text-center">
                            Área Barbeiro
                        </Link>
                    </div>
                </div>
            )}


            
            <hr className="h-[2px] text-white my-3 mx-[15%] border"/>
        </nav>
    );
}

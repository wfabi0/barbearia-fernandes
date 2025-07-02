"use client";

import Link from 'next/link';
import Image from 'next/image';
import Logo from '../../../public/logo-barbearia.png';
import {  useRef } from 'react';

export default function NavBar() {
    const dropdownRef = useRef<HTMLDivElement>(null);

    return (
        <nav className="bg-transparent shadow-lg">
            <div className="mx-auto px-4">
                <div className="flex justify-between items-center relative my-5">
                    <Link href="/" className="bg-white rounded-md px-2 left-1/2 transform -translate-x-1/2 z-10 flex justify-center items-center relative w-[350px] h-[125px]"                  >
                        <Image src={Logo} alt="Logo Barbearia Fernandes" className="object-cover" fill></Image>

                    </Link>
                    <div className="flex-grow"></div>
                    <div className="flex items-center space-x-4">
                        <div className="hidden md:block relative" ref={dropdownRef}>
                            <button className="flex items-center text-gray-300 bg-white rounded-full p-2 hover:bg-gray-200 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-white">
                                <Link href="/login">
                                    <svg className="h-6 w-6 text-gray-800" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                                        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth="2" d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                                    </svg>
                                </Link>
                            </button>
                        </div>
                    </div>
                </div>
            </div>
            <hr className="h-[5px] bg-white text-white my-3 mx-[15%] border" />
        </nav>
    );
}

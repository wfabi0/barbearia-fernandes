'use client';

import { Geist, Geist_Mono } from "next/font/google";
import NavBar from '../NavBar/NavBar';
import Footer from '../Footer/Footer';
import { useState, useEffect } from "react";

const geistSans = Geist({
  variable: "--font-geist-sans",
  subsets: ["latin"],
});

const geistMono = Geist_Mono({
  variable: "--font-geist-mono",
  subsets: ["latin"],
});


type UserType = "admin" | "client" | null;

export default function Body({children,}: Readonly<{ children: React.ReactNode;}>) {
    
    const [userType, setUserType] = useState<UserType>('client');

    useEffect(() => {
        if (typeof window !== 'undefined') {
            const savedUserType = localStorage.getItem('userType');
            if (savedUserType) {
                setUserType(savedUserType as UserType);
            }
        }
    }, []);
    return (
        <body
            className={`${geistSans.variable} ${geistMono.variable} antialiased`}
            style={
                userType === 'admin'
                    ? { backgroundColor: "E2E8F0" }
                    : { backgroundImage: "url('./bg-50.png')", backgroundSize: "cover" }
            }
        >
            <NavBar></NavBar>
            <main className="w-full">
                {children}
            </main>
            <Footer></Footer>
        </body>
    );
};

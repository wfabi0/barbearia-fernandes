
'use client';

import { useState, useEffect } from "react";
import { AuthProvider } from "@/context/AuthContext";

type UserType = "admin" | "client" | null;

export default function StyleController({ children }: { children: React.ReactNode }) {
    const [userType, setUserType] = useState<UserType>(null);

    useEffect(() => {
        const savedUserType = localStorage.getItem('userType');
        if (savedUserType === 'admin' || savedUserType === 'client') {
            setUserType(savedUserType);
        } else {
            setUserType('client');
        }
    }, []);

    useEffect(() => {

        if (userType === 'admin') {
            document.body.style.backgroundColor = "#E2E8F0";
        } else if (userType === 'client') {
            document.body.style.backgroundImage = "url('/bg-50.png')";
            //document.body.style.backgroundSize = "cover"; 
        }
    }, [userType]);

    return (
        <AuthProvider>
            {children}
        </AuthProvider>
    );
}
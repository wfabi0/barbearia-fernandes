"use client";

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/context/AuthContext';

export default function ProtectedLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const { isAuthenticated, isLoading } = useAuth();
  const router = useRouter();


  useEffect(() => {
    if (!isLoading && !isAuthenticated) {
      router.push('/login');
    }
  }, [isAuthenticated, isLoading, router]);

  
  if (isLoading) {
    return <p className="text-center p-10">Verificando sua sessão...</p>;
  }

  if (isAuthenticated) {
    if (typeof window !== 'undefined') {
      const userType = localStorage.getItem('userType');
      if (userType === 'admin') {
        router.push('/admin');
      } else if (userType === 'client') {
        router.push('/scheduling');
      }
    }
    return <>{children}</>;
  }

  return null;
}
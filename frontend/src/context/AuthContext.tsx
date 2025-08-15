'use client';

import { createContext, useState, useEffect, useContext, ReactNode, useCallback } from 'react';
import { useRouter } from 'next/navigation';
import api from '@/services/api'

interface User {
  id: number;
  name: string;
  email: string;
}

interface AuthContextType {
  isAuthenticated: boolean;
  user: User | null;
  isLoading: boolean;
  login: (token: string) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const router = useRouter();

  const logout = useCallback(() => {
    setUser(null);
    localStorage.removeItem('jwt_token');
    router.push('/login');
  }, [router]);

  useEffect(() => {
    const checkUser = async () => {
      const token = localStorage.getItem('jwt_token');
      if (token) {
        try {
          const response = await api.get('/users/me');
          if (response.data.perfil === 'BARBEIRO') {
            localStorage.setItem('userType', 'admin');
          }
          else {
            localStorage.setItem('userType', 'client');
          }
          setUser(response.data);
        } catch (error) {
          console.error("Token inválido, fazendo logout.");
          console.error(error);
          logout();
        }
      }
      setIsLoading(false);
    };
    checkUser();
  }, [logout]);

  const login = async (token: string) => {
    localStorage.setItem('jwt_token', token);
    try {
      const response = await api.get('/users/me');
      if (response.data.perfil === 'BARBEIRO') {
        localStorage.setItem('userType', 'admin');
      }
      else {
        localStorage.setItem('userType', 'client');
      }
      setUser(response.data);
      router.push('/');
    } catch (error) {
      console.error("Falha ao buscar dados do usuário após o login", error);
    }
  };

  const isAuthenticated = !!user;

  return (
    <AuthContext.Provider value={{ isAuthenticated, user, isLoading, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
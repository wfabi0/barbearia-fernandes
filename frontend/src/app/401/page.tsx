import Link from "next/link";

export default function Error401() {
    return (
        <div className="min-h-screen flex flex-col items-center justify-center">
            <div className="bg-white shadow-lg rounded-lg p-8 flex flex-col items-center">
            <svg className="w-16 h-16 text-red-500 mb-4" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24">
                <circle cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="2" />
                <line x1="9" y1="9" x2="15" y2="15" stroke="currentColor" strokeWidth="2" />
                <line x1="15" y1="9" x2="9" y2="15" stroke="currentColor" strokeWidth="2" />
            </svg>
            <h1 className="text-3xl font-bold text-gray-800 mb-2">Não autorizado</h1>
            <p className="text-gray-600 mb-6 text-center">Você não tem permissão para acessar esta página.</p>
            <Link
                href="/"
                className="inline-block px-6 py-2 bg-primary-600 text-white rounded hover:bg-primary-700 transition"
            >
                Voltar para o início
            </Link>
            </div>
        </div>
    )
};

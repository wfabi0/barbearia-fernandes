import Link from "next/link";

export default function Error401() {
    return (
        <div className="min-h-screen flex flex-col items-center justify-center bg-gradient-to-br from-primary-50 to-primary-200">
            <div className="bg-white shadow-2xl rounded-2xl p-10 flex flex-col items-center max-w-md w-full">
            <svg className="w-20 h-20 text-red-500 mb-6" fill="none" stroke="currentColor" strokeWidth="2" viewBox="0 0 24 24">
                <circle cx="12" cy="12" r="10" stroke="currentColor" strokeWidth="2" />
                <line x1="9" y1="9" x2="15" y2="15" stroke="currentColor" strokeWidth="2" />
                <line x1="15" y1="9" x2="9" y2="15" stroke="currentColor" strokeWidth="2" />
            </svg>
            <h1 className="text-4xl font-extrabold text-gray-800 mb-3 text-center">Suporte</h1>
            <p className="text-gray-600 mb-6 text-center">
                Entre em contato com a <span className="font-semibold text-primary-700">Equipe da OnebitJR</span> para obter suporte.
            </p>
            <a
                href="mailto:contato@onebitjr.com"
                className="mb-4 inline-block px-6 py-2 bg-yellow-300 text-black rounded-lg shadow hover:bg-yellow-400 hover:text-black transition font-medium"
            >
                Falar com a Equipe da OnebitJR
            </a>
            <Link
                href="/"
                className="inline-block px-6 py-2 border border-yellow-400 text-black rounded-lg hover:bg-yellow-200 hover:text-black transition font-medium"
            >
                Voltar para o início
            </Link>
            </div>
        </div>
    )
};

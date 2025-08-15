import React from 'react';
import { FaMapMarkerAlt, FaEnvelope, FaPhone, FaWhatsapp, FaGlobe } from 'react-icons/fa';

const Footer: React.FC = () => {
  const currentYear = new Date().getFullYear();

  return (
    <footer className="bg-gray-900 text-gray-300">
      <div className="container mx-auto px-6 py-12">
        <div className="grid grid-cols-1 md:grid-cols-3 gap-10 text-center md:text-left">          
          <div className="flex flex-col justify-center items-center md:items-start">
             <h3 className="font-bold text-lg text-white mb-4 text-center w-full">Barbearia Fernandes</h3>
             <p className="text-sm text-gray-400 mt-2 w-full text-center">Onde o estilo encontra a tradição.</p>
          </div>
          <div className="flex flex-col justify-center items-center">
            <h3 className="font-bold text-lg text-white mb-4 text-center w-full">Barbearia Fernandes</h3>
            <p className="text-sm mt-2 max-w-xs w-full text-center">
              Sua referência em cortes clássicos e modernos no Rio de Janeiro.
            </p>
          </div>          
          <div className="flex flex-col justify-center items-center">
             <h3 className="font-bold text-lg text-white mb-4 text-center w-full">Contato</h3>
             <ul className="space-y-3 text-sm w-4/6 text-center">
                <li className="flex items-center gap-3 justify-center md:justify-start">
                  <FaMapMarkerAlt className="text-yellow-500"/>
                  <a href="#" className="hover:text-yellow-400 transition-colors">Av. Rio Branco 123 - Centro, RJ</a>
                </li>
                <li className="flex items-center gap-3 justify-center md:justify-start">
                  <FaEnvelope className="text-yellow-500"/>
                  <a href="mailto:contato@barbeariafernandes.com.br" className="hover:text-yellow-400 transition-colors">contato@barbeariafernandes.com.br</a>
                </li>
                 <li className="flex items-center gap-3 justify-center md:justify-start">
                  <FaPhone className="text-yellow-500"/>
                  <a href="tel:2122390278" className="hover:text-yellow-400 transition-colors">(21) 2239-0278</a>
                </li>
                <li className="flex items-center gap-3 justify-center md:justify-start">
                  <FaWhatsapp className="text-yellow-500"/>
                  <a href="https://api.whatsapp.com/send?phone=5521971441290" target="_blank" rel="noopener noreferrer" className="hover:text-yellow-400 transition-colors">(21) 97144-1290</a>
                </li>
                 <li className="flex items-center gap-3 justify-center md:justify-start">
                  <FaGlobe className="text-yellow-500"/>
                  <a href="https://barbeariafernandes.com.br" target="_blank" rel="noopener noreferrer" className="hover:text-yellow-400 transition-colors">www.barbeariafernandes.com.br</a>
                </li>
             </ul>
          </div>
        </div>
        <div className="border-t border-gray-800 my-8"></div>        
        <div className="text-center text-sm text-gray-500 flex flex-col md:flex-row justify-between items-center gap-4">
           <p>&copy; {currentYear} Barbearia Fernandes. Todos os direitos reservados.</p>
           <div className="flex gap-6">
              <a href="/politica-privacidade" className="hover:text-yellow-400 transition-colors no-underline">Política de Privacidade</a>
              <a href="/termos-uso" className="hover:text-yellow-400 transition-colors no-underline">Termos de Uso</a>
           </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
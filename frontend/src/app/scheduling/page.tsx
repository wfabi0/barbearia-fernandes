'use client';
import React, { useState } from 'react';
import { format, addMonths, subMonths, getDaysInMonth, startOfMonth } from 'date-fns';
import { ptBR } from 'date-fns/locale';
import { FaFilter, FaPlus, FaPencilAlt, FaTimes } from 'react-icons/fa';

//================================================================//
// 1. DEFINIÇÕES DE TIPOS E DADOS MOCK (Dados de Exemplo)
//================================================================//
type Status = 'Agendado' | 'Concluído' | 'Cancelado';

interface HistoryItem {
  id: number;
  datetime: string;
  barber: string;
  service: string;
  status: Status;
  previousStatus?: Status;
}

const historyData: HistoryItem[] = [
  { id: 1, datetime: '2025-06-10 10:00', barber: 'José Fernandes', service: 'Corte Simples', status: 'Agendado' },
  { id: 2, datetime: '2025-06-10 10:00', barber: 'João Fernandes', service: 'Corte Simples', status: 'Concluído' },
  { id: 3, datetime: '2025-06-10 10:00', barber: 'João Fernandes', service: 'Corte Simples', status: 'Cancelado', previousStatus: 'Agendado' },
  { id: 4, datetime: '2025-06-10 10:00', barber: 'João Fernandes', service: 'Corte Simples', status: 'Concluído' },
];


//================================================================//
// 2. MINI-COMPONENTES LOCAIS (para organização do JSX)
//================================================================//
const StatusBadge: React.FC<{ status: Status }> = ({ status }) => {
  const baseClasses = "px-3 py-1 text-xs font-semibold rounded-full inline-block";
  const statusClasses: { [key in Status]: string } = {
    'Agendado': 'bg-blue-100 text-blue-800',
    'Concluído': 'bg-green-100 text-green-800',
    'Cancelado': 'bg-red-100 text-red-800',
  };
  return <span className={`${baseClasses} ${statusClasses[status]}`}>{status}</span>;
};

const ActionButtons: React.FC = () => (
  <div className="flex flex-col space-y-2">
    <button className="flex items-center justify-center gap-2 w-full text-sm bg-yellow-400 hover:bg-yellow-500 text-gray-800 font-semibold py-1 px-2 rounded-md transition-colors">
      <FaPlus /> Adicionar Comentário
    </button>
    <button className="flex items-center justify-center gap-2 w-full text-sm bg-blue-500 hover:bg-blue-600 text-white font-semibold py-1 px-2 rounded-md transition-colors">
      <FaPencilAlt /> Editar Agendamento
    </button>
    <button className="flex items-center justify-center gap-2 w-full text-sm bg-red-600 hover:bg-red-700 text-white font-semibold py-1 px-2 rounded-md transition-colors">
      <FaTimes /> Cancelar Agendamento
    </button>
  </div>
);


//================================================================//
// 3. COMPONENTE PRINCIPAL DA PÁGINA
//================================================================//
const Scheduling: React.FC = () => {
  const [currentDate, setCurrentDate] = useState(new Date(2024, 5, 1)); // June 2024
  const [selectedDay, setSelectedDay] = useState<number>(26);

  // Lógica do Calendário
  const firstDayOfMonth = startOfMonth(currentDate).getDay();
  const daysInMonth = getDaysInMonth(currentDate);
  const calendarDays = Array.from({ length: daysInMonth }, (_, i) => i + 1);
  const placeholderDays = Array.from({ length: firstDayOfMonth });
  
  return (
    <div className="min-h-screen bg-gray-800">
      <main className="p-4 md:p-8">
        <div className="bg-white p-6 rounded-xl shadow-2xl flex flex-col lg:flex-row gap-6">
          
          {/* ======================= COLUNA DA ESQUERDA (SIDEBAR) ======================= */}
          <aside className="w-full lg:w-1/4 space-y-4">
            <div className="bg-gray-100 p-4 rounded-lg">
              <h2 className="text-lg font-bold mb-2">Horário</h2>
              {/* Calendário Compacto */}
              <div>
                <div className="flex justify-between items-center mb-2">
                  <h3 className="font-bold capitalize">{format(currentDate, 'MMMM yyyy', { locale: ptBR })}</h3>
                  <div className="flex space-x-1">
                    <button onClick={() => setCurrentDate(subMonths(currentDate, 1))} className="text-lg font-bold">‹</button>
                    <button onClick={() => setCurrentDate(addMonths(currentDate, 1))} className="text-lg font-bold">›</button>
                  </div>
                </div>
                <div className="grid grid-cols-7 text-center text-xs text-gray-500">
                  {['SUN', 'MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT'].map(day => <div key={day}>{day}</div>)}
                </div>
                <div className="grid grid-cols-7 text-center mt-1">
                  {placeholderDays.map((_, i) => <div key={`placeholder-${i}`}></div>)}
                  {calendarDays.map(day => (
                    <div
                      key={day}
                      onClick={() => setSelectedDay(day)}
                      className={`p-1 rounded-full cursor-pointer ${selectedDay === day ? 'bg-blue-500 text-white' : 'hover:bg-gray-200'}`}
                    >
                      {day}
                    </div>
                  ))}
                </div>
              </div>
            </div>

            {/* Filtros de Agendamento */}
            <div className="space-y-2">
              <div>
                <label htmlFor="barber" className="text-sm font-medium text-gray-700">Barbeiro</label>
                <select id="barber" className="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:ring-brand-secondary focus:border-brand-secondary">
                  <option>Carlos Fernandes</option>
                  <option>João Fernandes</option>
                </select>
              </div>
              <div>
                <label htmlFor="service" className="text-sm font-medium text-gray-700">Serviço</label>
                <select id="service" className="mt-1 block w-full p-2 border border-gray-300 rounded-md shadow-sm focus:ring-brand-secondary focus:border-brand-secondary">
                  <option>Corte - R$ 30,00</option>
                  <option>Barba - R$ 25,00</option>
                  <option>Corte + Barba - R$ 50,00</option>
                </select>
              </div>
              <button className="w-full bg-gray-700 text-white font-bold py-2 px-4 rounded-md hover:bg-gray-800 transition-colors">
                Agendar/Salvar
              </button>
            </div>
          </aside>

          {/* ======================= CONTEÚDO PRINCIPAL (TABELA) ======================= */}
          <div className="flex-1">
            {/* Barra de Filtro e Pesquisa */}
            <div className="flex items-center gap-4 mb-4">
              <button className="flex items-center gap-2 p-2 border rounded-md bg-gray-50">
                <FaFilter className="text-gray-600" />
                <span className="font-semibold">Filtros</span>
              </button>
              <input 
                type="text" 
                placeholder="Pesquisar" 
                className="w-full p-2 border border-gray-300 rounded-md focus:ring-brand-secondary focus:border-brand-secondary"
              />
            </div>

            {/* Tabela de Histórico */}
            <div className="bg-white rounded-lg overflow-hidden border border-gray-200">
              <div className="bg-brand-secondary text-white font-bold p-3">
                Histórico de Agendamentos
              </div>
              <div className="overflow-x-auto">
                <table className="w-full text-sm text-left text-gray-700">
                  <thead className="bg-gray-50 text-xs text-gray-500 uppercase">
                    <tr>
                      <th scope="col" className="px-4 py-3">#</th>
                      <th scope="col" className="px-4 py-3">Horário</th>
                      <th scope="col" className="px-4 py-3">Barbeiro</th>
                      <th scope="col" className="px-4 py-3">Serviço</th>
                      <th scope="col" className="px-4 py-3">Situação</th>
                      <th scope="col" className="px-4 py-3">Ações</th>
                    </tr>
                  </thead>
                  <tbody>
                    {historyData.map((item) => (
                      <tr key={item.id} className="bg-white border-b hover:bg-gray-50">
                        <td className="px-4 py-4 font-medium">{item.id}</td>
                        <td className="px-4 py-4">{item.datetime}</td>
                        <td className="px-4 py-4">{item.barber}</td>
                        <td className="px-4 py-4">{item.service}</td>
                        <td className="px-4 py-4">
                          <div className="flex flex-wrap gap-2">
                            <StatusBadge status={item.status} />
                            {item.previousStatus && <StatusBadge status={item.previousStatus} />}
                          </div>
                        </td>
                        <td className="px-4 py-4">
                          <ActionButtons />
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>

        </div>
      </main>
    </div>
  );
};

export default Scheduling;
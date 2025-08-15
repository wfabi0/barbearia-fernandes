'use client';
import React from 'react';
import { FaFilter, FaClock, FaCheckCircle, FaTimesCircle } from 'react-icons/fa';
import { format } from 'date-fns';
import { ptBR } from 'date-fns/locale';

// 1. Definir o tipo para um agendamento
type AppointmentStatus = 'livre' | 'ocupado';

interface Appointment {
  time: string;
  status: AppointmentStatus;
  client?: string;      // Propriedade opcional
  service?: string;     // Propriedade opcional
  professional?: string;// Propriedade opcional
}

// 2. Definir a interface para as props do componente
interface ScheduleProps {
  selectedDate: Date;
}

// 3. Tipar o array de agendamentos
const appointments: Appointment[] = [
  { time: '08:00', status: 'livre' },
  { time: '09:00', status: 'livre' },
  { time: '10:00', status: 'livre' },
  {
    time: '13:00',
    status: 'ocupado',
    client: 'Cliente da Silva',
    service: 'Corte + Barba',
    professional: 'Fábio',
  },
];

// 4. Aplicar os tipos ao componente
const Schedule: React.FC<ScheduleProps> = ({ selectedDate }) => {
  return (
    <div className="bg-brand-light rounded-lg shadow-lg p-5 w-full max-w-md">
      <div className="flex items-center mb-5">
        <FaFilter className="text-gray-600" />
        <select defaultValue="fabio" className="ml-3 p-2 border border-gray-300 rounded-md w-full focus:ring-2 focus:ring-brand-secondary focus:outline-none">
          <option value="fabio">Fábio</option>
          <option value="bruno">Bruno</option>
          <option value="carlos">Carlos</option>
        </select>
      </div>

      <div className="bg-brand-secondary text-white p-4 rounded-lg font-bold mb-2 flex items-center gap-3">
        <FaClock />
        Horários Disponíveis
      </div>

      {/* 5. Data formatada dinamicamente */}
      <div className="bg-[#443737] text-white text-center py-2 rounded-md text-sm mb-5 capitalize">
        {format(selectedDate, "EEEE, dd 'de' MMMM", { locale: ptBR })}
      </div>

      <div className="space-y-3">
        {appointments.map((slot, index) => {
          const isBusy = slot.status === 'ocupado';
          return (
            <div
              key={index}
              className={`flex justify-between items-center p-4 rounded-md border
                ${isBusy ? 'bg-status-busy-bg border-red-300' : 'bg-status-free-bg border-green-300'}`
              }
            >
              <div>
                <div className="flex items-center gap-3 font-bold text-gray-800">
                  {isBusy ?
                    <FaTimesCircle className="text-status-busy" /> :
                    <FaCheckCircle className="text-status-free" />
                  }
                  {slot.time}
                </div>
                {isBusy && (
                  <div className="text-sm text-gray-600 mt-1 pl-1">
                    <p className="font-semibold">{slot.client}</p>
                    <p className="text-gray-500">{slot.service}</p>
                  </div>
                )}
              </div>
              {isBusy ? (
                <div className="bg-red-200 text-status-busy text-xs font-bold px-3 py-1 rounded-full">
                  {slot.professional}
                </div>
              ) : (
                <span className="font-bold text-status-free">Livre</span>
              )}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Schedule;
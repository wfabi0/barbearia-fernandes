'use client';
import React, { useState } from 'react';
import { 
  format, 
  addMonths, 
  subMonths, 
  startOfMonth, 
  endOfMonth, 
  startOfWeek, 
  endOfWeek, 
  eachDayOfInterval, 
  isSameMonth, 
  isToday 
} from 'date-fns';
import { ptBR } from 'date-fns/locale';

// 1. Definir a interface para as props do componente
interface CalendarProps {
  onDateSelect: (date: Date) => void;
}

const Calendar: React.FC<CalendarProps> = ({ onDateSelect }) => {
  // 2. Adicionar o tipo <Date> para o estado
  const [currentMonth, setCurrentMonth] = useState<Date>(new Date(2025, 0, 1)); // Jan 2025
  const [selectedDate, setSelectedDate] = useState<Date>(new Date(2025, 5, 15)); // Jun 15, 2025

  const nextMonth = (): void => setCurrentMonth(addMonths(currentMonth, 1));
  const prevMonth = (): void => setCurrentMonth(subMonths(currentMonth, 1));

  // 3. Tipar o parâmetro 'day' como Date
  const handleDateClick = (day: Date): void => {
    setSelectedDate(day);
    if (onDateSelect) {
      onDateSelect(day);
    }
  };

  const monthStart = startOfMonth(currentMonth);
  const monthEnd = endOfMonth(monthStart);
  const startDate = startOfWeek(monthStart);
  const endDate = endOfWeek(monthEnd);

  const days: Date[] = eachDayOfInterval({ start: startDate, end: endDate });
  const dayHeaders: string[] = ['Dom', 'Seg', 'Ter', 'Qua', 'Qui', 'Sex', 'Sáb'];

  return (
    <div className="bg-white rounded-lg shadow-lg p-5 w-full max-w-md">
      <div className="bg-brand-secondary text-white p-3 rounded-t-lg -m-5 mb-5 font-bold">
        📅 Grade de Horários
      </div>
      <div className="flex justify-between items-center mb-4">
        <button onClick={prevMonth} className="w-8 h-8 flex items-center justify-center rounded-full border border-gray-300 hover:bg-gray-100">
          ‹
        </button>
        <h2 className="text-lg font-bold capitalize">
          {format(currentMonth, 'MMMM yyyy', { locale: ptBR })}
        </h2>
        <button onClick={nextMonth} className="w-8 h-8 flex items-center justify-center rounded-full border border-gray-300 hover:bg-gray-100">
          ›
        </button>
      </div>
      <div className="grid grid-cols-7 gap-2 text-center">
        {dayHeaders.map(day => (
          <div key={day} className="font-bold text-gray-500 text-sm">{day}</div>
        ))}
        {days.map((day, i) => {
          const isCurrentMonth = isSameMonth(day, currentMonth);
          const isSelected = selectedDate && format(day, 'yyyy-MM-dd') === format(selectedDate, 'yyyy-MM-dd');

          return (
            <div
              key={i}
              className={`
                p-2 rounded-full cursor-pointer transition-colors duration-200
                ${isCurrentMonth ? 'text-gray-700' : 'text-gray-300'}
                ${!isSelected && isCurrentMonth ? 'hover:bg-gray-200' : ''}
                ${isSelected ? 'bg-brand-secondary text-white' : ''}
                ${isToday(day) && !isSelected ? 'border-2 border-blue-500' : ''}
              `}
              onClick={() => handleDateClick(day)}
            >
              {format(day, 'd')}
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Calendar;
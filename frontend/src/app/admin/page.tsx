'use client';
import { useState } from 'react';

import Calendar from '../../Components/Calendar';
import Schedule from '../../Components/Schedule';

export default function Scheduling() {
    const [selectedDate, setSelectedDate] = useState(new Date(2025, 5, 15));

  return (
    <div className="min-h-screen bg-brand-bg">    
      <main className="flex flex-col md:flex-row justify-center items-start gap-10 p-5 md:p-10">
        <Calendar onDateSelect={setSelectedDate} />
        <Schedule selectedDate={selectedDate} />
      </main>
    </div>
  );
};

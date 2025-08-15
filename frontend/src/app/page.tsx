'use client';

import React from 'react';
import Image from 'next/image';
import NavBar from "@/components/NavBar";
import Footer from '@/components/Footer';
import ServicesSection from '@/components/ServicesSection';

import aboutImage from '@/../public/aparando_barba.jpg'
import aboutImage2 from '@/../public/itens_barber.png';
import BackgroundImage from '@/../public/trabalhando.png';

const Home: React.FC = () => {
  return (
    <>
    <div className='bg-[#121212]'>
      <NavBar />
    </div>
    
      <main className="bg-[#0D0D0D] text-white">
        <section
          className="relative h-screen flex items-center justify-center text-center bg-cover bg-center"
          style={{ backgroundImage: `url(${BackgroundImage.src})`, backgroundSize: 'cover' }}
        >
          <div className="absolute inset-0 bg-black/70"></div>

          <div className="relative z-5 flex flex-col items-center gap-4 px-4">
            <h1 className="text-4xl md:text-6xl font-extrabold tracking-wider leading-tight">

            </h1>
            <p className="text-lg md:text-xl text-gray-300">
              Horário de funcionamento: 09:00 às 18:00
            </p>
            <button className="mt-4 bg-yellow-500 text-black font-bold py-3 px-8 rounded-lg text-lg hover:bg-yellow-600 transition-colors duration-300">
              Agendar horário
            </button>
          </div>
        </section>
        
        <ServicesSection />
        <section className="py-20 md:py-32 bg-[#121212]">
          <div className="container mx-auto px-6 grid grid-cols-1 md:grid-cols-2 items-center gap-12 lg:gap-20">
            <div className="relative h-96 flex items-center justify-center">
              <div className="absolute top-0 left-0 w-2/3 h-2/3">
                <Image
                  src={aboutImage2}
                  alt="Homem cortando cabelo"
                  layout="fill"
                  objectFit="cover"
                  className="rounded-lg shadow-2xl"
                />
              </div>
              <div className="absolute bottom-0 right-0 w-2/3 h-2/3">
                <Image
                  src={aboutImage}
                  alt="Barbeiro aparando a barba de cliente"
                  layout="fill"
                  objectFit="cover"
                  className="rounded-lg shadow-2xl border-4 border-[#121212]"
                />
              </div>
            </div>

            <div className="flex flex-col gap-4">
              <h2 className="text-4xl font-bold mb-4">Sobre</h2>
              <p className="text-gray-400 leading-relaxed">
                Lorem ipsum dolor sit amet consectetur. Sed vitae egestas risus in eleifend porttitor
                nam. Amet ut dictumst commodo aliquam eget augue et mi. Commodo sollicitudin
                ultrices ut nulla venenatis quam at.
              </p>
              <p className="text-gray-400 leading-relaxed">
                Lorem ipsum dolor sit amet consectetur. Sed vitae egestas risus in eleifend porttitor
                nam. Amet ut dictumst commodo aliquam eget augue et mi. Commodo sollicitudin
                ultrices ut nulla venenatis quam at.
              </p>
              <p className="font-semibold text-gray-200 mt-4">
                Horário de funcionamento 09: às 18:00
              </p>
            </div>
          </div>
        </section>
        
      </main>
      <Footer />
    </>

  );
};

export default Home;